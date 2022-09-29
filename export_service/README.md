## About The Project

<div>    
  <img src="out/src/plantuml/exportLIS_simple/exportLIS_simple.png" a>
</div>

### Built With

* Quarkus
* HAPI
* Docker
* Wiremock
* Maven
  ```

### Installation

1. Up mockup services
   ```
   docker-compose -f src/main/docker/wiremock.yml up
   ```
2. debug ExportService
   ```
    mvn quarkus:dev
   ```


## Usage

Invoke a GET Request to following endpoint for Map one laborder

```
http://localhost:8080/exportservice/message/laborder/1.18272
   ```

   ```
http://localhost:8080/exportservice/message/laborder/2.4068387
   ```


<div>    
  <img src="docs/invocation.png" a>
</div>

Invoke a GET Request to following endpoint for see mock data

```
http://localhost:8080/exportservice/laborder/mock/2.4068387
```



