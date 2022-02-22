# Santander API 
> API consiste em uma ponte ao fluxo de processos pagamentos eletrônicos através de boletos e financiamento para clientes e não clientes do Banco Santander.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-Maven-000?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)


<img align="right" width="200" height="150" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/santander-banner.jpeg">

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
| RestEasy            | 3.12.1    |

## Sobre a Estrutura da REST API (VirtusPay)
O *Webwservice* recebe as *request* via REST POST/PUT/GET, na qual sua estrutura segue o padrão (JSON). Abaixo segue as bibliotecas utilizadas neste projeto a fim de dar embasamento ao código a ser implementado para criação do *webservice*.

| Framework           |   Tipo    |
|---------------------|:---------:|
| Tipo de Serviço     | Pagamento |
| Modelo              | REST      |
| Ling. Intermediária | JSON      |
| Protocolo           | HTTP      |
| Tipo Compactação    | GZIP      |

Modelo de Requisição REST utilizando os parâmetros Authentication Bearer e *Content-Type* setando o valor **"application/json; charset=utf-8"**


## Processo de Autenticação (TOKEN)
O fornecedor disponibiliza ao cliente um identificador no formato de TOKEN que deve ser informado em conjunto com usuário e senha a fim de caso validado seja fornecido um identificador final (TOKEN) com o intuito de validar todo o processo transacional.

<img align="middle" width="600" height="500" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/processo_autenticacao.jpeg">

O token inicial (identificador do cliente) deve ser enviado no cabeçalho da chamada a autenticação, na qua caso seja validada será fornecido um segundo token (transacional) a fim a ser utilizada nas demais chamadas do processo. Desta forma, o token inicial é só utilizado na primeira chamada ao método de autenticação.

<img align="middle" width="600" height="200" src="https://github.com/InfoteraTecnologia/santander/blob/master/assets/processo_autenticacao2.jpeg">

> **NOTA:** *O TOKEN Transacional permanece ativo por 15 (quinze) minutos, onde ao expirar este tempo será necessário realizar uma nova chamada para autenticação*.


## Código de Requisições Santander
Para realizar a pré-analise e a análise de proposta, o Santander requisita algumas informações que são padronizadas por eles. Desta forma, nas requisições são passados parâmetros numerais ou em formato UUID que são obtidos através de chamadas auxiliares ao fornecedor. 

A tabela abaixo descreve a função,descrição e onde é requisitada pelo fornecedor, na qual utilizando o protocolo HTTP (GET) a fim de retornar os valores necessários.

|    Função    | Descrição                                |   Requisição    |
|:------------:|:----------------------------------------:|:---------------:|
| Identificar TAB | Serviço de utilizado para retornar o id da loja/tab a ser utilizada através da pesquisa pelo seu código de TAB | Pré Analise / Simulação / Proposta / Obj Financiado / Qtd Parcelas |
| Obj Financiado | Serviço de domínio que retorna os produtos (objetos financiado) relacionados à TAB | Pré-Analise |
| SubSegmento | Serviço destinado ao retorno dos subsegmentos atribuídos à TAB cadastrada nas bases do Santander | Pré-Analise |
| Qtd Parcelas | Serviço de domínio que retorna a quantidade máxima de parcelas que são disponíveis para TAB informada  | Pré-Analise |
| Formas de Pagto | Serviço de domínio que retorna a lista de Formas de Pagamentos disponíveis para a operação de acordo com o produto | Simulação |
| Profissões | Serviço de domínio que retorna a lista de Categorias/profissões utilizadas na captura da proposta | |
| Estado Cívil | Serviço de domínio que retorna a lista de Estados Civis disponíveis para a utilização na captura da proposta | |
| Naturalidade | Serviço de domínio que a partir da pesquisa por UF, retorna a lista de municípios | |
| 



> **NOTA:** *O objeto OtherInfo são parâmetros solicitado pelo fornecedor (Virtus Pay), desta forma, é obrigatório sua passagem para permitir que a autenticação seja avaliada. Caso contrário, o fornecedor não permitirá a continuação do fluxo de pagamento pelo webservice*.


**Documentação Oficial da API:** [VirtusPay Support](https://documenter.getpostman.com/view/215460/SVSPnmLs#intro)


### Ambientes
Para acesso aos ambientes (*Homologação/Produção*) da VirtusPay se faz necessário a criação de uma conta pelo suporte técnico, na qual estes ambientes são totalmente distintos um do outro, pois seus endpoints são diferentes. Desta forma, a criação de uma não implica na criação da outra, sendo necessário solicitar uma conta especifica para o ambiente a ser utilizado.

|    Ambientes    |	         Endpoints             |
|:---------------:|:------------------------------:|
|  *HOMOLOGAÇÃO*  | https://hml.usevirtus.com.br/api/   |
|  *PRODUÇÃO*	  | https://core.usevirtus.com.br/api/  |


### Limites e Restrições
Com o objetivo de ampliar o acesso ao crédito, o VirtusPay possibilita que os usuários paguem suas compras seguindo alguns critérios para este fim, na qual o webservice deve se atentar para não haver inconsistências no estado das ordens. São elas:

1. São aceitas ordens apenas maiores de idade, CPF sem restrições, com RG ou CNH em dia; 
2. São parceladas apenas uma compra por vez por CPF;
3. A compra deve ter valor mínimo de R$ 150,00 e máximo de R$ 10.000,00;
4. O cliente pode escolher entre 3 a 15 parcelas, a depender do valor da compra;
5. A Data de vencimento do boleto parcelado será referente a data de aprovação do seu crédito.

Quanto a integração entre os webservices também temos alguns limites e restrições, no manuseio a ser aplicado para possibilitar uma interação sadia entre os mesmos. São elas:
* Já na primeira chamada ao webservice (PreApproved) é realizada uma análise ao perfil do cliente cadastrado em sua plataforma, na qual determina o range de opções para parcelamento de sua compra. Desta forma, ao ser aprovada é disponibilizado um ***valor a ser quitado em um prazo de 24 hrs***, no parâmetro *down_payment* (Valor de Entrada);
* Ao realizar a chamada ao método para realizar um cancelamento (Order/[ID Transaction]/void) é possível selecionar dois tipos de reembolso (*Refund By*), o TED e o ORPAG. O primeiro obriga a passagem de parâmetros adicionais como Dados Bancários (*Extra Data*) do cliente, que terá o Valor Parcial (*Amount*) ou Total, onde o amount poderá ser omitido, a ser devolvido em Conta Bancária;
* O fornecedor (Virtus Pay) permite a atualização das propostas encaminhadas a sua plataforma através de *Impulso API* ou *Comunicação Passiva*, através de um **Webhook**, isto é possível ao informar no parâmetro **callback**, onde será o ponto de acesso ao insumo a ser atualizado, evitando assim a necessidade de periodicamente realizar uma consulta a fim de atualizar o *status* de uma proposta ou *status* do processamento de um cancelamento de proposta. Desta forma, ***o sistema realizará 3 tentativas de notificação com intervalo de 30min caso o “HTTP response status code” seja diferente de “200” [OK]***;


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
|  data   |    Date    |    (24)     |     Sim     | Formato do tipo Data recebido em parâmetros nas classes |
|   cpf   |   String   |    (11)	 |     Sim     | CPF do Cliente*. Exemplo: “11122233344”        |
|cellphone|	  String   |    (11)	 |     Sim	   | Número do Celular com DDD do Cliente*. Exemplo: "21988889999" |
|  email  |   String   |             |     Sim	   | E-mail do Cliente*. Exemplo: “email@email.com.br” |
|   ip    |   String   |             |     Sim     | IP atual do Cliente*. Exemplo: “187.65.95.12” |
|  cep	  |   String   |    (8)      |     Sim     | CEP do Cliente*. Exemplo: “95000625” |


### As Funcionalidades do WebService

Toda a chamada ao webservice se faz necessário de se autenticar a fim de ser autorizado a trafegar informações entre os *webservices*, desta forma, é passado em toda requisição (*request*) o autorizador (Authorization Token) do tipo *Token*, que é encaminhado no cabeçalho (*header*) do envelopamento SOAP, a fim de ser validado pelo webservice da Virtus Pay a fim de validar o cliente que deseja acessar a plataforma.

A funcionalidade de Pré-Aprovação (***PreApproved***) tem a função de autorizar o acesso a plataforma e também ao analisar os dados enviados sobre o cliente a fim de determinar as condições a qual ele tem acesso pela mesma. Desta forma, caso seu cadastro seja aprovado é encaminhado um range de opções de parcelamento, a qual ele tem possibilidade de parcelar o valor de compra informado.

<img align="center" width="400" height="400" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/fluxo_pre_approved.jpeg">

A imagem acima demonstra como a chamada ao método preApproved (POST) ocorre ilustrando a requisição com o autorizador, onde no corpo (*body*) é passado os dados do cliente, as informações acordadas entre a Virtus Pay e a Infotera (**Other Info**), que consiste em informações sobre sua última compra pela plataforma Virtus, e o valor da compra.

A funcionalidade Proposta ou Ordem (***Order***) consiste em uma variação de funções conforme o tipo de protocolo invocado. Ao utilizar o protocolo **POST** é encaminhada uma ordem a ser analisada pela plataforma a fim de acatada ou não pela Virtus Pay, na qual são solicitadas alguns detalhes sobre o pedido, dados do cliente, informações sobre a parcela escolhida, ponto de acesso via *Webhook* e o ID da pré-aprovação (*ID PreApproved*) que é retornado na chamada anterior. 

<img align="center" width="400" height="400" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/fluxo_order_proposta.jpeg">

Caso o resposta (*response*) seja satisfatória, é retornado um ID para a Transação (*ID Transaction*) a ser utilizado em todo o fluxo da proposta, além dele, é retornado os detalhes do pedido, dados do cliente, dados da compra e informações sobre o webhook.

Mas a proposta não é aprovada já ao envia-la, ela possuí estados de processamento que pode ser consultado ao utilizar o protocolo **GET** ao método *Order* a fim de verificar seu ciclo de aprovação.

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/fluxo_consulta_order.jpeg">

Para este fim é necessário passar como parâmetro do protocolo o valor ID Transaction, que corresponde ao id da transação correspondente a proposta que foi encaminhado como resposta. Desta forma, em seu retorno será obtida a mesma resposta devolvida ao chamar a proposta, mas com a atualização sobre o seu estado.

Também existe a possibilidade do cliente querem cancelar a compra do insumo, desta forma, ao utilizar o protocolo **PUT** ao método *Order* é encaminhada uma solititação para cancelamento da operação.

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/fluxo_cancel.jpeg">

Para isto, é necessário passar como parâmetro do protocolo o valor ID Transaction, com o acréscimo do parâmetro [/void], a fim de identificar a operação, passando em seu corpo (*body*) o tipo de cancelamento (TED / ORPAG), o motivo do cancelamento, **(optional)** os dados bancário e **(opcional)** o valor a ser estornado. Desta forma, em seu retorno será obtida informação sobre o estado da ordem e o estado do processamento do cancelamento.

Existe também a possibilidade de consultar periodicamente o estado deste processamento ao realizar uma chamada utilizando o protocolo **GET** ao método *Order*, mas acrescentando ao final da chamada o parâmetro [/void].

<img align="middle" width="400" height="400" src="https://github.com/InfoteraTecnologia/virtuspay/blob/master/assets/fluxo_consulta_cancel.jpeg">

Desta forma, como retorno será obtido as informações atualizadas sobre o estado da ordem e o estado do processamento da operação de cancelamento.


### Código de Estados (Request Status)
As tabelas abaixo contém os possíveis status de retorno.
A primeira tabela corresponde ao estado de proposta.
| Status | Descrição |
|:------:|:---------:|
| **P** | ***Pendente ->*** o cliente criou um pedido com a VirtusPay porém não seguiu com a jornada de crédito; |
| **N** | ***Analisada ->*** a proposta se encontra com nossa mesa de crédito; |
| **A** | ***Aprovada ->*** a proposta foi aprovada por nossa mesa de crédito |
| **R** | ***Recusada ->*** a proposta foi recusada por nossa mesa de crédito; |
| **C** | ***Cancelada ->*** a proposta foi cancelada por ficar pendente mais que 48h ou por solicitação da loja/cliente; |
| **E** | ***Efetivada ->*** o cliente pagou a entrada, portanto o pedido pode ser liberado no sistema; |


A segunda tabela corresponde aos estados do processamento de proposta para o cancelamento:
| Status | Descrição |
|:------:|:---------:|
| **PEN** | ***Pendente ->*** o cancelamento ainda não foi concluído, porém solicitado |
| **ENV** | ***Enviado ->*** o reembolso foi enviado  |
| **EFE** | ***Efetivado ->*** o reembolso foi finalizado |

A terceira tabela está relacionada ao recurso para verificar o repasse e agenda de repasse. 
| Status | Descrição |
|:------:|:---------:|
| **REA** | ***Realizado ->*** o repasse foi realizado |
| **CAN** | ***Cancelado ->*** o repasse foi cancelado, isso acontece por exemplo quando cancelamos uma proposta |
| **AGE** | ***Agendado ->*** o repasse está agendado com data de previsão |
| **ERR** | ***Erro ->*** O repasse não foi enviado por algum erro, isso pode acontecer por exemplo caso exista alguma inconsistência nos dados bancários da empresa |

## Suporte Técnico
O contato para suporte disponível é através de endereço eletrônico [atendimento@usevirtus.com.br](atendimento@usevirtus.com.br), na qual não é apontado prazos para SLA e horários para atendimento.
