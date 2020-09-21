package toolkit.http.module

import cats.effect.Sync
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await.{ready, result}
import com.twitter.finagle.http.{Request, Response}
import com.typesafe.config.Config
import toolkit.runtime.{Context, Hook, RuntimeModule}

trait HttpModule {

  this : Context =>

  def exposeHttpService(service : => Service[Request, Response])(implicit cfg : Config) : Hook[F] // TODO : http-config (for binding params)

}

trait HttpModuleImpl extends HttpModule {

  this : Context
    with RuntimeModule =>

  override def exposeHttpService(service : => Service[Request, Response])(implicit cfg : Config) = new Hook[F] {

    override def startup : F[Unit] = Sync[F].delay(ready(server)) // TODO : startup-timeout

    override def shutdown : F[Unit] = Sync[F].delay(result(server.close()))  // TODO : shutdown-timeout

    private[this] lazy val server = Http.serve(":8080", service) // TODO : configuration

  }
}

