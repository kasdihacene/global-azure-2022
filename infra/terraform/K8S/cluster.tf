## -----------------------------------------------------------------------------##
# This script allows provisioning an Azure container registry for storing
# published docker images, for more details, refer to Microsoft documentation
#
## -----------------------------------------------------------------------------##
provider "azurerm" {
  tenant_id       = var.tenant
  subscription_id = var.subscription
  client_id       = var.client_id
  client_secret   = var.client_secret
  features {}
  #This is required for v2 of the provider even if empty or plan will fail
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

