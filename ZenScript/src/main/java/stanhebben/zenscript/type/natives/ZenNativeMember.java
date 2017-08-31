package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

import static stanhebben.zenscript.util.StringUtil.methodMatchingError;

/**
 * @author Stan
 */
public class ZenNativeMember {
    
    private final List<IJavaMethod> methods = new ArrayList<>();
    private IJavaMethod getter;
    private IJavaMethod setter;
    
    public IJavaMethod getGetter() {
        return getter;
    }
    
    public void setGetter(IJavaMethod getter) {
        if(this.getter == null) {
            this.getter = getter;
        } else {
            throw new RuntimeException("already has a getter");
        }
    }
    
    public IJavaMethod getSetter() {
        return setter;
    }
    
    public void setSetter(IJavaMethod setter) {
        if(this.setter == null) {
            this.setter = setter;
        } else {
            throw new RuntimeException("already has a setter");
        }
    }
    
    public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
        return new InstanceGetValue(position, value);
    }
    
    public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment) {
        return new StaticGetValue(position);
    }
    
    public void addMethod(JavaMethod method) {
        methods.add(method);
    }
    
    public List<IJavaMethod> getMethods() {
        return methods;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(methods.toArray());
    }
    
    private class InstanceGetValue implements IPartialExpression {
        
        private final ZenPosition position;
        private final IPartialExpression value;
        
        public InstanceGetValue(ZenPosition position, IPartialExpression value) {
            this.position = position;
            this.value = value;
        }
        
        @Override
        public Expression eval(IEnvironmentGlobal environment) {
            return new ExpressionCallVirtual(position, environment, getter, value.eval(environment));
        }
        
        @Override
        public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
            return new ExpressionCallVirtual(position, environment, setter, value.eval(environment), other);
        }
        
        @Override
        public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
            return getter.getReturnType().getMember(position, environment, this, name);
        }
        
        @Override
        public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
            IJavaMethod method = JavaMethod.select(false, methods, environment, values);
            if(method == null) {
                environment.error(position, methodMatchingError(methods, values));
                return new ExpressionInvalid(position);
            } else {
                return new ExpressionCallVirtual(position, environment, method, value.eval(environment), values);
            }
        }
        
        @Override
        public ZenType[] predictCallTypes(int numArguments) {
            return JavaMethod.predict(methods, numArguments);
        }
        
        @Override
        public IZenSymbol toSymbol() {
            return null;
        }
        
        @Override
        public ZenType getType() {
            return getter.getReturnType();
        }
        
        @Override
        public ZenType toType(IEnvironmentGlobal environment) {
            environment.error(position, "not a valid type");
            return ZenType.ANY;
        }
    }
    
    private class StaticGetValue implements IPartialExpression {
        
        private final ZenPosition position;
        
        private StaticGetValue(ZenPosition position) {
            this.position = position;
        }
        
        @Override
        public Expression eval(IEnvironmentGlobal environment) {
            return new ExpressionCallStatic(position, environment, setter);
        }
        
        @Override
        public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
            return new ExpressionCallStatic(position, environment, setter);
        }
        
        @Override
        public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
            return getter.getReturnType().getMember(position, environment, this, name);
        }
        
        @Override
        public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
            IJavaMethod method = JavaMethod.select(true, methods, environment, values);
            if(method == null) {
                environment.error(position, methodMatchingError(methods, values));
                return new ExpressionInvalid(position);
            } else {
                return new ExpressionCallStatic(position, environment, method, values);
            }
        }
        
        @Override
        public ZenType[] predictCallTypes(int numArguments) {
            return JavaMethod.predict(methods, numArguments);
        }
        
        @Override
        public IZenSymbol toSymbol() {
            return new StaticSymbol();
        }
        
        @Override
        public ZenType getType() {
            return getter.getReturnType();
        }
        
        @Override
        public ZenType toType(IEnvironmentGlobal environment) {
            environment.error(position, "not a valid type");
            return ZenType.ANY;
        }
    }
    
    private class StaticSymbol implements IZenSymbol {
        
        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new StaticGetValue(position);
        }
    }
}
