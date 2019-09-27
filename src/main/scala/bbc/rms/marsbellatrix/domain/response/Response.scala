package bbc.rms.marsbellatrix.domain.response

case class Response[T](
  `$schema`: String,
  total: Int,
  limit: Int,
  offset: Int,
  results: Seq[T])
