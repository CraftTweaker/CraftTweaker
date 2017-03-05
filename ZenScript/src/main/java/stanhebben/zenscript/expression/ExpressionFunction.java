package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Stanneke
 */
public class ExpressionFunction extends Expression {
    
    private final List<ParsedFunctionArgument> arguments;
    private final ZenType returnType;
    private final List<Statement> statements;
    
    private final ZenTypeFunction functionType;
    
    public ExpressionFunction(ZenPosition position, List<ParsedFunctionArgument> arguments, ZenType returnType, List<Statement> statements) {
        super(position);
        
        System.out.println("Function expression: " + arguments.size() + " arguments");
        
        this.arguments = arguments;
        this.returnType = returnType;
        this.statements = statements;
        
        ZenType[] argumentTypes = new ZenType[arguments.size()];
        for(int i = 0; i < arguments.size(); i++) {
            argumentTypes[i] = arguments.get(i).getType();
        }
        functionType = new ZenTypeFunction(returnType, argumentTypes);
    }
    
    @Override
    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
        if(type instanceof ZenTypeNative) {
            ZenTypeNative nativeType = (ZenTypeNative) type;
            Class nativeClass = nativeType.getNativeClass();
            if(nativeClass.isInterface() && nativeClass.getMethods().length == 1) {
                // functional interface
                Method method = nativeClass.getMethods()[0];
                if(returnType != ZenTypeAny.INSTANCE && !returnType.canCastImplicit(environment.getType(method.getGenericReturnType()), environment)) {
                    environment.error(position, "return type not compatible");
                    return new ExpressionInvalid(position);
                }
                if(arguments.size() != method.getParameterTypes().length) {
                    environment.error(getPosition(), String.format("Expected %s arguments, received %s arguments", method.getParameterTypes().length, arguments.size()));
                    return new ExpressionInvalid(position);
                }
                for(int i = 0; i < arguments.size(); i++) {
                    ZenType argumentType = environment.getType(method.getGenericParameterTypes()[i]);
                    if(arguments.get(i).getType() != ZenTypeAny.INSTANCE && !argumentType.canCastImplicit(arguments.get(i).getType(), environment)) {
                        environment.error(position, "argument " + i + " doesn't match");
                        return new ExpressionInvalid(position);
                    }
                }
                
                return new ExpressionJavaLambda(position, nativeClass, arguments, statements, environment.getType(nativeClass));
            } else {
                environment.error(position, type.toString() + " is not a functional interface");
                return new ExpressionInvalid(position);
            }
        } else {
            environment.error(position, "Cannot cast a function literal to " + type.toString());
            return new ExpressionInvalid(position);
        }
    }
    
    @Override
    public ZenType getType() {
        return functionType;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        // TODO: implement
        // TODO: make sure the function is compiled properly
        throw new UnsupportedOperationException("not yet implemented");
    }
}
