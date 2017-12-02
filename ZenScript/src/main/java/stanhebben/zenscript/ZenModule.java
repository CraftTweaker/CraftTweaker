package stanhebben.zenscript;

import org.objectweb.asm.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.*;
import stanhebben.zenscript.statements.*;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 * Main module class. Contains a compiled module. The static methods of this
 * class can be used to compile a specific file or files.
 * <p>
 * Modules may contain statements in their source, or define functions.
 * Functions in different scripts within the same module are accessible to each
 * other, but not to other modules.
 *
 * @author Stan Hebben
 */
public class ZenModule {
    
    private final Map<String, byte[]> classes;
    private final MyClassLoader classLoader;
    
    
       
    
    /**
     * Constructs a module for the given set of classes. Mostly intended for
     * internal use.
     *
     * @param classes         classes for module
     * @param baseClassLoader class loader
     */
    public ZenModule(Map<String, byte[]> classes, ClassLoader baseClassLoader) {
        this.classes = classes;
        classLoader = new MyClassLoader(baseClassLoader);
    }
    
    /**
     * Compiles a set of parsed files into a module.
     *
     * @param mainFileName      main filename (used for debug info)
     * @param scripts           scripts to compile
     * @param environmentGlobal global compile environment
     * @param debug             enable debug mode (outputs classes to generated directory)
     */
    public static void compileScripts(String mainFileName, List<ZenParsedFile> scripts, IEnvironmentGlobal environmentGlobal, boolean debug) {
        ClassWriter clsMain = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        clsMain.visitSource(mainFileName, null);
        
        clsMain.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "__ZenMain__", null, internal(Object.class), new String[]{internal(Runnable.class)});
        MethodOutput mainRun = new MethodOutput(clsMain, Opcodes.ACC_PUBLIC, "run", "()V", null, null);
        mainRun.start();
        
        for(ZenParsedFile script : scripts) {
            ClassWriter clsScript = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            clsScript.visitSource(script.getFileName(), null);
            EnvironmentClass environmentScript = new EnvironmentClass(clsScript, script.getEnvironment());
            
            clsScript.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, script.getClassName().replace('.', '/'), null, internal(Object.class), new String[]{internal(Runnable.class)});
                   	
            if(!script.getGlobals().isEmpty()) {
            	MethodOutput clinit = new MethodOutput(clsScript, Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            	EnvironmentMethod clinitEnvironment = new EnvironmentMethod(clinit, environmentScript);
            	clinit.start();
            	
	            for (Map.Entry<String, ParsedGlobalValue> entry : script.getGlobals().entrySet()) {
	            	environmentGlobal.putValue(entry.getKey(), new SymbolGlobalValue(entry.getValue(), clinitEnvironment), entry.getValue().getPosition());
	            }
	            
	            clinit.ret();
	            clinit.end();
            }
            
            for(Map.Entry<String, ParsedFunction> function : script.getFunctions().entrySet()) {
                ParsedFunction fn = function.getValue();
                environmentScript.putValue(function.getKey(), new SymbolZenStaticMethod(script.getClassName(), fn.getName(), fn.getSignature(), fn.getArgumentTypes(), fn.getReturnType()), fn.getPosition());
            }
            for(Map.Entry<String, ParsedFunction> function : script.getFunctions().entrySet()) {
                ParsedFunction fn = function.getValue();
                
                String signature = fn.getSignature();
                MethodOutput methodOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, function.getKey(), signature, null, null);
                EnvironmentMethod methodEnvironment = new EnvironmentMethod(methodOutput, environmentScript);
                
                List<ParsedFunctionArgument> arguments = function.getValue().getArguments();
                for(int i = 0; i < arguments.size(); i++) {
                    ParsedFunctionArgument argument = arguments.get(i);
                    methodEnvironment.putValue(argument.getName(), new SymbolArgument(i, argument.getType()), fn.getPosition());
                }
                
                methodOutput.start();
                Statement[] statements = fn.getStatements();
                for(Statement statement : statements) {
                    statement.compile(methodEnvironment);
                }
                if(function.getValue().getReturnType() != ZenType.VOID) {
                    if(statements[statements.length - 1] instanceof StatementReturn) {
                        if(((StatementReturn) statements[statements.length - 1]).getExpression() != null) {
                            fn.getReturnType().defaultValue(fn.getPosition()).compile(true, methodEnvironment);
                            methodOutput.returnType(fn.getReturnType().toASMType());
                        }
                    } else {
                        fn.getReturnType().defaultValue(fn.getPosition()).compile(true, methodEnvironment);
                        methodOutput.returnType(fn.getReturnType().toASMType());
                    }
                }
                methodOutput.end();
            }
            
            if(script.getStatements().size() > 0) {
                MethodOutput scriptOutput = new MethodOutput(clsScript, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "__script__", "()V", null, null);
                IEnvironmentMethod functionMethod = new EnvironmentMethod(scriptOutput, environmentScript);
                // scriptOutput.enableDebug();
                scriptOutput.start();
                for(Statement statement : script.getStatements()) {
                    statement.compile(functionMethod);
                }
                scriptOutput.ret();
                scriptOutput.end();
                
                mainRun.invokeStatic(script.getClassName().replace('.', '/'), "__script__", "()V");
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
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();
        
        // debug: output classes
        if(debug) {
            try {
                File outputDir = new File("generated");
                outputDir.mkdir();
                
                for(String className : environmentGlobal.getClassNames()) {
                    File outputFile = new File(outputDir, className.replace('.', '/') + ".class");
                    
                    if(!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }
                    
                    FileOutputStream output = new FileOutputStream(outputFile);
                    output.write(environmentGlobal.getClass(className));
                    output.close();
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        
        environmentGlobal.putClass("__ZenMain__", clsMain.toByteArray());
    }
    
    /**
     * Compiles a single script file.
     *
     * @param single          file to be compiled
     * @param environment     compile environment
     * @param baseClassLoader class loader
     *
     * @return compiled module
     * @throws IOException if the file could not be read
     */
    public static ZenModule compileScriptFile(File single, IZenCompileEnvironment environment, ClassLoader baseClassLoader) throws IOException {
        Map<String, byte[]> classes = new HashMap<>();
        ClassNameGenerator nameGen = new ClassNameGenerator();
        EnvironmentGlobal environmentGlobal = new EnvironmentGlobal(environment, classes, nameGen);
        
        String filename = single.getName();
        String className = extractClassName(filename);
        
        FileInputStream input = new FileInputStream(single);
        Reader reader = new InputStreamReader(new BufferedInputStream(input));
        ZenTokener parser = new ZenTokener(reader, environment, filename, false);
        ZenParsedFile file = new ZenParsedFile(filename, className, parser, environmentGlobal);
        reader.close();
        
        List<ZenParsedFile> files = new ArrayList<>();
        files.add(file);
        
        compileScripts(filename, files, environmentGlobal, false);
        
        generateDebug(classes);
        
        return new ZenModule(classes, baseClassLoader);
    }
    
    /**
     * Compiles a single script file.
     *
     * @param script          file to be compiled
     * @param name            name of the script to be compiled
     * @param environment     compile environment
     * @param baseClassLoader class loader
     *
     * @return compiled module
     * @throws IOException if the file could not be read
     */
    public static ZenModule compileScriptString(String script, String name, IZenCompileEnvironment environment, ClassLoader baseClassLoader) throws IOException {
        Map<String, byte[]> classes = new HashMap<>();
        ClassNameGenerator nameGen = new ClassNameGenerator();
        EnvironmentGlobal environmentGlobal = new EnvironmentGlobal(environment, classes, nameGen);
        
        String className = extractClassName(name);
        
        StringReader reader = new StringReader(script);
        ZenTokener parser = new ZenTokener(reader, environment, name, false);
        ZenParsedFile file = new ZenParsedFile(name, className, parser, environmentGlobal);
        reader.close();
        
        List<ZenParsedFile> files = new ArrayList<>();
        files.add(file);
        
        compileScripts(name, files, environmentGlobal, false);
        
        
        generateDebug(classes);
        
        return new ZenModule(classes, baseClassLoader);
    }

    /**
     * Compiles a zip file as module. All containing files (inside the given
     * subdirectory) will be compiled.
     *
     * @param file        zip file
     * @param subdir      subdirectory (use empty string to compile all)
     * @param environment compile environment
     *
     * @return compiled module
     * @throws IOException if the file could not be read properly
     */
    public static ZenModule compileZip(File file, String subdir, IZenCompileEnvironment environment, ClassLoader baseClassLoader) throws IOException {
        Map<String, byte[]> classes = new HashMap<>();
        ClassNameGenerator nameGen = new ClassNameGenerator();
        EnvironmentGlobal environmentGlobal = new EnvironmentGlobal(environment, classes, nameGen);
        
        List<ZenParsedFile> files = new ArrayList<>();
        
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            
            if(entry.getName().startsWith(subdir) && !entry.getName().equals(subdir)) {
                String filename = entry.getName().substring(subdir.length());
                String className = extractClassName(filename);
                
                Reader reader = new InputStreamReader(new BufferedInputStream(zipFile.getInputStream(entry)));
                ZenTokener parser = new ZenTokener(reader, environment, filename, false);
                ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
                files.add(pfile);
                reader.close();
            }
        }
        
        String filename = file.getName();
        compileScripts(filename, files, environmentGlobal, true);
        
        return new ZenModule(classes, baseClassLoader);
    }
    
    private static void generateDebug(Map<String, byte[]> classes) throws IOException {
        // debug: output classes
        File outputDir = new File("generated");
        outputDir.mkdir();
        
        for(Map.Entry<String, byte[]> entry : classes.entrySet()) {
            File outputFile = new File(outputDir, entry.getKey().replace('.', '/') + ".class");
            if(!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            FileOutputStream output = new FileOutputStream(outputFile);
            output.write(entry.getValue());
            output.close();
        }
    }
    
    /**
     * Converts a filename into a class name.
     *
     * @param filename filename to convert
     *
     * @return class name
     */
    public static String extractClassName(String filename) {
        filename = filename.replace('\\', '/');
        if(filename.startsWith("/"))
            filename = filename.substring(1);
        
        // trim extension
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) filename = filename.substring(0, lastDot);
        
        
        filename = filename.replace(".", "_");
        filename = filename.replace(" ", "_");
    
        
        String dir;
        String name;
        
        // get file name vs folder path
        int lastSlash = filename.lastIndexOf('/');
        if(lastSlash > 0) {
            dir = filename.substring(0, lastSlash);
            name = filename.substring(lastSlash + 1);
        } else {
            name = filename;
            dir = "";
        }
        
        return dir.length() > 0 ? (dir.replace("/", "_") + "_") : "" + StringUtil.capitalize(name);
        
    }
    
    // ######################
    // ### Static methods ###
    // ######################
    
    /**
     * Retrieves the main runnable. Running this runnable will execute the
     * content of the given module.
     *
     * @return main runnable
     */
    public Runnable getMain() {
        try {
            
            return (Runnable) classLoader.loadClass("__ZenMain__").newInstance();
        } catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            return null;
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
            if(classes.containsKey(name)) {
                return defineClass(name, classes.get(name), 0, classes.get(name).length);
            }
            
            return super.findClass(name);
        }
    }
}
