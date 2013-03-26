Feature: Provide a Calculation Service via MQ
 Scenario: Single Calculator via MQ
  Given a rabbitmq server connection
  And a service listening to routing key "services.calculator"
  And a client
  When the client sends number 5 to the server with the key "services.calculator"
  And the client sends number 2 to the server with the key "services.calculator"
  And the client sends the operator "-" to the server with the key "services.calculator"
  Then the service should acquire all three elements
  And guarantee their order
  And process the result and send it back to the client
  And the client should receive a reply with the value 3