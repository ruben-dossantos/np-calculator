package MQCalculator

import collection.mutable

class MultipleCalc{

  val stack = new mutable.Stack[Double]

  def push(num:Double) = stack.push(num)

  def op(op:Char):Double = {

    try{
      val num2 = stack.pop()
      val num1 = stack.pop()


      var result:Double = 0
      op match{
        case '+' =>
          result=num1+num2
          push(result)
          result
        case '*' =>
          result=num1*num2
          push(result)
          result
        case '-' =>
          result=num1-num2
          push(result)
          result
        case '/' =>
          result=num1/num2
          push(result)
          result
        case _ => 0
      }
    }
    catch{
      case e: NoSuchElementException => {
        println("Faltam números na stack para fazer a operação!")
        0
      }
      case _: Exception =>{
        println("Erro desconhecido!")
        0
      }
    }
  }

  def clean(){
    while(!stack.isEmpty){
      stack.pop()
    }
  }

}
