package stanhebben.zenscript.expression;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import stanhebben.zenscript.compiler.EnvironmentClass;
import stanhebben.zenscript.compiler.EnvironmentMethod;
import stanhebben.zenscript.compiler.IEnvironmentClass;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.symbols.SymbolArgument;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import stanhebben.zenscript.util.ZenTypeUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 * @author Stanneke
 */
public class ExpressionJavaLambdaSimpleGeneric extends Expression {

    private final Class interfaceClass, genericClass;
    private final List<ParsedFunctionArgument> arguments;
    private final List<Statement> statements;
    private final String descriptor;

    private final ZenType type;

    public ExpressionJavaLambdaSimpleGeneric(ZenPosition position, Class interfaceClass, List<ParsedFunctionArgument> arguments, List<Statement> statements, ZenType type) {
        super(position);

        this.interfaceClass = interfaceClass;
        this.arguments = arguments;
        this.statements = statements;

        this.type = type;

        ZenType genericType = arguments.get(0).getType();
        this.genericClass = genericType.equals(ZenType.ANY) ? Object.class : genericType.toJavaClass();

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < arguments.size(); i++) {
            ZenType t = arguments.get(i).getType();
            if(t.equals(ZenType.ANY)) {
                sb.append(signature(interfaceClass.getMethods()[0].getParameterTypes()[i]));
            } else {
                sb.append(t.getSignature());
            }
        }
        sb.append(")").append(signature(interfaceClass.getDeclaredMethods()[0].getReturnType()));
        this.descriptor = sb.toString();
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(!result)
            return;

        Method method = interfaceClass.getMethods()[0];

        // generate class
        String clsName = environment.makeClassName();

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, clsName, createMethodSignature(), "java/lang/Object", new String[]{internal(interfaceClass)});


        MethodOutput constructor = new MethodOutput(cw, Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.start();
        constructor.loadObject(0);
        constructor.invokeSpecial("java/lang/Object", "<init>", "()V");
        constructor.ret();
        constructor.end();

        MethodOutput output = new MethodOutput(cw, Opcodes.ACC_PUBLIC, method.getName(), descriptor, null, null);
        IEnvironmentClass environmentClass = new EnvironmentClass(cw, environment);
        IEnvironmentMethod environmentMethod = new EnvironmentMethod(output, environmentClass);

        for(int i = 0; i < arguments.size(); i++) {
            ZenType typeToPut = arguments.get(i).getType();
            if(typeToPut.equals(ZenType.ANY)) typeToPut = environment.getType(method.getGenericParameterTypes()[i]);
            if(typeToPut == null) typeToPut = environment.getType(method.getParameterTypes()[i]);

            environmentMethod.putValue(arguments.get(i).getName(), new SymbolArgument(i + 1, typeToPut), getPosition());
        }

        output.start();
        for(Statement statement : statements) {
            statement.compile(environmentMethod);
        }
        output.ret();
        output.end();

        if (!Objects.equals(genericClass, Object.class)) {
            MethodOutput bridge = new MethodOutput(cw, Opcodes.ACC_PUBLIC | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE, method.getName(), ZenTypeUtil.descriptor(method), null, null);
            bridge.loadObject(0);
            bridge.loadObject(1);
            bridge.checkCast(internal(genericClass));
            if(arguments.size() > 1) {
                for (int i = 1; i < arguments.size();) {
                    bridge.load(org.objectweb.asm.Type.getType(method.getParameterTypes()[i]), ++i);
                }
            }

            bridge.invokeVirtual(clsName, method.getName(), descriptor);
            bridge.returnType(org.objectweb.asm.Type.getReturnType(method));
            bridge.end();
        }

        environment.putClass(clsName, cw.toByteArray());

        // make class instance
        environment.getOutput().newObject(clsName);
        environment.getOutput().dup();
        environment.getOutput().construct(clsName);
    }

    private String createMethodSignature() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ljava/lang/Object;");
        sb.append(signature(interfaceClass));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("<").append(signature(genericClass)).append(">").append(";");
        return sb.toString();
    }
}
