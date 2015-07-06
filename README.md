# A demo application of Spark with Dagger and AngularJS
The goal of this application is to show how common use-cases can be implemented with Spark:

- public web-service,
- web-services with restricted access (authentication required),
- CRUD.

This application use-case implementations are really simple ; be aware that in order to remain simple, some security issues were not addressed (encryption...).

## I want to see it running
Just launch the ```org.devteam.App``` class and open http://localhost:4567/ in your browser.

If it is not already the case, you will also have to configure the annotation processor in your IDE : https://immutables.github.io/apt.html

## How does this works ?
1. The ```App``` create an instance of the dependency graph (see [Dagger 2] (http://google.github.io/dagger/))
2. The ```Router``` initialize all web application routes and starts the server
3. The server is up and running on port 4567

## Action composition
The great thing with Spark and Java 8 is the ability the compose actions very easily:

1. Let's consider ```get("/user", userWs::list)``` : the action ```userWs::list``` which list all the users is mapped to the path "/user"
2. Want to produce JSON ? No problem: ```get("/user", jsonFilter.jsonResponse(userWs::list))```: ```jsonFilter.jsonResponse``` is a simple function that takes a Route as parameter and returns a Route ; the returned Route just serializes the result of the Route taken as parameter in JSON
3. Want to restrict access to the web-service ? ```get("/user", jsonFilter.jsonResponse(authenticationFilter.authenticate(userWs::list)))``` : the authenticationFilter works the same as the jsonFilter !
4. Almost all your actions are using JSON and authentication ? Factorize it all: ```Route authenticatedAndJsonResponse(Route route) { return authenticationFilter.authenticate(jsonFilter.jsonResponse(route)); }```

Even tough before and after filters are proposed by the framework, action composition enables the application to have a lot more flexibility: you can apply a filter to only certain actions. Moreover, it is easier to debug actions composition since you know exactly what you are doing.

## Spark review
### Strengths
- Spark launch fast ~ about 50ms
- Spark *is* fast : the overhead on top of Jetty is very small, Spark action execution is almost as fast as raw servlet
- Spark is simple : no reflection, no annotation, small library ; you will not encounter a lot of bad surprises using Spark

### Weaknesses
- Swagger cannot be easily integrated, see [issue #258](https://github.com/perwendel/spark/issues/258)
- The current HTTP API is blocking: if you want to implement long polling or WebSocket, you will have a hard time
- There is no instance API to configure Spark server, only the static API is available :(, see [pull request #167](https://github.com/perwendel/spark/pull/167)

### Typical use cases
- Microservices architecture
- Small web-application (small but fast!)

### Alternative
Since the version 2.4, Play Framework provides a way to [embed the server in a simple Java application](https://www.playframework.com/documentation/2.4.x/JavaEmbeddingPlay):
```Java
Server server = Server.forRouter(new RoutingDsl()
    .GET("/hello/:to").routeTo(to ->
        ok("Hello " + to)
    )
    .build()
);
```
This behavior enables to use Play with Maven (or Gradle/SBT...) in a similar way you would use Spark.
That makes Play a very serious competitor to Spark since its API is non-blocking and it provides an instance API to configure the server.
I plan to port this demo application to Play Framework to see how it compares to Spark :)

## Going further
A real application will likely need other functionalities.

### Configuration
to provide a configuration functionality to a Spark application, a good choice would be the [config library](https://github.com/typesafehub/config).

### Web-service client
[OkHttp](http://square.github.io/okhttp/) and [Retrofit](http://square.github.io/retrofit/) target especially Android, however they also work great in any Java application.

### Database querying
A good alternative to JPA is [jOOQ](https://github.com/jOOQ/jOOQ) which enables to build type safe SQL queries.
As a connection pool [HikariCP](https://github.com/brettwooldridge/HikariCP) seems to be the better at the moment.