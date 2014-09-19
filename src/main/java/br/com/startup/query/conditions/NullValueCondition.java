package br.com.startup.query.conditions;

public class NullValueCondition implements Condition {

	private final String atributo;

	public NullValueCondition(final String atributo) {
		super();
		this.atributo = atributo;
	}

	public boolean exigeParametro() {
		return false;
	}

	public Object getParametro() {
		return null;
	}

	public void build(final StringBuilder builder, final int contadorParametro, final String alias) {
		builder.append(alias).append(".").append(atributo).append(" is NULL ");
	}

}
