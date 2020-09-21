package backend.runtime

import java.util.concurrent.Executors.newFixedThreadPool

import cats.effect.{ConcurrentEffect, ContextShift, IO, Timer}
import toolkit.runtime.Hook.onShutdown
import toolkit.runtime.{Context, Hook, RuntimeModule}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.fromExecutor

trait IORuntimeModule extends Context with RuntimeModule {

  override type F[A] = IO[A]

  override implicit lazy val concurrentEffect : ConcurrentEffect[IO] = IO.ioConcurrentEffect(contextShift)

  override implicit lazy val contextShift : ContextShift[IO] = IO.contextShift(executionContext)

  override implicit lazy val timer : Timer[IO] = IO.timer(executionContext)

  lazy val shutdownExecutionContext : Hook[IO] = {
    onShutdown(IO(underlyingExecutorService.shutdown()))
  }

  implicit lazy val executionContext : ExecutionContext = fromExecutor(underlyingExecutorService)
  // FIXME : flexible pool-configuration
  private[this] lazy val underlyingExecutorService = newFixedThreadPool(10)

}
