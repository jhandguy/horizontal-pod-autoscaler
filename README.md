# Horizontal Pod Autoscaler

A sample project showcasing various Horizontal Pod Autoscaler implementations.

## Blog Posts

- [Horizontal Pod Autoscaler in Kubernetes (Part 1) — Simple Autoscaling using Metrics Server](https://jhandguy.github.io/posts/simple-horizontal-autoscaling/)
- [Horizontal Pod Autoscaler in Kubernetes (Part 2) — Advanced Autoscaling using Prometheus Adapter](https://jhandguy.github.io/posts/advanced-horizontal-autoscaling/)

## Installing

### Autoscaling Golang service using Metrics Server

```shell
kind create cluster --image kindest/node:v1.23.4 --config=kind/cluster.yaml

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install ingress-nginx/ingress-nginx --name-template ingress-nginx --create-namespace -n ingress-nginx --values kind/ingress-nginx-values.yaml --version 4.0.19 --wait

helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server
helm install metrics-server/metrics-server --name-template metrics-server --create-namespace -n metrics-server --values kind/metrics-server-values.yaml --version 3.8.2 --wait

helm install golang-sample-app/helm-chart --name-template sample-app --create-namespace -n sample-app --wait
```

### Autoscaling Kotlin service using Metrics Server

```shell
kind create cluster --image kindest/node:v1.23.4 --config=kind/cluster.yaml

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install ingress-nginx/ingress-nginx --name-template ingress-nginx --create-namespace -n ingress-nginx --values kind/ingress-nginx-values.yaml --version 4.0.19 --wait

helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server
helm install metrics-server/metrics-server --name-template metrics-server --create-namespace -n metrics-server --values kind/metrics-server-values.yaml --version 3.8.2 --wait

helm install kotlin-sample-app/helm-chart --name-template sample-app --create-namespace -n sample-app --wait
```

### Autoscaling Golang service using Prometheus Adapter

```shell
kind create cluster --image kindest/node:v1.23.4 --config=kind/cluster.yaml

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install ingress-nginx/ingress-nginx --name-template ingress-nginx --create-namespace -n ingress-nginx --values kind/ingress-nginx-values.yaml --version 4.0.19 --wait

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prometheus-community/kube-prometheus-stack --name-template prometheus --create-namespace -n prometheus --version 34.9.1 --wait
helm install prometheus-community/prometheus-adapter --name-template prometheus-adapter --create-namespace -n prometheus-adapter --values kind/prometheus-adapter-values.yaml --version 3.2.1 --wait

helm install golang-sample-app/helm-chart --name-template sample-app --create-namespace -n sample-app --set prometheus.enabled=true --wait
```

## Smoke Testing

```shell
curl localhost/success -H "Host: sample.app" -v
curl localhost/error -H "Host: sample.app" -v
```

## Load Testing

```shell
k6 run k6/script.js
```

## Uninstalling

```shell
kind delete cluster
```
