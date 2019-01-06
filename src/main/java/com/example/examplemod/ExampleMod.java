package com.example.examplemod;

import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import org.apache.logging.log4j.*;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.codemodel.definition.ClassDefinition;
import org.openzen.zenscript.codemodel.member.*;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.javabytecode.JavaBytecodeRunUnit;
import org.openzen.zenscript.javashared.*;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.*;

import javax.tools.JavaFileObject;
import java.io.File;
import java.lang.reflect.*;
import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod {
    
    public static List<IRecipe> recipeList = new ArrayList<>();
    
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
    public ExampleMod() {
        // Register the preInit method for modloading
        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        // Register the init method for modloading
        FMLModLoadingContext.get().getModEventBus().addListener(this::init);
        System.out.println(">>>");
        // Register ourselves for server, registry and other game events we are interested in
        
        System.out.println("<<<");
    }
    
    private void preInit(final FMLPreInitializationEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void init(final FMLInitializationEvent event) {
        // some example code
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        try {
            ScriptingEngine engine = new ScriptingEngine();
            engine.debug = true;
            
            // The name of the module can be freely chosen (but make sure it matches the dependency name below)
            // The name of the base package MUST match with the package of the classes that will be registered below
            JavaNativeModule example = engine.createNativeModule("example", "com.example.examplemod");
            example.addClass(MCItemStack.class);
            example.addGlobals(CraftTweakerApi.class);
            example.addClass(TestClass.class);
            example.addGlobals(TestClass.class);
            engine.registerNativeProvided(example);
            File inputDirectory = new File("scripts");
            inputDirectory.mkdir();
            File[] inputFiles = Optional.ofNullable(inputDirectory.listFiles((dir, name) -> name.endsWith(".zs"))).orElseGet(() -> new File[0]);
            SourceFile[] sourceFiles = new SourceFile[inputFiles.length];
            for(int i = 0; i < inputFiles.length; i++)
                sourceFiles[i] = new FileSourceFile(inputFiles[i].getName(), inputFiles[i]);
            
            final PrefixedBracketParser bracketExpressionParser = new PrefixedBracketParser(null);
            bracketExpressionParser.register("item", new SimpleBracketParser(engine.registry, example.loadStaticMethod(MCItemStack.class.getMethod("create", String.class))));
            
            
            SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles, bracketExpressionParser, new FunctionParameter[0], "example");
            if(!scripts.isValid()) {
                System.out.println(">>> this is bad");
                return;
            }
            
            engine.registerCompiled(scripts);
            engine.run(Collections.emptyMap(), new Loader(this.getClass().getClassLoader()));
        } catch(CompileException | ParseException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    
        System.out.println(">>>");
        System.out.println(recipeList);
    }
    
    private static final Method findLoadedClassMethod;
    static {
        Method method;
        try {
            method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            method.setAccessible(true);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
            method = null;
        }
        
        findLoadedClassMethod = method;
    }
    
    private static class Loader extends ClassLoader {
        
        private Loader(ClassLoader classLoader) {
            super(classLoader);
        }
        
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if(findLoadedClassMethod != null) {
                try {
                    Class<?> clazz = (Class<?>) findLoadedClassMethod.invoke(this.getParent(), name);
                    if(clazz != null)
                        return clazz;
                } catch(IllegalAccessException | InvocationTargetException ignored) {
                }
            }
            return getParent().loadClass(name);
        }
    }
    
    //    @SubscribeEvent
    //    public void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
    //        // register a new block here
    //        LOGGER.info("HELLO from Register Block");
    //    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.fromStacks(new ItemStack(Blocks.DIRT)));
        final RecipeManager recipeManager = event.getServer().getRecipeManager();
        recipeManager.addRecipe(new ShapelessRecipe(new ResourceLocation("examplemod", "test"), "", new ItemStack(Items.DIAMOND), list));
        for(IRecipe iRecipe : recipeList) {
            recipeManager.addRecipe(iRecipe);
        }
    }
}
