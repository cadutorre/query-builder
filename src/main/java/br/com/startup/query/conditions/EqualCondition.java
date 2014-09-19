package br.com.startup.query.conditions;

import java.text.MessageFormat;

public class EqualCondition implements Condition {

	private final Object objeto;
	private final String atributo;

	public EqualCondition(final Object objeto, final String atributo) {
		super();
		this.objeto = objeto;
		this.atributo = atributo;
	}

	public boolean exigeParametro() {
		return true;
	}

	public Object getParametro() {
		return this.objeto;
	}

	public void build(final StringBuilder builder, final int contadorParametro, final String alias) {
		builder.append(alias).append(".").append(atributo).append(" = ").append(MessageFormat.format(FORMAT_PARAMETRO, contadorParametro));
	}
}