import obraspublicas.*
import pages.ObraListPage
import pages.TaxaAtrasoPoliticoPage
import steps.TestDataAndOperations
import static cucumber.api.groovy.EN.*
import pages.PoliticoListPage
import pages.PoliticoShowPage


//#CONTROLLER
//Scenario: Adicionar politico nao existente
//Given que o sistema nao tem um politico com CPF "01234567891"
//When eu tentar cadastrar um politico com CPF "01234567891"
//Then o sistema ira cadastrar o politico de CPF "01234567891"

Given(~'^que o sistema nao tem um politico com CPF "([^"]*)"$'){
    String cpf ->
        Politico politico = Politico.findByCpf(cpf)
        assert politico == null
}

When(~'^eu tentar cadastrar um politico com CPF "([^"]*)"$'){
    String cpf ->
        TestDataAndOperations.createPolitico(cpf)
}

Then(~'^o sistema ira cadastrar o politico de CPF "([^"]*)"$'){
    String cpf ->
        Politico politico = Politico.findByCpf(cpf)
        assert TestDataAndOperations.politicoCompatibleTo(politico, cpf)
}

//Scenario: Atualizar id de um politico
//Given que exista um politico com  CPF "01234567890”
//When eu tentar atualizar o id do político  com CPF "01234567890”
//Then o olitico de CPF "01234567890" tera seu id atualizado
Given(~'^o politico com CPF "([^"]*)” esta armazenado no sistema$'){
    String  cpf ->
        TestDataAndOperations.createPolitico(cpf)
        Politico politico = Politico.findByCpf(cpf)
        assert politico != null
}

When (~'^eu tentar atualizar o id do politico de CPF "([^"]*)”$') {
    String cpf ->
        Long newId
        TestDataAndOperations.updateIdPolitico(cpf, newId)
}

Then(~'^o politico de CPF "([^"]*)" tera seu id atualizado$') {
    String cpf->
        Politico politico= Politico.findByCpf(cpf)
        assert TestDataAndOperations.politicoCompatibleTo(politico, cpf)
}
//Scenario: Remover político existente
//Given o politico com CPF "01234567890” esta armazenado no sistema
//When eu tentar remover o político com CPF “01234567890”
//Then o politico de CPF “01234567890” sera removido


When(~'^eu tentar remover o politico de CPF “([^"]*)”$') {
    String cpf ->
        sizePoliticoBefore = Politico.count()
        TestDataAndOperations.deletePolitico(cpf)

}

Then(~'^o politico de CPF “([^"]*)” sera removido$'){
    String cpf ->
        assert Politico.count() == (sizePoliticoBefore-1)
}

//Scenario: Adicionar político existente
//Given o sistema tem um político com nome “Eduardo” e CPF "01234567890”
//When eu tentar cadastrar um político com o nome “Eduardo” e CPF “01234567890”
//Then o sistema não irá cadastrar o político

Given(~'^que o sistema tem um politico de CPF "([^"]*)"$'){
    String cpf ->
        TestDataAndOperations.createPolitico(cpf)
        Politico politico = Politico.findByCpf(cpf)
        assert politico != null
}

Then(~'^o sistema nao ira cadastrar o politico de CPF "([^"]*)"$'){
    String cpf ->
        politicos = Politico.findAllByCpf(cpf)
        assert politicos.size() == 1
}
/************
 tpa
 ******/

//Scenario: Visualizar político GUI
//Given que o usuario esta no menu de político e quer visualizar os detalhes do político com o cpf "98765432109"
//When o usuario seleciona o político com o cpf "98765432109"
//Then o sistema exibe os detalhes do político com o cpf "98765432109"


Given(~'^que o usuario esta no menu de político e quer visualizar os detalhes do político com o cpf "([^"]*)"$') {
    String cpf ->
        TestDataAndOperations.createPolitico(cpf)
        to PoliticoListPage
        at PoliticoListPage
}

When(~'^o usuario seleciona o político com o cpf "([^"]*)"$') {
    String cpf ->
        at PoliticoListPage
        assert page.checkPoliticoAtList(cpf) == true
        page.selectPoliticoAtList(cpf)
}

Then(~'^o sistema exibe os detalhes do político com o cpf "([^"]*)"$'){
    String cpf ->
        at PoliticoShowPage
        assert page.verifyCpfPolitico(cpf) == true
}
