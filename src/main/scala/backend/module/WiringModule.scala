package backend.module

import backend.books.module.BookModuleImpl
import backend.persistence.module.PersistenceModuleImpl
import toolkit.config.module.ConfigModuleImpl
import toolkit.http.module.HttpModuleImpl
import toolkit.runtime.{AppLifecycle, Context, Hook, RuntimeModule}

trait WiringModule
  extends AppLifecycle
    with HttpModuleImpl
    with BookModuleImpl
    with ConfigModuleImpl
    with PersistenceModuleImpl {

  this : Context
    with RuntimeModule =>

  import io.finch._
  import circe._
  import com.softwaremill.macwire.wireSet

  override lazy val wholeLifecycle = wireSet[Hook[F]]

  private[this] lazy val exposeHttpApiHook = exposeHttpService(bookEndpoint.toService)

}
