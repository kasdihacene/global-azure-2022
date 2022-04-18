## Terraform Lab :pencil:

During this Lab, we will use terraform to manage our infrastructure as code and to perform software delivery more
efficiently.

### Lab aim :rocket:

    Provision an Azure Container Registry (ACR), for building, storing, 
    and managing container images and artifacts in a private registry.

### Initialize terraform environment

1- Provision an ACR (Azure Container Registry) from the portal :

we can do it from the `azure portal`, but if we want to reproduce several times this action, this will become very
tedious.

2- Provision an ACR using terraform with azure provider :

We will use terraform to provision the azure resources with a language called HCL (HashiCorp Configuration Language). It
reads configuration files and provides an execution plan of changes, which can be reviewed for safety and then applied
and provisioned.

- We need to export some environment variables before running terraform script, these Env Variables will be used to set
  the value of an input variable in the root module.

:
building_construction: [Some help - Set Env Variables](https://www.terraform.io/docs/configuration-0-11/variables.html#environment-variables)

```shell
export TF_VAR_tenant=c2a34640-51d2-436b-b7b0-f60df4236c85
export TF_VAR_subscription=092102b2-b0d8-4bef-b5d8-e7f32c36ef62
export TF_VAR_client_id=<CLIENT ID>
export TF_VAR_client_secret=<SECRET>
```

- Configure the Microsoft Azure Provider
  https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs


- Here the link to the template for provisioning an ACR (Azure Container Registry)
  https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/resources/container_registry

3- Naming convention for azure resources

Naming sample :

````yaml
resource group name : rg_dev_fr_001

rg : resource group
lacombe : application
hk : owner
dev : environment
fr : region (Europe France Central)
001 : number (random)
````

:
building_construction: [Some help - Naming convention](https://docs.microsoft.com/fr-fr/azure/cloud-adoption-framework/ready/azure-best-practices/resource-naming)

### Main commands:

    > terrafom init          Prepare your working directory for other commands

    > terrafom validate      Check whether the configuration is valid

    > terrafom plan          Show changes required by the current configuration

    > terrafom apply         Create or update infrastructure

    > terrafom destroy       Destroy previously-created infrastructure

See more commands here :point_right: https://www.terraform.io/docs/cli/commands/index.htmlfor safety and then applied
and provisioned.

- We need to export some environment variables before running terraform script, these Env Variables will be used to set
  the value of an input variable in the root module.

:
building_construction: [Some help - Set Env Variables](https://www.terraform.io/docs/configuration-0-11/variables.html#environment-variables)

```shell
export TF_VAR_tenant=c2a34640-51d2-436b-b7b0-f60df4236c85
export TF_VAR_subscription=092102b2-b0d8-4bef-b5d8-e7f32c36ef62
export TF_VAR_client_id=<CLIENT ID>
export TF_VAR_client_secret=<SECRET>
```

- Configure the Microsoft Azure Provider
  https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs


- Here the link to the template for provisioning an ACR (Azure Container Registry)
  https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/resources/container_registry

3- Naming convention for azure resources

Naming sample :

````yaml
resource group name : rg_dev_fr_001

rg : resource group
lacombe : application
hk : owner
dev : environment
fr : region (Europe France Central)
001 : number (random)
````

:
building_construction: [Some help - Naming convention](https://docs.microsoft.com/fr-fr/azure/cloud-adoption-framework/ready/azure-best-practices/resource-naming)

### Main commands:

    > terrafom init          Prepare your working directory for other commands

    > terrafom validate      Check whether the configuration is valid

    > terrafom plan          Show changes required by the current configuration

    > terrafom apply         Create or update infrastructure

    > terrafom destroy       Destroy previously-created infrastructure

See more commands here :point_right: https://www.terraform.io/docs/cli/commands/index.html