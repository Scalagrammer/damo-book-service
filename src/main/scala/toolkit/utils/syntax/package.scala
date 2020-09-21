package toolkit.utils

import cats.effect.{Bracket, ContextShift}
import doobie.{ConnectionIO, Transactor}

package object syntax {
  object convertible {
    implicit class ConvertibleOps[A](private val a : A) extends AnyVal {
      def to[B](implicit C : Convertible[A, B]) : B = C.convert(a)
    }
  }

  object doobie {

    import cats.syntax.apply._
    import _root_.doobie.implicits._

    implicit class ConnectionIOOps[A](private val connected : ConnectionIO[A]) extends AnyVal {
      def within[F[_]](implicit CS : ContextShift[F]
                               , T : Transactor[F]
                               , B : Bracket[F, Throwable]) : F[A] = connected.transact(T) <* CS.shift
    }
  }
}
