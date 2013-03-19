package cucumber

import api.scala.{ScalaDsl, EN}
import org.junit.Assert._
import Calculator.Calc



class CalculatorTest extends ScalaDsl with EN {

  var result:Option[Double] = None

  Given("""^a number (\d+)$"""){ (arg0:Int) =>
    Calc.push(arg0)
  }


  Given("""^an operation ([*+/-])$"""){ (arg0:Char) =>
    result = Calc.op(arg0)
  }

  When("""^these are added$"""){ () =>
    result = Calc.op('+')
  }

  When("""^these are multiplied$"""){ () =>
    result = Calc.op('*')
  }

  When("""^these are subtracted$"""){ () =>
    result = Calc.op('-')
  }

  When("""^these are divided$"""){ () =>
    result = Calc.op('/')
  }

  Then("""^result should be (\d+)$"""){ (arg0:Int) =>
    result match{
      case Some(num)=>assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    }

  }
  Then("""^result is (\d+)$"""){ (arg0:Int) =>
    result match{
      case Some(num)=>assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    }
  }






}