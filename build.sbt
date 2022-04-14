name := "catsEffPlayGround"

version := "0.1"

scalaVersion := "2.13.8"

val KafkaVersion = "2.7.0"

libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.9" withSources() withJavadoc()

//libraryDependencies ++= Seq("com.banno" %% "kafka4s" % "4.0.4")

libraryDependencies ++= {
  Seq(
    "org.apache.kafka" %% "kafka" % "3.1.0",
    "org.apache.kafka" % "kafka-clients" % "3.1.0",
  )
}

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps"
)