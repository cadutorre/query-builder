package br.com.startup.query;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.startup.query.conditions.Condition;
import br.com.startup.query.order.Order;

public class QueryBuilder implements Serializable {

	private static final long serialVersionUID = -2574036579959241799L;

	private static final String SELECT_HQL = "SELECT {0} FROM {1} {0}";
	private static final String COUNT_HQL = "SELECT COUNT(*) FROM {0}";

	private final List<Condition> conditions;
	private final List<Order> orders;

	private final Class<?> classe;
	private final String alias;
	private final String instrucaoHQL;

	private Integer firstResult;
	private Integer maxResults;

	private QueryBuilder(final Class<?> classe, final String selectHql) {
		super();

		this.classe = classe;
		this.conditions = new ArrayList<Condition>();
		this.orders = new ArrayList<Order>();

		this.alias = classe.getSimpleName().toLowerCase();
		this.instrucaoHQL = selectHql;
	}

	public Query build(final EntityManager entityManager) {

		final StringBuilder hqlBuilder = buildQuery();

		final Query query = entityManager.createQuery(hqlBuilder.toString());

		if (hasFirstResult()) {
			query.setFirstResult(firstResult);
		}

		if (hasMaxResults()) {
			query.setMaxResults(maxResults);
		}

		if (hasConditions()) {

			int contadorParametro = 1;

			for (final Condition condition : conditions) {

				if (condition.exigeParametro()) {
					query.setParameter(contadorParametro, condition.getParametro());
				}

				contadorParametro++;
			}
		}

		return query;
	}

	private StringBuilder buildQuery() {

		final StringBuilder hqlBuilder = new StringBuilder();

		hqlBuilder.append(MessageFormat.format(instrucaoHQL, alias, classe.getSimpleName()));

		if (hasConditions()) {

			int contadorParametro = 1;

			hqlBuilder.append(" WHERE (");

			for (final Iterator<Condition> iterator = conditions.iterator(); iterator.hasNext();) {

				final Condition condition = iterator.next();

				condition.build(hqlBuilder, contadorParametro, alias);

				contadorParametro++;

				if (iterator.hasNext()) {
					hqlBuilder.append(" AND ");
				}
			}

			hqlBuilder.append(")");
		}

		if (hasOrders()) {
			hqlBuilder.append(" ORDER BY ");

			for (final Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {

				final Order order = iterator.next();

				order.build(hqlBuilder, alias);

				if (iterator.hasNext()) {
					hqlBuilder.append(", ");
				}
			}
		}

		return hqlBuilder;
	}

	public void order(final Order order) {
		orders.add(order);
	}

	private boolean hasConditions() {
		return conditions.size() > 0;
	}

	private boolean hasOrders() {
		return orders.size() > 0;
	}

	private boolean hasMaxResults() {
		return maxResults != null;
	}

	private boolean hasFirstResult() {
		return firstResult != null;
	}

	public void where(final Condition condition) {
		conditions.add(condition);
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(final Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(final Integer maxResults) {
		this.maxResults = maxResults;
	}

	public static QueryBuilder select(final Class<?> classe) {
		return new QueryBuilder(classe, SELECT_HQL);
	}

	public static QueryBuilder count(final Class<?> classe) {
		return new QueryBuilder(classe, COUNT_HQL);
	}
}
