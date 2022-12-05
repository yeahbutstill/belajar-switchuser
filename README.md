# Aplikasi Switch User #

Penjelasan bisa dibaca [di blog saya](https://software.endy.muhardin.com/java/switch-user-spring-security/)

## Run docker compose
```shell
$ docker compose up -d
```

## Access docker container
```shell
$ docker exec -it switch_user bash
```

## Login postgres
```shell
$ psql -U yeahbutstill
```
## Insert data
```postgres-psql
CREATE DATABASE switchuser;
```