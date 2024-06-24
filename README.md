# Oak Tecnologia - Desafio

## O que se trata

![Output sample](https://i.makeagif.com/media/6-24-2024/sgkUNZ.gif)

Trata-se de um desafio proposto pela [Oak Tecnologia](https://www.oaktecnologia.com/). Este sistema oferece uma plataforma capaz de gerenciar produtos. A aplicação pode ser acessada em: [Oak Desafio](https://oak-desafio.vercel.app/).

> Observação: A API foi implantada no Render.com utilizando o plano gratuito. Devido a isso, as requisições podem apresentar um tempo de resposta maior do que o desejado. Infelizmente, eu recomendo um pouco de paciência ao realizar operações na versão disponível na web, pois o tempo de processamento pode variar. A documentação da API pode ser acessada em: [Swagger](https://oak-desafio-backend.onrender.com/swagger-ui/index.html)

## Como executar

Instruções para configurar e executar a aplicação localmente:

### Pré-requisitos

- Node.js
- Docker
- Java 11+

### Backend

1. Navegue até o diretório do backend:

   ```bash
   cd backend
   ```

2. Configure o arquivo `.env` com as variáveis de ambiente necessárias. **Exemplo**:

   ```env
   DATABASE_URL=jdbc:postgresql://localhost:5432/oak-desafio-db-example
   DATABASE_USERNAME=user1
   DATABASE_PASSWORD=user123
   ```

3. Volte até o diretório raiz do projeto:

   ```bash
   cd ..
   ```

4. Execute o Docker Compose para iniciar os serviços do banco de dados e da aplicação:
   ```bash
   docker-compose up --build
   ```

### Frontend

1. Navegue até o diretório do frontend:

   ```bash
   cd frontend
   ```

2. Configure o arquivo `.env` com as variáveis de ambiente necessárias. **Exemplo**:

   ```bash
     VITE_BASE_URL=http://localhost:8080
   ```

3. Instale as dependências do projeto:

   ```bash
   npm install
   ```

4. Inicie a aplicação:
   ```bash
   npm run dev
   ```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
