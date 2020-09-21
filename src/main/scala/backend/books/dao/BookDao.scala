package backend.books.dao

import java.util.UUID

import backend.books.model.persistence.BookEntity
import doobie.ConnectionIO

trait BookDao {
  def findById(id : UUID) : ConnectionIO[Option[BookEntity]]
}

class BookDaoImpl extends BookDao {

  import doobie._
  import doobie.implicits._
  import cats.syntax.functor._
  import doobie.postgres.implicits._

  override def findById(id : UUID) = {
    sql"""
         |select id, title
         |from books
         |where id = $id
         |""".stripMargin
             .query[BookEntity]
             .to[List]
             .map(_.headOption)
  }
}
