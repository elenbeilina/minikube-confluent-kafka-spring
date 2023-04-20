### Minikube Confluent Kafka Spring.

#### Test scenario:

1. Build project:
    ```
    mvn clean package
    ```
2. Run local k8s cluster:
   ```
    minikube start
   ```
3. To point your shell to minikube’s docker-daemon, run:
   ```
    eval $(minikube -p minikube docker-env)
   ```
4. Build docker image with tag: minikube-confluent-kafka-spring:
   ```
   docker build -t aqua-len/minikube-confluent-kafka-spring .
   ```
5. To create a deployment run the command:
   ```
   kubectl apply -f k8s/kafka-configuration.yaml
   ```
6. In order to Services of type LoadBalancer to be exposed, [minikube tunnel](https://minikube.sigs.k8s.io/docs/handbook/accessing/#using-minikube-tunnel) needs to be running:
   ```
   minikube tunnel
   ```
7. View status of the Kafka cluster in [Control Center](http://localhost:9021/clusters). \
There should be data in the main topic.
---