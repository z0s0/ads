package ads.repo.util

import doobie.ConnectionIO
import doobie.implicits.toDoobieApplicativeErrorOps
import doobie.postgres._
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

sealed trait DbError

sealed trait MutationError extends DbError
sealed trait ReadError extends DbError

final case class NotFound(msg: String) extends ReadError

final case class UniquenessViolation(msg: String) extends MutationError
final case class ForeignKeyViolation(msg: String) extends MutationError
case object Impossible extends MutationError

/*
  this module is supposed to be used at the most low layer of app
  it catches sql protocol errors and converts them to meaningful errors
  just use .withMutationsErrorHandling on any ConnectionIO[F]
 */
object DbError {
  implicit val jsonEncoder: Encoder[MutationError] = deriveEncoder

  implicit final class ConnectionIOOps[A](val self: ConnectionIO[A]) extends AnyVal {
    def withMutationErrorsHandling: doobie.ConnectionIO[Either[MutationError, A]] =
      self.attemptSomeSqlState {
        case sqlstate.class23.UNIQUE_VIOLATION      => UniquenessViolation("")
        case sqlstate.class23.FOREIGN_KEY_VIOLATION => ForeignKeyViolation("")
      }
  }
}

object ReadError {
  def notFound(msg: String): DbError = NotFound(msg)
}
object MutationError {
  val impossible: MutationError = Impossible
}
