package bbc.rms.marsbellatrix.config

import com.typesafe.config.{Config => AkkaConfig, ConfigFactory => AkkaConfigFactory}

trait Config {
  protected lazy val config: AkkaConfig = AkkaConfigFactory.load()

  lazy val serviceName: String = config.getString("service.name")

  lazy val httpInterface: String = config.getString("service.http.interface")
  lazy val httpPort: Int = config.getInt("service.http.port")

  lazy val defaultPageLimit: Int = config.getInt("service.pagination.default-limit")
  lazy val maximumPageLimit: Int = config.getInt("service.pagination.max-limit")

  lazy val itemSchemaUrl: String = config.getString("service.schema.item-response-url")
  lazy val errorSchemaUrl: String = config.getString("service.schema.error-response-url")

  lazy val errorDocumentationUrl: String = config.getString("service.error.documentation-url")
}
