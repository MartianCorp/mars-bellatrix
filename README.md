# mars-bellatrix

One Paragraph of project description goes here

## Getting Started

[Runbook](https://confluence.dev.bbc.co.uk/display/RMServices/Services)

### Prerequisites

* [OpenJDK 8](https://openjdk.java.net/install/) 
* [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html)

### Installing

* Export a password protected p12 version of your BBC developer Certificate
* Add the following environment variable

```
export SBT_OPTS="-Djavax.net.ssl.trustStore=/etc/pki/jssecacerts -Djavax.net.ssl.keyStore=/path/to/your/bbc/developer/certificate.p12 -Djavax.net.ssl.keyStorePassword=<password> -Djavax.net.ssl.keyStoreType=PKCS12 -Djsse.enableSNIExtension=false"
```
then

```
sbt run
```

Service will be available here: [http://localhost:8080/status](http://localhost:8080/status)

For more information about setting up a dev environment, please see https://confluence.dev.bbc.co.uk/display/RMServices/New+starters+info

## Running the tests

```
sbt test # Unit tests
sbt it:test # Integration tests
sbt test-all # Run all
```

To get coverage reports run
```
sbt clean coverage test
sbt clean coverage it:test
```


### Coding style

[Scalafmt](https://scalameta.org/scalafmt/) will run on every compile.  IntelliJ configuration [here](https://scalameta.org/scalafmt/docs/installation.html#intellij)
Configuration in [build.sbt](build.sbt)


## Deployment



### Build
[Jenkins build job](https://ci.rms.tools.bbc.co.uk/job/mars-bellatrix/)
[Jenkins deploy job](https://ci.rms.tools.bbc.co.uk/job/mars-bellatrix-deploy/)
[Cosmos Project](https://cosmos.tools.bbc.co.uk/services/mars-bellatrix)
Live deployment is manual step in Cosmos UI

## Built With

* [sbt](https://www.scala-sbt.org/) - build tool
* [akka-http](https://doc.akka.io/docs/akka-http/current/) - Web Framework
* [Json4s](http://json4s.org/) - Json parsing and generation

## Contributing

Please read [Contributing to RMS](https://confluence.dev.bbc.co.uk/display/RMServices/Contributing+to+RMS) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [rms-protomen](https://github.com/bbc/rms-protomen) to automate our versioning and releases. For the versions available, see the [releases on this repository](https://github.com/bbc/mars-bellatrix/releases). 

## Authors

[Contributors](https://github.com/bbc/mars-bellatrix/contributors) who participated in this project.


