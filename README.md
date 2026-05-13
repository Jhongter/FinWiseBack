# springboot-usuario-jwt

API REST com autenticação via **JWT (JSON Web Token)**, construída sobre o projeto `springboot-usuario-monolito-vue`.

## O que foi adicionado

| Antes | Depois |
|---|---|
| Sem autenticação | Autenticação JWT stateless |
| Sem senha | Campo `senha` com hash BCrypt |
| Endpoints públicos | Endpoints protegidos com token |

## Estrutura nova / alterada

```
config/
  SecurityConfig.java     ← Regras de segurança (rotas públicas x protegidas)
  JwtFilter.java          ← Intercepta requisições e valida o token
  OpenApiConfig.java      ← Configuração do Swagger com suporte a Bearer

service/
  JwtService.java         ← Gera e valida tokens JWT
  AuthService.java        ← Lógica de login (verifica senha com BCrypt)

controller/
  AuthController.java     ← POST /auth/login

dto/
  LoginRequest.java       ← { email, senha }
  LoginResponse.java      ← { token, nome, email }
```

## Endpoints

| Método | Rota | Auth | Descrição |
|--------|------|------|-----------|
| POST | `/auth/login` | ❌ | Autentica e retorna o JWT |
| POST | `/usuarios` | ✅ | Cadastra novo usuário |
| GET | `/usuarios` | ✅ | Lista usuários |
| GET | `/usuarios/{id}` | ✅ | Busca por id |
| PUT | `/usuarios/{id}` | ✅ | Atualiza usuário |
| DELETE | `/usuarios/{id}` | ✅ | Remove usuário |

## Como usar

### 1. Rodar o projeto

```bash
./mvnw spring-boot:run
```

### 2. Fazer login

```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"ana@exemplo.com","senha":"123456"}'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "nome": "Ana Souza",
  "email": "ana@exemplo.com"
}
```

### 3. Usar o token nas requisições protegidas

```bash
curl http://localhost:8081/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### 4. Cadastrar novo usuário

```bash
curl -X POST http://localhost:8081/usuarios \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Carlos","email":"carlos@exemplo.com","senha":"minhasenha"}'
```

## Swagger UI

Acesse [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html), faça login pelo endpoint `/auth/login`, copie o token e clique em **Authorize** no topo da página.

## Usuários de teste (senha: `123456`)

| Nome | E-mail |
|------|--------|
| Ana Souza | ana@exemplo.com |
| Bruno Lima | bruno@exemplo.com |

## Configuração JWT (`application.properties`)

```properties
jwt.secret=minha-chave-super-secreta-que-deve-ter-pelo-menos-32-caracteres
jwt.expiration-ms=86400000   # 24 horas
```

> ⚠️ **Em produção**, substitua `jwt.secret` por uma string longa e aleatória e use variáveis de ambiente.
