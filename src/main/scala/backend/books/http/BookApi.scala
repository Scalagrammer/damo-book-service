package backend.books.http

import java.util.UUID

import backend.books.model.protocol.response.BookResponse
import backend.books.service.BookService
import cats.effect.Sync
import io.finch.EndpointModule
import com.twitter.finagle.http.Status

class BookApi[F[_] : Sync](service : BookService[F]) extends EndpointModule[F] {

  import io.finch._
  import circe._
  import cats.syntax.functor._

  import toolkit.utils.syntax.convertible._

  import Status.NotFound
  import Output.{payload, empty => emptyPayload}

  import service.{findById => findBookById}

  def getBook : Endpoint[F, BookResponse] = {
    get("books" :: path[UUID]).mapOutputAsync { id =>
      findBookById(id)
        .map {
          case None        => emptyPayload(NotFound)
          case Some(value) => payload(value.to[BookResponse])
        }
    }
  }
}