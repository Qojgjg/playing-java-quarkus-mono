# Use mTLS from multiple client

```shell script
cd quarkus-client
./mvnw clean compile quarkus:dev
curl http://localhost:8082/client_2/client
curl http://localhost:8081/client_1/client
```
