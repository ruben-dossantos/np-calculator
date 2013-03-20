Feature: Handle Negative Numbers
 Scenario Outline: Multiple Calculations with Negative Numbers
  Given a number <n1>
  And a number <n2>
  And an operation <op>
  Then result is <result>
  Examples:
  |  n1  |  n2   | op | result |
  |   1  |   -1  | *  |  -1    |
  | 300  | -200  | -  |  500   |
  | 11   |  -10  | *  |  -110  |