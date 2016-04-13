package stanhebben.zenscript.definitions;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.util.ZenPosition;

import static stanhebben.zenscript.ZenTokener.*;
/**
 * @author Techlone
 */
public class ParsedGlobalValue {
    public static ParsedGlobalValue parse(ZenTokener parser, IEnvironmentGlobal environment) {
        Token start = parser.next();
        String name = parser.required(T_ID, "identifier expected").getValue();

        ZenType type = ZenTypeAny.INSTANCE;
        if (parser.optional(T_AS) != null) {
            type = ZenType.read(parser, environment);
        }

        parser.required(T_ASSIGN, "global value must be initialized immediately");

        ParsedExpression parsedValue = ParsedExpression.read(parser, environment);
        Expression value = parsedValue.compile(null, type).eval(environment);

        type = value.getType();
        parser.required(T_SEMICOLON, "; expected");
        return new ParsedGlobalValue(start.getPosition(), name, type, value);
    }

    private final String name;
    private final ZenPosition position;
    private final ZenType type;
    private final Expression value;
    private final String signature;

    public ParsedGlobalValue(ZenPosition position, String name, ZenType type, Expression value) {
        this.position = position;
        this.name = name;
        this.type = type;
        this.value = value;
        this.signature = type.getSignature();
    }

    public ZenPosition getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public ZenType getType() {
        return this.type;
    }

    public Expression getValue() {
        return this.value;
    }

    public String getSignature() {
        return signature;
    }
}
