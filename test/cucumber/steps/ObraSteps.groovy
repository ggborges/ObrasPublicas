import cucumber.api.PendingException
import pages.ObraListPage
import pages.ObraShowPage
import util.ObraStrings

import static cucumber.api.groovy.EN.*
import obraspublicas.*
import steps.TestDataAndOperations

//CONTROLLER
//Scenario: Adicionar obra não existente
//	Given que o sistema não tem uma obra chamada “Praça do arsenal”
//	When eu tentar cadastrar uma obra com o nome “Praça do arsenal”
//	Then o sistema irá cadastrar a obra de nome "Praça do arsenal"
/**
* @author = ehmr
*/
Given(~'^que o sistema nao tem uma obra chamada "([^"]*)"$'){
	String nomeObra -> 
		Obra obra = Obra.findByNome(nomeObra)
		assert obra == null
		//assert true
}

When(~'^eu tentar cadastrar uma obra com o nome "([^"]*)"$'){
	String nomeObra ->
    	TestDataAndOperations.createObra(nomeObra)
}

Then(~'^o sistema ira cadastrar a obra de nome "([^"]*)"$'){
	String nomeObra ->
		Obra obra = Obra.findByNome(nomeObra)
		assert TestDataAndOperations.obraCompatibleTo(obra, nomeObra)
}


//Scenario: Adicionar obra existente
//	Given que o sistema tem uma obra chamada “Praça do arsenal”
//	When eu tentar cadastrar uma obra com o nome “Praça do arsenal”
//	Then o sistema não irá cadastrar a obra de nome "Praça do arsenal"
/**
* @author = ehmr
*/
Given(~'^que o sistema tem uma obra chamada "([^"]*)"$'){
	String nomeObra ->
		TestDataAndOperations.createObra(nomeObra)
		Obra obra = Obra.findByNome(nomeObra)
		assert obra != null
}

Then(~'^o sistema nao ira cadastrar a obra de nome "([^"]*)"$'){
	String nomeObra ->
		obras = Obra.findAllByNome(nomeObra)
    	assert obras.size() == 1
}

/**
 * @author = rrms
 */
//Scenario: Buscar percentual de obras atrasadas
//Given que o sistema tem uma lista de "4" Obras
//And o sistema tem "3" obra atrasada
//Then o percentual de atrasos sera de "75" por cento
Given(~'^que o sistema tem uma lista de "([^"]*)" Obras$'){
	int qtdObras ->
		obras = TestDataAndOperations.getObras()
		assert obras.size()==qtdObras
}

And(~'^o sistema tem "([^"]*)" obra atrasada$'){
	int qtdObrasAtrasadas ->
		int qtdAtrasos = TestDataAndOperations.qtdObrasAtrasadas();
		assert qtdAtrasos==qtdObrasAtrasadas
}

Then(~'^o percentual de atrasos sera de "([^"]*)" por cento$'){
	int percentualAtrasos ->
		assert TestDataAndOperations.relatorioAtraso()==percentualAtrasos
}
/**
 * @author = rrms
 */
//Scenario: Buscar percentual de obras com orcamento estourado
//Given que o sistema tem uma lista de "4" Obras com seus orcamentos
//And o sistema tem "3" obra com orcamento estourado
//Then o percentual de orcamento estourado sera de "75" por cento
Given(~'^que o sistema tem uma lista de "([^"]*)" Obras com seus orcamentos$'){
	int qtdObras ->
		obras = TestDataAndOperations.getObras()
		assert obras.size()==qtdObras
}

And(~'o sistema tem "([^"]*)" obra com orcamento estourado$'){
	int qtdObrasAtrasadas ->
		int qtdAtrasos = TestDataAndOperations.qtdObrasAtrasadas();
		assert qtdAtrasos==qtdObrasAtrasadas
}

Then(~'^o percentual de orcamento estourado sera de "([^"]*)" por cento$'){
	int percentualAtrasos ->
		assert TestDataAndOperations.relatorioAtraso()==percentualAtrasos
}


//Scenario Visualizar obra
//	Given que o usuário está no menu de obras e quer visualizar os detalhes da obra "Praça do arsenal"
//	When o usuário seleciona a obra "Praça do arsenal"
//	Then o sistema exibe os detalhes da obra "Praça do arsenal"
/**
 * @author = ehmr
 */
Given(~'^que o usuario esta no menu de obras e quer visualizar os detalhes da obra "([^"]*)"$') {
	String nomeObra ->
		TestDataAndOperations.createObra(nomeObra)
		to ObraListPage
		at ObraListPage
}

When(~'^o usuario seleciona a obra "([^"]*)"$') {
	String nomeObra ->
		at ObraListPage
		assert page.checkObraAtList(nomeObra) == true
		page.selectObraAtList(nomeObra)
}

Then(~'^o sistema exibe os detalhes da obra "([^"]*)"$'){
	String nomeObra ->
		at ObraShowPage
		assert page.verifyNomeObra(nomeObra) == true
}

/**
 * @author = tpa
 */
//Scenario: Atualizar obra
//	Given que existe uma obra no sistema chamada "Praca do arsenal"
//	When eu tentar atualizar os dados da obra com o nome "Praca do arsenal"
//	Then o sistema atualiza a obra com o nome "Praca do arsenal"
When (~'^eu tentar atualizar os dados da obra com o nome "([^"]*)"$'){
	String nomeObra ->                        //tem que verificar
		TestDataAndOperations.atualizaObra(nomeObra)//tem que verificar
}
Then (~'^o sistema atualiza a obra com o nome "([^"]*)"$'){
	String nomeObra ->
		Obra obra = Obra.findByNome(nomeObra)
		assert TestDataAndOperations.obraCompatibleTo(obra, nomeObra)
}
/**
 * @author = tpa
 */
//Scenario: Adicionar obra com data de termino anterior a data de início
//Given que o sistema nao tem uma obra chamada "Praca do arsenal"
//When eu tento cadastrar uma obra com o nome "Praca do arsenal"
//And insiro a data inicial "12 October 2017" e a data final "12 October 2013"
//Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"

And (~'^insiro a data inicial "([^"]*)" e a data final "([^"]*)"$'){
	String ini, fin ->
		assert TestDataAndOperations.checkDataFI(ini,fin) == true
}
/**
 * @author = tpa
 */
//Scenario:  Adicionar obra com data de termino anterior a data corrente
//Given que o sistema nao tem uma obra chamada "Praca do arsenal"
//When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
//And insiro a data final "12 October 2013"
//Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"
And (~'^insiro a data final "([^"]*)"$'){
	String fin ->
		assert TestDataAndOperations.checkDataFC(fin) == true
}
/**
 * @author = tpa
 */
//Scenario: Atualizar nome de obra com nome já existente
//Given que o sistema tem uma obra chamada "Praca do arsenal"
//And tem uma obra com o nome "Ilha do Retiro"
//When eu tentar atualizar o nome da obra "Praca do arsenal" com o nome "Ilha do Retiro"
//Then o sistema não atualiza a obra com o novo nome "Ilha do Retiro"

And(~'^tem uma obra com o nome "([^"]*)"$'){
	String nomeObra ->
		Obra obra = TestDataAndOperations.findObraByNome(nomeObra)
		assert obra != null
}
When(~'^eu tentar atualizar o nome da obra "([^"]*)" com o nome "([^"]*)"$'){
	String nome1, nome2 ->
		assert TestDataAndOperations.checkAtualizaNome(nome1, nome2) == false
}

Then(~'^o sistema não atualiza a obra com o novo nome "([^"]*)"$'){
	String nomeObra ->
		obras = Obra.findAllByNome(nomeObra)
		assert obras.size() <= 1
}
/**
 * @author = tpa
 */
//Scenario: Adicionar obra de um politico inexistente
//Given que o sistema nao tem uma obra chamada "Praca do arsenal"
//And não existe um politico com o cpf "11122233344"
//When eu tentar cadastrar uma obra com o nome "Praca do arsenal"
//Then o sistema nao ira cadastrar a obra de nome "Praca do arsenal"
And(~'^não existe um politico com o cpf "([^"]*)"$'){
	String cpf ->
		Obra obra = TestDataAndOperations.findPoliticoByCPF(cpf)
		assert obra == null
}
/**
 * @author = tpa
 */
//Scenario: Devolver a taxa de obras atrasadas de um determinado político
//Given que o sistema tem um politico com o cpf "98765432109"
//And o sistema tem "2" obras associada ao politico com o cpf "98765432109"
//And o sistema tem "1" obra atrasada associada ao politico com o cpf "98765432109"
//Then o percentual de obras atrasadas para o politico com o cpf "98765432109" é de "50" por cento

Given(~'^que o sistema tem um politico com o cpf "([^"]*)"$'){
	String cpf ->
		Politico politico = TestDataAndOperations.findPoliticoByCPF(cpf)
		assert politico != null
}

And(~'^o sistema tem "([^"]*)" obras associada ao politico com o cpf "([^"]*)"$'){
	int qtdObrasPolitico, String cpf ->
		int qtd = TestDataAndOperations.qtdObrasPolitico(cpf)
		assert qtd == qtdObrasPolitico
}
And(~'^o sistema tem "([^"]*)" obra atrasada associada ao politico com o cpf "([^"]*)"$'){
	int qtdObrasAtrasadasPolitico, String cpf ->
		int qtd = TestDataAndOperations.qtdObrasAtrasadasPolitico(cpf)
		assert qtdObrasAtrasadasPolitico == qtd
}
Then(~'^o percentual de obras atrasadas para o politico com o cpf "([^"]*)" é de "([^"]*)" por cento$'){
	String cpf, float porcetagem ->
		float qtd = TestDataAndOperations.taxaObrasAtrasadasPolitico(cpf)
		assert porcetagem == qtd
}
/**
 * @author = tpa
 */
//Scenario: Devolver a taxa de obras com orçamentos estourados de um determinado político
//Given que o sistema tem um politico com o cpf "98765432109"
//And o sistema tem "2" obras associada ao politico com o cpf "98765432109"
//And o sistema tem "1" obra com orçamento estourado associada ao politico com o cpf "98765432109"
//Then o percentual de obras com orcamento estourado para o politico com o cpf "98765432109" é de "50" por cento
And(~'^o sistema tem "([^"]*)" obra com orçamento estourado associada ao politico com o cpf "([^"]*)"$') {
	int qtdObrasEstouradasPolitico, String cpf ->
		int qtd = TestDataAndOperations.qtdObrasEstouradasPolitico(cpf)
		assert qtdObrasEstouradasPolitico == qtd
}
Then(~'^o percentual de obras com orcamento estourado para o politico com o cpf "([^"]*)" é de "([^"]*)" por cento$'){
	String cpf, float porcetagem ->
		float qtd = TestDataAndOperations.taxaObrasEstouradasPolitico(cpf)
		assert porcetagem == qtd
}


/**
 * @author = ehmr
 **/
//Scenario: Remover obra existente
//	Given que o sistema tem uma obra chamada "Praca do arsenal"
//	When eu tentar remover a obra com o nome "Praca do arsenal"
//	Then o sistema ira remover a obra com nome "Praca do arsenal"
int sizeObrasBefore;
When (~'^eu tentar remover a obra com o nome "([^"]*)"$'){
	String nomeObra ->
		sizeObrasBefore = Obra.count()
		TestDataAndOperations.removeObra(nomeObra)
}
Then (~'^o sistema ira remover a obra com nome "([^"]*)"$'){
	String nomeObra ->
		assert Obra.count() == (sizeObrasBefore-1)
}

/**
 * @author = ehmr
 **/
//Scenario: Verificar status andamento obra
//	Given que o sistema tem uma obra chamada "Praca do arsenal" que esta atrasada mas esta com status "Em dia"
//	When eu tentar verificar o status da obra com o nome "Praca do arsenal"
//	Then o sistema ira atualizar obra com nome "Praca do arsenal" para "atrasada"
Given(~'^que o sistema tem uma obra chamada "([^"]*)" que esta atrasada mas esta com status "([^"]*)"$'){
	String nomeObra, statusAndamentoObra ->
        TestDataAndOperations.createObra(nomeObra)
        Obra testObra = Obra.findByNome(nomeObra)
        assert testObra != null
}

When (~'^eu tentar verificar o status da obra com o nome "([^"]*)"$'){
	String nomeObra ->
        TestDataAndOperations.sincronizarStatusAndamentoObra(nomeObra)
}

Then (~'^o sistema ira atualizar obra com nome "([^"]*)" para "([^"]*)"$'){
	String nomeObra, statusAndamentoObra ->
        assert TestDataAndOperations.verificarStatusAndamentoObra(nomeObra, ObraStrings."$statusAndamentoObra")
}

