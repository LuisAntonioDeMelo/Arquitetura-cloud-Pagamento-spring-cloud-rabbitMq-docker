# Spring Cloud + Mensageria RabbitMQ + Docker

Estudo sobre Spring Cloud, Mensageria e buildando em Docker

## Tecnologias

`Java 17`
`Spring Boot`
`Spring Cloud`
`RabbitMQ`
`Docker`

## Comandos

Eureka

```
http://localhost:8761/
```

## Docker Imgs

Para cada microsserviço, RabbitMQ e Keycloak, será criado container para cada. Para que cada um possa se comunicar com o outro, será necessário criar um Rede (Network)

```
docker network create ms-credito-network

```

A partir de agora, para todos os containers, colocar esta rede: `ms-credito-network`

RabbitMQ

```
# latest RabbitMQ 3.12

docker -it --name rabbitmq --network ms-credito-network -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

Login e senha: guest

```

Keycloak

```
docker -it --name keycloack --network ms-credito-network -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.1.2 start-dev
```

MSs

A criação será a partir do arquivo `Dockerfile`. Cada microserviço deverá conter um, conforme abaixo:

Build MS no Docker

Crie o arquivo `Dockerfile` para cada microsservico `<br>`
Configure da seguinte forma:`<br>`

```
FROM openjdk:11
WORKDIR /app
COPY ./target/eurekaserver-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT java -jar app.jar
```

depois execute o comando para criar a imagem

```
docker build --tag eurekaserver .
```

depois para instalar no docker

```
docker run --name ms-credito-eurekaserver -p 8761:8761 --network ms-credito-network -d eurekaserver

```

```
docker run --name ms-credito-rabbitmq -p 5672:5672 -p 15672:15672 --network ms-credito-network rabbitmq:3.12-management
docker run --name ms-credito-eurekaserver -p 8761:8761 --network ms-credito-network eurekaserver
docker run --name ms-credito-clientes --network ms-credito-network -e EUREKA_SERVER=ms-credito-eurekaserver -d mscliente
docker run --name ms-credito-cartao --network ms-credito-network -e RABBITMQ_SERVER=ms-credito-rabbitmq -e EUREKA_SERVER=ms-credito-eurekaserver -d mscartao
docker run --name ms-credito-avaliadorcredito --network ms-credito-network -e RABBITMQ_SERVER=ms-credito-rabbitmq -e EUREKA_SERVER=ms-credito-eurekaserver -d msavaliadorcredito

```
