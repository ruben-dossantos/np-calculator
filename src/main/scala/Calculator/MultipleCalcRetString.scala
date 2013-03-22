package Calculator

import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/22/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
class MultipleCalcRetString{
  val stack = new mutable.Stack[Double]


  def push(num:Double) = stack.push(num)

  def op(op:Char):String = {
    val num2 = stack.pop()
    val num1 = stack.pop()
    var result:Double = 0
    op match{
      case '+' =>
        result=num1+num2
        push(result)
        num2+" + "+num1+" = "+result
      case '*' =>
        result=num1*num2
        push(result)
        num2+" * "+num1+" = "+result
      case '-' =>
        result=num1-num2
        push(result)
        num2+" - "+num1+" = "+result
      case '/' =>
        result=num1/num2
        push(result)
        num2+" / "+num1+" = "+result
      case _ => "Erro"
    }
  }

}