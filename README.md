rebound-client-java
===================
A convenience library for working with rebound in the jvm.

[![Build Status](https://travis-ci.org/pileworx/rebound-client-java.svg?branch=develop)](https://travis-ci.org/pileworx/rebound)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b18750ca0df941d7beb0726487c83b39)](https://www.codacy.com/app/marcuslange/rebound-client-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pileworx/rebound-client-java&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b18750ca0df941d7beb0726487c83b39)](https://www.codacy.com/app/marcuslange/rebound-client-java?utm_source=github.com&utm_medium=referral&utm_content=pileworx/rebound-client-java&utm_campaign=Badge_Coverage)

Please visit [rebound](https://github.com/pileworx/rebound) for details on the rebound mock server and it's API.

Usage
-----

Include the dependency in your build:
```xml
<dependency>
  <groupId>io.pileworx</groupId>
  <artifactId>rebound-client-java</artifactId>
  <version>0.2.0</version>
</dependency>
```
API
---

Create an instance of the client

```java
String reboundHost = "http://localhost:8585";
ReboundRestClient client = ReboundRestClient.create(reboundHost);
```

Mocking
-------

Defining a mock:

```java
Mock mock = new DefineMock().with(m -> {
            m.scenario = "Test Scenario";
            m.when = new DefineRequest().with(req -> {
                req.method = Method.GET;
                req.path = "/foo";
                req.query = "foo=bar&bar=baz";
                req.headers = Header.of​("Accept", "application/hal+json");
                req.body = "{\"foo\":\"bar\"}";
            }).build();
            m.then = List.of(
                    new DefineResponse().with(resp1 -> {
                        resp1.status = 200;
                        resp1.headers = Header.of​("Content-Type", "application/json");
                        resp1.body = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
                    }).build(),
                    new DefineResponse().with(resp2 -> {
                        resp2.status = 200;
                        resp2.headers = Header.of​("Content-Type", "application/json");
                        resp2.bodyFromFile = Paths.get("./src/test/java/io/pileworx/rebound/client/velocity.vm");
                        resp2.values = Map.of("myValue", "this is my value");
                    }).build()
            );
        }).build();
```
If one response is supplied, it will be returned on every matching request. If a list is supplied, they will return in order. After the last response is returned following requests will result in error.
Response Body support velocity templates. They can be defined inline or from a vm file. For more info please see [Velocity](http://people.apache.org/~henning/velocity/html/ch02s02.html). For more info on each of the properties, please see the rebound documentation at [rebound](https://github.com/pileworx/rebound).

Then you can add the mock to the server:

```java
Status status = client.createMock(mock);
```

Teardown
--------

In your teardown, you can reset the mock server like this.

```java
Status status = client.clearMocks();
```
