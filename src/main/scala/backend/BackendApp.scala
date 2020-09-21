package backend

import backend.module.WiringModule
import backend.runtime.IORuntimeModule
import toolkit.runtime.{AppStarter, ExitCode}

object BackendApp
  extends AppStarter
    with WiringModule
    with IORuntimeModule {
  override def run(args : List[String]) = for {
    _ <- startup()
    _ <- registerShutdownHook()
  } yield {
    ExitCode(0)
  }
}