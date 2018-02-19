package stanhebben.zenscript.statements;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

import static stanhebben.zenscript.ZenTokener.*;

public abstract class Statement {

    private final ZenPosition position;

    public Statement(ZenPosition position) {
        this.position = position;
    }

    public static Statement read(ZenTokener parser, IEnvironmentGlobal environment, ZenType returnType) {
        Token next = parser.peek();
        switch(next.getType()) {
            case T_AOPEN: {
                Token t = parser.next();
                ArrayList<Statement> statements = new ArrayList<>();
                while(parser.optional(T_ACLOSE) == null) {
                    statements.add(read(parser, environment, returnType));
                }
                return new StatementBlock(t.getPosition(), statements);
            }
            case T_RETURN: {
                parser.next();
                ParsedExpression expression = null;
                if(parser.peek() != null && !parser.isNext(T_SEMICOLON)) {
                    expression = ParsedExpression.read(parser, environment);
                }
                parser.required(T_SEMICOLON, "; expected");
                return new StatementReturn(next.getPosition(), returnType, expression);
            }
            case T_VAR:
            case T_VAL: {
                Token start = parser.next();
                String name = parser.required(T_ID, "identifier expected").getValue();

                ZenType type = null;
                ParsedExpression initializer = null;
                if(parser.optional(T_AS) != null) {
                    type = ZenType.read(parser, environment);
                }
                if(parser.optional(T_ASSIGN) != null) {
                    initializer = ParsedExpression.read(parser, environment);
                }
                parser.required(T_SEMICOLON, "; expected");
                return new StatementVar(start.getPosition(), name, type, initializer, start.getType() == T_VAL);
            }
            case T_IF: {
                Token t = parser.next();
                ParsedExpression expression = ParsedExpression.read(parser, environment);
                Statement onIf = read(parser, environment, returnType);
                Statement onElse = null;
                if(parser.optional(T_ELSE) != null) {
                    onElse = read(parser, environment, returnType);
                }
                return new StatementIf(t.getPosition(), expression, onIf, onElse);
            }
            case T_FOR: {
                Token t = parser.next();
                String name = parser.required(T_ID, "identifier expected").getValue();
                List<String> names = new ArrayList<>();
                names.add(name);

                while(parser.optional(T_COMMA) != null) {
                    names.add(parser.required(T_ID, "identifier expected").getValue());
                }

                parser.required(T_IN, "in expected");
                ParsedExpression source = ParsedExpression.read(parser, environment);
                Statement content = read(parser, environment, null);
                return new StatementForeach(t.getPosition(), names.toArray(new String[names.size()]), source, content);
            }
            case T_VERSION: {
                Token t = parser.next();
                parser.required(T_INTVALUE, "integer expected");

                parser.required(T_SEMICOLON, "; expected");
                return new StatementNull(t.getPosition());
            }
        }

        ZenPosition position = parser.peek().getPosition();
        StatementExpression result = new StatementExpression(position, ParsedExpression.read(parser, environment));
        parser.required(T_SEMICOLON, "; expected");
        return result;
    }

    public ZenPosition getPosition() {
        return position;
    }

    public boolean isReturn() {
        return false;
    }

    public abstract void compile(IEnvironmentMethod environment);
}
