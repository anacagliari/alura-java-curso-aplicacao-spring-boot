package br.com.aplicacoesspringboot.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.aplicacoesspringboot.screenmatch.principal.Principal;

/*
	List: Uma coleção ordenada que permite elementos duplicados. Os elementos são acessados por índices.
	Set: Uma coleção que não permite elementos duplicados e normalmente não possui ordem definida.
	Queue: Uma coleção que representa uma fila, onde os elementos são adicionados no final e removidos do início.
	Map: Uma coleção de pares chave-valor, onde cada chave é única e mapeada para um valor correspondente.
 */
@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}

}
