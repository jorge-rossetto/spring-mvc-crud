package app.domain;

import java.util.List;

public interface PessoaRepositoryCustom {

	List<Pessoa> findByFilter(Pessoa filter);
	
}
