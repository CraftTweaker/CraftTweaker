package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.casting.CastingAnySubtype;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Objects;

public abstract class Expression implements IPartialExpression {
    
    private final ZenPosition position;
    
    public Expression(ZenPosition position) {
        this.position = position;
    }
    
    public static Expression parse(ZenTokener parser, IEnvironmentMethod environment, ZenType predictedType) {
        return ParsedExpression.read(parser, environment).compile(environment, predictedType).eval(environment);
    }
    
    public ZenPosition getPosition() {
        return position;
    }
    
    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
        if(getType().equals(type)) {
            return this;
        } else if(Objects.equals(type.toJavaClass(),Object.class)) {
            
            // allows anything to cast to Object
            // This casts any primitive type to the object variation of it.
            if (this.getType().toJavaClass().isPrimitive()){
                Class clazz = this.getType().toJavaClass();
                ICastingRule castingRule = null;
                
                // primitive int to Object
                if (clazz.equals(int.class)){
                    castingRule = getType().getCastingRule(ZenType.INTOBJECT, environment);
                }else if (clazz.equals(double.class)){
                    castingRule = getType().getCastingRule(ZenType.DOUBLEOBJECT, environment);
                }else if (clazz.equals(boolean.class)){
                    castingRule = getType().getCastingRule(ZenType.BOOLOBJECT, environment);
                }else if (clazz.equals(short.class)){
                    castingRule = getType().getCastingRule(ZenType.SHORTOBJECT, environment);
                }else if (clazz.equals(float.class)){
                    castingRule = getType().getCastingRule(ZenType.FLOATOBJECT, environment);
                }else if (clazz.equals(byte.class)){
                    castingRule = getType().getCastingRule(ZenType.BYTEOBJECT, environment);
                }else if (clazz.equals(long.class)){
                    castingRule = getType().getCastingRule(ZenType.LONGOBJECT, environment);
                }
    
                if (castingRule != null){
                    return new ExpressionAs(position, this, castingRule);
                }
                
                environment.error(position, "Cannot cast primitive " + this.getType() + " to " + type);
                return new ExpressionInvalid(position, type);
            }else {
                return this;
            }
        } else {
            ICastingRule castingRule = getType().getCastingRule(type, environment);
            if(castingRule == null) {
                if(getType().canCastImplicit(type, environment)) {
                    return new ExpressionAs(position, this, new CastingAnySubtype(getType(), type));
                }
                environment.error(position, "Cannot cast " + this.getType() + " to " + type);
                return new ExpressionInvalid(position, type);
            } else {
                return new ExpressionAs(position, this, castingRule);
            }
        }
    }
    
    public abstract void compile(boolean result, IEnvironmentMethod environment);
    
    public void compileIf(Label onElse, IEnvironmentMethod environment) {
        if(getType() == ZenType.BOOL) {
            compile(true, environment);
            environment.getOutput().ifEQ(onElse);
        } else if(getType().isPointer()) {
            compile(true, environment);
            environment.getOutput().ifNull(onElse);
        } else {
            throw new RuntimeException("cannot compile non-pointer non-boolean value to if condition");
        }
    }
    
    // #########################################
    // ### IPartialExpression implementation ###
    // #########################################
    
    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        return this;
    }
    
    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        environment.error(position, "not a valid lvalue");
        return new ExpressionInvalid(position, getType());
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        return getType().getMember(position, environment, this, name);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        return getType().call(position, environment, this, values);
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return getType().predictCallTypes(numArguments);
    }
    
    @Override
    public IZenSymbol toSymbol() {
        return null;
    }
    
    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        environment.error(position, "not a valid type");
        return ZenType.ANY;
    }
}
