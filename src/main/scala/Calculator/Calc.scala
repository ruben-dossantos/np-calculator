package Calculator

import collection.mutable


object Calc {
  val stack = new mutable.Stack[Double]


  def push(num:Double){
    stack.push(num)

  }

  def op(op:Char):Option[Double] = {
    val num2 = stack.pop()
    val num1 = stack.pop()
    op match{
      case '+' => Some(num1+num2)
      case '*' => Some(num1*num2)
      case '-' => Some(num1-num2)
      case '/' => Some(num1/num2)
      case _ => None
    }
  }
}
