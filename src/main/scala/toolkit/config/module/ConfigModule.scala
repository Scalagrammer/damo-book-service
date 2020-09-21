package toolkit.config.module

import com.typesafe.config.{Config, ConfigFactory}

import ConfigFactory.{load => loadConfig}

trait ConfigModule {
  implicit def config : Config
}

trait ConfigModuleImpl extends ConfigModule {
  override implicit lazy val config : Config = loadConfig().resolve()
}
