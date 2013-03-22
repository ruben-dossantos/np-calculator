import Calculator.{WebServerString, WebServer}
import dispatch._


object main {
  def main(args: Array[String]) {

    //WebServer.Start()
    //Thread.sleep(99999999)
    WebServerString.Start()
    Thread.sleep(99999999)
    val req = url("http://postcatcher.in/catchers/514b34b6a4060502000000a5")
    val map = Map("a"-> "b")
    req << map

    val res = Http(req OK as.String)
    println(res())


  }
}
