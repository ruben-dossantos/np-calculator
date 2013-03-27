Feature: Provide a Calculation Web Service
 Scenario: Single Calculator 
  Given a web service exposing the Calculator
  When I POST number 5 to /number/
  And I POST number 10 to /number/
  And I POST number 10 to /number/
  And I POST an operation "+" to /operation/
  And I POST an operation "+" to /operation/
  Then the response is 25