package Calculator

import akka.actor.Actor

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/22/13
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
class MultipleCalcActorString  extends Actor {

  val mc:MultipleCalcRetString = new MultipleCalcRetString
  var result:String = ""

  def receive = {
    case x:Int => mc.push(x)
    case x:Char =>  {
      result=mc.op(x)
      sender ! result
    }
  }

}
