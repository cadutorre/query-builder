package br.com.startup.query.order;

import java.text.MessageFormat;

public class Order {

	protected static final String MODELO_ALIAS = "{0}.{1}";

	private final String attributo;
	private final boolean desc;

	private Order(final String attributo, final boolean desc) {
		super();
		this.attributo = attributo;
		this.desc = desc;
	}

	public static Order desc(final String atributo) {
		return new Order(atributo, true);
	}

	public static Order asc(final String atributo) {
		return new Order(atributo, false);
	}

	public String getAttributo() {
		return attributo;
	}

	public boolean isDesc() {
		return desc;
	}

	public void build(final StringBuilder hqlBuilder, final String alias) {
		hqlBuilder.append(" ").append(MessageFormat.format(MODELO_ALIAS, alias, this.attributo));

		if (isDesc()) {
			hqlBuilder.append(" DESC");
		}
	}
}
