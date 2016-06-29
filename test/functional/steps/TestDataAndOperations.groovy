package steps

import obraspublicas.*

import util.EnderecoController

class TestDataAndOperations {

    static obras = [
            [nome               : "Praca do arsenal",
             descricao          : "Praça localizada no recife antigo",
             imagem             : "http://www.turismonorecife.com.br/sites/default/files/praca_do_arsenal_0.jpg",
             precoPlanejado     : 1250000.23,
             precoFinal         : 25000000.50,
             dataPlanejada      : (new Date("12 October 2012")),
             dataTermino        : (new Date("12 October 2016")),
             latitude           : 12,
             longitude          : 45,
             empresaResponsavel : "Moura Dubeux"//,
             //politicoResponsavel: TestDataAndOperations.findPoliticoByCPF("01234567891")
            ],

            [nome               : "Ilha do Retiro",
             descricao          : "Casa do leão",
             imagem             : "youtube.com.br",
             precoPlanejado     : 1250000.23,
             precoFinal         : 1200000.50,
             dataPlanejada      : (new Date("12 October 2017")),
             dataTermino        : (new Date("12 October 2010")),
             latitude           : 12,
             longitude          : 40,
             empresaResponsavel : "Edu Dubeux"//,
             //politicoResponsavel: TestDataAndOperations.findPoliticoByCPF("98765432109")
            ]
    ]

    static enderecos = [
            [numero: 323,
             rua: "Avenida camarao",
             bairro: "Cordeiro",
             cidade: "Recife",
             estado: "Pernambuco",
             CEP: "50721-360"],

            [numero: 319,
             rua: "Avenida Domingos Ferreira",
             bairro: "Boa Viagem",
             cidade: "Recife",
             estado: "Pernambuco",
             CEP: "12345-67"]
    ]

    static politicos = [
            [partido  : "Partido Pernambucano",
             foto     : "youtube.com",
             descricao: "Esse é virado no mói de coentro",
             qualidade: 10.0,
             nome     : "Eduardo Lokão",
             cpf      : "01234567891",
             email    : "eduobra@obra.com"],

            [partido  : "PT",
             foto     : "youtube.com",
             descricao: "Esse é LADRÃO",
             qualidade: 0.0,
             nome     : "Robson nada Lokão",
             cpf      : "98765432109",
             email    : "robsobra@obra.com"]
    ]

    static public def findObraByNome(String obraNome) {
        obras.find { obra ->
            obra.nome == obraNome
        }
    }

    static public def findPoliticoByCPF(String politicoCPF) {
        politicos.find { politico ->
            politico.cpf == politicoCPF
        }
    }

    static public def findEnderecoByCEPAndNumero(String CEP, int numero){
        enderecos.find { endereco ->
            endereco.CEP == CEP && endereco.numero == numero
        }
    }

    static public void createObra(String obraNome) {
        def cont = new ObraController()
        cont.params << TestDataAndOperations.findObraByNome(obraNome) << [politicoResponsavel: TestDataAndOperations.findPoliticoByCPF("01234567891")]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void createPolitico(String cpf) {
        def cont = new PoliticoController()
        cont.params << TestDataAndOperations.findPoliticoByCPF(cpf)
        cont.create()
        cont.save()
        cont.response.reset()
    }

    //anderson
    static  public  void deletePolitico(String politicoCpf){
        def testPolico= Politico.findByCpf(politicoCpf)
        def cont = new PoliticoController()
        def date = new Date()
        cont.params<<[cpf: testPolico.cpf]
        cont.delete()
    }


    static public void createEndereco(String CEP, int numero) {
        def cont = new EnderecoController()
        cont.params << TestDataAndOperations.findEnderecoByceoAndNumero(CEP, numero)
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public boolean obraCompatibleTo(obra, nomeObra) {
        def testObra = findObraByNome(nomeObra)
        def compatible = false
        if (testObra == null && obra == null) {
            compatible = true
        } else if (testObra != null && obra != null) {
            compatible = true
           // testObra.each { key, data ->
           //     compatible = compatible && (obra."$key" == data)
           // }
        }
        return compatible
    }

    static public boolean politicoCompatibleTo(politico, cpf) {
        def testPolitico = findPoliticoByCPF(cpf)
        def compatible = false
        if (testPolitico == null && politico == null) {
            compatible = true
        } else if (testPolitico != null && politico != null) {
            compatible = true
            testPolitico.each { key, data ->
                compatible = compatible && (politico."$key" == data)
            }
        }
        return compatible
    }


    static public Politico updateIdPolitico(String cpf,newId){
        def testPolitico= Politico.findByCpf(cpf)
        testPolitico.setId(newId)
        def cont = new PoliticoController()
        cont.params << testPolitico.properties
        cont.update()
        def updateTestPolitico = Politico.findById(newId)
        return updateTestPolitico

    }

    static public boolean enderecoCompatibleTo(endereco, CEP, numero) {
        def testEndereco = findEnderecoByCEPAndNumero(CEP, numero)
        def compatible = false
        if (testEndereco == null && endereco == null) {
            compatible = true
        } else if (testEndereco != null && endereco != null) {
            compatible = true
            testEndereco.each { key, data ->
                compatible = compatible && (endereco."$key" == data)
            }
        }
        return compatible
    }



}
