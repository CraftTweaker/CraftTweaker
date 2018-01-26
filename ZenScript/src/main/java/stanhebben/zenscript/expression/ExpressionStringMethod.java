package stanhebben.zenscript.expression;

import org.objectweb.asm.Opcodes;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Method;
import java.util.*;

import static stanhebben.zenscript.util.StringUtil.methodMatchingError;

public class ExpressionStringMethod implements IPartialExpression {
    
    private static final Map<String, ZenNativeMember> members = new HashMap<>();
    private static boolean initialised = false;
    
    private final ZenPosition position;
    private final Expression source;
    private final String methodName;
    private final List<IJavaMethod> methods;
    
    
    public ExpressionStringMethod(ZenPosition position, Expression expression, String method) {
        this.position = position;
        this.source = expression;
        this.methodName = method;
        this.methods = members.get(method).getMethods();
    }
    
    public static boolean hasMethod(String name, ITypeRegistry typeRegistry) {
        if(!initialised) {
            initialise(typeRegistry);
        }
        return members.containsKey(name);
    }
    
    //No statics and no characters (ZS doesn't know latter)
    private static boolean checkMethod(Method method) {
        for(Class<?> parameter : method.getParameterTypes()) {
            if(parameter.isAssignableFrom(char.class) || parameter.isAssignableFrom(char[].class))
                return false;
        }
        return (method.getReturnType().isAssignableFrom(char.class) || method.getReturnType().isAssignableFrom(char[].class)) ? false : (method.getModifiers() & Opcodes.ACC_STATIC) == 0;
    }
    
    private static void initialise(ITypeRegistry typeRegistry) {
        for(Method method : String.class.getMethods()) {
            if(checkMethod(method)) {
                String methodName = method.getName();
                members.putIfAbsent(methodName, new ZenNativeMember());
                members.get(methodName).addMethod(new JavaMethod(method, typeRegistry));
            }
        }
        initialised = true;
    }
    
    @Override
    public ZenType getType() {
        return ZenType.ANY;
    }
    
    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        return ZenType.ANY;
    }
    
    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        environment.error(position, "Cannot evaluate a StringMethod");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        environment.error(position, "Cannot assign to a String method");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        environment.error(position, "Cannot get the member of a String method");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        IJavaMethod method = JavaMethod.select(false, methods, environment, values);
        if(method == null) {
            environment.error(position, methodMatchingError(methods, values));
            return new ExpressionInvalid(position);
        } else {
            return new ExpressionCallVirtual(position, environment, method, source.eval(environment), values);
        }
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return JavaMethod.predict(members.get(methodName).getMethods(), numArguments);
    }
    
    @Override
    public IZenSymbol toSymbol() {
        throw new UnsupportedOperationException("Cannot get a symbol from a String method");
    }
}
