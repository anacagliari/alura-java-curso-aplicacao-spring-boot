package br.com.aplicacoesspringboot.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDado implements IConverteDado {
    /* Criarmos um novo serviço para converter dados e ter flexibilidade. Se quisermos converter outros tipos de dados, poderemos fazer isso utilizando esse serviço.
    No Json usamos o fromJson e toJson. No Jackson, precisamos de uma classe chamada ObjectMapper. */
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    /* Ao implementar a interface, é gerado um override no qual precisamos identificar o que será devolvido. Nesse caso devolveremos uma instância genérica também. Para isso
    será preciso ler o valor do json e transformá-lo nessa estrutura genérica. Então, após return passamos mapper que é o objeto do Jackson que faz a conversão, e pediremos 
    para realizar a leitura do json e tente transformar na classe que a pessoa passou. */
    public <T> T obterDados(String json, Class<T> classe) { 
        try {
            // Escrevemos .readValue(json, classe). Assim conseguimos fazer com o Jackson exatamente o que foi feito com o Json.
            return mapper.readValue(json, classe); 
        } catch (JsonProcessingException e) {
            /* a lâmpada vermelha aparece na lateral esquerda do código. Ao clicá-la, notamos que como o readValue lança uma exceção, precisamos cobri-lo com um 
            try/catch, ou jogar na assinatura do método. Faremos a primeira opção, para isso clicamos na lâmpada e selecionamos a opção "Surround with try/cath". Feito isso o 
            ConverteDados está pronto. Para qualquer classe que quisermos representar usaremos o mesmo converteDados. */
            throw new RuntimeException(e);
        }
    }

}
