package br.com.startup.query.conditions;

public interface Condition {

	public static final String FORMAT_PARAMETRO = "p{0}";

	boolean exigeParametro();

	Object getParametro();

	void build(StringBuilder builder, int contadorParametro, String alias);

}
