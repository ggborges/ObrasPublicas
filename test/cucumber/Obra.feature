Feature: Obra
  As a usuario do sistema
  I want to adicionar, remover, modificar e visualizar obras no sistema
  So That eu posso gerar paginas web para exibir informacoes sobre a obra

#CONTROLLER
Scenario: Adicionar obra nao existente
  Given que o sistema nao tem uma obra chamada "Praca do arsenal"
  When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
  Then o sistema ira cadastrar a obra de nome "Praca do arsenal"

Scenario: Buscar percentual de obras atrasadas
  Given que o sistema tem uma lista de "4" Obras
  And o sistema tem "3" obra atrasada
  Then o percentual de atrasos sera de "75" por cento

Scenario: Buscar percentual de obras com orcamento estourado
  Given que o sistema tem uma lista de "4" Obras com seus orcamentos
  And o sistema tem "3" obra com orcamento estourado
  Then o percentual de orcamento estourado sera de "75" por cento

Scenario: Adicionar obra existente
  Given que o sistema tem uma obra chamada "Praca do arsenal"
  When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
  Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"

Scenario: Atualizar obra
  Given que o sistema tem uma obra chamada "Praca do arsenal"
  When eu tentar atualizar os dados da obra com o nome "Praca do arsenal"
  Then o sistema atualiza a obra com o nome "Praca do arsenal"

Scenario: Remover obra existente 
    Given que o sistema tem uma obra chamada "Praca do arsenal"
    When eu tentar remover a obra com o nome "Praca do arsenal"
    Then o sistema ira remover a obra com nome "Praca do arsenal"

Scenario: Verificar status andamento obra
    Given que o sistema tem uma obra chamada "Praca do arsenal" que esta atrasada mas esta com status "emDia"
    When eu tentar verificar o status da obra com o nome "Praca do arsenal"
    Then o sistema ira atualizar obra com nome "Praca do arsenal" para "atrasada"

Scenario: Adicionar obra com data de termino anterior a data de início
    Given que o sistema nao tem uma obra chamada "Praca do arsenal"
    When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
    And insiro a data inicial "12 October 2017" e a data final "12 October 2013"
    Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"

Scenario:  Adicionar obra com data de termino anterior a data corrente
    Given que o sistema nao tem uma obra chamada "Praca do arsenal"
    When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
    And insiro a data final "12 October 2013"
    Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"

Scenario: Atualizar nome de obra com nome já existente
    Given que o sistema tem uma obra chamada "Praca do arsenal"
    And tem uma obra com o nome "Ilha do Retiro"
    When eu tentar atualizar o nome da obra "Praca do arsenal" com o nome "Ilha do Retiro"
    Then o sistema não atualiza a obra com o novo nome "Ilha do Retiro"

Scenario: Adicionar obra de um politico inexistente
    Given que o sistema nao tem uma obra chamada "Praca do arsenal"
    And não existe um politico com o cpf "11122233344"
    When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
    Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"

Scenario: Devolver a taxa de obras atrasadas de um determinado político
    Given que o sistema tem um politico com o cpf "98765432109"
    And o sistema tem "2" obras associada ao politico com o cpf "98765432109"
    And o sistema tem "1" obra atrasada associada ao politico com o cpf "98765432109"
    Then o percentual de obras atrasadas para o politico com o cpf "98765432109" é de "50" por cento

Scenario: Devolver a taxa de obras com orçamentos estourados de um determinado político
    Given que o sistema tem um politico com o cpf "98765432109"
    And o sistema tem "2" obras associada ao politico com o cpf "98765432109"
    And o sistema tem "1" obra com orçamento estourado associada ao politico com o cpf "98765432109"
    Then o percentual de obras com orcamento estourado para o politico com o cpf "98765432109" é de "50" por cento

#GUI

Scenario: Visualizar obra
  Given que o usuario esta no menu de obras e quer visualizar os detalhes da obra "Praca do arsenal"
  When o usuario seleciona a obra "Praca do arsenal"
  Then o sistema exibe os detalhes da obra "Praca do arsenal"
