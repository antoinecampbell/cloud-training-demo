# Variables
variable "discovery_replicas" {
  default = 1
}
variable "discovery_image" {
  default = "cloud-training-demo/discovery"
}
variable "discovery_image_tag" {
  default = "latest"
}
variable "discovery_min_ready_seconds" {
  default = 10
}
variable "revision_history_limit" {
  default = 10
}
variable "discovery_strategy_max_unavailable" {
  default = 1
}
variable "discovery_strategy_max_surge" {
  default = 1
}
variable "discovery_memory_request" {
  default = "768M"
}
variable "discovery_container_port" {
  default = 8761
}
locals {
  discovery_app = "discovery"
}

# Resources
resource "kubernetes_service" "discovery" {
  metadata {
    namespace = local.namespace
    name = local.discovery_app
    labels = {
      app = local.discovery_app
      environment = var.environment
      version = var.discovery_image_tag
    }
  }
  spec {
    selector = {
      app = local.discovery_app
    }
    type = "NodePort"
    port {
      port = var.discovery_container_port
      protocol = "TCP"
      #node_port = 30761  Omitted to allow dynamic port allocation
    }
  }
}

resource "kubernetes_deployment" "discovery" {
  depends_on = [
    kubernetes_service.discovery
  ]
  metadata {
    namespace = local.namespace
    name = "${local.discovery_app}-deployment"
    labels = {
      app = local.discovery_app
      environment = var.environment
      version = var.discovery_image_tag
    }
  }
  spec {
    min_ready_seconds = var.discovery_min_ready_seconds
    revision_history_limit = var.revision_history_limit
    strategy {
      type = "RollingUpdate"
      rolling_update {
        max_unavailable = var.discovery_strategy_max_unavailable
        max_surge = var.discovery_strategy_max_surge
      }
    }
    selector {
      match_labels = {
        app = local.discovery_app
      }
    }
    template {
      metadata {
        labels = {
          app = local.discovery_app
          environment = var.environment
          version = var.discovery_image_tag
        }
      }
      spec {
        container {
          name = local.discovery_app
          image = "${var.discovery_image}:${var.discovery_image_tag}"
          image_pull_policy = "IfNotPresent"
          resources {
            requests = {
              memory = var.discovery_memory_request
            }
          }
          port {
            container_port = var.discovery_container_port
          }
          env {
            name = "spring.profiles.active"
            value = "docker"
          }
        }
      }
    }
  }
}

# Outputs
output "discovery_service_node_port" {
  value = kubernetes_service.discovery.spec[0].port[0].node_port
}
