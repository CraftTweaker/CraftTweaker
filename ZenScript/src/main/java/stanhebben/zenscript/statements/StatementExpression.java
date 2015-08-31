package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.util.ZenPosition;

public class StatementExpression extends Statement {
	private final ParsedExpression expression;

	public StatementExpression(ZenPosition position, ParsedExpression expression) {
		super(position);

		this.expression = expression;
	}

	@Override
	public void compile(IEnvironmentMethod environment) {
		environment.getOutput().position(getPosition());
		expression.compile(environment, null)
			.eval(environment)
			.compile(false, environment);
	}
}
