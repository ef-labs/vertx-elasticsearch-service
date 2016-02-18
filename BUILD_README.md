# Building vertx-elasticsearch-service

Quick install skipping integration tests

```
mvn install -DskipITs
```

Install with integration tests running elasticsearch 2.x on localhost (uses default profile localhost)

```
mvn install
```

Install with integration tests running elasticsearch 2.x on dockerhost

```
mvn install -Pdockerhost
```
