package br.com.aplicacoesspringboot.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.aplicacoesspringboot.screenmatch.model.DadosEpisodio;
import br.com.aplicacoesspringboot.screenmatch.model.DadosSerie;
import br.com.aplicacoesspringboot.screenmatch.model.DadosTemporada;
import br.com.aplicacoesspringboot.screenmatch.model.Episodio;
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
        
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); 
        /*   lambda e stream
        **LAMBDA (funções anônimas)        
        São uma maneira de definir funções que são frequentemente usadas uma única vez, direto no local onde elas serão usadas. Principal vantagem é simplificar o código e mais legível.
        t: temporadas - e: episodios. - substitui os for dentro de for, descrito abaixo. 
        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        } 
        ** STREAM        
        Forma de trabalhar com coleções de dados no Java. Elas permitem realizar operações de forma mais eficiente e concisa, utilizando uma abordagem funcional.
        Uma stream é uma sequência de elementos que pode ser processada em paralelo ou em série. Ela pode ser criada a partir de uma coleção, um array, um arquivo, entre outros.
        A partir daí, podemos realizar diversas operações nessa stream, como filtrar, mapear, ordenar, entre outras.
        As operações intermediárias são aquelas que podem ser aplicadas em uma stream e retornam uma nova stream como resultado. Essas operações não são executadas imediatamente,
        mas apenas quando uma operação final é chamada.
        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
        nomes.stream()
             .sorted()  //ordena os elementos
             .limit(3)  //limita o número de elementos da stream
             .filter(n -> n.startsWith("N"))  //permite filtrar os elementos da stream com base em uma condição. Por exemplo, podemos filtrar uma lista de números para retornar apenas os números pares.
             .map(n -> n.toUpperCase())  //permite transformar cada elemento da stream em outro tipo de dado. Por exemplo, podemos transformar uma lista de strings em uma lista de seus respectivos tamanhos.
             .forEach(System.out::println);  //permite executar uma ação em cada elemento da stream. Por exemplo, podemos imprimir cada elemento da lista. 
        OUTRAS OPERAÇÕES
        .collect //permite coletar os elementos da stream em uma coleção ou em outro tipo de dado. Por exemplo, podemos coletar os números pares em um conjunto. 
        .distinct (que remove elementos duplicados)
        .skip (que pula os primeiros elementos da stream)
        .reduce (que combina os elementos da stream em um único resultado) */
        
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                                    .flatMap(t -> t.episodios().stream())
                                    .collect(Collectors.toList());
        System.out.println("Top 5 episódios: ");
        dadosEpisodios.stream()
                      .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                      .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                      .limit(5)
                      .forEach(System.out::println);
        
        List<Episodio> episodios = temporadas.stream()
                                             .flatMap(t -> t.episodios().stream()
                                                                        .map(d -> new Episodio(t.numero(), d))
                                            ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                 .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                 .forEach(e -> System.out.println(
                    "Temporada: " + e.getTemporada() +
                    " | Episódio: " + e.getTitulo() +
                    " | Data de lançamento: " + e.getDataLancamento().format(formatador)
                 ));
    }
}
