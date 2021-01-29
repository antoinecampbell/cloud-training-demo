# Config
provider "kubernetes" {
  config_path = "~/.kube/config"
}

# Variables
variable "environment" {
  default = "minikube"
}
locals {
  namespace = kubernetes_namespace.cloud_training_demo.metadata[0].name
}

# Resources
resource "kubernetes_namespace" "cloud_training_demo" {
  metadata {
    name = "cloud-training-demo-${var.environment}"
  }
}

# Outputs
output "namespace" {
  value = local.namespace
}
