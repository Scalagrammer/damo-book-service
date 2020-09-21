package backend.books.model.protocol.response

import java.util.UUID

case class BookResponse(id : UUID, title : String)

object BookResponse {

  import io.circe.Codec.AsObject
  import io.circe.generic.semiauto.deriveCodec

  implicit lazy val jsonCodec : AsObject[BookResponse] = deriveCodec[BookResponse]

}