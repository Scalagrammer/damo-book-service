package toolkit.utils

trait Convertible[A, B] {
  def convert(a : A) : B
}

object Convertible {
  def apply[A, B](f : A => B) : Convertible[A, B] = f(_)
}
