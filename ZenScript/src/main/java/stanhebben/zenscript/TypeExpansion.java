package stanhebben.zenscript;

import org.objectweb.asm.*;
import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.expand.*;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * Type expansions provide additional members for existing types. They can add
 * members, casters or operators and these will be available to all compiling
 * scripts.
 *
 * @author Stan Hebben
 */
public class TypeExpansion {
    
    private final String type;
    private final Map<String, ZenExpandMember> members;
    private final Map<String, ZenExpandMember> staticMembers;
    private final List<ZenExpandCaster> casters;
    private final List<ZenNativeOperator> trinaryOperators;
    private final List<ZenNativeOperator> binaryOperators;
    private final List<ZenNativeOperator> unaryOperators;
    
    /**
     * Creates an empty type expansion. Use the expand method to register the
     * actual expansions.
     *
     * @param type type name
     */
    public TypeExpansion(String type) {
        this.type = type;
        
        members = new HashMap<>();
        staticMembers = new HashMap<>();
        casters = new ArrayList<>();
        trinaryOperators = new ArrayList<>();
        binaryOperators = new ArrayList<>();
        unaryOperators = new ArrayList<>();
    }
    
    /**
     * Added a native type expansion. The provided class must contain the proper
     * annotations for the required expansions, else nothing will happen. Each
     * expansion method must be static and accept the expanded type as first
     * argument.
     *
     * @param cls   expanding class
     * @param types type registry
     */
    public void expand(Class cls, ITypeRegistry types) {
        for(Method method : cls.getMethods()) {
            String methodName = method.getName();
            
            for(Annotation annotation : method.getAnnotations()) {
                if(annotation instanceof ZenCaster) {
                    checkStatic(method);
                    casters.add(new ZenExpandCaster(new JavaMethod(method, types)));
                } else if(annotation instanceof ZenGetter) {
                    checkStatic(method);
                    ZenGetter getterAnnotation = (ZenGetter) annotation;
                    String name = getterAnnotation.value().length() == 0 ? method.getName() : getterAnnotation.value();
                    
                    // error checking for faulty @ZenGetter annotations TODO: Confirm working
                    if (method.getReturnType().equals(Void.TYPE)){
                        throw new RuntimeException("ZenGetter needs a non Void returntype - " + cls.getName() + "." + method.getName());
                    }
                    if (method.getParameterCount() > 0){
                        throw new RuntimeException("ZenGetter may not have any parameters - " + cls.getName() + "." + method.getName());
                    }
                    
                    if(!members.containsKey(name)) {
                        members.put(name, new ZenExpandMember(type, name));
                    }
                    members.get(name).setGetter(new JavaMethod(method, types));
                } else if(annotation instanceof ZenSetter) {
                    checkStatic(method);
                    ZenSetter setterAnnotation = (ZenSetter) annotation;
                    // error checking for faulty @ZenSetter annotations
                    if (method.getParameterCount() != 1){
                        throw new RuntimeException("ZenSetter must have exactly one parameter - " + cls.getName() + "." + method.getName());
                    }
                    
                    String name = setterAnnotation.value().length() == 0 ? method.getName() : setterAnnotation.value();
                    
                    if(!members.containsKey(name)) {
                        members.put(name, new ZenExpandMember(type, name));
                    }
                    members.get(name).setSetter(new JavaMethod(method, types));
                } else if(annotation instanceof ZenOperator) {
                    checkStatic(method);
                    ZenOperator operatorAnnotation = (ZenOperator) annotation;
                    switch(operatorAnnotation.value()) {
                        case NEG:
                        case NOT:
                            if(method.getParameterTypes().length != 1) {
                                // TODO: error
                            } else {
                                unaryOperators.add(new ZenNativeOperator(operatorAnnotation.value(), new JavaMethod(method, types)));
                            }
                            break;
                        case ADD:
                        case SUB:
                        case CAT:
                        case MUL:
                        case DIV:
                        case MOD:
                        case AND:
                        case OR:
                        case XOR:
                        case INDEXGET:
                        case RANGE:
                        case CONTAINS:
                        case COMPARE:
                            if(method.getParameterTypes().length != 2) {
                                throw new RuntimeException("Binary operator expansion needs a static method with 2 arguments - " + cls.getName() + "." + method.getName());
                            } else {
                                binaryOperators.add(new ZenNativeOperator(operatorAnnotation.value(), new JavaMethod(method, types)));
                            }
                            break;
                        case INDEXSET:
                            if(method.getParameterTypes().length != 3) {
                                // TODO: error
                            } else {
                                trinaryOperators.add(new ZenNativeOperator(operatorAnnotation.value(), new JavaMethod(method, types)));
                            }
                            break;
                    }
                } else if(annotation instanceof ZenMethod) {
                    checkStatic(method);
                    ZenMethod methodAnnotation = (ZenMethod) annotation;
                    if(methodAnnotation.value().length() > 0) {
                        methodName = methodAnnotation.value();
                    }
                    if(!members.containsKey(methodName)) {
                        members.put(methodName, new ZenExpandMember(type, methodName));
                    }
                    members.get(methodName).addMethod(new JavaMethod(method, types));
                } else if(annotation instanceof ZenMethodStatic) {
                    checkStatic(method);
                    ZenMethodStatic methodAnnotation = (ZenMethodStatic) annotation;
                    if(methodAnnotation.value().length() > 0) {
                        methodName = methodAnnotation.value();
                    }
                    if(!staticMembers.containsKey(methodName)) {
                        staticMembers.put(methodName, new ZenExpandMember(type, methodName));
                    }
                    staticMembers.get(methodName).addMethod(new JavaMethod(method, types));
                }
            }
        }
    }
    
    /**
     * Registers all casting rules to the given delegate.
     *
     * @param environment compilation environment
     * @param rules       target delegate
     */
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules) {
        for(ZenExpandCaster caster : casters) {
            caster.constructCastingRules(environment, rules);
        }
    }
    
    /**
     * Retrieves a caster from this expansion. May return null if no suitable
     * caster was available.
     *
     * @param type        target type
     * @param environment compilation environment
     *
     * @return caster, or null if there is no suitable caster
     */
    public ZenExpandCaster getCaster(ZenType type, IEnvironmentGlobal environment) {
        for(ZenExpandCaster caster : casters) {
            if(caster.getTarget().equals(type)) {
                return caster;
            }
        }
        
        for(ZenExpandCaster caster : casters) {
            if(caster.getTarget().canCastImplicit(type, environment)) {
                return caster;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a unary operator from this expansion. May return null if no
     * suitable operator was available.
     *
     * @param position    calling position
     * @param environment compile environment
     * @param value       argument value
     * @param operator    unary operator
     *
     * @return the resulting expression, or null if no operator was available
     */
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        for(ZenNativeOperator op : unaryOperators) {
            if(op.getOperator() == operator) {
                return new ExpressionCallStatic(position, environment, op.getMethod(), value);
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a binary operator from this expansion. May return null if no
     * suitable operator was available.
     *
     * @param position    calling position
     * @param environment compile environment
     * @param left        first argument value (left side)
     * @param right       second argument value (right side)
     * @param operator    binary operator
     *
     * @return the resulting expression, or null if no operator was available
     */
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        for(ZenNativeOperator op : binaryOperators) {
            if(op.getOperator() == operator) {
                return new ExpressionCallStatic(position, environment, op.getMethod(), left, right);
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a ternary operator from this expansion. May return null if no
     * suitable operator was available.
     *
     * @param position    calling position
     * @param environment compile environment
     * @param first       first argument value
     * @param second      second argument value
     * @param third       third arugment value
     * @param operator    ternary operator
     *
     * @return the resulting expression, or null if no operator was available
     */
    public Expression ternary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        for(ZenNativeOperator op : trinaryOperators) {
            if(op.getOperator() == operator) {
                return new ExpressionCallStatic(position, environment, op.getMethod(), first, second, third);
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves an instance member from this expansion. May return null if no
     * suitable member was available.
     *
     * @param position    calling position
     * @param environment compile environment
     * @param value       instance value
     * @param member      member name
     *
     * @return resulting member expression, or null if no such member was
     * available
     */
    public IPartialExpression instanceMember(ZenPosition position, IEnvironmentGlobal environment, Expression value, String member) {
        if(members.containsKey(member)) {
            return members.get(member).instance(position, environment, value);
        }
        
        return null;
    }
    
    /**
     * Retrieves a static member from this expansion. May return null if no
     * suitable member was available.
     *
     * @param position    calling position
     * @param environment compile environment
     * @param member      member name
     *
     * @return resulting static member expression, or null if no such member was
     * available
     */
    public IPartialExpression staticMember(ZenPosition position, IEnvironmentGlobal environment, String member) {
        if(staticMembers.containsKey(member)) {
            return staticMembers.get(member).instance(position, environment);
        }
        
        return null;
    }
    
    public void compileAnyCast(ZenType type, MethodOutput output, IEnvironmentGlobal environment, int localValue, int localClass) {
        if(type == null)
            throw new IllegalArgumentException("type cannot be null");
        
        Type asmType = type.toASMType();
        if(asmType == null) {
            throw new RuntimeException("type has no asm type");
        }
        
        for(ZenExpandCaster caster : casters) {
            Label skip = new Label();
            output.loadObject(localClass);
            output.constant(caster.getTarget().toASMType());
            output.ifACmpNe(skip);
            output.load(asmType, localValue);
            caster.compile(output);
            output.returnType(caster.getTarget().toASMType());
            output.label(skip);
        }
        
        for(ZenExpandCaster caster : casters) {
            String casterAny = caster.getTarget().getAnyClassName(environment);
            if(casterAny == null)
                // TODO: make sure this isn't necessary
                continue;
            
            Label skip = new Label();
            output.loadObject(localClass);
            output.invokeStatic(casterAny, "rtCanCastImplicit", "(Ljava/lang/Class;)Z");
            output.ifEQ(skip);
            output.load(type.toASMType(), localValue);
            caster.compile(output);
            output.loadObject(localClass);
            output.invokeStatic(casterAny, "rtAs", "(" + caster.getTarget().getSignature() + "Ljava/lang/Class;)Ljava/lang/Object;");
            output.returnType(caster.getTarget().toASMType());
            output.label(skip);
        }
    }
    
    public void compileAnyCanCastImplicit(ZenType type, MethodOutput output, IEnvironmentGlobal environment, int localClass) {
        for(ZenExpandCaster caster : casters) {
            Label skip = new Label();
            output.loadObject(localClass);
            output.constant(caster.getTarget().toASMType());
            output.ifACmpNe(skip);
            output.iConst1();
            output.returnInt();
            output.label(skip);
        }
        
        for(ZenExpandCaster caster : casters) {
            String casterAny = caster.getTarget().getAnyClassName(environment);
            if(casterAny == null) {
                // TODO: make sure no type ever does this
                continue;
            }
            
            Label skip = new Label();
            output.loadObject(localClass);
            output.invokeStatic(casterAny, "rtCanCastImplicit", "(Ljava/lang/Class;)Z");
            output.ifEQ(skip);
            output.iConst1();
            output.returnInt();
            output.label(skip);
        }
    }
    
    public boolean compileAnyUnary(MethodOutput output, OperatorType type, IEnvironmentMethod environment) {
        for(ZenNativeOperator operator : unaryOperators) {
            if(operator.getOperator() == type) {
                ZenType returnType = operator.getMethod().getReturnType();
                
                output.loadObject(0);
                operator.getMethod().invokeStatic(output);
                output.invokeStatic(returnType.getAnyClassName(environment), "valueOf", "(" + returnType.getSignature() + ")" + ZenType.ANY.getSignature());
                output.returnObject();
                return true;
            }
        }
        
        return false;
    }
    
    // #######################
    // ### Private methods ###
    // #######################
    
    /**
     * Checks if the given method is static. Throws an exception if not.
     *
     * @param method metod to validate
     */
    private void checkStatic(Method method) {
        if((method.getModifiers() & Modifier.STATIC) == 0) {
            throw new RuntimeException("Expansion method " + method.getName() + " must be static");
        }
    }
}
