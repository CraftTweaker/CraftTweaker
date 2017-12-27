package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Stan
 */
public class ParsedExpressionFunction extends ParsedExpression {
    
    private final ZenType returnType;
    private final List<ParsedFunctionArgument> arguments;
    private final List<Statement> statements;
    
    public ParsedExpressionFunction(ZenPosition position, ZenType returnType, List<ParsedFunctionArgument> arguments, List<Statement> statements) {
        super(position);
        
        this.returnType = returnType;
        this.arguments = arguments;
        this.statements = statements;
    }
    
    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        if(predictedType != null && predictedType instanceof ZenTypeNative) {
            System.out.println("Known predicted function type: " + predictedType);

            ZenTypeNative nativeType = (ZenTypeNative) predictedType;
            Class nativeClass = nativeType.getNativeClass();
            if(nativeClass.isInterface() && nativeClass.getMethods().length == 1) {
                // functional interface
                Method method = nativeClass.getMethods()[0];
                if(returnType != ZenTypeAny.INSTANCE && !returnType.canCastImplicit(environment.getType(method.getGenericReturnType()), environment)) {
                    environment.error(getPosition(), "return type not compatible");
                    return new ExpressionInvalid(getPosition());
                }
                if(arguments.size() != method.getParameterTypes().length) {
                    environment.error(getPosition(), String.format("Expected %s arguments, received %s arguments", method.getParameterTypes().length, arguments.size()));
                    return new ExpressionInvalid(getPosition());
                }
                for(int i = 0; i < arguments.size(); i++) {
                    ZenType argumentType = environment.getType(method.getParameterTypes()[i]);
                    if(arguments.get(i).getType() != ZenTypeAny.INSTANCE && !argumentType.canCastImplicit(arguments.get(i).getType(), environment)) {
                        environment.error(getPosition(), "argument " + i + " doesn't match");
                        return new ExpressionInvalid(getPosition());
                    }
                }
                return isGeneric(method) ? new ExpressionJavaLambdaSimpleGeneric(getPosition(), nativeClass, arguments, statements, environment.getType(nativeClass)) : new ExpressionJavaLambda(getPosition(), nativeClass, arguments, statements, environment.getType(nativeClass));
            } else {
                environment.error(getPosition(), predictedType.toString() + " is not a functional interface");
                return new ExpressionInvalid(getPosition());
            }
        } else {
            System.out.println("No known predicted type");
            return new ExpressionFunction(getPosition(), arguments, returnType, statements);
        }
    }

    private boolean isGeneric(Method method) {
        for (Type type : method.getGenericParameterTypes()) {
            if(type.getTypeName().equals("T")) return true;
        }
        return false;

    }
}
