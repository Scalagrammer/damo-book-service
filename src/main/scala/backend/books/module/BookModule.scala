package backend.books.module

import backend.books.dao.{BookDao, BookDaoImpl}
import backend.books.http.BookApi
import backend.books.service.{BookService, BookServiceImpl}
import backend.persistence.module.PersistenceModule
import doobie.util.transactor.Transactor
import toolkit.config.module.ConfigModule
import toolkit.runtime.{Context, RuntimeModule}

trait BookModule {

  this : Context =>

  def bookService : BookService[F]

}

trait BookModuleImpl extends BookModule {

  this : Context
    with ConfigModule
    with RuntimeModule
    with PersistenceModule =>

  import bookApi.getBook
  import com.softwaremill.macwire.wire

  override lazy val bookService : BookService[F] = wire[BookServiceImpl[F]]

  lazy val bookEndpoint = getBook

  private[this] lazy val bookDao : BookDao = wire[BookDaoImpl]

  private[this] lazy val bookApi : BookApi[F] = wire[BookApi[F]]

  private[this] implicit lazy val tx : Transactor[F] = transactor("books-tx")

}
