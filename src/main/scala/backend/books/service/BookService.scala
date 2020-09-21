package backend.books.service

import java.util.UUID

import backend.books.dao.BookDao
import backend.books.model.domain.Book
import cats.data.OptionT
import cats.effect.{Bracket, ContextShift}
import doobie.util.transactor.Transactor

trait BookService[F[_]] {
  def findById(id : UUID) : F[Option[Book]]
}

class BookServiceImpl[F[_] : Transactor : ContextShift](dao : BookDao)
                                                       ( implicit T : Transactor[F]
                                                                , B : Bracket[F, Throwable] ) extends BookService[F] {

  import doobie.implicits._
  import cats.syntax.apply._
  import toolkit.utils.syntax.convertible._

  override def findById(id : UUID) = {
    OptionT(dao.findById(id).transact(T))
      .map(_.to[Book])
      .value <* ContextShift[F].shift // shift from IO to the service-layer
  }
}