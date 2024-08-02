package br.com.aplicacoesspringboot.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.aplicacoesspringboot.screenmatch.model.DadosEpisodio;
import br.com.aplicacoesspringboot.screenmatch.model.DadosSerie;
import br.com.aplicacoesspringboot.screenmatch.model.DadosTemporada;
import br.com.aplicacoesspringboot.screenmatch.service.ConsumoApi;
import br.com.aplicacoesspringboot.screenmatch.service.ConverteDado;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDado conversor = new ConverteDado();

    private final String ENDERECO = "https://www.omdbapi.com/?t="; // constante (permite que valores fixos e imutáveis sejam armazenados e utilizados ao longo do código...)
    private final String API_KEY = "&apikey=c6a5853b"; // constante (...muito úteis quando temos valores que não devem ser alterados durante a execução do programa. Nomenclatura: "snake_case")


    public void exibeMenu() {
        System.out.println("Digite o nome da série: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println); // mesma coisa que: temporadas.forEach(t -> System.out.println(t));
        
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); /* lambda(funções anônimas) são uma maneira de definir funções que são frequentemente usadas uma única vez, 
        direto no local onde elas serão usadas. Principal vantagem é simplificar o código e torna-o mais fácil de ler e compreender.
        t: temporadas - e: episodios. - substitui os for dentro de for, descrito abaixo. 
        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        } */

    }
}
