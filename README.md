# project-product


## API's

POST	/products	Criação de um produto
PUT	/products/	Atualização de um produto
GET	/products/	Busca de um produto por ID
GET	/products	Lista de produtos
GET	/products/search	Lista de produtos filtrados
DELETE	/products/	Deleção de um produto

## Detalhes

Porta configurada no projeto é 9999
Banco de dados é o H2 Database
Para acessar a documentação no swagger basta acessar um vez que o projeto estiver rodando http://localhost:9999/swagger-ui.html#/

## Observação

GET /products/search Recebe 3 Parâmetros, sendo um deles um String Termo codificada em Base 64
