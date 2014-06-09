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
import stanhebben.zenscript.definitions.Import;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.statements.StatementReturn;
import stanhebben.zenscript.symbols.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolArgument;
import stanhebben.zenscript.symbols.SymbolZenStaticMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.StringUtil;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 *
 * @author Stanneke
 */
public class ZenModule {
	public static void compileScripts(String mainclass, String mainFileName, List<ZenParsedFile> scripts, IEnvironmentGlobal environmentGlobal) {
		ClassWriter clsMain = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		clsMain.visitSource(mainclass, null);
		
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
			ClassWriter clsScript = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			clsScript.visitSource(script.getFileName(), null);
			EnvironmentClass environmentScript = new EnvironmentClass(clsScript, environmentGlobal);
			
			clsScript.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, script.getClassName(), null, internal(Object.class), new String[] {internal(Runnable.class)});
			for (Import imprt : script.getImports()) {
				List<String> name = imprt.getName();
				IPartialExpression value = environmentGlobal.getValue(name.get(0), imprt.getPosition());
				for (int i = 1; i < name.size(); i++) {
					IPartialExpression member = value.getMember(imprt.getPosition(), environmentGlobal, name.get(i));
					if (member == null) {
						environmentGlobal.error(imprt.getPosition(), StringUtil.join(name, ".") + " not found");
						break;
					}
					value = member;
				}
				IZenSymbol symbol = value.toSymbol();
				if (symbol == null) {
					environmentGlobal.error(imprt.getPosition(), StringUtil.join(name, ".") + " is not a valid import");
				} else {
					environmentScript.putValue(
							imprt.getRename() == null
									? name.get(name.size() - 1)
									: imprt.getRename(), value.toSymbol());
				}
			}
			
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
	
	public static ZenModule compileScriptFile(File single, IZenCompileEnvironment environment) throws IOException {
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
		ZenParser parser = new ZenParser(reader, environment);
		ZenParsedFile file = new ZenParsedFile(filename, className, parser, environmentGlobal);
		reader.close();
		
		List<ZenParsedFile> files = new ArrayList<ZenParsedFile>();
		files.add(file);
		
		compileScripts(className, filename, files, environmentGlobal);
		
		// debug: output classes
		File outputDir = new File("generated");
		outputDir.mkdir();
		
		for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
			File outputFile = new File(outputDir, entry.getKey() + ".class");
			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(entry.getValue());
			output.close();
		}
		
		return new ZenModule(classes);
	}
	
	public static ZenModule compileZip(File file, String subdir, IZenCompileEnvironment environment) throws IOException {
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
				ZenParser parser = new ZenParser(reader, environment);
				ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
				files.add(pfile);
				reader.close();
			}
		}
		
		String filename = file.getName();
		String className = extractClassName(filename);
		compileScripts(className, filename, files, environmentGlobal);
		
		// debug: output classes
		File outputDir = new File("generated");
		outputDir.mkdir();
		
		for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
			File outputFile = new File(outputDir, entry.getKey() + ".class");
			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(entry.getValue());
			output.close();
		}
		
		return new ZenModule(classes);
	}
	
	private final Map<String, byte[]> classes;
	private final MyClassLoader classLoader;
	
	private ZenModule(Map<String, byte[]> classes) {
		this.classes = classes;
		classLoader = new MyClassLoader();
	}
	
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
	
	private class MyClassLoader extends ClassLoader {
		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException {
			if (classes.containsKey(name)) {
				return this.defineClass(name, classes.get(name), 0, classes.get(name).length);
			}
			
			return super.findClass(name);
		}
	}
	
	private static String extractClassName(String filename) {
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
}
