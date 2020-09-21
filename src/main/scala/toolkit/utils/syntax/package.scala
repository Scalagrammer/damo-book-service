package toolkit.utils

package object syntax {
  object convertible {
    implicit class ConvertibleOps[A](private val a : A) extends AnyVal {
      def to[B](implicit C : Convertible[A, B]) : B = C.convert(a)
    }
  }
}
