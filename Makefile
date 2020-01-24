
.PHONY: run_client
## run_client: Starts client application
run_client:
	@mvn -f pom.client.xml javafx:compile && \
	mvn -f pom.client.xml javafx:run

.PHONY: run_server
## run_server: Starts server application
run_server:
	@mvn -f pom.xml javafx:compile && \
	mvn -f pom.xml javafx:run
