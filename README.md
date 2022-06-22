# Santander Financiamento API 
> API consiste em uma ponte ao fluxo de processos pagamentos eletrônicos através de boletos e financiamento para clientes e não clientes do Banco Santander.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-Maven-000?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![Gson Badge](https://img.shields.io/badge/-Gson-informational?style=flat-square&logo=Google&logoColor=white&link=https://sites.google.com/site/gson/)](https://sites.google.com/site/gson/)
[![Jackson Badge](https://img.shields.io/badge/-Jackson-blueviolet?style=flat-square&logo=GitHub&logoColor=white&link=https://github.com/FasterXML/jackson/)](https://github.com/FasterXML/jackson/)

<img align="right" width="200" height="150" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/santander-banner.jpeg">

## Sobre o Santander
O **Santander Brasil** é a subsidiária do banco espanhol Banco Santander no Brasil. Sediada em São Paulo, no estado homônimo, a operação brasileira entrou em atividade desde 1982 e é parte integrante do Grupo Santander, de origem espanhola, que é o principal conglomerado financeiro da Zona do Euro.

Esta API foi desenvolvida para tratar um produto, denominada *Santander Financiamento - Cessão de Crédito (CSC)*, que tem o intuito de ser uma opção de parcelamento com oferta sem juros para o cliente final e com custo financeiro do lojista, sem concorrência com o limite de cartão de crédito e complementar às demais formas de pagamentos. Este produto tem como clientes finais - Pessoas Físicas e Pessoas Jurídicas, Correntistas ou não Correntistas do Banco Santander, mas para uso do *webservice* será restrito para Pessoas Físicas.

Produto CSC
1. Conceito - É a compra e venda de créditos em que a Santander financiamentos passa a ser a titular de créditos de uma determinada empresa (Cedente), decorrentes de
um contrato de prestação de serviços ou fornecimento de mercadorias. Para efetivação do negócio, este Cedente deve ser titular do crédito, garantindo a existência, titularidade e boa formalização do referido crédito. As definições são as seguintes:

	* Cedente: Pessoa Jurídica, formalmente cadastrada na Santander Financiamentos e titular dos créditos a serem cedidos;
	* Cessionária: Aymoré Crédito, Financiamento e Investimento S/A.

2. Modalidades e Características

	2.1. Modalidade de Contratação pelo Cliente Final
	* CSC à vista: Ela permite a antecipação integral de um recebível, com valor pago 100% ao lojista de uma só vez, ou seja, à vista.
	* CSC Parceria: Permite a distribuição da liberação financeira devida ao cedente, sendo: percentual determinado para o fabricante e o valor restante para o lojista, proporcionando melhor gerenciamento financeiro e controle de fabricantes e fornecedores.
	* CSC Liberação Parcelada: Permite o pagamento de forma parcelada à empresa cedente dos créditos.

	2.2. Outras Características do Produto
	* As taxas são definidas conforme as políticas da área de Precificação Santander Financiamentos;
	* Não há incidência de tributos (IOF - Imposto sobre Operação Financeira ou ISS - Impostos sobre Serviços);
	* Os prazos são definidos conforme as políticas definidas pela área de Riscos;
	* Não há garantias e seguros.

3. Formas de Pagamento - Boleto e débito em conta corrente Santander.

4. Formalização do Produto - A formalização entre o Cliente e o intermediário (Cedente) é feita através da Ficha Cadastral para alguns intermediários autorizados pelo Superintendentes de Rede, os mesmos são responsáveis pela guarda dos documentos de formalização com as devidas responsabilidades formalizadas em aditamento ao credenciamento (conceito de fiel depositário).

Em resumo, o produto consiste na consessão de crédito para a Santander Financiamentos com o consentimento do cliente final. É uma
opção de parcelamento sem juros para o cliente final e com custo financeiro para o lojista, na qual o lojista e a fábrica recebem o valor total ou parcial da operação (descontada apenas a retenção da operação), conforme acordo previamente definido. 

### Descrição da Aplicação
O objetivo desta aplicação é servir de base para a utilização das chamadas aos serviços disponibilizados ao projeto INTEGRA, denominação aplicada ao projeto elaborado para desenvolver os serviços relacionados ao processo de pré-análise, simulação, envio de proposta, consulta e cancelamento de Propostas de CSC (ou Ordens) geradas na plataforma, referente ao produto Santander +Vezes (Financiamentos).

Para isso, o cliente deve estar previamente cadastrado na plataforma do Santander Financiamento, e seu estado deve estar Aprovado, selecionar o insumo desejado e escolher a forma de pagamento do boleto bancário à vista ou financiamento com débito em conta, no Infotravel.


<img align="middle" width="600" height="200" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_principal.jpeg">


## Principais Frameworks do Projeto
Os frameworks são pacotes de códigos prontos que facilita o desenvolvimento de aplicações, desta forma, utilizamos estes para obter funcionalidades para agilizar a construção da aplicação. Abaixo segue os frameworks utilizados para o desenvolvimento este projeto:

**Pré-Requisito**: Java 11 (11.0.13-OpenJDK 2021-10-19) | Maven 3 (3.6.3)

| Framework           |  Versão   |
|---------------------|:---------:|
| Spring Boot         | 2.6.2     |
| IT Common           | 1.9.14.0  |
| Gson                | 2.8.2     |
| Jackson             | 2.7.0     |
| RestEasy            | 3.12.1    |


## Sobre a Estrutura da REST API (Santander - Accenture)
O *Webwservice* recebe as *request* via REST POST/GET, na qual sua estrutura segue o padrão (JSON). Abaixo segue as bibliotecas utilizadas neste projeto a fim de dar embasamento ao código a ser implementado para criação do *webservice*.

| Framework           |   Tipo    |
|---------------------|:---------:|
| Tipo de Serviço     | Pagamento |
| Modelo              | REST      |
| Ling. Intermediária | JSON      |
| Protocolo           | HTTP      |
| Tipo Compactação    | GZIP      |

Modelo de Requisição REST utilizando os parâmetros Authentication Bearer e *Content-Type* setando o valor **"application/json; charset=utf-8"**


## Processo de Autenticação (TOKEN)
O fornecedor disponibiliza ao cliente credenciais no formato Base64 (Usuário de Consumo - *Consumer Key/Consumer Secret*) e Autenticação (*username/password*), na qual devem ser passadas a fim de abrir uma sessão. A partir da análise dos valores informados é gerado um *TOKEN* **(Base64)**, que válida os processos realizados durante a sessão. A chamada ao *endpoint* se faz através de um formulário codificado (*form-urlencoded*) que encaminhará uma requisição do tipo POST, na qual é realizada em duas etapas:

1. É fornecido os parâmetros para identificação do cliente através do formulário, anulando o parâmetro **storeId** (*null*) a fim de obter a lista de códigos de negócio disponibilidado para o cliente, denominado de *identificação da loja ou lojaID (StoreID)*;


<img align="middle" width="600" height="450" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/processo_autenticacao.jpeg">

Caso o retorno seja satisfatório, é retornado uma lista de código de negócios onde identica o cliente que deseja realizar a aquisição do produto CSC - Santander.

```json
{
 "stores": [
       {
           "statusCode": "A",
           "code": "1234290000",
           "name": "ORINTER VIAGENS",
           "id": 145497,
           "documentNumber": "82170291000120"
       },
       {
           "statusCode": "A",
           "code": "4725870000",
           "name": "VIAGENS PROMO T",
           "id": 263940,
           "documentNumber": "5008876000106"
       },
       {
           "statusCode": "I",
           "code": "4139400000",
           "name": "CATIVA",
           "id": 205952,
           "documentNumber": "5111606000118"
       }
   ]
}
```

2. Após obter o código correspondente ao lojista (cliente), é realizada uma nova chamada ao *endpoint*, mas agora fornecendo o número do lojista (StoreID) para obter o token de sessão;

<img align="middle" width="600" height="200" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/processo_autenticacao2.jpeg">

Desta forma, o retorno sendo satisfatório será devolvido o TOKEN *Transacional* que validará a sessão com inforações sobre seu tipo e validade.

```json
{
  "access_token": "eyJ4NXQiOiJNelU2IiwiYWxnIjoiUlMyNTYif.AI-xf57Mlh6eG162tFi_TA0DwSRP2ha1NwmLe4wpgSL_WqpMT3eIj8pxIlAw3rxozzwI3vUcy4h4L6QODQxK0qXOsXuITKKXIoEwWdjGAxUM9zpypiIQeW93y5NX_fgxcPg7zJAYyxvyfmMyu76Kkl6KvmjkPTncc5BT9t-irY1xg",
  "refresh_token": "",
  "token_type": "Bearer",
  "expires_in": "3600"
}
```

> **NOTA:** *O TOKEN Transacional (Bearer) permanece ativo até o tempo apontado em *expires_in* (segundos), na qual ao expirar será necessário realizar uma nova chamada para autenticação*.

### Formulário Codificado (Form-Encoded)
O método de autenticação utiliza um formulário codificado (*Form-Encoded*) a fim de passar parâmetros que identifiquem o cliente através do protocolo padrão **OAuth 2.0**. Para este fim, foi necessário utilizar o ***ObjectMapper (Jackson)*** que possibilita utilizar a funcionalidade de leitura e escrita JSON, tanto de elementos para POJOs básicos (*Plain Old Java Objects*), quanto para elementos de um Modelo de Árvore JSON de uso geral (*JsonNode*). 
Desta forma, como o ObjectMapper é personalizável foi implementada a classe auxiliar *ObjectUrlEncodedConverter*, para converter os parâmetros do formulário para os valores esperados pelo fornecedor, utilizando as funcionalidades avançadas de serialização e desserialização das classes ObjectReader e ObjectWriter. O Mapper (e ObjectReaders, ObjectWriters que constrói) usará instâncias da JsonParser e JsonGenerator para implementar a leitura/escrita do JSON.
O objetivo da criação desta classe é montar o formulário através da passagem de um objeto, denominado FormEncoded, que passará os parâmetros para o *HttpEntity* no *Rest Template* ao injetado no ***MessageConverters***.

<img align="middle" width="600" height="50" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/injecao_mapper.jpeg">

Instância da classe FormEncoded para passá-la como formulário codificado em HttpEntity:
```java
  private FormEncoded montarFormUrl(WSIntegrador integrador, AuthTokenRQ authToken) throws ErrorException{
      FormEncoded form = new FormEncoded();
      try {
          form.setGrantType("password");
          form.setUsername(authToken.getUsername());
          form.setPassword(authToken.getPassword());
          form.setBusinessCode(2);
          form.setLoginTypeId(9);
          form.setTpLoginCode("00008");
          form.setStoreId(authToken.getStoreId() != null && Utils.isNumerico(authToken.getStoreId()) ? Integer.parseInt(authToken.getStoreId()) : null);
          form.setRevokeSession(Boolean.TRUE);
      } catch (NumberFormatException e) {
          throw new ErrorException(integrador, RESTClient.class, "montarFormUrl", WSMensagemErroEnum.GENVAL, 
                  "Erro ao montar formulário (FormEncoded)" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
      }
      return form;
  }
```

## Verificação de Produtos (Financiamento)
O fornecedor devolve quais são os produtos de financiamento disponibilizado para cliente, onde para continuar com o fluxo de financiamento, se faz necessário invocar o *endpoint* de domínios (*Domains*) para listá-los. Desta forma, é necessário realizar uma requisição (GET) com a identificação da loja (lojaID - StoreID) retornado na primeira chamada em *token*.
```json
[
  {
    "id": 358,
    "code": "CDC",
    "desc": "Crédito Direto ao Consumidor",
    "subsegment": {
      "id": 33,
      "code": "350",
      "type": "Máquinas e Equipamentos"
    },
    "isEnabledForStore": true,
    "isEnabledForSalesman": false,
    "isEnabledForClientType": "PF"
  },
  {
    "id": 359,
    "code": "CSC",
    "desc": "Cessão",
    "subsegment": {
      "id": 33,
      "code": "350",
      "type": "Máquinas e Equipamentos"
    },
    "isEnabledForStore": true,
    "isEnabledForSalesman": false,
    "isEnabledForClientType": "BOTH"
  }
]
```


## Código de Requisições Santander
Para realizar a pré-analise e a análise de proposta, o Santander requisita algumas informações que são padronizadas por eles. Desta forma, nas requisições são passados parâmetros numerais ou em formato UUID que são obtidos através de chamadas auxiliares ao fornecedor. 

A tabela abaixo descreve a função,descrição e onde é requisitada pelo fornecedor, na qual utilizando o protocolo HTTP (GET) a fim de retornar os valores necessários.

|    Função    | Descrição                                | Protocolo |   Requisição    |
|:------------:|:----------------------------------------:|:---------:|:---------------:|
| Token        | Validação de usuário e criação de token para validação de sessão | POST | Sessão | 
| Products | Serviço utilizado para retornar os produtos de negócio disponibilizado pelo fornecedor a fim de obter seu código para as chamadas as suas funcionalidades | GET | Pré Analise |
| List-Terms | Retorna os termos e condições a serem aprovados para o quitante, referente aos produtos disponibilidados ao cliente, para dar continuidade ao fluxo de pagamento | GET | Pré-Analise |
| Consent-Register | Retorna os termos de políticas da Lei Geral de Proteção de Dados (LGPD - Lei n°13.853) a fim de ser acordada com o quitante para dar continuidade ao fluxo de pagamento | POST | Pré-Analise |
| Simulation | Cria uma requisição para encaminhar uma simulação com dados do quitante e os aceites dos termos | POST | Simulação |
| ProposalID | Verifica se a proposta foi encaminhada e as formas de pagamento possíveis disponibilizadas para o quitante | GET | Simulação |
| Finish | Finaliza a simulação encaminhando uma requisição com a proposta a ser aprovada pelo fornecedor | POST | Simulação |
| Register | Registra a proposta no fornecedor obtendo o retorno do que foi acordado com o fornecedor | GET | Proposta |
| Save | Realiza o envio da proposta para análise de fraude e geração do checklist, em caso de erro na requisição, precisará efetuar nova chamada em *Register* [domains/register/{idProposal}] para obter os dados atualizados, porque os dados podem ser salvo de forma parcial | POST | Proposta | 
| Check-List | Retorna a lista dos documentos necessários para firmar a proposta ao encaminhar seu número de identificação | POST | Proposta | 
| Upload-Documents | Realiza o envio do arquivo dos documentos digitalizados do quitante | POST | Proposta | 
| Formalization | Realiza a geração do link para a Assinatura Digital | POST | Proposta | 
| Formalization-Finish | Realiza a finalização da etapa de formalização do quitante | POST | Proposta | 

> **NOTA:** *Se uma **Proposta** estiver em andamento e outra simulação for realizada, a Proposta em andamento será **CANCELADA***.

### Ambientes
Para acesso aos ambientes (*Homologação/Produção*) da Santander Accenture se faz necessário a criação de uma conta pelo suporte técnico, na qual estes ambientes são totalmente distintos um do outro, pois seus endpoints são diferentes. Desta forma, a criação de uma não implica na criação da outra, sendo necessário solicitar uma conta especifica para o ambiente a ser utilizado.

|    Ambientes    |	         Endpoints             |
|:---------------:|:------------------------------:|
|  *HOMOLOGAÇÃO*  | https://brpiosantanderapihml.viverebrasil.com.br/   |
|  *PRODUÇÃO*	  | https://brpiosantanderapi.viverebrasil.com.br/  |

Estrutura de Chamadas ao Fornecedor - Coleção de Métodos [ Postman - BR Santander API ](https://github.com/InfoteraTecnologia/santander/blob/master/assets/santander.postman_collection.json)

| Credencial | Valores |
|:------:|:------:|
| Usuário | 09551043000130 |
| Senha | InfssteR@@2569 |

**Documentação Oficial da API:** [Santander - Accenture](https://brpiosantanderhml.viverebrasil.com.br/devportal/apis)

### Limites e Restrições
* O ambiente de homologação é um replicação do ambiente de produção, porém com um universo reduzido de massa e não necessariamente replica as condições de produção;
* Entre o 1º e 5º dia útilo o ambiente de homologação pode apresentar instabilidades em virtude da atualização dos sistemas;
* Realizar testes em 6h e 22h, após esse período, o ambiente de homologação também pode apresentar instabildiades;

### Definição de Formatos
Para permitir a serialização/deserialização de datas foi necessário implementar a instância do Gson para a passagem de um padrão (**pattern**) a fim de permitir o seu funcionamento. Desta forma, na configuração do Projeto (*VirtusPayConfiguration*) é implementado um Bean a fim de instância-lo ao iniciar o Spring.

```java
	@Bean
    public Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        Gson gson = builder.create();
        
		return gson;
    }
```

Dependendo a chamada realizada é aplicado formatos distintos para o parâmetro, referente ao valor Data (Date), já os demais seguem o mesmo valor em todas as chamadas aos métodos do fornecedor.
A tabela abaixo apresenta os parâmetros e seus respectivos valores, como tipo, obrigatoriedade e exemplo de uso.

|  Nome   |    Tipo    |   Tamanho   | Obrigatório | Descrição                                      |
|:-------:|:----------:|:-----------:|:-----------:|:----------------------------------------------:|
|  data   |   String   |    (8)     |     Sim     | Formato do tipo Data recebido em parâmetros nas classes |
|   cpf   |   String   |    (11)	 |     Sim     | CPF do Cliente*. Exemplo: “11122233344”        |
|cellphone|	  String   |    (11)	 |     Sim	   | Número do Celular com DDD do Cliente*. Exemplo: "21988889999" |
|  email  |   String   |             |     Sim	   | E-mail do Cliente*. Exemplo: “email@email.com.br” |
|   ip    |   String   |             |     Sim     | IP atual do Cliente*. Exemplo: “187.65.95.12” |
|  cep	  |   String   |    (8)      |     Sim     | CEP do Cliente*. Exemplo: “95000625” |


### As Funcionalidades do WebService
Toda a chamada ao *webservice* é necessário realizar a autenticação (***TOKEN***) a fim de ser autorizado e validar suas chamadas aos métodos, desta forma, para concluir este processo de autenticação são necessárias duas chamadas.

<img align="center" width="300" height="200" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/token1.jpeg">

A primeira funcionalidade é realizada sem o parâmetro *StoreID* para obter todos os IDs disponibilizados para o cliente.

<img align="center" width="300" height="200" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/token2.jpeg">

A segunda é obtido o valor do **StoreID** que identifica o cliente e realiza uma nova chamada a fim de obter o token de validação de sessão (*Bearer*). 
> Nota: O token (*Bearer*) deve ser passado para as demais chamadas enquanto o token não expira.

A funcionalidade de Pré-Aprovação (***PreApproved***) tem a função de autorizar o acesso a plataforma e também ao analisar os dados enviados sobre o cliente a fim de determinar as condições a qual ele tem acesso pela mesma. Desta forma, caso seu cadastro seja aprovado é encaminhado um range de opções de parcelamento, a qual ele tem possibilidade de parcelar o valor de compra informado.

<img align="center" width="400" height="400" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_pre_approved.jpeg">

A imagem acima demonstra como a chamada ao método preApproved (POST) ocorre ilustrando a requisição com o autorizador, onde no corpo (*body*) é passado os dados do cliente, as informações acordadas entre a Virtus Pay e a Infotera (**Other Info**), que consiste em informações sobre sua última compra pela plataforma Virtus, e o valor da compra.

A funcionalidade Proposta ou Ordem (***Order***) consiste em uma variação de funções conforme o tipo de protocolo invocado. Ao utilizar o protocolo **POST** é encaminhada uma ordem a ser analisada pela plataforma a fim de acatada ou não pela Virtus Pay, na qual são solicitadas alguns detalhes sobre o pedido, dados do cliente, informações sobre a parcela escolhida, ponto de acesso via *Webhook* e o ID da pré-aprovação (*ID PreApproved*) que é retornado na chamada anterior. 

<img align="center" width="400" height="400" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_order_proposta.jpeg">

Caso o resposta (*response*) seja satisfatória, é retornado um ID para a Transação (*ID Transaction*) a ser utilizado em todo o fluxo da proposta, além dele, é retornado os detalhes do pedido, dados do cliente, dados da compra e informações sobre o webhook.

Mas a proposta não é aprovada já ao envia-la, ela possuí estados de processamento que pode ser consultado ao utilizar o protocolo **GET** ao método *Order* a fim de verificar seu ciclo de aprovação.

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_consulta_order.jpeg">

Para este fim é necessário passar como parâmetro do protocolo o valor ID Transaction, que corresponde ao id da transação correspondente a proposta que foi encaminhado como resposta. Desta forma, em seu retorno será obtida a mesma resposta devolvida ao chamar a proposta, mas com a atualização sobre o seu estado.

Também existe a possibilidade do cliente querem cancelar a compra do insumo, desta forma, ao utilizar o protocolo **PUT** ao método *Order* é encaminhada uma solititação para cancelamento da operação.

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_cancel.jpeg">

Para isto, é necessário passar como parâmetro do protocolo o valor ID Transaction, com o acréscimo do parâmetro [/void], a fim de identificar a operação, passando em seu corpo (*body*) o tipo de cancelamento (TED / ORPAG), o motivo do cancelamento, **(optional)** os dados bancário e **(opcional)** o valor a ser estornado. Desta forma, em seu retorno será obtida informação sobre o estado da ordem e o estado do processamento do cancelamento.

Existe também a possibilidade de consultar periodicamente o estado deste processamento ao realizar uma chamada utilizando o protocolo **GET** ao método *Order*, mas acrescentando ao final da chamada o parâmetro [/void].

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/fluxo_consulta_cancel.jpeg">

Desta forma, como retorno será obtido as informações atualizadas sobre o estado da ordem e o estado do processamento da operação de cancelamento.


### Código de Estados (Request Status)
A tabela abaixo contém os possíveis status de retorno.
| Codigo | Nome       | Descrição                                     | Ação            |
|:---:|:-------------:|:---------------------------------------------:|:---------------:|
| 400 | Bad Request   | Essa resposta significa que o servidor não entendeu a requisição pois está com uma sintaxe inválida. (por exemplo, sintaxe de solicitação malformada, enquadramento de mensagem de solicitação inválido). | Conferir os campos da chamada. |
| 401 | Unauthorized  | Não autenticado. | O cliente deve se autenticar para obter a resposta solicitada.
| 403 | Forbidden     | O cliente não tem direitos de acesso ao conteúdo portanto o servidor está rejeitando dar a resposta. | Pedir liberação de acesso. |
| 404 | Not Found     | Serviço não encontrado. | Tentar novamente mais tarde. |
| 429 | Too Many Requests | O usuário enviou muitas solicitações em um determinado período (limitação de taxa) | Tentar novamente mais tarde. |
| 500 | Internal Server Error| O servidor encontrou uma situação com a qual não sabe lidar. (Erro no servidor) | Reportar o erro para o time tecnico. |
| 503 | Service Unavailable | O servidor não está pronto para manipular a requisição. Causas comuns são um servidor em manutenção ou sobrecarregado. |  Tentar novamente mais tarde |
| 504 | Gateway Timeout | Esta resposta de erro é dada quando o servidor está atuando como um gateway e não obtém uma resposta a tempo | Tentar novamente mais tarde |

## Suporte Técnico
O contato para suporte disponível é através de endereço eletrônico [atendimento@santander.com.br](https://brpiosantanderhml.viverebrasil.com.br/devportal/apis), na qual não é apontado prazos para SLA e horários para atendimento.

