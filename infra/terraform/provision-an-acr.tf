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

resource "azurerm_resource_group" "rg_dev_fr_001" {
  name     = "rg-dev-fr-001"
  location = "francecentral"
}

## Provision an ACR
resource "azurerm_container_registry" "acr_dev_fr_001" {
  name                = "acrDevFr001"
  resource_group_name = azurerm_resource_group.rg_dev_fr_001.name
  location            = azurerm_resource_group.rg_dev_fr_001.location
  sku                 = "Standard"
  admin_enabled       = true
}
