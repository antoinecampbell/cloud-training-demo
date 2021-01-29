# Cloud Training Demo Commands

## Setup
1. Build docker images
    ```bash
    ./gradlew dockerImage
    ```

## Minikube
1. Start minikube
    ```bash
    minikube start --driver=virtualbox --cpus=4 --memory=4096MB
    ```
1. Set local docker context to minikube
    ```bash
     eval $(minikube -p minikube docker-env)
    ```
1. Build all service docker images on minikube
    ```bash
     ./gradlew dockerImage
    ```
1. Navigate to the project kubernetes folder
    ```bash
    cd k8s/
    ```
1. Deploy all resources
    ```bash
    kubectl apply -f ./ --record
    ``` 
1. Get minikube IP address
    ```bash
    minikube ip
    ```
1. View the exposed ui-service node port
    ```bash
    kubectl get service ui-service
    ```
1. View the UI app using the minikube IP and service NodePort
    - http://&lt;minibuke IP>:30080, e.g. http://192.168.99.102:30080
1. Delete all resources
    ```bash
    kubectl delete -f ./
    ```

## Minikube - Terraform
1. Start minikube
    ```bash
    minikube start --driver=virtualbox --cpus=4 --memory=4096MB
    ```
1. Set local docker context to minikube
    ```bash
     eval $(minikube -p minikube docker-env)
    ```
1. Build all service docker images on minikube
    ```bash
     ./gradlew dockerImage
    ```
1. Navigate to the project Terraform workloads folder
    ```bash
    cd terraform/workloads
    ```
1. Initialize Terraform
     ```bash
    terraform init
    ```
1. Create Terraform resources and set variables
     ```bash
    terraform apply
    ```
1. View created pods in new namespace
     ```bash
    kubectl get pods -n cloud-training-demo-minikube
    ```
1. Destroy Terraform resources
     ```bash
    terraform destroy -var environment=<environment name>
    ```

## Kustomize
1. Install kustomize
    ```bash
    brew install kustomize
    ```
1. Deploy all resources, from the `k8s` directory
    ```bash
    kustomize build . | kubectl apply -f -
    ```
1. Deploy patched resources, from the `kustomize/dev` directory
    ```bash
    kustomize build . | kubectl apply -f -
    ```
1. Delete all resources, from the `k8s` directory
    ```bash
    kustomize build . | kubectl delete -f -
    ```

## Docker Compose
1. Start all services
    ```bash
    docker-compose up -d
    ```
1. Scale note service
    ```bash
    docker-compose scale note-service=2
    ```
1. Destroy all resources
    ```bash
    docker-compose down -v
    ```
    
### View Applications
- Note Web UI: http://localhost
- Discovery Service UI: http://localhost:8761
- PGAdmin: http://localhost:8000
- Portainer: http://localhost:9000

## Apache Jmeter
```bash
jmeter -n -t test-plan.jmx -Jhostname=<hostname or IP>
```

## Google Kubernetes Engine (GKE)
1. Set default zone
    ```bash
    gcloud config set compute/zone us-east1-b
    ```
1. List GKE clusters
    ```bash
    gcloud container clusters list
    ```
1. Conntect to GKE cluster
    ```bash
    gcloud container clusters get-credentials --zone=us-east1-b <cluster-name>
    ```
1. List nodes
    ```bash
    kubectl get nodes
    ```
1. Navigate to the project kubernetes folder
    ```bash
    cd k8s/
    ```
1. Deploy all resources
    ```bash
    kubectl apply -f ./ --record
    ```
1. View running pods
    ```bash
    kubectl get pods
    ```
1. View services
    ```bash
    kubectl get services
    ```
1. Expose UI deployment externally via a load balancer
    ```bash
    kubectl expose deployment ui-deployment --type=LoadBalancer --name=ui-service-balanced
    ```
1. View Horizontal Pod Autoscalers
    ```bash
    kubectl get hpa
    ```
1. Delete load balanced UI service
    ```bash
    kubectl delete service ui-service-balanced
    ```
1. All deployments and services
    ```bash
    kubectl delete -f ./
    ```
