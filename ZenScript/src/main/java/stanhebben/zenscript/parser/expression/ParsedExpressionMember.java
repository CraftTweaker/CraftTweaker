package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ParsedExpressionMember extends ParsedExpression {

    private final ParsedExpression value;
    private final String member;

    public ParsedExpressionMember(ZenPosition position, ParsedExpression value, String member) {
        super(position);

        this.value = value;
        this.member = member;
    }

    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        return value.compile(environment, null).getMember(getPosition(), environment, member);
    }
}
