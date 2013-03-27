package Calculator

import akka.actor.Actor


class MultipleCalcActor extends Actor {

  val mc: MultipleCalc = new MultipleCalc
  var result: Option[Double] = None

  def receive = {
    case x: Int => mc.push(x)
    case x: Char => {
      result = mc.op(x)
      sender ! result
    }
  }
}
