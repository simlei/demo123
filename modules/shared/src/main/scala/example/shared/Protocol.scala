package example.shared

import io.circe.Decoder
import io.circe.Encoder

object Protocol:
  object GetSuggestions:

    case class Request(
        search: String,
        prefixOnly: Option[Boolean] = None
    ) derives Decoder,
          Encoder.AsObject

    case class Response(suggestions: Seq[String])
        derives Decoder,
          Encoder.AsObject

  object demo123:

    case class Request(
      address: Address,
      // testContent: List[String],
      ) derives Decoder, Encoder.AsObject
    
    case class Address(
      values: List[Int]
      ) derives Decoder, Encoder.AsObject

    case class Response(
      result: Int
      ) derives Decoder, Encoder.AsObject
