package app;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.domain.Pessoa;
import app.domain.PessoaRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PessoaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... arg0) throws Exception {
		for (int i = 0; i < 5; i++) {
			Pessoa pessoa = new Pessoa();
			pessoa.setNome("Pessoa " + i);
			
			Random random = new Random();
			pessoa.setIdade(random.nextInt(70 - 20) + 20);
			repository.save(pessoa);
		}
	}

}
