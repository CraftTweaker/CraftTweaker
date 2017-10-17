package stanhebben.zenscript.type;

import org.objectweb.asm.*;
import org.objectweb.asm.Type;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.type.iterator.*;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.*;
import stanhebben.zenscript.value.IAny;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static stanhebben.zenscript.util.AnyClassWriter.*;
import static stanhebben.zenscript.util.ZenTypeUtil.*;

/**
 * @author Stanneke
 */
public class ZenTypeNative extends ZenType {
    
    private static final int ITERATOR_NONE = 0;
    private static final int ITERATOR_ITERABLE = 1;
    private static final int ITERATOR_LIST = 2;
    private static final int ITERATOR_MAP = 3;
    
    private final Class<?> cls;
    private final String anyName;
    private final String anyName2;
    private final List<ZenTypeNative> implementing;
    private final Map<String, ZenNativeMember> members;
    private final Map<String, ZenNativeMember> staticMembers;
    private final List<ZenNativeCaster> casters;
    private final List<ZenNativeOperator> trinaryOperators;
    private final List<ZenNativeOperator> binaryOperators;
    private final List<ZenNativeOperator> unaryOperators;
    
    private int iteratorType;
    private String classPkg;
    private String className;
    private Annotation iteratorAnnotation;
    private ZenType iteratorKeyType;
    private ZenType iteratorValueType;
    
    public ZenTypeNative(Class<?> cls) {
        this.cls = cls;
        members = new HashMap<>();
        staticMembers = new HashMap<>();
        casters = new ArrayList<>();
        trinaryOperators = new ArrayList<>();
        binaryOperators = new ArrayList<>();
        unaryOperators = new ArrayList<>();
        implementing = new ArrayList<>();
        
        anyName2 = cls.getName() + "Any";
        anyName = anyName2.replace('.', '/');
    }
    
    public void complete(ITypeRegistry types) {
        int iterator = ITERATOR_NONE;
        Annotation _iteratorAnnotation = null;
        String _classPkg = cls.getPackage().getName().replace('/', '.');
        String _className = cls.getSimpleName();
        boolean fully = false;
        
        Queue<ZenTypeNative> todo = new LinkedList<>();
        todo.add(this);
        addSubtypes(todo, types);
        
        Annotation[] clsAnnotations = cls.getAnnotations();
        for(Annotation annotation : clsAnnotations) {
            if(annotation instanceof ZenClass) {
                String value = ((ZenClass) annotation).value();
                int dot = value.lastIndexOf('.');
                if(dot < 0) {
                    _classPkg = null;
                    _className = value;
                } else {
                    _classPkg = value.substring(0, dot);
                    _className = value.substring(dot + 1);
                }
            }
            if(annotation instanceof IterableSimple) {
                iterator = ITERATOR_ITERABLE;
                _iteratorAnnotation = annotation;
                if(!Iterable.class.isAssignableFrom(cls)) {
                    // TODO: illegal
                }
            }
            if(annotation instanceof IterableList) {
                iterator = ITERATOR_LIST;
                _iteratorAnnotation = annotation;
                if(!List.class.isAssignableFrom(cls)) {
                    // TODO: illegal
                }
            }
            if(annotation instanceof IterableMap) {
                iterator = ITERATOR_MAP;
                _iteratorAnnotation = annotation;
                if(!Map.class.isAssignableFrom(cls)) {
                    // TODO: illegal
                }
            }
        }
        
        //TODO check this
        for(Method method : cls.getMethods()) {
            boolean isMethod = fully;
            String methodName = method.getName();
            
            for(Annotation annotation : method.getAnnotations()) {
                if(annotation instanceof ZenCaster) {
                    casters.add(new ZenNativeCaster(JavaMethod.get(types, method)));
                    isMethod = false;
                } else if(annotation instanceof ZenGetter) {
                    ZenGetter getterAnnotation = (ZenGetter) annotation;
    
                    checkGetter(method, cls);
                    String name = getterAnnotation.value().length() == 0 ? method.getName() : getterAnnotation.value();
                    
                    if(!members.containsKey(name)) {
                        members.put(name, new ZenNativeMember());
                    }
                    JavaMethod javaMethod = new JavaMethod(method, types);
                    members.get(name).setGetter(javaMethod);
                    isMethod = false;
                } else if(annotation instanceof ZenSetter) {
                    ZenSetter setterAnnotation = (ZenSetter) annotation;
                    
                    checkSetter(method, cls);
                    String name = setterAnnotation.value().length() == 0 ? method.getName() : setterAnnotation.value();
                    
                    if(!members.containsKey(name)) {
                        members.put(name, new ZenNativeMember());
                    }
                    members.get(name).setSetter(new JavaMethod(method, types));
                    isMethod = false;
                } else if(annotation instanceof ZenMemberGetter) {
                    binaryOperators.add(new ZenNativeOperator(OperatorType.MEMBERGETTER, new JavaMethod(method, types)));
                } else if(annotation instanceof ZenMemberSetter) {
                    trinaryOperators.add(new ZenNativeOperator(OperatorType.MEMBERSETTER, new JavaMethod(method, types)));
                } else if(annotation instanceof ZenOperator) {
                    ZenOperator operatorAnnotation = (ZenOperator) annotation;
                    switch(operatorAnnotation.value()) {
                        case NEG:
                        case NOT:
                            if(method.getParameterTypes().length != 0) {
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
                            if(method.getParameterTypes().length != 1) {
                                // TODO: error
                            } else {
                                binaryOperators.add(new ZenNativeOperator(operatorAnnotation.value(), new JavaMethod(method, types)));
                            }
                            break;
                        case INDEXSET:
                            if(method.getParameterTypes().length != 2) {
                                // TODO: error
                            } else {
                                trinaryOperators.add(new ZenNativeOperator(operatorAnnotation.value(), new JavaMethod(method, types)));
                            }
                            break;
                    }
                    isMethod = false;
                } else if(annotation instanceof ZenMethod) {
                    isMethod = true;
                    
                    ZenMethod methodAnnotation = (ZenMethod) annotation;
                    if(methodAnnotation.value().length() > 0) {
                        methodName = methodAnnotation.value();
                    }
                }
            }
            
            if(isMethod) {
                if((method.getModifiers() & Modifier.STATIC) > 0) {
                    if(!staticMembers.containsKey(methodName)) {
                        staticMembers.put(methodName, new ZenNativeMember());
                    }
                    staticMembers.get(methodName).addMethod(new JavaMethod(method, types));
                } else {
                    if(!members.containsKey(methodName)) {
                        members.put(methodName, new ZenNativeMember());
                    }
                    members.get(methodName).addMethod(new JavaMethod(method, types));
                }
            }
        }

        for (Field field: cls.getFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof ZenProperty) {
                    ZenProperty zenProperty = (ZenProperty) annotation;
                    String propertyName = zenProperty.value();
                    if (propertyName.isEmpty()) {
                        propertyName = field.getName();
                    }

                    String methodEnding = propertyName.substring(0, 1).toUpperCase(Locale.US) + propertyName.substring(1);
                    String getterName = zenProperty.getter();
                    if (getterName.isEmpty()) {
                        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                            getterName = "is" + methodEnding;
                        } else {
                            getterName = "get" + methodEnding;
                        }
                    }

                    String setterName = zenProperty.setter();
                    if (setterName.isEmpty()) {
                        setterName = "set" + methodEnding;
                    }

                    members.putIfAbsent(propertyName, new ZenNativeMember());

                    try {
                        Method getterMethod = cls.getMethod(getterName);
                        checkGetter(getterMethod, cls);
                        members.get(propertyName).setGetter(new JavaMethod(getterMethod, types));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("Couldn't find getter for property " + propertyName + " on " + cls.getName());
                    }

                    try {
                        Method setterMethod = cls.getMethod(setterName, field.getType());
                        checkSetter(setterMethod, cls);
                        members.get(propertyName).setSetter(new JavaMethod(setterMethod, types));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("Couldn't find setter for property " + propertyName + " on " + cls.getName());
                    }
                }
            }
        }
        
        this.iteratorType = iterator;
        this.iteratorAnnotation = _iteratorAnnotation;
        this.classPkg = _classPkg;
        this.className = _className;
    }

    private void checkGetter(Method method, Class cls) {
        if (method.getReturnType().equals(Void.TYPE)){
            throw new RuntimeException("ZenGetter needs a non Void returntype - " + cls.getName() + "." + method.getName());
        }
        if (method.getParameterCount() > 0){
            throw new RuntimeException("ZenGetter may not have any parameters - " + cls.getName() + "." + method.getName());
        }
    }

    private void checkSetter(Method method, Class cls) {
        if (method.getParameterCount() != 1){
            throw new RuntimeException("ZenSetter must have exactly one parameter - " + cls.getName() + "." + method.getName());
        }
        if (!method.getReturnType().equals(Void.TYPE)) {
            throw new RuntimeException("ZenSetter must have a void return type");
        }
    }
    
    public Class getNativeClass() {
        return cls;
    }
    
    public void complete(IEnvironmentGlobal environment) {
        if(iteratorAnnotation instanceof IterableSimple) {
            IterableSimple annotation = (IterableSimple) iteratorAnnotation;
            iteratorValueType = ZenType.parse(annotation.value(), environment);
        }
        if(iteratorAnnotation instanceof IterableList) {
            IterableList annotation = (IterableList) iteratorAnnotation;
            iteratorKeyType = ZenTypeInt.INSTANCE;
            iteratorValueType = ZenType.parse(annotation.value(), environment);
        }
        if(iteratorAnnotation instanceof IterableMap) {
            IterableMap annotation = (IterableMap) iteratorAnnotation;
            iteratorKeyType = ZenType.parse(annotation.key(), environment);
            iteratorValueType = ZenType.parse(annotation.value(), environment);
        }
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal global) {
        if(!global.containsClass(anyName2)) {
            global.putClass(anyName2, new byte[0]);
            // global.putClass(anyName2, AnyClassWriter.construct(new
            // AnyNativeDefinition(global), anyName2, toASMType()));
        }
        
        return anyName;
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        ZenNativeMember member = members.get(name);
        if(member == null) {
            for(ZenTypeNative type : implementing) {
                if(type.members.containsKey(name)) {
                    member = type.members.get(name);
                    break;
                }
            }
        }
        
        if(member == null) {
            Expression evalue = value.eval(environment);
            IPartialExpression member2 = memberExpansion(position, environment, evalue, name);
            if(member2 == null) {
                for(ZenTypeNative type : implementing) {
                    member2 = type.memberExpansion(position, environment, evalue, name);
                    if(member2 != null)
                        break;
                }
            }
            if(member2 == null) {
                if(hasBinary(STRING, OperatorType.MEMBERGETTER)) {
                    return binary(position, environment, value.eval(environment), new ExpressionString(position, name), OperatorType.MEMBERGETTER);
                } else {
                    environment.error(position, "No such member in " + getName() + ": " + name);
                    return new ExpressionInvalid(position);
                }
            } else {
                return member2;
            }
        } else {
            return member.instance(position, environment, value);
        }
    }
    
    @Override
    public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        ZenNativeMember member = staticMembers.get(name);
        if(member == null) {
            for(ZenTypeNative type : implementing) {
                if(type.staticMembers.containsKey(name)) {
                    member = type.staticMembers.get(name);
                    break;
                }
            }
        }
        if(member == null) {
            IPartialExpression member2 = staticMemberExpansion(position, environment, name);
            if(member2 == null) {
                for(ZenTypeNative type : implementing) {
                    member2 = type.staticMemberExpansion(position, environment, name);
                    if(member2 != null)
                        break;
                }
            }
            if(member2 == null) {
                environment.error(position, "No such static member in " + getName() + ": " + name);
                return new ExpressionInvalid(position);
            } else {
                return member2;
            }
        } else {
            return member.instance(position, environment);
        }
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        switch(iteratorType) {
            case ITERATOR_NONE:
                break;
            case ITERATOR_ITERABLE:
                if(numValues == 1) {
                    return new IteratorIterable(methodOutput.getOutput(), iteratorValueType);
                } else if(numValues == 2) {
                    return new IteratorList(methodOutput.getOutput(), iteratorValueType);
                }
                break;
            case ITERATOR_MAP:
                if(numValues == 1) {
                    return new IteratorMapKeys(methodOutput.getOutput(), new ZenTypeAssociative(iteratorValueType, iteratorKeyType));
                } else if(numValues == 2) {
                    return new IteratorMap(methodOutput.getOutput(), new ZenTypeAssociative(iteratorValueType, iteratorKeyType));
                }
                break;
            case ITERATOR_LIST:
                if(numValues == 1) {
                    // list is also iterable
                    return new IteratorIterable(methodOutput.getOutput(), iteratorValueType);
                } else if(numValues == 2) {
                    return new IteratorList(methodOutput.getOutput(), iteratorValueType);
                }
                break;
        }
        return null;
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        if(cls.getSuperclass() != null) {
            ZenType superType = environment.getType(cls.getSuperclass());
            
            rules.registerCastingRule(superType, new CastingRuleNone(this, superType));
            
            superType.constructCastingRules(environment, rules, followCasters);
        }
        
        for(Class iface : cls.getInterfaces()) {
            ZenType ifaceType = environment.getType(iface);
            
            rules.registerCastingRule(ifaceType, new CastingRuleNone(this, ifaceType));
            
            ifaceType.constructCastingRules(environment, rules, followCasters);
        }
        
        if(followCasters) {
            for(ZenNativeCaster caster : casters) {
                // TODO: implement
            }
            
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.constructCastingRules(environment, rules);
            }
        }
        
        rules.registerCastingRule(BOOL, new CastingNotNull(this));
        rules.registerCastingRule(ANY, new CastingRuleNullableStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, this)));
    }
    
    @Override
    public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
        return type == this || canCastImplicit(type, environment) || type.canCastExplicit(this, environment);
    }
    
    @Override
    public Class toJavaClass() {
        return cls;
    }
    
    @Override
    public Type toASMType() {
        return Type.getType(cls);
    }
    
    @Override
    public int getNumberType() {
        return 0;
    }
    
    @Override
    public String getSignature() {
        return signature(cls);
    }
    
    @Override
    public boolean isPointer() {
        return true;
    }
    
    @Override
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        for(ZenNativeOperator unaryOperator : unaryOperators) {
            if(unaryOperator.getOperator() == operator) {
                return new ExpressionCallVirtual(position, environment, unaryOperator.getMethod(), value);
            }
        }
        
        environment.error(position, "operator not supported");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        for(ZenNativeOperator binaryOperator : binaryOperators) {
            if(binaryOperator.getOperator() == operator) {
                return new ExpressionCallVirtual(position, environment, binaryOperator.getMethod(), left, right);
            }
        }
        
        environment.error(position, "operator not supported");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        for(ZenNativeOperator trinaryOperator : trinaryOperators) {
            if(trinaryOperator.getOperator() == operator) {
                return new ExpressionCallVirtual(position, environment, trinaryOperator.getMethod(), first, second, third);
            }
        }
        
        environment.error(position, "operator not supported");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        if(type == CompareType.EQ || type == CompareType.NE) {
            for(ZenNativeOperator binaryOperator : binaryOperators) {
                if(binaryOperator.getOperator() == OperatorType.EQUALS) {
                    Expression result = new ExpressionCallVirtual(position, environment, binaryOperator.getMethod(), left, right);
                    if(type == CompareType.EQ) {
                        return result;
                    } else {
                        return new ExpressionArithmeticUnary(position, OperatorType.NOT, result);
                    }
                }
            }
        }
        
        return new ExpressionCompareGeneric(position, binary(position, environment, left, right, OperatorType.COMPARE), type);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        // TODO: support functional interfaces
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        // TODO: support functional interface
        return new ZenType[numArguments];
    }
    
    private boolean hasBinary(ZenType type, OperatorType operator) {
        for(ZenNativeOperator binaryOperator : binaryOperators) {
            if(binaryOperator.getOperator() == operator) {
                return true;
            }
        }
        
        return false;
    }

	/*
     * private void compileAnyUnary(String anySignature, OperatorType operator,
	 * IEnvironmentMethod environment) { List<ZenNativeOperator> operators = new
	 * ArrayList<ZenNativeOperator>(); for (ZenNativeOperator unary :
	 * this.unaryOperators) { if (unary.getOperator() == operator) {
	 * operators.add(unary); } }
	 * 
	 * MethodOutput output = environment.getOutput(); if (operators.isEmpty()) {
	 * output.newObject(ZenRuntimeException.class); output.dup();
	 * output.constant("Cannot " + operator + " " + classPkg + '.' + className);
	 * output.construct(ZenRuntimeException.class, String.class);
	 * output.aThrow(); } else { if (operators.size() > 1) {
	 * environment.error(null, "Multiple " + operator + " operators for " +
	 * cls); }
	 * 
	 * ZenNativeOperator unary = operators.get(0);
	 * 
	 * output.loadObject(0); output.getField(anySignature, "value",
	 * signature(cls));
	 * 
	 * output.invoke(unary.getMethod());
	 * environment.getType(unary.getClass()).compileCast(null, environment,
	 * ANY); output.returnObject(); } }
	 * 
	 * private void compileAnyBinary(String anySignature, OperatorType operator,
	 * IEnvironmentMethod environment) { List<ZenNativeOperator> operators = new
	 * ArrayList<ZenNativeOperator>(); for (ZenNativeOperator binary :
	 * binaryOperators) { if (binary.getOperator() == operator) {
	 * operators.add(binary); } }
	 * 
	 * MethodOutput output = environment.getOutput(); if (operators.isEmpty()) {
	 * output.newObject(ZenRuntimeException.class); output.dup();
	 * output.constant("Cannot " + operator + " on " + classPkg + '.' +
	 * className); output.construct(ZenRuntimeException.class, String.class);
	 * output.aThrow(); } else if (operators.size() == 1) { ZenNativeOperator
	 * binary = operators.get(0);
	 * 
	 * output.loadObject(0); output.getField(anySignature, "value",
	 * signature(cls)); output.loadObject(1); output.invoke(binary.getMethod());
	 * environment.getType(binary.getClass()).compileCast(null, environment,
	 * ANY); output.returnObject(); } else { environment.error(null, "Multiple "
	 * + operator + " operators for " + cls + " (which is not yet supported)");
	 * } }
	 * 
	 * public void compileAnyMember(String anySignature, EnvironmentMethod
	 * environment) { // TODO: complete MethodOutput output =
	 * environment.getOutput(); output.aConstNull(); output.returnObject(); }
	 */
    
    @Override
    public String getName() {
        return classPkg + '.' + className;
    }
    
    @Override
    public String toString() {
        return "ZenTypeNative: " + getName();
    }
    
    /**
     * Function that dumps all info about the current ZenType
     * @return
     */
    public List<String> dumpTypeInfo(){
        List<String> stringList = new ArrayList<>();
        
        // goes over all non static members
        members.forEach((s, zenNativeMember) -> {
            stringList.add("Members: " + s);
            for(IJavaMethod iJavaMethod : zenNativeMember.getMethods()) {
                stringList.add("\t" + iJavaMethod.toString());
            }
            
            if (zenNativeMember.getGetter() != null){
                stringList.add("Getter: " + zenNativeMember.getGetter().toString());
            }
            if (zenNativeMember.getSetter() != null){
                stringList.add("Setter: " + zenNativeMember.getSetter().toString());
            }
        });
    
        staticMembers.forEach((s, zenNativeMember) -> {
            stringList.add("Static Members: " + s);
            for(IJavaMethod iJavaMethod : zenNativeMember.getMethods()) {
                stringList.add("\t" + iJavaMethod.toString());
            }
            
            if (zenNativeMember.getGetter() != null){
                stringList.add("Static Getter: " + zenNativeMember.getGetter().toString());
            }
            if (zenNativeMember.getSetter() != null){
                stringList.add("Static Setter: " + zenNativeMember.getSetter().toString());
            }
        });
        
        return stringList;
    }
    
    
    public Map<String, ZenNativeMember> getMembers() {
        return members;
    }
    
    public Map<String, ZenNativeMember> getStaticMembers() {
        return staticMembers;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
    
    private void addSubtypes(Queue<ZenTypeNative> todo, ITypeRegistry types) {
        while(!todo.isEmpty()) {
            ZenTypeNative current = todo.poll();
            if(current.cls.getSuperclass() != Object.class) {
                ZenType type = types.getType(current.cls.getSuperclass());
                if(type instanceof ZenTypeNative) {
                    todo.offer((ZenTypeNative) type);
                    implementing.add((ZenTypeNative) type);
                }
            }
            for(Class iface : current.cls.getInterfaces()) {
                ZenType type = types.getType(iface);
                if(type instanceof ZenTypeNative) {
                    todo.offer((ZenTypeNative) type);
                    implementing.add((ZenTypeNative) type);
                }
            }
        }
    }
    
    private class AnyNativeDefinition implements IAnyDefinition {
        
        private final IEnvironmentGlobal environment;
        
        public AnyNativeDefinition(IEnvironmentGlobal environment) {
            this.environment = environment;
        }
        
        @Override
        public void defineMembers(ClassVisitor output) {
            output.visitField(Opcodes.ACC_PRIVATE, "value", "F", null, null);
            
            MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(F)" + signature(IAny.class), null, null);
            valueOf.start();
            valueOf.newObject(anyName2);
            valueOf.dup();
            valueOf.load(toASMType(), 0);
            valueOf.construct(anyName2, "L" + cls.getName() + ";");
            valueOf.returnObject();
            valueOf.end();
            
            MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(L" + cls.getName() + ";)V", null, null);
            constructor.start();
            constructor.loadObject(0);
            constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
            constructor.loadObject(0);
            constructor.load(Type.FLOAT_TYPE, 1);
            constructor.putField(anyName2, "value", "L" + cls.getName() + ";");
            constructor.returnType(Type.VOID_TYPE);
            constructor.end();
        }
        
        @Override
        public void defineStaticCanCastImplicit(MethodOutput output) {
            // Class
            // if (cls.isAssignableFrom(param)) return true;
            output.constant(Type.getType(cls));
            output.loadObject(1);
            output.invokeVirtual(Class.class, "isAssignableFrom", boolean.class, Class.class);
            
            Label lblNotAssignable = new Label();
            output.ifEQ(lblNotAssignable);
            
            output.iConst1();
            output.returnInt();
            
            output.label(lblNotAssignable);
            
            for(ZenNativeCaster caster : casters) {
                caster.compileAnyCanCastImplicit(ZenTypeNative.this, output, environment, 0);
            }
            
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCanCastImplicit(ZenTypeNative.this, output, environment, 0);
            }
            
            output.iConst0();
            output.returnInt();
        }
        
        @Override
        public void defineStaticAs(MethodOutput output) {
            output.constant(Type.getType(cls));
            output.loadObject(1);
            output.invokeVirtual(Class.class, "isAssignableFrom", boolean.class, Class.class);
            
            Label lblNotAssignable = new Label();
            output.ifEQ(lblNotAssignable);
            
            output.loadObject(0);
            output.returnObject();
            
            output.label(lblNotAssignable);
            
            for(ZenNativeCaster caster : casters) {
                caster.compileAnyCast(ZenTypeNative.this, output, environment, 0, 1);
            }
            
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCast(ZenTypeNative.this, output, environment, 0, 1);
            }
            
            throwCastException(output, "float", 1);
        }
        
        @Override
        public void defineNot(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "not");
            /*
             * for (ZenNativeOperator unaryOperator : unaryOperators) { if
			 * (unaryOperator.getOperator() == OperatorType.NOT) {
			 * 
			 * } }
			 * 
			 * TypeExpansion expansion = environment.getExpansion(getName()); if
			 * (expansion != null) { if (expansion.compileAnyUnary(output,
			 * OperatorType.NOT)) {
			 * 
			 * } }
			 */
        }
        
        @Override
        public void defineNeg(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "negate");
        }
        
        @Override
        public void defineAdd(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "+");
        }
        
        @Override
        public void defineSub(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "-");
        }
        
        @Override
        public void defineCat(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "~");
        }
        
        @Override
        public void defineMul(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "*");
        }
        
        @Override
        public void defineDiv(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "/");
        }
        
        @Override
        public void defineMod(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "%");
        }
        
        @Override
        public void defineAnd(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "&");
        }
        
        @Override
        public void defineOr(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "|");
        }
        
        @Override
        public void defineXor(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "^");
        }
        
        @Override
        public void defineRange(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "..");
        }
        
        @Override
        public void defineCompareTo(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "compare");
        }
        
        @Override
        public void defineContains(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "in");
        }
        
        @Override
        public void defineMemberGet(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "member get");
        }
        
        @Override
        public void defineMemberSet(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "member set");
        }
        
        @Override
        public void defineMemberCall(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "member call");
        }
        
        @Override
        public void defineIndexGet(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "index get");
        }
        
        @Override
        public void defineIndexSet(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "index set");
        }
        
        @Override
        public void defineCall(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "call");
        }
        
        @Override
        public void defineAsBool(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asBool");
        }
        
        @Override
        public void defineAsByte(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asByte");
        }
        
        @Override
        public void defineAsShort(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asShort");
        }
        
        @Override
        public void defineAsInt(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asInt");
        }
        
        @Override
        public void defineAsLong(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asLong");
        }
        
        @Override
        public void defineAsFloat(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asFloat");
        }
        
        @Override
        public void defineAsDouble(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asDouble");
        }
        
        @Override
        public void defineAsString(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "asString");
        }
        
        @Override
        public void defineAs(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "as");
        }
        
        @Override
        public void defineIs(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "is");
        }
        
        @Override
        public void defineGetNumberType(MethodOutput output) {
            output.iConst0();
            output.returnInt();
        }
        
        @Override
        public void defineIteratorSingle(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "iterator");
        }
        
        @Override
        public void defineIteratorMulti(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "iterator");
        }
        
        @Override
        public void defineEquals(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "equals");
        }
        
        @Override
        public void defineHashCode(MethodOutput output) {
            // TODO: implement
            throwUnsupportedException(output, getName(), "hashCode");
        }
    }
}
