axon 4.0
https://axoniq.io/

java -jar axonserver-4.0.jar

http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb


创建聊天室
curl -X POST http://127.0.0.1:8080/rooms -H 'Content-Type: application/json' -d '{"name":"myChatRoom"}'

加入聊天室
curl -X POST http://127.0.0.1:8080/rooms/d413b067-864d-4546-a7e6-68d95df2d1e0/participants -d '{"participant":"fengshuaiju"}'

