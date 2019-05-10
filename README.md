rebound-client-java
===================
A convenience library for working with rebound in the jvm.

[![Build Status](https://travis-ci.org/pileworx/rebound-client-java.svg?branch=develop)](https://travis-ci.org/pileworx/rebound)

Please visit [rebound](https://github.com/pileworx/rebound) for details on the rebound mock server and it's API.

Usage
-----

Add maven repository to your build.
- [https://dl.bintray.com/pileworx/maven-release](https://dl.bintray.com/pileworx/maven-release)

Include the dependency in your build:
```xml
<dependency>
  <groupId>pileworx</groupId>
  <artifactId>rebound-client-java</artifactId>
  <version>0.1.0</version>
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
There are several way to create a mock. I'll cover two.

First with a velocity template file.
 ex MyTemplate.vm
 
```vtl
[
#foreach($i in [1..5])
  {
    "propertyName":"${myValue}"
  }#if($foreach.count != 5), #end
#end
]
```

Then you can add the mock to the server:

```java
String METHOD = "GET";
String PATH = "/foo";
int STATUS = 200;
String QUERY_STRING = "foo=bar&bar=baz";
String CONTENT_TYPE = "application/hal+json";
Path RESPONSE_BY_PATH = Paths.get("./src/test/java/io/pileworx/MyTemplate.vm");
Map<String, String> VALUES = new HashMap<>();

VALUES.put("myValue", "this is my value");

Status status = client.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING, RESPONSE_BY_PATH, VALUES);
```

The second is with a string literal (this can still be velocity template language), instead of an external file.

```java
String METHOD = "POST";
String PATH = "/foo";
int STATUS = 200;
String QUERY_STRING = "foo=bar&bar=baz";
String CONTENT_TYPE = "application/hal+json";
String RESPONSE_LITERAL = "[#foreach($i in [1..5]){\"propertyName\":\"${myValue}\"} #if($foreach.count != 5), #end #end]";
Map<String, String> VALUES = new HashMap<>();

VALUES.put("myValue", "this is my value");

Status status = client.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING, RESPONSE_LITERAL, VALUES);
```

In addition other createMock methods exist to send a more limited set of data in the mock.

Teardown
--------

In your teardown, you can reset the muck server like this.

```java
Status status = client.clearMocks();
```