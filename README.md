# catalog-management
Microservice for managing music streaming catalog.

## What is this?
**TODO (later on)**

## Development setup
When first opening the project, IntelliJ might need some time to import the dependencies specified inside poms.
You will likely need to allow the IDE to import these dependencies (some kind of prompt should show up).

1. Click on "Add Configuration" in the upper right part of IntelliJ.
2. Add a new configuration (+) of type Application.
3. Set `com.kumuluz.ee.EeApplication` as the main class and set "Use classpath of module" to `api`.
4. Give the configuration a distinct name (or leave it as it is if you're a fan of not naming things) and save it.
5. Run the PostgreSQL Docker container  
```docker run -d --name catalog -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=catalog -p 5432:5432 postgres:latest```  
*If you are running Docker inside a VM, you might need to change the database IP in* `config.yaml`.
6. Run the configuration.

Open up [localhost:8080/servlet](localhost:8080/servlet) in your browser. You should see the text "IT WORKS!" on a plain "website".  
To test the REST API, send a GET request to `localhost:8080/v1/artist`.

## Documentation
The project uses OpenAPI3 (KumuluzEE OpenAPI3).

The documentation is available at [http://localhost:8080/api-specs/ui/](http://localhost:8080/api-specs/ui/).  
The "raw" version can be accessed at [localhost:8080/api-specs/v1/openapi.json](localhost:8080/api-specs/v1/openapi.json).