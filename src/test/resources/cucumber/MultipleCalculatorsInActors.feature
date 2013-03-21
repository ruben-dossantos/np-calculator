Feature: Handle Multiple Calculators
 Scenario: Multiple Calculators
  Given a calculator actor named "a"
  And a calculator actor named "b"
  When a number 10 is sent to actor "a"
  And a number 5 is sent to actor "b"
  And a number -2 is sent to actor "a"
  And a number 0 is sent to actor "b"
  And an operation "+" is sent to actor "a"
  And an operation "*" is sent to actor "b"
  Then the result in actor "a" is 8
  And the result in actor "b" is 0