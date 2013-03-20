Feature: Handle Multiple Operations
 Scenario Outline: Multiple Calculation With multiple parts
  Given a number <n1>
  And a number <n2>
  And an operation <op1>
  And a number <n3>
  And a number <n4>
  And an operation <op2>
  And an operation <op3>
  Then result is <result>
  Examples:
  |  n1  |  n2   |  n3  |  n4 | op1 | op2 | op3 | result  |
  |   1  |   -1  | 10   | 20  | -   |  +  | *   |  60     |
  |   1  |   -1  | 10   | 20  | -   |  +  | *   |  60     |
  |   1  |   -1  | 10   | 20  | -   |  +  | *   |  60     |
  |   1  |   -1  | 10   | 20  | -   |  +  | *   |  60     |