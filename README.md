# Cloud Training Demo Commands

## Google Compute Engine
Start all services
```bash
docker-compose up -d
```
Scale note service
```bash
docker-compose scale note-service=2
```
Destroy all services
```bash
docker-compose down -v
```

## Google Kubernetes Engine (GKE)
Set default zone
```bash
gcloud config set compute/zone us-east1-b
```
List GKE clusters
```bash
gcloud container clusters list
```
Conntect to GKE cluster
```bash
gcloud container clusters get-credentials --zone=us-east1-b <cluster-name>
```
List nodes
```bash
kubectl get nodes
```
Navigate to the project kubernetes folder
```bash
cd cloud-training-demo/k8s/
```
Run Kubernetes deployments and services
```bash
kubectl apply -f ./ --record
```
View running pods
```bash
kubectl get pods
```
View services
```bash
kubectl get services
```
Expose UI deployment externally via a load balancer
```bash
kubectl expose deployment ui-deployment --type=LoadBalancer --name=ui-service-balanced
```
Delete load balanced UI service
```bash
kubectl delete service ui-service-balanced
```
All deployments and services
```bash
kubectl delete -f ./
```
