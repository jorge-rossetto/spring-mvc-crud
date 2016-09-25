package app;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.domain.CorCabelo;
import app.domain.Pessoa;
import app.domain.PessoaRepository;

@Repository
public class MassaDeDados {

	@Autowired
	private PessoaRepository repository;
	
	public void popular() {
		for (int i = 0; i < 5; i++) {
			Pessoa pessoa = new Pessoa();
			pessoa.setNome("Pessoa " + i);

			Random random = new Random();
			pessoa.setIdade(random.nextInt(70 - 20) + 20);
			
			pessoa.setCorCabelo(getCorCabelo(random.nextInt(4)));
			
			pessoa.setDataNascimento(DateUtils.addYears(new Date(), -30));
			
			repository.save(pessoa);
		}
	}

	private CorCabelo getCorCabelo(int i) {
		switch (i) {
		case 0:
			return CorCabelo.CASTANHO;
		case 1:
			return CorCabelo.LOIRO;
		case 2:
			return CorCabelo.PRETO;
		case 3:
			return CorCabelo.RUIVO;
		default:
			return null;
		}
	}
	
}