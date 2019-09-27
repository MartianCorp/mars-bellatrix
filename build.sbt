import org.scalastyle.sbt.ScalastylePlugin
import scala.util.Properties

scalaVersion := "2.11.11"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-encoding",
  "utf8"
)

val applicationDependencies = {
  val akkaVersion               = "2.5.4"
  val akkaHttpVersion           = "10.0.10"
  val akkaHttpJson4sVersion     = "1.17.0"
  val json4sVersion             = "3.5.0"
  val jodaTimeVersion           = "2.9.4"
  val jodaConvertVersion        = "1.8"
  val logbackVersion            = "1.2.1"
  val slf4sVersion              = "1.7.12"
  val kamonVersion              = "0.6.7"
  val monitoringClientVersion   = "1.1.0"
  val specs2Version             = "3.8.9"
  val wireMockVersion           = "2.6.0"

  Seq(
    "com.typesafe.akka"      %% "akka-actor"        % akkaVersion,
    "com.typesafe.akka"      %% "akka-slf4j"        % akkaVersion,
    "com.typesafe.akka"      %% "akka-stream"       % akkaVersion,
    "com.typesafe.akka"      %% "akka-http"         % akkaHttpVersion,
    "de.heikoseeberger"      %% "akka-http-json4s"  % akkaHttpJson4sVersion,
    "org.json4s"             %% "json4s-jackson"    % json4sVersion,
    "org.json4s"             %% "json4s-ext"        % json4sVersion,
    "joda-time"              %  "joda-time"         % jodaTimeVersion,
    "org.joda"               %  "joda-convert"      % jodaConvertVersion,
    "ch.qos.logback"         %  "logback-classic"   % logbackVersion,
    "org.slf4s"              %% "slf4s-api"         % slf4sVersion,
    "io.kamon"               %% "kamon-core"        % kamonVersion,
    "io.kamon"               %% "kamon-statsd"      % kamonVersion,
    "io.kamon"               %% "kamon-akka-http"   % kamonVersion,
    "bbc.rms"                %% "monitoring-client" % monitoringClientVersion,
    "com.typesafe.akka"      %% "akka-http-testkit" % akkaHttpVersion % "test",
    "org.specs2"             %% "specs2-core"       % specs2Version   % "it,test",
    "org.specs2"             %% "specs2-mock"       % specs2Version   % "it,test",
    "com.github.tomakehurst" %  "wiremock"          % wireMockVersion % "it,test"
  )
}

val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := s"${name.value}.jar",
  assemblyMergeStrategy in assembly := ServiceAssembly.aspectjAopMergeStrategy
)

val speculateSettings = Seq(
  speculateServiceName := name.value,
  speculateVersion := Properties.envOrElse("BUILD_NUMBER", "dev"),
  speculateRequires := Seq(
    "java-1.8.0-openjdk",
    "rms-collectd",
    "rms-filebeat",
    "ibl-sysadmin",
    "rms-aspectj-weaver"),
  speculateBuildRequires := Seq("java-1.8.0-openjdk"),
  speculateJavaOpts := Seq(
    "-server",
    "-javaagent:/usr/lib/rms-aspectj-weaver/aspectjweaver-1.8.9.jar"
  )
)

val `test-all` = taskKey[Unit]("Run Unit tests, integration tests and scalastyle.")
val testSettings = IntegrationTests.settings ++ Seq(
  `test-all` in Compile := Def.sequential(
    test in Test,
    test in IntegrationTests.ItTest,
    (ScalastylePlugin.scalastyle in Compile).toTask("")
  ).value
)

lazy val `mars-bellatrix` = (project in file("."))
  .enablePlugins(SbtSpeculate)
  .configs(IntegrationTests.ItTest)
  .settings(
    organization := "bbc.rms",
    name := "mars-bellatrix",
    libraryDependencies ++= applicationDependencies,
    resolvers ++= Seq("BBC Artifactory" at "https://dev.bbc.co.uk/artifactory/repo/"),
    javaOptions in run ++= Seq(
      "-Dconfig.resource=application.dev.conf",
      "-Dlogback.configurationFile=logback.dev.xml"),
    fork in run := true
  )
  .settings(testSettings)
  .settings(assemblySettings)
  .settings(speculateSettings)
  .enablePlugins(SbtTrace).settings(Seq(traceUser := "bbc"))
