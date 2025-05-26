
# Agendador de Notificações

Projeto de uma API Rest para agendar,consultar e cancelar notificações. Desenvolvido com Java e Spring Boot.


## Documentação da API

#### Realiza um novo agendamento de notificação

```http
  POST /agendamento
```

| Chave   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `emailDestinatario` | `string` | O email que receberá a notificação |
| `telefoneDestinatario` | `string` | O telefone que receberá a notificação |
| `mensagem` | `string` | O conteúdo que será enviado na notificação |
| `dataHoraEnvio` | `string` | A data e a hora em que o envio da notificação ocorrerá. o Formato deverá ser : "dd-MM-yyyy HH:mm:ss"|

#### Retorna um agendamento de notificação específico

```http
  GET /agendamento/${id}
```

| Chave   | Tipo       | Descrição                                             |
| :---------- | :--------- |:------------------------------------------------------|
| `id`      | `Long` | **Obrigatório**. O ID do item que você quer consultar |

#### Cancela um agendamento de notificação específico

```http
  PUT /agendamento/${id}
```

| Chave   | Tipo       | Descrição                                            |
| :---------- | :--------- |:-----------------------------------------------------|
| `id`      | `Long` | **Obrigatório**. O ID do item que você quer cancelar |




## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/lucasballonecker/agendamento-notificacao
```

Entre no diretório do projeto

```bash
  docker-compose up --build
```



## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```

