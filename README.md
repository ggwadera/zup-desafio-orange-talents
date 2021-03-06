# Desafio Zup Orange Talents

Para ler o artigo escrito para este desafio, [clique aqui](Desafio.md).

Para rodar a aplicação localmente em sua máquina, é preciso ter a JDK 15 instalada, e executar os comandos abaixo:

```shell
git clone https://github.com/ggwadera/zup-desafio-orange-talents.git
cd zup-desafio-orange-talents
./gradlew build
./gradlew bootRun
```

A aplicação então estará disponível no endereço `http://localhost:8080`.

Também é disponibilizado uma imagem Docker para rodar a aplicação. Para isso utilize o comando abaixo.

```shell
docker run --rm -it -p 8080:8080 ggwadera/zup
```

A API também está disponível online na plataforma Heroku, onde você pode testar sem precisar rodar nada localmente. Para
isso, utilize o link https://zup-desafio.herokuapp.com/ (para criar uma conta, por exemplo,
utilize https://zup-desafio.herokuapp.com/api/user/signup).

## Endpoints:

* **POST** `/api/user/signup` — cria uma conta de usuário, requer body com os valores abaixo:

```json
{
  "name": "string",
  "cpf": "string",
  "email": "string",
  "birthday": "yyyy-mm-dd"
}
```

* **GET** `/api/user/{id}` — busca uma conta no banco de dados a partir do ID requisitado.

## Contexto:

Você está fazendo uma API REST que precisa suportar o processo de abertura de nova conta no banco. O primeiro passo
desse fluxo é cadastrar os dados pessoais de uma pessoa. Precisamos de apenas algumas informações obrigatórias:

* Nome
* E-mail
* CPF
* Data de nascimento

Caso os dados estejam corretos, é necessário gravar essas informações no banco de dados relacional e retornar o status
adequado para a aplicação cliente, que pode ser uma página web ou um aplicativo mobile.

Você deve fazer com que sua API devolva a resposta adequada para o caso de falha de validação.

## Desafio:

Dado que você fosse implementar esta etapa utilizando Java como linguagem e Spring + Hibernate como stacks de tecnologia
fundamentais da aplicação, precisamos que você faça o seguinte:

Escreva um post de blog explicando de maneira detalhada tudo que você faria para implementar esse código.

No texto, queremos que você aborde os pontos:

* Quais tecnologias do mundo Spring você usaria?
* Quais classes seriam criadas nesse processo?
* Qual foi o seu processo de decisão para realizar a implementação?
* Qual o papel de cada tecnologia envolvida no projeto?

Os itens acima são obrigatórios, mas não se limite. Se quiser escrever mais, fique à vontade (não existe quantidade
máxima de páginas ou caracteres).

**Item bônus:** Se ficou fácil, considere que você também precisa explicar como faria para proteger a aplicação de
e-mail e CPF duplicados.
