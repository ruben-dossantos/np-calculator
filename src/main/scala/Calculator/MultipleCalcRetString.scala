package Calculator

import collection.mutable

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
        num1+" + "+num2+" = "+result
      case '*' =>
        result=num1*num2
        push(result)
        num1+" * "+num2+" = "+result
      case '-' =>
        result=num1-num2
        push(result)
        num1+" - "+num2+" = "+result
      case '/' =>
        result=num1/num2
        push(result)
        num1+" / "+num2+" = "+result
      case _ => "Erro"
    }
  }

}