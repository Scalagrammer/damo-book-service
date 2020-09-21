package backend.persistence.module

import java.util.concurrent.Executors.newFixedThreadPool

import cats.effect.Blocker.liftExecutionContext
import com.typesafe.config.Config
import com.zaxxer.hikari.HikariDataSource

import doobie.hikari.HikariTransactor
import toolkit.runtime.{Context, RuntimeModule}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.fromExecutor

import doobie.util.transactor.Transactor

trait PersistenceModule {

  this : Context =>

  def transactor(configPath : String)(implicit cfg : Config) : Transactor[F]

}

trait PersistenceModuleImpl extends PersistenceModule {

  this : Context
    with RuntimeModule =>

  import net.ceedubs.ficus.Ficus._
  import com.zaxxer.hikari.{HikariConfig => HConfig}

  override def transactor(configPath : String)(implicit cfg : Config) : Transactor[F] = {

    val doobieConfig = cfg.as[Config](configPath)

    val executionContext : ExecutionContext = fromExecutor(newFixedThreadPool(doobieConfig.as[Int]("concurrency")))

    val dataSource = new HikariDataSource(HikariConfig(doobieConfig))

    HikariTransactor[F](dataSource, executionContext, liftExecutionContext(executionContext))

  }

  object HikariConfig {
    def apply(config : Config) : HConfig = new HConfig {
      setJdbcUrl(config.as[String]("url"))
      setMinimumIdle(config.as[Int]("min-idle"))
      setUsername(config.as[String]("username"))
      setPassword(config.as[String]("password"))
      setMaximumPoolSize(config.as[Int]("max-pool-size"))
      setDriverClassName(classOf[org.postgresql.Driver].getName)
    }
  }

}
