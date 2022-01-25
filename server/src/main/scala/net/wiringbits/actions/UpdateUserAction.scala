package net.wiringbits.actions

import net.wiringbits.api.models.UpdateUser
import net.wiringbits.repositories.UsersRepository

import java.time.Clock
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UpdateUserAction @Inject() (
    usersRepository: UsersRepository
)(implicit
    ec: ExecutionContext,
    clock: Clock
) {

  def apply(userId: UUID, request: UpdateUser.Request): Future[Unit] = {
    val validate = Future {
      if (request.name.isEmpty) new RuntimeException(s"The name is required")
      else ()
    }

    for {
      _ <- validate
      _ <- usersRepository.update(userId, request.name)
    } yield ()
  }
}
