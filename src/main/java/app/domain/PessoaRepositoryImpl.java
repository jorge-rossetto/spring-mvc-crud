package app.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;


public class PessoaRepositoryImpl implements PessoaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public List<Pessoa> findByFilter(Pessoa filter) {
		StringBuilder jpql = new StringBuilder();

		HashMap<String, Object> params = new HashMap<String, Object>();

		jpql.append("select this from Pessoa this where 1=1 ");

		if (StringUtils.isNotBlank(filter.getNome())) {
			jpql.append("and lower(this.nome) like :nome ");
			params.put("nome", "%" + filter.getNome().toLowerCase() + "%");
		}

		TypedQuery<Pessoa> resultQuery = em.createQuery(jpql.toString(), Pessoa.class);
		
		setParameters(resultQuery, params);

		return resultQuery.getResultList();
	}
	
	protected void setParameters(TypedQuery<?> resultQuery, Map<String, Object> listaParams) {
        setParameters(resultQuery, null, listaParams);
    }
	
    protected void setParameters(TypedQuery<?> resultQuery, TypedQuery<?> countQuery, Map<String, Object> listaParams) {
        Set<Entry<String, Object>> pares = listaParams.entrySet();

        for (Entry<String, Object> par : pares) {
            setParameter(resultQuery, countQuery, par.getKey(), par.getValue());
        }
    }
    
    protected void setParameter(TypedQuery<?> resultQuery, TypedQuery<?> countQuery, String nomeParam, Object valorParam) {

        resultQuery.setParameter(nomeParam, valorParam);

        if (countQuery != null) {
            countQuery.setParameter(nomeParam, valorParam);
        }
    }

}
