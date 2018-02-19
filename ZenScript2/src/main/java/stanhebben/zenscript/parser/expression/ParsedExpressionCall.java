package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/**
 * @author Stanneke
 */
public class ParsedExpressionCall extends ParsedExpression {

    private final ParsedExpression receiver;
    private final List<ParsedExpression> arguments;

    public ParsedExpressionCall(ZenPosition position, ParsedExpression receiver, List<ParsedExpression> arguments) {
        super(position);

        this.receiver = receiver;
        this.arguments = arguments;
    }

    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        IPartialExpression cReceiver = receiver.compile(environment, null);
        ZenType[] predictedTypes = cReceiver.predictCallTypes(arguments.size());

        Expression[] cArguments = new Expression[arguments.size()];
        for(int i = 0; i < cArguments.length; i++) {
            IPartialExpression cArgument = arguments.get(i).compile(environment, predictedTypes[i]);
            cArguments[i] = cArgument.eval(environment);
        }

        return cReceiver.call(getPosition(), environment, cArguments);
    }
}
