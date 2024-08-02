package br.com.aplicacoesspringboot.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.aplicacoesspringboot.screenmatch.model.DadosSerie;
import br.com.aplicacoesspringboot.screenmatch.service.ConsumoApi;
import br.com.aplicacoesspringboot.screenmatch.service.ConverteDado;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* no consumoApi obtemos os dados da série Gilmore Girls, atribuímos para a
		 * variável json e imprimimos. Depois, instanciamos um conversor e mandamos
		 * transformar para DadosSerie, por fim imprimimos os dados da série. */
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		/* json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		 * System.out.println(json); */
		ConverteDado conversor = new ConverteDado();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}

}
