# Basic example showing distributed tracing across Finatra apps
This is an example app where two Finatra (Scala) services collaborate on an http request. Notably, timing of these requests are recorded into [Zipkin](http://zipkin.io/), a distributed tracing system. This allows you to see the how long the whole operation took, as well how much time was spent in each service.

Here's an example of what it looks like
![zipkin screen shot](https://cloud.githubusercontent.com/assets/64215/22550169/d0c8557e-e989-11e6-94f6-1d745b66cb80.png)

# Implementation Overview

Web requests are served by [Finatra](https://github.com/twitter/finatra) controllers, which trace requests by default.

These traces are sent out of process with [Zipkin Finagle integration](https://github.com/openzipkin/zipkin-finagle) via Http.

This example intentionally avoids advanced topics like async and load balancing, eventhough Finatra supports them.

# Running the example
This example has two services: frontend and backend. They both report trace data to zipkin. To setup the demo, you need to start Frontend, Backend and Zipkin.

Once the services are started, open http://localhost:8080/
* This will call the backend (http://localhost:9000/api) and show the result, which defaults to a formatted date.

Next, you can view traces that went through the backend via http://localhost:9411/?serviceName=backend
* This is a locally run zipkin service which keeps traces in memory

## Starting the Services
In a separate tab or window, start each of [finatra.FrontendMain](/src/main/scala/finatra/FrontendServer.java) and [finatra.BackendMain](/src/main/scala/finatra/BackendServer.java):
```bash
$ sbt "run-main -Dzipkin.initialSampleRate=1.0 finatra.FrontendMain"
$ sbt "run-main finatra.BackendMain"
```

Next, run [Zipkin](http://zipkin.io/), which stores and queries traces reported by the above services.

```bash
wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
java -jar zipkin.jar
```

## Troubleshooting

Finagle includes a flag that dumps traces into the error log (or stderr in our example setup). This can be helpful
if you want to see which annotations are being sent to zipkin.

ex.
```bash
$ sbt "run-main -Dzipkin.initialSampleRate=1.0 -Dcom.twitter.finagle.tracing.debugTrace=true finatra.FrontendMain"
```
