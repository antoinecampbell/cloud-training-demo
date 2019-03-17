# Cloud Training Demo Commands

## Google Compute Engine
1. Start all services
    ```bash
    docker-compose up -d
    ```
1. Scale note service
    ```bash
    docker-compose scale note-service=2
    ```
1. Destroy all services
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
    cd cloud-training-demo/k8s/
    ```
1. Run Kubernetes deployments and services
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
