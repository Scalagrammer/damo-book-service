package backend.books

import backend.books.model.domain.Book
import backend.books.model.protocol.response.BookResponse
import toolkit.utils.Convertible

package object http {

  implicit lazy val book2BookResponse : Convertible[Book, BookResponse] = {
    case Book(id, title) => BookResponse(id, title)
  }

}
