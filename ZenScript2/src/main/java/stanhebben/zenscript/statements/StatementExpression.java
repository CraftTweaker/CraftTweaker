package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.parser.expression.*;
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
        //boolean shouldCompile = expression.getClass().getName().equals("stanhebben.zenscript.parser.expression.ParsedExpressionIndexSet");
        boolean shouldCompile = ParsedExpressionIndexSet.class.isInstance(expression);
        expression.compile(environment, null).eval(environment).compile(shouldCompile, environment);
    }
}
