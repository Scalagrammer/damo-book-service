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

class BookServiceImpl[F[_] : Transactor : ContextShift : Bracket[*[_], Throwable]](dao : BookDao) extends BookService[F] {

  import toolkit.utils.syntax.doobie._
  import toolkit.utils.syntax.convertible._

  override def findById(id : UUID) = {
    OptionT(dao.findById(id).within[F]).map(_.to[Book]).value
  }
}