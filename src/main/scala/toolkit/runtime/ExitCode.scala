package toolkit.runtime

case class ExitCode(code : Int)

object ExitCode {
  def apply(code : Int) : ExitCode = new ExitCode(code & 0xFF)
}
