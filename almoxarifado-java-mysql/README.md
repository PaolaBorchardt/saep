# Sistema de Almoxarifado — Java + MySQL

API REST para controle de estoque, entradas/saídas, usuários, autenticação e rastreabilidade.

## Requisitos
- JDK 21+
- Maven 3.9+
- MySQL 8+

## Configuração
1. Crie o banco: `CREATE DATABASE almoxarifado CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
2. Copie `.env.example` como referência e configure as variáveis de ambiente `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
3. Execute `mvn spring-boot:run`.
4. Na primeira execução, são criados dois usuários de demonstração:
   - administrador: `admin` / `Admin123!`
   - operador: `operador` / `Operador123!`

**Troque as senhas antes de qualquer uso real.** As contas iniciais são apenas para desenvolvimento/demonstração.

Por padrão, o banco usa `ddl-auto=update`. Para produção, use migrações versionadas, TLS, gestão segura de segredos, backups e política de senhas.

## Endpoints principais
Todas as rotas exigem autenticação HTTP Basic.

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/api/produtos?nome=parafuso` | ADMIN/OPERADOR | Listar/pesquisar produtos |
| GET | `/api/produtos/{id}` | ADMIN/OPERADOR | Consultar produto |
| POST | `/api/produtos` | ADMIN | Cadastrar produto |
| PUT | `/api/produtos/{id}` | ADMIN | Atualizar produto |
| DELETE | `/api/produtos/{id}` | ADMIN | Excluir produto sem movimentações |
| POST | `/api/movimentacoes/entrada` | ADMIN/OPERADOR | Registrar entrada |
| POST | `/api/movimentacoes/saida` | ADMIN/OPERADOR | Registrar saída |
| GET | `/api/movimentacoes?produtoId=1` | ADMIN/OPERADOR | Histórico filtrável |
| GET | `/api/relatorios/estoque` | ADMIN/OPERADOR | Posição atual do estoque |
| GET | `/api/usuarios` | ADMIN | Listar usuários |
| POST | `/api/usuarios` | ADMIN | Criar usuário |

### Exemplos
Cadastrar produto:
```json
POST /api/produtos
{"nome":"Parafuso M8","descricao":"Aço zincado","quantidadeInicial":25,"estoqueMinimo":5}
```
Entrada:
```json
POST /api/movimentacoes/entrada
{"produtoId":1,"quantidade":10,"observacao":"Compra NF 123"}
```
Saída:
```json
POST /api/movimentacoes/saida
{"produtoId":1,"quantidade":4,"observacao":"Ordem de serviço 25"}
```

## Regras de negócio implementadas
1. O sistema exige autenticação e diferencia ADMIN de OPERADOR.
2. Apenas ADMIN cadastra/altera/exclui produtos e cria/lista usuários.
3. Toda movimentação registra produto, tipo, quantidade, data/hora, usuário responsável e observação.
4. Quantidade deve ser maior que zero.
5. Saída só é permitida quando há saldo suficiente; o estoque nunca fica negativo.
6. O saldo e a movimentação são atualizados na mesma transação.
7. Exclusão de produto com histórico de movimentações é bloqueada para preservar auditoria.
8. Produtos podem ser pesquisados pelo nome; movimentações podem ser filtradas por produto.
9. Erros são retornados em JSON com status HTTP adequado.

## Observações
- API REST; não inclui interface gráfica.
- O banco deve estar iniciado antes de executar a aplicação.
- Endpoints de usuários não expõem senhas.
