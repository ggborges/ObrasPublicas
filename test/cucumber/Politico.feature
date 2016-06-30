Feature: Políticos
  As a usuário do sistema
  I want to adicionar, remover, modificar e visualizar políticos no sistema
  So That eu posso gerar páginas web para exibir informações sobre o político

#CONTROLLER
  Scenario: Adicionar politico nao existente
    Given que o sistema nao tem um politico com CPF "01234567891"
    When eu tentar cadastrar um politico com CPF "01234567891"
    Then o sistema ira cadastrar o politico de CPF "01234567891"

  Scenario: Adicionar politico existente
    Given que o sistema tem um politico de CPF "98765432109"
    Then o sistema nao ira cadastrar o politico de CPF "98765432109"

  Scenario: Atualizar o id de um politico
    Given o politico com CPF "01234567891” esta armazenado no sistema
    When eu tentar atualizar o id do politico de CPF "01234567891”
    Then o politico de CPF "01234567891" tera seu id atualizado

  Scenario: Remover político existente
    Given o politico com CPF "01234567891” esta armazenado no sistema
    When eu tentar remover o politico de CPF “01234567891”
    Then o politico de CPF “01234567891” sera removido


#GUI
Scenario: Visualizar político
    Given que o usuario esta no menu de político e quer visualizar os detalhes do político com o cpf "98765432109"
    When o usuario seleciona o político com o cpf "98765432109"
    Then o sistema exibe os detalhes do político com o cpf "98765432109"
