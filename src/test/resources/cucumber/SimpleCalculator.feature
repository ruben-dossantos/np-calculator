@wip
Feature: RPN Addition
 Scenario: Add two numbers
  Given a number 10
  And a number 25
  When these are added
  Then result should be 35
 Scenario: Multiply two numbers
   Given a number 10
   And a number 10
   When these are multiplied
   Then result should be 100
 Scenario: Subtract two numbers
   Given a number 50
   And a number 25
   When these are subtracted
   Then result should be 25
 Scenario: Divide two numbers
   Given a number 50
   And a number 5
   When these are divided
   Then result should be 10

 Scenario Outline: Multiple Calculation
  Given a number <n1>
  And a number <n2>
  And an operation <op>
  Then result is <result>
  Examples:
  |  n1  |  n2  | op | result |
  |   1  |   1  | +  |  2     |
  | 300  | 200  | -  |  100   |
  | 11   |  10  | *  |  110   |