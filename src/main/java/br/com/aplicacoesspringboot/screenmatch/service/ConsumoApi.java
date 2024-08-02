package br.com.aplicacoesspringboot.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient(); 
        HttpRequest request = HttpRequest.newBuilder() // para qual endereço vou fazer uma requisição (criamos uma URI para especificar para qual endereço faremos a requisição)
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client 
                    .send(request, HttpResponse.BodyHandlers.ofString()); // (Depois, tentamos receber uma resposta. Para isso, o cliente enviará a requisição e receberemos essa resposta)
        } catch (IOException e) {
            throw new RuntimeException(e); // try catch para tratar exceções (porque pode ocorrer algum erro ao passar um endereço errado, por exemplo)
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body(); // corpo da resposta que requisitei (O retorno do método devolve a String json, que é o corpo da resposta, o response.body())
        return json;
    }
}