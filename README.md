# Encurtador de URL

Este projeto é um encurtador de urls, disponibilizado através de uma **API Rest**.

Está disponível em produção em **https://burls.herokuapp.com**

É possível utilizar os arquivos json dentro da pasta **postman** para importar a configuração dos endpoints e dos ambientes dentro do [Postman](https://www.getpostman.com/).

O endpoint **/url** recebe um **POST**, contendo a url a ser encurtada com o seguinte payload:

```(javascript)
{
  “originalURL”: “https://noticias.uol.com.br/cotidiano/ultimas-noticias/2019/04/19/onca-parda-invade-casas-e-caminha-por-telhados-no-interior-de-sao-paulo.htm”
}
```

Será devolvido o payload contendo a URL original e a encurtada, no seguinte padrão:

```(javascript)
{
  "originalURL": "https://noticias.uol.com.br/cotidiano/ultimas-noticias/2019/04/19/onca-parda-invade-casas-e-caminha-por-telhados-no-interior-de-sao-paulo.htm",
  "shortURL": "https://burls.herokuapp.com/5cba6f04a7b11b0001776442",
  "clicks": 0
}
```

Existe também o endpoint [/v1/analytics](https://burls.herokuapp.com/v1/analytics), que retorna dados estatísticos sobre a aplicação.

A stack utilizada inclui Java 8, Spring Boot 2, MongoDB, Docker e NGinx.

Passo a passo para execução, **partindo da premissa que o java, maven e docker já estão instalados e funcionando:**

###Local(DEV):
Execute os comandos:

Subir o mongo:
`
docker run mongo
`

Dentro da raiz do projeto:
`
mvn clean spring-boot:run
`

A aplicação estará disponível em http://localhost:8080

###Docker
Também é possível criar a imagem da aplicação, para rodar a mesma através do docker, já em conjunto com o mongo e o balanceador de carga.

Execute os comandos(na raiz do projeto):

Construir a aplicação:
`
mvn clean package
`

Construir a imagem:
`
docker image build -t encurtador:1.0 .
`

Subir o docker-compose:
`
docker-compose up -d
`

Desta forma a aplicação subirá em 3 nós, sendo balanceada por um **Nginx**.

A aplicação estará disponível em http://localhost:8010
