package stanhebben.zenscript.type;

import org.objectweb.asm.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.util.*;

public class ZenTypeArrayBasic extends ZenTypeArray {
    
    private final Type asmType;
    
    public ZenTypeArrayBasic(ZenType base) {
        super(base);
        
        asmType = Type.getType("[" + base.toASMType().getDescriptor());
    }
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof ZenTypeArrayBasic) {
            ZenTypeArrayBasic o = (ZenTypeArrayBasic) other;
            return o.getBaseType().equals(getBaseType());
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.getBaseType() != null ? this.getBaseType().hashCode() : 0);
        return hash;
    }
    
    @Override
    public ICastingRule getCastingRule(ZenType type, IEnvironmentGlobal environment) {
        ICastingRule base = super.getCastingRule(type, environment);
        if(base == null && getBaseType() == ANY && type instanceof ZenTypeArray) {
            ZenType toBaseType = ((ZenTypeArray) type).getBaseType();
            if(type instanceof ZenTypeArrayBasic) {
                return new CastingRuleArrayArray(ANY.getCastingRule(toBaseType, environment), this, (ZenTypeArrayBasic) type);
            } else if(type instanceof ZenTypeArrayList) {
                return new CastingRuleArrayList(ANY.getCastingRule(toBaseType, environment), this, (ZenTypeArrayList) type);
            } else {
                throw new RuntimeException("Invalid array type: " + type);
            }
        } else {
            return base;
        }
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        ICastingRuleDelegate arrayRules = new CastingRuleDelegateArray(rules, this);
        getBaseType().constructCastingRules(environment, arrayRules, followCasters);
        
        if(followCasters) {
            constructExpansionCastingRules(environment, rules);
        }
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        if(numValues == 1) {
            return new ValueIterator(methodOutput.getOutput());
        } else if(numValues == 2) {
            return new IndexValueIterator(methodOutput.getOutput());
        } else {
            return null;
        }
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal global) {
        return null;
    }
    
    @Override
    public Type toASMType() {
        return asmType;
    }
    
    @Override
    public Class toJavaClass() {
        try {
            return Class.forName("[L" + getBaseType().toJavaClass().getName() + ";");
        } catch(ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public String getSignature() {
        return "[" + getBaseType().getSignature();
    }
    
    @Override
    public IPartialExpression getMemberLength(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
        return new ExpressionArrayLength(position, value.eval(environment));
    }
    
    @Override
    public Expression indexGet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index) {
        return new ExpressionArrayGet(position, array, index.cast(position, environment, INT));
    }
    
    @Override
    public Expression indexSet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index, Expression value) {
        return new ExpressionArraySet(position, array, index.cast(position, environment, INT), value.cast(position, environment, getBaseType()));
    }
    
    private class ValueIterator implements IZenIterator {
        
        private final MethodOutput methodOutput;
        private int index;
        
        public ValueIterator(MethodOutput methodOutput) {
            this.methodOutput = methodOutput;
        }
        
        @Override
        public void compileStart(int[] locals) {
            index = methodOutput.local(Type.INT_TYPE);
            methodOutput.iConst0();
            methodOutput.storeInt(index);
        }
        
        @Override
        public void compilePreIterate(int[] locals, Label exit) {
            methodOutput.dup();
            methodOutput.arrayLength();
            methodOutput.loadInt(index);
            methodOutput.ifICmpLE(exit);
            
            methodOutput.dup();
            methodOutput.loadInt(index);
            methodOutput.arrayLoad(getBaseType().toASMType());
            methodOutput.store(getBaseType().toASMType(), locals[0]);
        }
        
        @Override
        public void compilePostIterate(int[] locals, Label exit, Label repeat) {
            methodOutput.iinc(index, 1);
            methodOutput.goTo(repeat);
        }
        
        @Override
        public void compileEnd() {
            // pop the array
            methodOutput.pop();
        }
        
        @Override
        public ZenType getType(int i) {
            return getBaseType();
        }
    }
    
    private class IndexValueIterator implements IZenIterator {
        
        private final MethodOutput methodOutput;
        
        public IndexValueIterator(MethodOutput methodOutput) {
            this.methodOutput = methodOutput;
        }
        
        @Override
        public void compileStart(int[] locals) {
            methodOutput.iConst0();
            methodOutput.storeInt(locals[0]);
        }
        
        @Override
        public void compilePreIterate(int[] locals, Label exit) {
            methodOutput.dup();
            methodOutput.arrayLength();
            methodOutput.loadInt(locals[0]);
            methodOutput.ifICmpLE(exit);
            
            methodOutput.dup();
            methodOutput.loadInt(locals[0]);
            methodOutput.arrayLoad(getBaseType().toASMType());
            methodOutput.store(getBaseType().toASMType(), locals[1]);
        }
        
        @Override
        public void compilePostIterate(int[] locals, Label exit, Label repeat) {
            methodOutput.iinc(locals[0]);
            methodOutput.goTo(repeat);
        }
        
        @Override
        public void compileEnd() {
            // pop the array
            methodOutput.pop();
        }
        
        @Override
        public ZenType getType(int i) {
            return i == 0 ? INT : getBaseType();
        }
    }
}
