FROM eclipse-temurin:21-jre-alpine

COPY build/libs/kotlin-sample-app-all.jar /kotlin-sample-app.jar

ENTRYPOINT java $JAVA_OPTS \
	-jar /kotlin-sample-app.jar \
	-port=$HTTP_PORT \
	-P:kubernetes.node=$KUBERNETES_NODE \
	-P:kubernetes.namespace=$KUBERNETES_NAMESPACE \
	-P:kubernetes.pod=$KUBERNETES_POD

EXPOSE $HTTP_PORT
