package app.domain;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ContextNotActiveException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.gov.frameworkdemoiselle.DemoiselleException;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Reflections;

public abstract class DAO<T, I, F> {

    private EntityManager entityManager;

    private Pagination pagination;

    private Class<T> beanClass;

    protected Class<T> getBeanClass() {
        if (this.beanClass == null) {
            this.beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
        }

        return this.beanClass;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    protected EntityManager getEntityManager() {
        if (this.entityManager == null) {
            this.entityManager = Beans.getReference(EntityManager.class);
        }

        return this.entityManager;
    }

    protected Pagination getPagination() {
        if (pagination == null) {
            try {
                PaginationContext context = Beans.getReference(PaginationContext.class);
                pagination = context.getPagination(getBeanClass());

            } catch (ContextNotActiveException cause) {
                pagination = null;
            }
        }

        return pagination;
    }

    protected CriteriaQuery<T> createCriteriaQuery() {
        return getCriteriaBuilder().createQuery(getBeanClass());
    }

    protected Query createQuery(final String ql) {
        return getEntityManager().createQuery(ql);
    }

    protected void handleException(Throwable cause) throws Throwable {
        if (cause instanceof TransactionRequiredException) {
            String message = "no-transaction-active";

            throw new DemoiselleException(message, cause);

        } else {
            throw cause;
        }
    }

    /**
     * Merge all changes made to the passed entity to a managed entity. The passed instance is not modified nor becomes
     * managed, instead the managed entity is returned by this method.
     */
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public T load(final I id) {
        return getEntityManager().find(getBeanClass(), id);
    }

    public List<T> findAll() {
        String hql = "select this from " + getBeanClass().getSimpleName() + " this";
        TypedQuery<T> listQuery = getEntityManager().createQuery(hql, getBeanClass());
        return listQuery.getResultList();
    }

    /**
     * Search JPQL integrated into the context of paging
     * 
     * @param jpql
     *            - query in syntax JPQL
     * @return a list of entities
     */
    protected List<T> findByJPQL(String jpql) {
        TypedQuery<T> listQuery = getEntityManager().createQuery(jpql, getBeanClass());

        if (getPagination() != null) {
            String countQuery = createCountQuery(jpql);
            Query query = getEntityManager().createQuery(countQuery);
            Number cResults = (Number) query.getSingleResult();
            getPagination().setTotalResults(cResults.intValue());
            listQuery.setFirstResult(getPagination().getFirstResult());
            listQuery.setMaxResults(getPagination().getPageSize());
        }

        return listQuery.getResultList();
    }

    /**
     * Search CriteriaQuery integrated into the context of paging
     * 
     * @param criteriaQuery
     *            - structure CriteriaQuery
     * @return a list of entities
     */
    protected List<T> findByCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        TypedQuery<T> listQuery = getEntityManager().createQuery(criteriaQuery);

        if (getPagination() != null) {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
            countQuery.select(builder.count(countQuery.from(getBeanClass())));
            countQuery.where(criteriaQuery.getRestriction());
            getEntityManager().createQuery(countQuery);
            getPagination().setTotalResults((int) (getEntityManager().createQuery(countQuery).getSingleResult() + 0));
            listQuery.setFirstResult(getPagination().getFirstResult());
            listQuery.setMaxResults(getPagination().getPageSize());
        }

        return listQuery.getResultList();
    }

    /**
     * Converts a query into a count query
     * 
     * @param query
     * @return
     */
    private String createCountQuery(String query) {
        String result = query;
        Matcher matcher = Pattern.compile("[Ss][Ee][Ll][Ee][Cc][Tt](.+)[Ff][Rr][Oo][Mm]").matcher(result);

        if (matcher.find()) {
            String group = matcher.group(1).trim();
            result = result.replaceFirst(group, "COUNT(" + group + ")");
            matcher = Pattern.compile("[Oo][Rr][Dd][Ee][Rr](.+)").matcher(result);

            if (matcher.find()) {
                group = matcher.group(0);
                result = result.replaceFirst(group, "");
            }

        } else {
            throw new DemoiselleException("malformed-jpql");
        }

        return result;
    }

    public void delete(final I id) {
        T entity = load(id);
        deleteEntity(entity);
    }

    public void deleteEntity(T entity) {
        if (!getEntityManager().contains(entity)) {
            entity = getEntityManager().merge(entity);
        }

        getEntityManager().remove(entity);
    }

    public T insert(final T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    /**
     * Define um parametro simultaneamente nas queries de resultado de count. Se não houver query de count essa pode ser
     * passada null.
     * 
     * @param resultQuery
     * @param countQuery
     * @param nomeParam
     * @param valorParam
     */
    protected void setParameter(TypedQuery<?> resultQuery, TypedQuery<?> countQuery, String nomeParam, Object valorParam) {

        resultQuery.setParameter(nomeParam, valorParam);

        if (countQuery != null) {
            countQuery.setParameter(nomeParam, valorParam);
        }
    }

    /**
     * Define a lista de parametros passado nas duas queries informadas. A query count é opcional.
     * 
     * @param resultQuery
     * @param countQuery
     * @param listaParams
     */
    protected void setParameters(TypedQuery<?> resultQuery, TypedQuery<?> countQuery, Map<String, Object> listaParams) {
        Set<Entry<String, Object>> pares = listaParams.entrySet();

        for (Entry<String, Object> par : pares) {
            setParameter(resultQuery, countQuery, par.getKey(), par.getValue());
        }
    }

    /**
     * Define a lista de parametros passado na query informada.
     * 
     * @param resultQuery
     * @param listaParams
     */
    protected void setParameters(TypedQuery<?> resultQuery, Map<String, Object> listaParams) {
        setParameters(resultQuery, null, listaParams);
    }

    public abstract List<T> findByFilter(F filter);
}