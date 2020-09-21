package backend.books

import backend.books.model.domain.Book
import backend.books.model.persistence.BookEntity
import toolkit.utils.Convertible

package object service {

  implicit lazy val bookEntity2Book : Convertible[BookEntity, Book] = {
    case BookEntity(id, title) => Book(id, title)
  }

}
