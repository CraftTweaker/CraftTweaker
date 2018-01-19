package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.*;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

import static stanhebben.zenscript.ZenTokener.*;
import static stanhebben.zenscript.util.StringUtil.unescapeString;

/**
 * @author Stanneke
 */
public abstract class ParsedExpression {
    
    private final ZenPosition position;
    
    public ParsedExpression(ZenPosition position) {
        this.position = position;
    }
    
    public static ParsedExpression read(ZenTokener parser, IEnvironmentGlobal environment) {
        return readAssignExpression(parser, environment);
    }
    
    private static ParsedExpression readAssignExpression(ZenTokener parser, IEnvironmentGlobal environment) {
        Token token = parser.peek();
        if(token == null) {
            ZenPosition position = new ZenPosition(parser.getFile(), parser.getLine(), parser.getLineOffset(), parser.getFile().getFileName());
            environment.error(position, "unexpected end of file; expression expected");
            return new ParsedExpressionInvalid(position);
        }
        
        ZenPosition position = token.getPosition();
        
        ParsedExpression left = readConditionalExpression(position, parser, environment);
        
        if(parser.peek() == null) {
            ZenPosition position2 = new ZenPosition(parser.getFile(), parser.getLine(), parser.getLineOffset(), parser.getFile().getFileName());
            environment.error(position2, "unexpected end of file - ; expected");
            return new ParsedExpressionInvalid(position2);
        }
        
        switch(parser.peek().getType()) {
            case T_ASSIGN:
                parser.next();
                return new ParsedExpressionAssign(position, left, readAssignExpression(parser, environment));
            case T_PLUSASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.ADD);
            case T_MINUSASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.SUB);
            case T_TILDEASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.CAT);
            case T_MULASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.MUL);
            case T_DIVASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.DIV);
            case T_MODASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.MOD);
            case T_ORASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.OR);
            case T_ANDASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.AND);
            case T_XORASSIGN:
                parser.next();
                return new ParsedExpressionOpAssign(position, left, readAssignExpression(parser, environment), OperatorType.XOR);
        }
        
        return left;
    }
    
    private static ParsedExpression readConditionalExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readOrOrExpression(position, parser, environment);
        
        if(parser.optional(T_QUEST) != null) {
            ParsedExpression onIf = readOrOrExpression(parser.peek().getPosition(), parser, environment);
            parser.required(T_COLON, ": expected");
            ParsedExpression onElse = readConditionalExpression(parser.peek().getPosition(), parser, environment);
            return new ParsedExpressionConditional(position, left, onIf, onElse);
        }
        
        return left;
    }
    
    private static ParsedExpression readOrOrExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readAndAndExpression(position, parser, environment);
        
        while(parser.optional(T_OR2) != null) {
            ParsedExpression right = readAndAndExpression(parser.peek().getPosition(), parser, environment);
            left = new ParsedExpressionOrOr(position, left, right);
        }
        return left;
    }
    
    private static ParsedExpression readAndAndExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readOrExpression(position, parser, environment);
        
        while(parser.optional(T_AND2) != null) {
            ParsedExpression right = readOrExpression(parser.peek().getPosition(), parser, environment);
            left = new ParsedExpressionAndAnd(position, left, right);
        }
        return left;
    }
    
    private static ParsedExpression readOrExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readXorExpression(position, parser, environment);
        
        while(parser.optional(T_OR) != null) {
            ParsedExpression right = readXorExpression(parser.peek().getPosition(), parser, environment);
            left = new ParsedExpressionBinary(position, left, right, OperatorType.OR);
        }
        return left;
    }
    
    private static ParsedExpression readXorExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readAndExpression(position, parser, environment);
        
        while(parser.optional(T_XOR) != null) {
            ParsedExpression right = readAndExpression(parser.peek().getPosition(), parser, environment);
            left = new ParsedExpressionBinary(position, left, right, OperatorType.XOR);
        }
        return left;
    }
    
    private static ParsedExpression readAndExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readCompareExpression(position, parser, environment);
        
        while(parser.optional(T_AND) != null) {
            ParsedExpression right = readCompareExpression(parser.peek().getPosition(), parser, environment);
            left = new ParsedExpressionBinary(position, left, right, OperatorType.AND);
        }
        return left;
    }
    
    private static ParsedExpression readCompareExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readAddExpression(position, parser, environment);
        
        switch(parser.peek() == null ? -1 : parser.peek().getType()) {
            case T_EQ: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.EQ);
            }
            case T_NOTEQ: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.NE);
            }
            case T_LT: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.LT);
            }
            case T_LTEQ: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.LE);
            }
            case T_GT: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.GT);
            }
            case T_GTEQ: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionCompare(position, left, right, CompareType.GE);
            }
            case T_IN: {
                parser.next();
                ParsedExpression right = readAddExpression(parser.peek().getPosition(), parser, environment);
                return new ParsedExpressionBinary(position, left, right, OperatorType.CONTAINS);
            }
        }
        
        return left;
    }
    
    private static ParsedExpression readAddExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readMulExpression(position, parser, environment);
        
        while(true) {
            if(parser.optional(T_PLUS) != null) {
                ParsedExpression right = readMulExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.ADD);
            } else if(parser.optional(T_MINUS) != null) {
                ParsedExpression right = readMulExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.SUB);
            } else if(parser.optional(T_TILDE) != null) {
                ParsedExpression right = readMulExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.CAT);
            } else {
                break;
            }
        }
        return left;
    }
    
    private static ParsedExpression readMulExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression left = readUnaryExpression(position, parser, environment);
        
        while(true) {
            if(parser.optional(T_MUL) != null) {
                ParsedExpression right = readUnaryExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.MUL);
            } else if(parser.optional(T_DIV) != null) {
                ParsedExpression right = readUnaryExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.DIV);
            } else if(parser.optional(T_MOD) != null) {
                ParsedExpression right = readUnaryExpression(parser.peek().getPosition(), parser, environment);
                left = new ParsedExpressionBinary(position, left, right, OperatorType.MOD);
            } else {
                break;
            }
        }
        
        return left;
    }
    
    private static ParsedExpression readUnaryExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        switch(parser.peek().getType()) {
            case T_NOT:
                parser.next();
                return new ParsedExpressionUnary(position, readUnaryExpression(parser.peek().getPosition(), parser, environment), OperatorType.NOT);
            case T_MINUS:
                parser.next();
                return new ParsedExpressionUnary(position, readUnaryExpression(parser.peek().getPosition(), parser, environment), OperatorType.NEG);
            default:
                return readPostfixExpression(position, parser, environment);
        }
    }
    
    private static ParsedExpression readPostfixExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        ParsedExpression base = readPrimaryExpression(position, parser, environment);
        
        while(true) {
            Token next = parser.peek();
            if(parser.optional(T_DOT) != null) {
                Token indexString = parser.optional(T_ID);
                if(indexString != null) {
                    base = new ParsedExpressionMember(position, base, indexString.getValue());
                } else {
                    Token indexString2 = parser.optional(T_STRING);
                    if(indexString2 != null) {
                        base = new ParsedExpressionMember(position, base, unescapeString(indexString2.getValue()));
                    } else {
                        Token last = parser.next();
                        throw new ParseException(last, "Invalid expression, last token: " + last.getValue());
                    }
                }
            } else if(next.getType() == T_DOT2 || (next.getType() == T_ID && next.getValue().equals("to"))) {
                parser.next();
                ParsedExpression to = readAssignExpression(parser, environment);
                return new ParsedExpressionBinary(position, base, to, OperatorType.RANGE);
            } else if(parser.optional(T_SQBROPEN) != null) {
                ParsedExpression index = readAssignExpression(parser, environment);
                parser.required(T_SQBRCLOSE, "] expected");
                if(parser.optional(T_ASSIGN) != null) {
                    ParsedExpression newVal = readAssignExpression(parser, environment);
                    base = new ParsedExpressionIndexSet(position, base, index, newVal);
                } else {
                    base = new ParsedExpressionIndex(position, base, index);
                }
            } else if(parser.optional(T_BROPEN) != null) {
                ArrayList<ParsedExpression> arguments = new ArrayList<>();
                if(parser.optional(T_BRCLOSE) == null) {
                    arguments.add(readAssignExpression(parser, environment));
                    while(parser.optional(T_COMMA) != null) {
                        arguments.add(readAssignExpression(parser, environment));
                    }
                    parser.required(T_BRCLOSE, ") expected");
                }
                base = new ParsedExpressionCall(position, base, arguments);
            } else if(parser.optional(T_AS) != null) {
                ZenType type = ZenType.read(parser, environment);
                base = new ParsedExpressionCast(position, base, type);
            } else {
                break;
            }
        }
        
        return base;
    }
    
    private static ParsedExpression readPrimaryExpression(ZenPosition position, ZenTokener parser, IEnvironmentGlobal environment) {
        switch(parser.peek().getType()) {
            case T_INTVALUE:
                return new ParsedExpressionValue(position, new ExpressionInt(position, Long.parseLong(parser.next().getValue()), ZenTypeInt.INSTANCE));
            case T_FLOATVALUE:
                String value = parser.next().getValue();
                ZenType zenType = ZenTypeDouble.INSTANCE;
                if (value.endsWith("f") || value.endsWith("F")) {
                    zenType = ZenTypeFloat.INSTANCE;
                    value = value.substring(0, value.length() - 1);
                }
                return new ParsedExpressionValue(position, new ExpressionFloat(position, Double.parseDouble(value), zenType));
            case T_STRINGVALUE:
                return new ParsedExpressionValue(position, new ExpressionString(position, unescapeString(parser.next().getValue())));
            /*
             * case T_DOLLAR: { Expression result = new
             * ExpressionDollar(position); if (parser.isNext(T_STRINGVALUE))
             * { return new ExpressionIndexString(position, result,
             * unescapeString(parser.next().getValue())); } return result; }
             */
            case T_ID:
                return new ParsedExpressionVariable(position, parser.next().getValue());
            case T_FUNCTION:
                // function (argname, argname, ...) { ...contents... }
                parser.next();
                parser.required(T_BROPEN, "( expected");
                
                List<ParsedFunctionArgument> arguments = new ArrayList<>();
                if(parser.optional(T_BRCLOSE) == null) {
                    do {
                        String name = parser.required(T_ID, "identifier expected").getValue();
                        ZenType type = ZenTypeAny.INSTANCE;
                        
                        if(parser.optional(T_AS) != null) {
                            type = ZenType.read(parser, environment);
                        }
                        
                        arguments.add(new ParsedFunctionArgument(name, type));
                    } while(parser.optional(T_COMMA) != null);
                    
                    parser.required(T_BRCLOSE, ") expected");
                }
                
                ZenType returnType = ZenTypeAny.INSTANCE;
                if(parser.optional(T_AS) != null) {
                    returnType = ZenType.read(parser, environment);
                }
                
                parser.required(T_AOPEN, "{ expected");
                
                List<Statement> statements = new ArrayList<>();
                if(parser.optional(T_ACLOSE) == null) {
                    while(parser.optional(T_ACLOSE) == null) {
                        statements.add(Statement.read(parser, environment, returnType));
                    }
                }
                
                return new ParsedExpressionFunction(position, returnType, arguments, statements);
            case T_LT: {
                Token start = parser.next();
                List<Token> tokens = new ArrayList<>();
                Token next = parser.next();
                while(next.getType() != ZenTokener.T_GT) {
                    tokens.add(next);
                    next = parser.next();
                }
                IZenSymbol resolved = parser.getEnvironment().getBracketed(environment, tokens);
                if(resolved == null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append('<');
                    Token last = null;
                    for(Token token : tokens) {
                        if(last != null)
                            builder.append(' ');
                        builder.append(token.getValue());
                        last = token;
                    }
                    builder.append('>');
                    if(!parser.ignoreBracketErrors) {
                        parser.getEnvironment().getErrorLogger().error(start.getPosition(), "Could not resolve " + builder.toString());
                    } else {
                        parser.getEnvironment().getErrorLogger().info(start.getPosition(), "Could not resolve " + builder.toString());
                    }
                    return new ParsedExpressionInvalid(start.getPosition());
                } else {
                    return new ParsedExpressionValue(start.getPosition(), parser.getEnvironment().getBracketed(environment, tokens).instance(start.getPosition()));
                }
            }
            case T_SQBROPEN: {
                parser.next();
                List<ParsedExpression> contents = new ArrayList<>();
                if(parser.optional(T_SQBRCLOSE) == null) {
                    while(parser.optional(T_SQBRCLOSE) == null) {
                        contents.add(readAssignExpression(parser, environment));
                        if(parser.optional(T_COMMA) == null) {
                            parser.required(T_SQBRCLOSE, "] or , expected");
                            break;
                        }
                    }
                }
                return new ParsedExpressionArray(position, contents);
            }
            case T_AOPEN: {
                parser.next();
                
                List<ParsedExpression> keys = new ArrayList<>();
                List<ParsedExpression> values = new ArrayList<>();
                if(parser.optional(T_ACLOSE) == null) {
                    while(parser.optional(T_ACLOSE) == null) {
                        keys.add(readAssignExpression(parser, environment));
                        parser.required(T_COLON, ": expected");
                        values.add(readAssignExpression(parser, environment));
                        
                        if(parser.optional(T_COMMA) == null) {
                            parser.required(T_ACLOSE, "} or , expected");
                            break;
                        }
                    }
                }
                return new ParsedExpressionMap(position, keys, values);
            }
            case T_TRUE:
                parser.next();
                return new ParsedExpressionBool(position, true);
            case T_FALSE:
                parser.next();
                return new ParsedExpressionBool(position, false);
            case T_NULL:
                parser.next();
                return new ParsedExpressionNull(position);
            case T_BROPEN:
                parser.next();
                ParsedExpression result = readAssignExpression(parser, environment);
                parser.required(T_BRCLOSE, ") expected");
                return result;
            default:
                Token last = parser.next();
                throw new ParseException(last, "Invalid expression, last token: " + last.getValue());
        }
    }
    
    public ZenPosition getPosition() {
        return position;
    }
    
    public abstract IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType);
    
    public Expression compileKey(IEnvironmentMethod environment, ZenType predictedType) {
        return compile(environment, predictedType).eval(environment);
    }
}
