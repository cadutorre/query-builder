package br.com.startup.query.conditions;

public final class ConditionFactory {

	private ConditionFactory() {
		super();

		throw new UnsupportedOperationException();
	}

	public static Condition equal(final String atributo, final Object value) {
		return new EqualCondition(value, atributo);
	}

	public static Condition nullValue(final String atributo) {
		return new NullValueCondition(atributo);
	}

}
