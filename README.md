## Springboot RSocket
#### Testing with rsc tool
* RSC cli can be downloaded from [here](https://github.com/making/rsc)
* Calling RSocket fire-and-forget - `java -jar rsc-0.9.1.jar --request --route=ticket.v1.cancel --data='{"requestId": "usr_arnhkNjw5vc1UyaNYyppcbHbF"}' --wiretap --debug tcp://localhost:6565`
* Calling RSocket request-response - `java -jar rsc-0.9.1.jar --request --route=ticket.v1.purchase --data='{"requestId": "usr_kmjjEALMT3RQRtsUO6juoq9HE"}' --wiretap --debug tcp://localhost:6565`
* Calling RSocket request-response stream - `java -jar rsc-0.9.1.jar --stream --route=movie.v1.stream --data='{"requestId": "usr_b5ZLyC040qIiiK5i1PQNt8S2k", "status": "ISSUED"}' --wiretap --debug --take=30 tcp://localhost:6565`

#### RSocket Example
* Example [here](https://github.com/benwilcock/spring-rsocket-demo)

### Types of RSocket
* Fire and Forget.
```
client          Server
(1) -----------> 
    <----------- (0)
```
* Request Response.
```
client          Server
(1) -----------> 
    <----------- (1)
```
* Stream
```
client          Server
(1) -----------> 
    <----------- (n)
```
* Channel
```
client          Server
(n) -----------> 
    <----------- (n)
```
#### RSocket More Example
```shell
java -jar rsc-0.9.1.jar --request --route=request-response --data='{"origin": "Hello", "interaction": "world"}' --debug tcp://localhost:6565
java -jar rsc-0.9.1.jar --request --route=fire-and-forget --data='{"origin": "Hello", "interaction": "world"}' --debug tcp://localhost:6565
java -jar rsc-0.9.1.jar --stream --route=stream --data='{"origin": "Hello", "interaction": "world"}' --debug --take=30 tcp://localhost:6565
```