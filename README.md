axon 4.*
https://axoniq.io/

docker-compose up -d

http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:querydb


创建聊天室
curl -X POST http://127.0.0.1:8080/rooms -H 'Content-Type: application/json' -d '{"name":"myChatRoom"}'

加入聊天室
curl -X PUT http://127.0.0.1:8080/rooms/{roomId}/chatter -H 'Content-Type: application/json' -d '{"participantName":"fengshuaiju","sex":"男"}'

查询聊天室的人员
curl -X GET http://127.0.0.1:8080/rooms/rooms/{roomId}/chatters

在聊天室里发送消息
curl -X POST http://127.0.0.1:8080/rooms/{roomId}/messages/{chatterId} -H 'Content-Type: application/json' -d '{"message":"chatter message"}'

