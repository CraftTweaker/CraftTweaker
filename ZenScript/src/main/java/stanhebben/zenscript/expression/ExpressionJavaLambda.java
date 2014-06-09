/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import java.lang.reflect.Method;
import java.util.List;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import stanhebben.zenscript.compiler.EnvironmentClass;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.compiler.EnvironmentMethod;
import stanhebben.zenscript.compiler.IEnvironmentClass;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.symbols.SymbolArgument;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.descriptor;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 *
 * @author Stanneke
 */
public class ExpressionJavaLambda extends Expression {
	private final Class interfaceClass;
	private final List<ParsedFunctionArgument> arguments;
	private final Statement[] statements;
	
	private final ZenType type;
	
	public ExpressionJavaLambda(
			ZenPosition position,
			Class interfaceClass,
			List<ParsedFunctionArgument> arguments,
			Statement[] statements,
			ZenType type) {
		super(position);
		
		this.interfaceClass = interfaceClass;
		this.arguments = arguments;
		this.statements = statements;
		
		this.type = type;
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (!result) return;
		
		Method method = interfaceClass.getMethods()[0];
		
		// generate class
		String clsName = environment.makeClassName();
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, clsName, null, "java/lang/Object", new String[] { internal(interfaceClass) });
		
		MethodOutput constructor = new MethodOutput(cw, Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.start();
		constructor.loadObject(0);
		constructor.invokeSpecial("java/lang/Object", "<init>", "()V");
		constructor.ret();
		constructor.end();
		
		MethodOutput output = new MethodOutput(cw, Opcodes.ACC_PUBLIC, method.getName(), descriptor(method), null, null);
		
		IEnvironmentClass environmentClass = new EnvironmentClass(cw, environment);
		IEnvironmentMethod environmentMethod = new EnvironmentMethod(output, environmentClass);
		
		for (int i = 0; i < arguments.size(); i++) {
			environmentMethod.putValue(
					arguments.get(i).getName(),
					new SymbolArgument(i + 1, environment.getType(method.getGenericParameterTypes()[i])));
		}
		
		output.start();
		for (Statement statement : statements) {
			statement.compile(environmentMethod);
		}
		output.end();
		
		environment.putClass(clsName, cw.toByteArray());
		
		// make class instance
		environment.getOutput().newObject(clsName);
		environment.getOutput().dup();
		environment.getOutput().construct(clsName);
	}
}
