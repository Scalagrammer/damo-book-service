package test

import backend.runtime.IORuntimeModule
import org.scalatest.{BeforeAndAfterAll, Suite}

trait IOTestRuntime extends IORuntimeModule with BeforeAndAfterAll {

  this : Suite =>

  override def afterAll() : Unit = {
    super[BeforeAndAfterAll].afterAll()
    this.shutdownExecutionContext.shutdown.unsafeRunSync()
  }
}
