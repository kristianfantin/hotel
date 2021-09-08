# hotel
API Between Broker and Front in order to provide values for booking in a hotel

github: https://github.com/kristianfantin/hotel

Para visualizar as tasks executas, acompanhe o Kanban do Projeto no git hub:
- https://github.com/users/kristianfantin/projects/2

Para rodar a aplicação: mvn spring-boot:run
- Como a aplicação está com o Swagger configurado, pode acessar http://localhost:8080 e vc terá acesso a documentação da API.
- Necessário rodar o docker com o REDIS para a aplicação não dar erro de cache:
  - docker run -it --name redis -p 6379:6379 redis:5.0.3 
