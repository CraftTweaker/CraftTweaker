package stanhebben.zenscript.expression;

import org.objectweb.asm.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.symbols.SymbolArgument;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Stanneke
 */
public class ExpressionFunction extends Expression {
    
    private final List<ParsedFunctionArgument> arguments;
    private final ZenType returnType;
    private final List<Statement> statements;
    private final String descriptor;
    
    private final ZenTypeFunction functionType;
    private final String className;
    
    public ExpressionFunction(ZenPosition position, List<ParsedFunctionArgument> arguments, ZenType returnType, List<Statement> statements, String className) {
        super(position);
        
        System.out.println("Function expression: " + arguments.size() + " arguments");
        
        this.arguments = arguments;
        this.returnType = returnType;
        this.statements = statements;
        
        ZenType[] argumentTypes = new ZenType[arguments.size()];
        for(int i = 0; i < arguments.size(); i++) {
            argumentTypes[i] = arguments.get(i).getType();
        }
        //className = environment.makeClassName();
        this.className = className;
        descriptor = makeDescriptor();
        functionType = new ZenTypeFunctionCallable(returnType, argumentTypes, className, descriptor);
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
        } else if(type instanceof ZenTypeFunction) {
            boolean matches = returnType.equals(((ZenTypeFunction) type).getReturnType());
            ZenType[] args = ((ZenTypeFunction) type).getArgumentTypes();
            if(matches) {
                for(int i = 0; i < arguments.size(); i++) {
                    matches &= arguments.get(i).getType().equals(args[i]);
                }
            }
            if(matches)
                return this;
            environment.error(position, "Cannot cast a function literal to " + type.toString());
            return new ExpressionInvalid(position);
            
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
        if(!result)
            return;
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", new String[0]);
        
        MethodOutput constructor = new MethodOutput(cw, Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.start();
        constructor.loadObject(0);
        constructor.invokeSpecial("java/lang/Object", "<init>", "()V");
        constructor.ret();
        constructor.end();
        
        MethodOutput output = new MethodOutput(cw, Opcodes.ACC_PUBLIC, "accept", makeDescriptor(), null, null);
        
        IEnvironmentClass environmentClass = new EnvironmentClass(cw, environment);
        IEnvironmentMethod environmentMethod = new EnvironmentMethod(output, environmentClass);
        
        for(int i = 0; i < arguments.size(); i++) {
            environmentMethod.putValue(arguments.get(i).getName(), new SymbolArgument(i + 1, arguments.get(i).getType()), getPosition());
        }
        
        output.start();
        for(Statement statement : statements) {
            statement.compile(environmentMethod);
        }
        output.ret();
        output.end();
        
        environment.putClass(className, cw.toByteArray());
        
        // make class instance
        environment.getOutput().newObject(className);
        environment.getOutput().dup();
        environment.getOutput().construct(className);
    }
    
    private String makeDescriptor() {
        StringBuilder sb = new StringBuilder("(");
        arguments.stream().map(ParsedFunctionArgument::getType).map(ZenType::getSignature).forEach(sb::append);
        sb.append(")").append(returnType.getSignature());
        return sb.toString();
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getDescriptor() {
        return descriptor;
    }
}
