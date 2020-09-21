package backend.books.http

import java.util.UUID
import java.util.UUID.fromString

import backend.books.model.domain.Book
import backend.books.service.BookService
import backend.books.model.protocol.response.BookResponse

import cats.effect.IO
import io.finch.Input.get

import org.scalatest.Inside
import org.scalatest.flatspec.AnyFlatSpec
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import test.IOTestRuntime

class BookApiSpec
  extends AnyFlatSpec
    with Inside
    with Matchers
    with MockFactory
    with IOTestRuntime {

  import cats.syntax.option._
  import com.softwaremill.macwire.wire

  "GET /books" should "respond with book" in new Wiring with TestData {

    serviceMock.findById _ expects (sampleBookId) returns IO(sampleBook.some)

    inside(api.getBook(get(s"/books/$sampleBookId")).awaitValue()) {
      case Some(Right(BookResponse(id, title))) =>
        id shouldBe sampleBookId
        title shouldBe sampleBookTitle
    }
  }

  trait Wiring {
    lazy val serviceMock : BookService[F] = mock[BookService[F]]

    lazy val api : BookApi[F] = wire[BookApi[F]]
  }

  trait TestData {
    lazy val sampleBookTitle : String = "sampleBookTitle"

    lazy val sampleBookId : UUID = fromString("d341984f-338d-4476-b999-ba8d32d06212")

    lazy val sampleBook : Book = Book(sampleBookId, sampleBookTitle)
  }
}
