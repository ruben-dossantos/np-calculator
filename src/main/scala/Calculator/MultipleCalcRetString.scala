package Calculator

import collection.mutable

class MultipleCalcRetString {
  val stack = new mutable.Stack[Double]

  var numAux: Double = 0
  var hasStack = false


  def push(num: Double) {
    stack.push(num)
    hasStack = true
  }

  def op(op: Char): String = {
    try {
      val num2 = stack.pop()
      numAux = num2
      val num1 = stack.pop()
      if (stack.isEmpty) {
        hasStack = false
      }


      var result: Double = 0
      op match {
        case '+' =>
          result = num1 + num2
          push(result)
          num1 + " + " + num2 + " = " + result
        case '*' =>
          result = num1 * num2
          push(result)
          num1 + " * " + num2 + " = " + result
        case '-' =>
          result = num1 - num2
          push(result)
          num1 + " - " + num2 + " = " + result
        case '/' => {
          if (num2 != 0) {
            result = num1 / num2
            push(result)
            num1 + " / " + num2 + " = " + result
          }
          else {
            println("Tentativa de divisao por 0")
            "Erro, Tentativa de divisao por 0"
          }
        }
        case _ => "Erro"
      }
    }
    catch {
      case e: NoSuchElementException => {
        println("Faltam numeros na stack para fazer a operacao!")
        if (hasStack) {
          push(numAux)
        }
        "Faltam numeros na stack para fazer a operacao!"
      }
      case _: Exception => {
        println("Erro desconhecido!")
        "Erro desconhecido"
      }
    }
  }

}