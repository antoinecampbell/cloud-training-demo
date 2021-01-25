# Cloud Training Demo Commands

## Setup
1. Build docker images
    ```shell script
    ./gradlew dockerImage
    ```

## Minikube
1. Start minikube
    ```shell script
    minikube start --driver=virtualbox --cpus=4 --memory=4096MB
    ```
1. Set local docker context to minikube
    ```shell script
     eval $(minikube -p minikube docker-env)
    ```
1. Build all service docker images on minikube
    ```shell script
     ./gradlew dockerImage
    ```
1. Navigate to the project kubernetes folder
    ```shell script
    cd k8s/
    ```
1. Deploy all resources
    ```shell script
    kubectl apply -f ./ --record
    ``` 
1. Get minikube IP address
    ```shell script
    minikube ip
    ```
1. View the exposed ui-service node port
    ```shell script
    kubectl get service ui-service
    ```
1. View the UI app using the minikube IP and service NodePort
    - http://&lt;minibuke IP>:30080, e.g. http://192.168.99.102:30080
1. Delete all resources
    ```shell script
    kubectl delete -f ./
    ```

## Kustomize
1. Install kustomize
    ```shell script
    brew install kustomize
    ```
1. Deploy all resources, from the `k8s` directory
    ```shell script
    kustomize build . | kubectl apply -f -
    ```
1. Deploy patched resources, from the `kustomize/dev` directory
    ```shell script
    kustomize build . | kubectl apply -f -
    ```
1. Delete all resources, from the `k8s` directory
    ```shell script
    kustomize build . | kubectl delete -f -
    ```

## Docker Compose
1. Start all services
    ```shell script
    docker-compose up -d
    ```
1. Scale note service
    ```shell script
    docker-compose scale note-service=2
    ```
1. Destroy all resources
    ```shell script
    docker-compose down -v
    ```
    
### View Applications
- Note Web UI: http://localhost
- Discovery Service UI: http://localhost:8761
- PGAdmin: http://localhost:8000
- Portainer: http://localhost:9000

## Apache Jmeter
```shell script
jmeter -n -t test-plan.jmx -Jhostname=<hostname or IP>
```

## Google Kubernetes Engine (GKE)
1. Set default zone
    ```shell script
    gcloud config set compute/zone us-east1-b
    ```
1. List GKE clusters
    ```shell script
    gcloud container clusters list
    ```
1. Conntect to GKE cluster
    ```shell script
    gcloud container clusters get-credentials --zone=us-east1-b <cluster-name>
    ```
1. List nodes
    ```shell script
    kubectl get nodes
    ```
1. Navigate to the project kubernetes folder
    ```shell script
    cd k8s/
    ```
1. Deploy all resources
    ```shell script
    kubectl apply -f ./ --record
    ```
1. View running pods
    ```shell script
    kubectl get pods
    ```
1. View services
    ```shell script
    kubectl get services
    ```
1. Expose UI deployment externally via a load balancer
    ```shell script
    kubectl expose deployment ui-deployment --type=LoadBalancer --name=ui-service-balanced
    ```
1. View Horizontal Pod Autoscalers
    ```shell script
    kubectl get hpa
    ```
1. Delete load balanced UI service
    ```shell script
    kubectl delete service ui-service-balanced
    ```
1. All deployments and services
    ```shell script
    kubectl delete -f ./
    ```
