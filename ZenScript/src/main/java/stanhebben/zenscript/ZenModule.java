package stanhebben.zenscript;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.definitions.ParsedGlobalValue;
import stanhebben.zenscript.symbols.SymbolGlobal;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.statements.StatementReturn;
import stanhebben.zenscript.symbols.SymbolArgument;
import stanhebben.zenscript.symbols.SymbolZenStaticMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 * Main module class. Contains a compiled module. The static methods of this
 * class can be used to compile a specific file or files.
 * 
 * Modules may contain statements in their source, or define functions.
 * Functions in different scripts within the same module are accessible to each
 * other, but not to other modules.
 * 
 * @author Stan Hebben
 */
public class ZenModule {
	public static final String zenMainClassName = "__ZenMain__";
	/**
	 * Compiles a set of parsed files into a module.
	 * 
	 * @param mainFileName main filename (used for debug info)
	 * @param parsedScripts scripts to compile
	 * @param environmentGlobal global compile environment
	 * @param debug enable debug mode (outputs classes to generated directory)
	 */
	public static void compileScripts(String mainFileName, List<ZenParsedFile> parsedScripts, IEnvironmentGlobal environmentGlobal, boolean debug) {
		for (ZenParsedFile parsedScript : parsedScripts) {
			String internalClassName = internal(parsedScript.getClassName());
			for (ParsedGlobalValue globalValue : parsedScript.getGlobalValues().values()) {
				environmentGlobal.putValue(globalValue.getName(), new SymbolGlobal(
						internalClassName,
						globalValue.getName(),
						globalValue.getType()), globalValue.getPosition());
			}
			for (ParsedFunction function : parsedScript.getFunctions().values()) {
				environmentGlobal.putValue(function.getName(), new SymbolZenStaticMethod(
						internalClassName,
						function.getName(),
						function.getSignature(),
						function.getArgumentTypes(),
						function.getReturnType()), function.getPosition());
			}
		}

		ClassWriter clsMain = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		clsMain.visitSource(mainFileName, null);
		clsMain.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, zenMainClassName, null, internal(Object.class), new String[] { internal(Runnable.class) });

		compileConstructor(clsMain);

		MethodOutput mainRun = new MethodOutput(clsMain, Opcodes.ACC_PUBLIC, "run", "()V", null, null);
		mainRun.start();

		for (ZenParsedFile parsedScript : parsedScripts) {
			ClassWriter clsScript = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

			clsScript.visitSource(parsedScript.getFileName(), null);
			clsScript.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, internal(parsedScript.getClassName()), null, internal(Object.class), new String[] { internal(Runnable.class) });

			EnvironmentClass environmentScript = new EnvironmentClass(clsScript, parsedScript.getEnvironment());

			compileConstructor(clsScript);
			compileGlobalValues(parsedScript, environmentScript, clsScript);
			compileFunctions(parsedScript, environmentScript, clsScript);
			compileStatements(parsedScript, environmentScript, clsScript, mainRun);

			clsScript.visitEnd();

			environmentGlobal.putClass(parsedScript.getClassName(), clsScript.toByteArray());
		}

		mainRun.ret();
		mainRun.end();
		clsMain.visitEnd();

		environmentGlobal.putClass(zenMainClassName, clsMain.toByteArray());

		// debug: output classes
		if (debug) {
			debugOutput(environmentGlobal);
		}
	}

	public static void compileConstructor(ClassWriter classWriter) {
		MethodOutput ctor = new MethodOutput(classWriter, Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		ctor.start();
		ctor.loadObject(0);
		ctor.invokeSpecial(internal(Object.class), "<init>", "()V");
		ctor.ret();
		ctor.end();
	}

	public static void compileGlobalValues(ZenParsedFile script, IEnvironmentClass environmentScript, ClassWriter clsScript) {
		if (script.getGlobalValues().size() > 0) {
			//define static fields
			for (ParsedGlobalValue globalValue : script.getGlobalValues().values()) {
				FieldVisitor fieldVisitor = clsScript.visitField(
						Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
						globalValue.getName(), globalValue.getSignature(),
						null, null);
				fieldVisitor.visitEnd();
			}

			//init static fields
			MethodOutput clinit = new MethodOutput(clsScript, Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
			EnvironmentMethod clinitEnvironment = new EnvironmentMethod(clinit, environmentScript);
			clinit.start();
			clinit.visitPrintln("Init global values " + script.getFileName());
			for (ParsedGlobalValue globalValue : script.getGlobalValues().values()) {
				globalValue.getValue().compile(true, clinitEnvironment);
				clinit.putStaticField(internal(script.getClassName()), globalValue.getName(), globalValue.getSignature());
			}
			clinit.ret();
			clinit.end();
		}
	}

	private static void compileFunctions(ZenParsedFile script, EnvironmentClass environmentScript, ClassWriter clsScript) {
		for (ParsedFunction function : script.getFunctions().values()) {
            MethodOutput methodOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, function.getName(), function.getSignature(), null, null);
            EnvironmentMethod environmentMethod = new EnvironmentMethod(methodOutput, environmentScript);

            for (ParsedFunctionArgument argument : function.getArguments()) {
                environmentMethod.putValue(argument.getName(), new SymbolArgument(
                        argument.getIndex(),
                        argument.getType()), function.getPosition());
            }

            methodOutput.start();

			List<Statement> statements = function.getStatements();
			for (Statement statement : statements) {
                statement.compile(environmentMethod);
            }

			ZenType returnType = function.getReturnType();
			int lastIndex = statements.size() - 1;
			if (returnType != ZenType.VOID && lastIndex >= 0) {
                Statement lastStatement = statements.get(lastIndex);
                if (!(lastStatement instanceof StatementReturn) || ((StatementReturn) lastStatement).getExpression() != null) {
                    returnType.defaultValue(function.getPosition()).compile(true, environmentMethod);
                    methodOutput.returnType(returnType.toASMType());
                } else {
					methodOutput.ret();
				}
            } else {
				methodOutput.ret();
			}

            methodOutput.end();
        }
	}

	private static void compileStatements(ZenParsedFile script, EnvironmentClass environmentScript, ClassWriter clsScript, MethodOutput mainRun) {
		if (script.getStatements().size() > 0) {
			MethodOutput scriptOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC, "run", "()V", null, null);
			IEnvironmentMethod functionMethod = new EnvironmentMethod(scriptOutput, environmentScript);

			scriptOutput.start();
			scriptOutput.visitPrintln("Executing " + script.getFileName());
			for (Statement statement : script.getStatements()) {
				statement.compile(functionMethod);
			}
			scriptOutput.ret();
			scriptOutput.end();

			String internalClassName = internal(script.getClassName());
			mainRun.newObject(internalClassName);
			mainRun.dup();
			mainRun.construct(internalClassName);
			mainRun.invokeVirtual(internalClassName, "run", "()V");
		}
	}

	public static void debugOutput(IEnvironmentGlobal environmentGlobal) {
		try {
			File outputDir = new File("generated");
			outputDir.mkdir();

			for (String className : environmentGlobal.getClassNames()) {
				File outputFile = new File(outputDir, className.replace('.', '/') + ".class");

				if (!outputFile.getParentFile().exists()) {
					outputFile.getParentFile().mkdirs();
				}

				FileOutputStream output = new FileOutputStream(outputFile);
				output.write(environmentGlobal.getClass(className));
				output.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private final Map<String, byte[]> classes;
	private final MyClassLoader classLoader;

	/**
	 * Constructs a module for the given set of classes. Mostly intended for
	 * internal use.
	 * 
	 * @param classes
	 */
	public ZenModule(Map<String, byte[]> classes, ClassLoader baseClassLoader) {
		this.classes = classes;
		classLoader = new MyClassLoader(baseClassLoader);
	}

	/**
	 * Retrieves the main runnable. Running this runnable will execute the
	 * content of the given module.
	 * 
	 * @return main runnable
	 */
	public Runnable getMain() {
		try {
			return (Runnable) classLoader.loadClass(zenMainClassName).newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (ClassNotFoundException ex) {
			return null;
		}
	}

	// ######################
	// ### Static methods ###
	// ######################

	/**
	 * Converts a filename into a class name.
	 * 
	 * @param filename filename to convert
	 * @return class name
	 */
	public static String extractClassName(String filename) {
		filename = filename.replace('\\', '/');
		if (filename.startsWith("/"))
			filename = filename.substring(1);

		int lastSlash = filename.lastIndexOf('/');
		int lastDot = filename.lastIndexOf('.');

		if (lastDot > lastSlash) {
			filename = filename.substring(0, lastDot);
		}
		if (lastSlash > 0) {
			String dir = filename.substring(0, lastSlash);
			String name = filename.substring(lastSlash + 1, lastSlash + 2).toUpperCase() + filename.substring(lastSlash + 2);
			return dir + '.' + name;
		} else {
			return filename.substring(0, 1).toUpperCase() + filename.substring(1);
		}
	}

	// #############################
	// ### Private inner classes ###
	// #############################

	/**
	 * Custom class loader. Loads classes from this module.
	 */
	private class MyClassLoader extends ClassLoader {
		private MyClassLoader(ClassLoader baseClassLoader) {
			super(baseClassLoader);
		}

		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException {
			if (classes.containsKey(name)) {
				return defineClass(name, classes.get(name), 0, classes.get(name).length);
			}

			return super.findClass(name);
		}
	}
}
