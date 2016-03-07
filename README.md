[![Build Status](https://travis-ci.org/miguelsaddress/akkademy-db.svg?branch=master)](https://travis-ci.org/miguelsaddress/akkademy-db.svg?branch=master)

# akkademy-db
Distributed Key-Value Store made with Akka, following ["Learning Akka" Book from Packtub](https://www.packtpub.com/application-development/learning-akka) by [Jason Goodwin](https://github.com/jasongoodwin)



This repo will build the distributed key-value store afore mentioned.
You can find the scala client here:
*  [AkkademyDb Scala Client](https://github.com/miguelsaddress/akkademy-db-client)

If you were to build your own client, for example in Java, bear in mind you need to include the messages repo as a dependency
* [AkkademyDb Messages] (https://github.com/miguelsaddress/akkademy-db-messages)

For example, add to your `sbt` file:

```scala
lazy val root = Project("root", file(".")) dependsOn(akkademyMessages)
lazy val akkademyMessages = 
    ProjectRef(uri("https://github.com/miguelsaddress/akkademy-db-messages.git"), "akkademy-db-messages")

```
