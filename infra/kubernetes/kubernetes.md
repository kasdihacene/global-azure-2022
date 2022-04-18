### Azure Kubernetes Services (AKS) :wheel_of_dharma:

### Lab aim :rocket:

    Deploy a container on K8S pods and expose the service publicly

We start by connecting to the Cluster, using `kubectl cli` , using the `az aks get-credentials command.

````shell
> az aks get-credentials --resource-group <RESOURCE-GROUP> --name <AZURE-CLUSTER-NAME>
````
#### :red_circle:  Main commands

:white_check_mark:  List all Nodes
````shell
> kubectl get nodes -A
````
:white_check_mark:  List all pods (-A option returns all pods of all namespaces)
````shell
> kubectl get pods -A
````

### :red_circle:  Kubectl in action

:white_check_mark:  Deploy a single container within a pod, complete the template on this project `/infra/kubernetes/pod-deployment.yaml` 

> kubectl create -f application-pod.yaml

:white_check_mark:  Destroy a created deployment file

> kubectl delete -f application-pod.yaml

:white_check_mark:  Update a deployment

> kubectl apply -f application-pod.yaml

:white_check_mark:  List all pods and watch the status of the deployment, if an error occurs, you can display the description of the deployment using the command :

> kubectl describe pod <POD-NAME> 

:white_check_mark:  Get logs of the running pod

> kubectl logs <POD-NAME>

:white_check_mark:  Get a Shell to a Running Container

>  kubectl exec -it labo21-app -- /bin/bash

:white_check_mark:  List all pods and its nodes

> kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name --all-namespaces
> kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name -n <APP-NAMESPACE>

:white_check_mark:  Get more information about a running pod

> kubectl get pod <POD-NAME> -o wide

:white_check_mark:  Port forward

> kubectl port-forward <pod-name> <local-port>:<node-port>

:white_check_mark:  List node consumption

> kubectl top node


#### :red_circle:  Integrate an existing ACR with existing AKS clusters

Integrate an existing ACR with existing AKS clusters by supplying valid values for acr-name as below.

````shell
az aks update -n <AZURE-CLUSTER-NAME> -g <RESOURCE-GROUP> --attach-acr <acr-name>
````

#### :red_circle:  Publishing Services (ServiceTypes)

`kube-proxy` is responsible for implementing a form of virtual IP for Services of type other than ExternalName.

You can also use Ingress to expose your Service. Ingress is not a Service type, but it acts as the entry point for your cluster. 
It lets you consolidate your routing rules into a single resource as it can expose multiple services under the same IP address.

### Documentation :books:

:building_construction: [Some help - kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)

:building_construction: [Some help - Initialize a container in a POD](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/#init-containers-in-use)

:building_construction: [Some help - Publishing Kubernetes services](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types)