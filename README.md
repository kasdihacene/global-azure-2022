## End-to-end application monitoring with Azure App Insights :rocket:

![End-to-end_application_monitoring_with_Azure_App_Insights](assets/End-to-end_application_monitoring_with_Azure_App_Insights.jpeg)


The purpose of this repository is to show you, how do we monitor the Java applications deployed on Azure Kubernetes Service, using a feature of Azure Monitor that provides extensible application performance management (APM) and monitoring for live web apps. Developers and DevOps professionals can use Application Insights to:

✅ Automatically detect performance anomalies.

✅ Help diagnose issues by using powerful analytics tools.

✅ See what users actually do with apps.

✅ Help continuously improve app performance and usability.

Since **November 2020**, using Java SDK is no more needed to instrument java applications and to collect application 
telemetry. The solution recommended by Microsoft is an **auto-instrumentation** using 3.0 agent which will 
track and correlate the application.

### Technical stack :building_construction:

The stack used on this tutorial is :

- SpringBoot 2.6.x
- Java 17
- OpenAPI 3.0 — Contract-FIRST (Documenting the API)
- Terraform
- Kubernetes and Helm chart

### Installing dev tools 💼

For this workshop we need some tools to interact with the Azure account `az cli`, Kubernetes services (`kubectl`), maybe the tool for IaaC to provision a resources (`Terraform`)

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

4- You need also helm cli (If you want to deploy directly the charts without using terraform - helm_release resource)