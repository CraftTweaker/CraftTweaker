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
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

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
        arguments.stream().map(ParsedFunctionArgument::getType).forEach(argument -> sb.append(argument.equals(ZenTypeAny.INSTANCE) ? signature(Object.class) : argument.getSignature()));
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
        cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, clsName, sign(), "java/lang/Object", new String[]{internal(interfaceClass)});


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
            environmentMethod.putValue(arguments.get(i).getName(), new SymbolArgument(i + 1, arguments.get(i).getType()), getPosition());
        }

        output.start();
        for(Statement statement : statements) {
            statement.compile(environmentMethod);
        }
        output.ret();
        output.end();

        environment.putClass(clsName, cw.toByteArray());

        // make class instance
        environment.getOutput().newObject(clsName);
        environment.getOutput().dup();
        environment.getOutput().construct(clsName);
    }

    private String sign() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ljava/lang/Object;");
        sb.append(signature(interfaceClass));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("<").append(signature(genericClass)).append(">").append(";");
        return sb.toString();
    }
}
