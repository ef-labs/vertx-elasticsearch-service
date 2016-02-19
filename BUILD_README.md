# Building vertx-elasticsearch-service

Quick install skipping integration tests

```
mvn install -DskipITs
```

Install with integration tests running elasticsearch 2.x on localhost:9300 (uses default profile localhost)

```
mvn install
```

Install with integration tests running elasticsearch 2.x on dockerhost:9300

```
mvn install -Pdockerhost
```
