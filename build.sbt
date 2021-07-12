lazy val mongoEventStream = (project in file("."))
  .settings(
    name := "mongoEventStream",
    version := "0.1",
    scalaVersion := "2.13.6",
    libraryDependencies ++= {
      val AkkaVersion = "2.6.14"
      Seq(
        "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "3.0.2",
        "com.typesafe.akka"  %% "akka-stream"                 % AkkaVersion,
        "ch.qos.logback"      % "logback-classic"             % "1.2.3"
      )
    }
  )
