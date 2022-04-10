## Deploy a SpringBoot application to Azure Kubernetes Services

### Application context (Social network)

On this application, we expose an API for our social network. Firstly, we will expose an endpoint for fetching all
published userPosts.

    The provider to use is: https://jsonplaceholder.typicode.com

We consume the endpoint `/userPosts`, below the structure of a post :

````json
{
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum..."
  }
````

### Technical stack

The stack used on this tutorial is :

- SpringBoot 2.6.x
- Java 17
- OpenAPI 3.0 — Contract-FIRST (Documenting the API)
- Junit `JUPITER` for unit testing
- Hexagonal architecture (Ports & use cases)

### Installing dev tools

For this workshop we need some tools to interact with the Azure account `az cli`, Kubernetes services (`kubectl`), maybe
the tool for IaaC to provision a resources (`Terraform`)

1- Azure Cli:

    Windows: https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-windows?tabs=azure-cli
    Linux: https://docs.microsoft.com/fr-fr/cli/azure/install-azure-cli-linux?pivots=apt
    Macos: https://docs.microsoft.com/fr-fr/cli/azure/install-azure-cli-macos

Check : $ az -v or az --version

2- kubectl (not kubelet) :

    Windows: https://kubernetes.io/fr/docs/tasks/tools/install-kubectl/#installer-kubectl-sur-windows
    Linux: https://kubernetes.io/fr/docs/tasks/tools/install-kubectl/#installer-kubectl-sur-linux
    Macos: https://kubernetes.io/fr/docs/tasks/tools/install-kubectl/#installer-avec-homebrew-sur-macos

Check : $ kubectl version

3- terraform

    All environments: https://www.terraform.io/downloads.html

Check : $ terraform -v or terraform --version