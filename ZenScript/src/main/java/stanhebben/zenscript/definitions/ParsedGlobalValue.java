package stanhebben.zenscript.definitions;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import static stanhebben.zenscript.ZenTokener.*;
/**
 * @author Techlone
 */
public class ParsedGlobalValue {
    public static ParsedGlobalValue parse(ZenTokener parser, IEnvironmentGlobal environment) {
        Token start = parser.next();
        String name = parser.required(T_ID, "identifier expected").getValue();

        ZenType type = ZenType.ANY;
        if (parser.optional(T_AS) != null) {
            type = ZenType.read(parser, environment);
        }

        parser.required(T_ASSIGN, "global value must be initialized immediately");
        ParsedExpression parsedValue = ParsedExpression.read(parser, environment);

        parser.required(T_SEMICOLON, "; expected");
        return new ParsedGlobalValue(start.getPosition(), name, type, parsedValue);
    }

    private final String name;
    private final ZenPosition position;
    private final ZenType type;
    private final ParsedExpression value;

    public ParsedGlobalValue(ZenPosition position, String name, ZenType type, ParsedExpression value) {
        this.position = position;
        this.name = name;
        this.type = type;
        this.value = value;
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

    public ParsedExpression getValue() {
        return value;
    }
}
