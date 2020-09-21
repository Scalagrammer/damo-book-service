package toolkit.runtime

import cats.effect.{ConcurrentEffect, ContextShift, Timer}

trait RuntimeModule {

  this : Context =>

  implicit def concurrentEffect : ConcurrentEffect[F]

  implicit def contextShift : ContextShift[F]

  implicit def timer : Timer[F]

}