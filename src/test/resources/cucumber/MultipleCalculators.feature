Feature: Handle Multiple Calculators
 Scenario: Multiple Calculators
  Given a calculator named "a"
  And a calculator named "b"
  When a number 10 is sent to "a"
  And a number 5 is sent to "b"
  And a number -2 is sent to "a"
  And a number 0 is sent to "b"
  And an operation "+" is sent to "a"
  And an operation "*" is sent to "b"
  Then the result in "a" is 8
  And the result in "b" is 0