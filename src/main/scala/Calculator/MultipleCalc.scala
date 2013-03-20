package Calculator

import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/20/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
class MultipleCalc{
  val stack = new mutable.Stack[Double]


  def push(num:Double) = stack.push(num)

  def op(op:Char):Option[Double] = {
    val num2 = stack.pop()
    val num1 = stack.pop()
    var result:Double = 0
    op match{
      case '+' =>
        result=num1+num2
        push(result)
        Some(result)
      case '*' =>
        result=num1*num2
        push(result)
        Some(result)
      case '-' =>
        result=num1-num2
        push(result)
        Some(result)
      case '/' =>
        result=num1/num2
        push(result)
        Some(result)
      case _ => None
    }
  }

  def clean(){
    do {
      stack.pop()
    }while(!stack.isEmpty)
  }

}
