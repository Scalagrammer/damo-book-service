package toolkit.runtime

trait AppLifecycle {

  this : Context
    with RuntimeModule =>

  import cats.implicits._

  def wholeLifecycle : Set[Hook[F]]

  def startup() : F[Unit] = {
    (wholeLifecycle.toList).foldMapM(_.startup)
  }

  def shutdown() : F[Unit] = {
    (wholeLifecycle.toList).foldMapM(_.shutdown)
  }

}
