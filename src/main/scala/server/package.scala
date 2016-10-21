import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalaz.\/
import scalaz.concurrent.Task
import scala.concurrent.ExecutionContext.Implicits.global

package object server {
  implicit class ExtendsFuture[A](future: Future[A]) {
    def asTask: Task[A] = {
      Task.async { f =>
        future.onComplete {
          case Success(a) => f(\/.right(a))
          case Failure(t) => f(\/.left(t))
        }
      }
    }
  }
}
