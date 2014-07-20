package stanhebben.zenscript;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import stanhebben.zenscript.compiler.ClassNameGenerator;
import stanhebben.zenscript.compiler.EnvironmentClass;
import stanhebben.zenscript.compiler.EnvironmentGlobal;
import stanhebben.zenscript.compiler.EnvironmentMethod;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
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
 * Modules may contain statements in their source, or define functions. Functions
 * in different scripts within the same module are accessible to each other, but
 * not to other modules.
 * 
 * @author Stan Hebben
 */
public class ZenModule {
	/**
	 * Compiles a set of parsed files into a module.
	 * 
	 * @param mainFileName main filename (used for debug info)
	 * @param scripts scripts to compile
	 * @param environmentGlobal global compile environment
	 */
	public static void compileScripts(String mainFileName, List<ZenParsedFile> scripts, IEnvironmentGlobal environmentGlobal) {
		ClassWriter clsMain = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		clsMain.visitSource(mainFileName, null);
		
		clsMain.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, "__ZenMain__", null, internal(Object.class), new String[] {internal(Runnable.class)});
		MethodOutput mainRun = new MethodOutput(clsMain, Opcodes.ACC_PUBLIC, "run", "()V", null, null);
		mainRun.start();
		
		for (ZenParsedFile script : scripts) {
			for (Map.Entry<String, ParsedFunction> function : script.getFunctions().entrySet()) {
				ParsedFunction fn = function.getValue();
				environmentGlobal.putValue(function.getKey(), new SymbolZenStaticMethod(
						script.getClassName(),
						fn.getName(),
						fn.getSignature(),
						fn.getArgumentTypes(),
						fn.getReturnType()));
			}
		}
		
		for (ZenParsedFile script : scripts) {
			ClassWriter clsScript = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			clsScript.visitSource(script.getFileName(), null);
			EnvironmentClass environmentScript = new EnvironmentClass(clsScript, script.getEnvironment());
			
			clsScript.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, script.getClassName(), null, internal(Object.class), new String[] {internal(Runnable.class)});
			
			for (Map.Entry<String, ParsedFunction> function : script.getFunctions().entrySet()) {
				ParsedFunction fn = function.getValue();
				
				String signature = fn.getSignature();
				MethodOutput methodOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, function.getKey(), signature, null, null);
				EnvironmentMethod methodEnvironment = new EnvironmentMethod(methodOutput, environmentScript);

				List<ParsedFunctionArgument> arguments = function.getValue().getArguments();
				for (int i = 0; i < arguments.size(); i++) {
					ParsedFunctionArgument argument = arguments.get(i);
					methodEnvironment.putValue(argument.getName(), new SymbolArgument(i, argument.getType()));
				}

				methodOutput.start();
				Statement[] statements = fn.getStatements();
				for (Statement statement : statements) {
					statement.compile(methodEnvironment);
				}
				if (function.getValue().getReturnType() != ZenType.VOID) {
					if (statements[statements.length - 1] instanceof StatementReturn) {
						if (((StatementReturn) statements[statements.length - 1]).getExpression() != null) {
							fn.getReturnType()
									.defaultValue(fn.getPosition())
									.compile(true, methodEnvironment);
							methodOutput.returnType(fn.getReturnType().toASMType());
						}
					} else {
						fn.getReturnType()
								.defaultValue(fn.getPosition())
								.compile(true, methodEnvironment);
						methodOutput.returnType(fn.getReturnType().toASMType());
					}
				}
				methodOutput.end();
			}

			if (script.getStatements().size() > 0) {
				MethodOutput scriptOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "__script__", "()V", null, null);
				IEnvironmentMethod functionMethod = new EnvironmentMethod(scriptOutput, environmentScript);
				scriptOutput.start();
				for (Statement statement : script.getStatements()) {
					statement.compile(functionMethod);
				}
				scriptOutput.ret();
				scriptOutput.end();
				
				mainRun.invokeStatic(script.getClassName(), "__script__", "()V");
			}
			
			clsScript.visitEnd();
			environmentGlobal.putClass(script.getClassName(), clsScript.toByteArray());
		}
		
		mainRun.ret();
		mainRun.end();
		clsMain.visitEnd();
		
		MethodVisitor constructor = clsMain.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitCode();
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		constructor.visitInsn(Opcodes.RETURN);
		constructor.visitMaxs(0, 0);
		constructor.visitEnd();
		
		environmentGlobal.putClass("__ZenMain__", clsMain.toByteArray());
	}
	
	/**
	 * Compiles a single script file.
	 * 
	 * @param single file to be compiled
	 * @param environment compile environment
	 * @param baseClassLoader
	 * @return compiled module
	 * @throws IOException if the file could not be read
	 */
	public static ZenModule compileScriptFile(File single, IZenCompileEnvironment environment, ClassLoader baseClassLoader) throws IOException {
		Map<String, byte[]> classes = new HashMap<String, byte[]>();
		ClassNameGenerator nameGen = new ClassNameGenerator();
		EnvironmentGlobal environmentGlobal = new EnvironmentGlobal(
				environment,
				classes,
				nameGen
		);
		
		String filename = single.getName();
		String className = extractClassName(filename);
		
		FileInputStream input = new FileInputStream(single);
		Reader reader = new InputStreamReader(new BufferedInputStream(input));
		ZenTokener parser = new ZenTokener(reader, environment);
		ZenParsedFile file = new ZenParsedFile(filename, className, parser, environmentGlobal);
		reader.close();
		
		List<ZenParsedFile> files = new ArrayList<ZenParsedFile>();
		files.add(file);
		
		compileScripts(filename, files, environmentGlobal);
		
		// debug: output classes
		File outputDir = new File("generated");
		outputDir.mkdir();
		
		for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
			File outputFile = new File(outputDir, entry.getKey().replace('.', '/') + ".class");
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(entry.getValue());
			output.close();
		}
		
		return new ZenModule(classes, baseClassLoader);
	}
	
	/**
	 * Compiles a zip file as module. All containing files (inside the given
	 * subdirectory) will be compiled.
	 * 
	 * @param file zip file
	 * @param subdir subdirectory (use empty string to compile all)
	 * @param environment compile environment
	 * @return compiled module
	 * @throws IOException if the file could not be read properly
	 */
	public static ZenModule compileZip(
			File file,
			String subdir,
			IZenCompileEnvironment environment,
			ClassLoader baseClassLoader) throws IOException {
		Map<String, byte[]> classes = new HashMap<String, byte[]>();
		ClassNameGenerator nameGen = new ClassNameGenerator();
		EnvironmentGlobal environmentGlobal = new EnvironmentGlobal(
				environment,
				classes,
				nameGen
		);
		
		List<ZenParsedFile> files = new ArrayList<ZenParsedFile>();
		
		ZipFile zipFile = new ZipFile(file);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			
			if (entry.getName().startsWith(subdir) && !entry.getName().equals(subdir)) {
				String filename = entry.getName().substring(subdir.length());
				String className = extractClassName(filename);
				
				Reader reader = new InputStreamReader(new BufferedInputStream(zipFile.getInputStream(entry)));
				ZenTokener parser = new ZenTokener(reader, environment);
				ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
				files.add(pfile);
				reader.close();
			}
		}
		
		String filename = file.getName();
		compileScripts(filename, files, environmentGlobal);
		
		// debug: output classes
		File outputDir = new File("generated");
		outputDir.mkdir();
		
		for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
			File outputFile = new File(outputDir, entry.getKey().replace('.', '/') + ".class");
			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(entry.getValue());
			output.close();
		}
		
		return new ZenModule(classes, baseClassLoader);
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
	 * Retrieves the main runnable. Running this runnable will execute the content
	 * of the given module.
	 * 
	 * @return main runnable
	 */
	public Runnable getMain() {
		try {
			return (Runnable) classLoader.loadClass("__ZenMain__").newInstance();
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
		if (filename.startsWith("/")) filename = filename.substring(1);
		
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
