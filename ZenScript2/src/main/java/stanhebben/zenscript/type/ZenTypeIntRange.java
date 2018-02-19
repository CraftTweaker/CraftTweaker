package stanhebben.zenscript.type;

import org.objectweb.asm.*;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.*;
import stanhebben.zenscript.value.IntRange;

import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 * @author Stan
 */
public class ZenTypeIntRange extends ZenType {
    
    public static final ZenTypeIntRange INSTANCE = new ZenTypeIntRange();
    
    private final IJavaMethod methodFrom;
    private final IJavaMethod methodTo;
    
    private ZenTypeIntRange() {
        ITypeRegistry dummy = new TypeRegistry();
        methodFrom = JavaMethod.get(dummy, IntRange.class, "getFrom");
        methodTo = JavaMethod.get(dummy, IntRange.class, "getTo");
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal global) {
        throw new UnsupportedOperationException("range values cannot yet be used as any value");
    }
    
    @Override
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        environment.error(position, "cannot apply unary operators on int ranges");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        environment.error(position, "cannot apply binary operators on int ranges");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        environment.error(position, "cannot apply ternary operators on int ranges");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        environment.error(position, "cannot compare int ranges");
        return new ExpressionInvalid(position, BOOL);
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        switch(name) {
            case "from":
                return new ExpressionCallVirtual(position, environment, methodFrom, value.eval(environment));
            case "to":
                return new ExpressionCallVirtual(position, environment, methodTo, value.eval(environment));
            default:
                environment.error(position, "no such member " + name + " in int range");
                return new ExpressionInvalid(position);
        }
    }
    
    @Override
    public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        environment.error(position, "int ranges don't have static members");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        environment.error(position, "int ranges cannot be called");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        
    }

	/*
     * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { if (type == this) { return
	 * value; }
	 * 
	 * environment.error(position, "cannot cast int ranges to any other type");
	 * return new ExpressionInvalid(position); }
	 */
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        if(numValues == 1) {
            return new IntRangeIterator(methodOutput);
        } else {
            return null;
        }
    }

	/*
	 * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type == this; }
	 * 
	 * @Override public boolean canCastExplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type == this; }
	 */
    
    @Override
    public Class toJavaClass() {
        return IntRange.class;
    }
    
    @Override
    public Type toASMType() {
        return Type.getType(IntRange.class);
    }
    
    @Override
    public int getNumberType() {
        return 0;
    }
    
    @Override
    public String getSignature() {
        return signature(IntRange.class);
    }
    
    @Override
    public boolean isPointer() {
        return true;
    }

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { throw new
	 * UnsupportedOperationException("Not supported yet."); //To change body of
	 * generated methods, choose Tools | Templates. }
	 */
    
    @Override
    public String getName() {
        return "stanhebben.zenscript.value.IntRange";
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
    
    private class IntRangeIterator implements IZenIterator {
        
        private final IEnvironmentMethod method;
        
        public IntRangeIterator(IEnvironmentMethod methodOutput) {
            this.method = methodOutput;
        }
        
        @Override
        public void compileStart(int[] locals) {
            MethodOutput output = method.getOutput();
            
            output.dup(); // copy IntRange reference
            methodFrom.invokeVirtual(output);
            output.storeInt(locals[0]);
            methodTo.invokeVirtual(output);
        }
        
        @Override
        public void compilePreIterate(int[] locals, Label exit) {
            
        }
        
        @Override
        public void compilePostIterate(int[] locals, Label exit, Label repeat) {
            MethodOutput output = method.getOutput();
            
            output.dup(); // copy limit
            
            output.iinc(locals[0]);
            output.loadInt(locals[0]);
            
            output.ifICmpGT(repeat);
            output.goTo(exit);
        }
        
        @Override
        public void compileEnd() {
            method.getOutput().pop(); // remove limit
        }
        
        @Override
        public ZenType getType(int i) {
            return INT;
        }
    }
}
