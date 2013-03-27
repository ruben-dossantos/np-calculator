package Calculator

import akka.actor.Actor

class MultipleCalcActorString extends Actor {

  val mc: MultipleCalcRetString = new MultipleCalcRetString
  var result: String = ""

  def receive = {
    case x: Int => mc.push(x)
    case x: Char => {
      result = mc.op(x)
      sender ! result
    }
  }

}
