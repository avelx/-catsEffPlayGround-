import sbtassembly.MergeStrategy

name := "catsEffPlayGround"

version := "0.1"

scalaVersion := "2.13.8"

val KafkaVersion = "2.7.0"

lazy val app = (project in file("app"))
  .settings(
    assembly / assemblyJarName := "cats_prom.jar"
  )

libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.9" withSources() withJavadoc()

libraryDependencies ++= {
  Seq(
    // Kafka
    "org.apache.kafka" %% "kafka" % "3.1.0",
    "org.apache.kafka" % "kafka-clients" % "3.1.0",

    // Kamon
    "io.kamon" %% "kamon-bundle" % "2.5.1",
    //"io.kamon" %% "kamon-apm-reporter" % "2.5.1",
    "io.kamon" %% "kamon-prometheus" % "2.5.1",
    "io.kamon" %% "kamon-akka" % "2.5.1"
  )
}

val defaultMergeStrategy: String => MergeStrategy = {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.deduplicate
    }
  case _ => MergeStrategy.deduplicate
}

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps"
)