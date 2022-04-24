## -----------------------------------------------------------------------------##
# This script allows provisioning an Azure Kubernetes Service and
# deploying helm chart to run Java Application within a pod
## -----------------------------------------------------------------------------##
provider "azurerm" {
  tenant_id       = var.tenant
  subscription_id = var.subscription
  client_id       = var.client_id
  client_secret   = var.client_secret
  features {}
  #This is required for v2 of the provider even if empty or plan will fail
}
provider "helm" {
  kubernetes {
    config_path            = "~/.kube/config"
    host                   = azurerm_kubernetes_cluster.training_lacombe_aks.kube_config[0].host
    client_certificate     = base64decode(azurerm_kubernetes_cluster.training_lacombe_aks.kube_config.0.client_certificate)
    client_key             = base64decode(azurerm_kubernetes_cluster.training_lacombe_aks.kube_config.0.client_key)
    cluster_ca_certificate = base64decode(azurerm_kubernetes_cluster.training_lacombe_aks.kube_config.0.cluster_ca_certificate)
  }
}

## Authenticating using a Service Principal with a Client Secret

variable "tenant" {
  type = string
}
variable "subscription" {
  type = string
}
variable "client_id" {
  type = string
}
variable "client_secret" {
  type = string
}

variable "ssh_public_key" {
  default     = "~/.ssh/aks-sshkeys-terraform/akssshtrainingkey.pub"
  description = "This variable defines the SSH Public Key for Linux k8s Worker nodes generated on my local machine"
}

locals {
  location       = "francecentral"
  app_name       = "global-azure-app"
  resource_group = "rg-dev-fr-001"
  acr_name       = "acrDevFr001"

  # THE ACR IS CREATED MANUALLY, BETTER TO USE THE SAME FOR ALL ENVIRONMENTS
  container_registry_name = "acrathanordevfr001"

  environment_tag = "dev"
  application_tag = "globalazure"

}

## -----------------------------------------------------------------------------##
## Provision a k8s cluster and configuring authentication services
# using Service Principal
## -----------------------------------------------------------------------------##
resource "azurerm_kubernetes_cluster" "training_lacombe_aks" {
  name                = "training-lacombe-aks"
  location            = local.location
  resource_group_name = local.resource_group
  dns_prefix          = "training-lacombe"

  default_node_pool {
    name       = "default"
    node_count = 1
    vm_size    = "Standard_D15_v2"
  }

  linux_profile {
    admin_username = "ubuntu"

    ssh_key {
      key_data = file(var.ssh_public_key)
    }
  }

  service_principal {
    client_id     = var.client_id
    client_secret = var.client_secret
  }

  tags = {
    Environment = "DEV"
  }
}

## -----------------------------------------------------------------------------##
## A Chart is a Helm package. It contains all of the resource definitions necessary
## to run an application, tool, or service inside of a Kubernetes cluster.
## -----------------------------------------------------------------------------##
resource "helm_release" "cloud-native-application-backend" {
  depends_on = [azurerm_application_insights.app_insights_cloud_native]
  name       = "global-azure-2022-release"
  chart      = "./helm/global-azure-2022-chart"
  namespace  = "default"

  values = [
    file("./helm/global-azure-2022-chart/values.yaml")
  ]

  # Set instrumentation key as environment variable for running pod
  set_sensitive {
    name  = "APPLICATIONINSIGHTS_CONNECTION_STRING"
    value = "InstrumentationKey=`${azurerm_application_insights.app_insights_cloud_native.instrumentation_key}`"
  }
}

resource "azurerm_log_analytics_workspace" "workspace_log_analytics_001" {
  name                = "workspace-log-analytics-001"
  location            = local.location
  resource_group_name = local.resource_group
  sku                 = "PerGB2018"
  retention_in_days   = 30
  daily_quota_gb      = 5
}

resource "azurerm_application_insights" "app_insights_cloud_native" {
  depends_on          = [azurerm_kubernetes_cluster.training_lacombe_aks]
  name                = "app-insights-cloud-native"
  location            = local.location
  resource_group_name = local.resource_group
  workspace_id        = azurerm_log_analytics_workspace.workspace_log_analytics_001.id
  application_type    = "web"
}


resource "azurerm_monitor_action_group" "cloud_native_app_action_group" {
  name                = "CriticalAlertsActionCloudNativeApp"
  resource_group_name = local.resource_group
  short_name          = "cnaction"

  webhook_receiver {
    name                    = "Webhook Cloud Native Application"
    service_uri             = "https://lacombedulionvert.webhook.office.com/webhookb2/cfe00144-9280-4ec7-99d6-65ba7e7c7f30@141f2430-08e9-4892-879e-8545cf73c23b/IncomingWebhook/a7bca04fd2194b20ad01b56bf2db8b96/b4ba4dd2-f137-4f1e-98b5-439ee08d8a9c"
    use_common_alert_schema = true
  }
  email_receiver {
    name                    = "sendtoteams"
    email_address           = "7a3c2082.lacombedulionvert.fr@fr.teams.ms"
    use_common_alert_schema = true
  }
}

# Alerting Action with result count trigger
resource "azurerm_monitor_scheduled_query_rules_alert" "alert_on_5xx_errors" {
  name                = format("%s-queryrule", "http-error-5xx")
  location            = local.location
  resource_group_name = local.resource_group

  action {
    action_group  = [azurerm_monitor_action_group.cloud_native_app_action_group.id]
    email_subject = "Cloud Native Error 5xx"
  }
  data_source_id = azurerm_application_insights.app_insights_cloud_native.id
  description    = "Alert when total results cross threshold"
  enabled        = true
  # Count all requests with server error result code grouped into 5-minute bins
  query          = <<-QUERY
  requests
    | where tolong(resultCode) >= 500
    | summarize count() by bin(timestamp, 5m)
  QUERY
  severity       = 1
  frequency      = 5
  time_window    = 5
  trigger {
    operator  = "GreaterThan"
    threshold = 0
  }
}