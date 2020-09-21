package toolkit.runtime

import cats.effect.{Concurrent, Effect, Fiber, IO, Sync}

import scala.sys.{ShutdownHookThread, addShutdownHook, exit}
import scala.util.control.NonFatal

trait AppStarter {

  this : Context
    with AppLifecycle
    with RuntimeModule =>

  import cats.syntax.flatMap._
  import cats.syntax.functor._

  def run(args : List[String]) : F[ExitCode]

  final def main(args : Array[String]) : Unit = {
    toIO[ExitCode](start(run(args.toList)).flatMap(_.join)).unsafeRunSync()
  } match {
    case ExitCode(0)    => ()
    case ExitCode(code) => exit(code)
  }

  final def registerShutdownHook() : F[ShutdownHookThread] = {

    val stopHook : IO[Unit] = toIO(shutdown())

    Sync[F].delay(addShutdownHook(stopHook.unsafeRunSync()))

  }

  private[this] def start(f : => F[ExitCode]) : F[Fiber[F, ExitCode]] = {

    import cats.syntax.applicativeError._

    def kick : F[ExitCode] = Sync[F].suspend {
      f.recover { case NonFatal(cause) =>
        cause.printStackTrace() ; ExitCode(1)
      }
    }

    for {
      s <- Concurrent[F].start(kick)
      _ <- Sync[F].delay(addShutdownHook(toIO[Unit](s.cancel).unsafeRunSync()))
    } yield s
  }

  private[this] def toIO[A](fa : F[A]) : IO[A] = Effect[F].toIO[A](fa)

}
