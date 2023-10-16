package com.blamejared.crafttweaker.gametest.framework.parameterized;

import com.blamejared.crafttweaker.gametest.framework.Modifier;
import com.blamejared.crafttweaker.gametest.framework.ModifingConsumer;
import com.blamejared.crafttweaker.gametest.framework.ScriptBuilder;
import com.blamejared.crafttweaker.gametest.framework.zencode.GameTestGlobals;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessMinecraftServer;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;

import java.lang.reflect.Method;

public class ParameterizedConsumer extends ModifingConsumer {
    
    private Arguments.Builder arguments;
    
    public ParameterizedConsumer(Class<?> testClass, Method testMethod, Modifier modifier, Arguments.Builder arguments) {
        
        super(testClass, testMethod, modifier);
        this.arguments = arguments;
    }
    
    @Override
    public void accept(GameTestHelper gameTestHelper) {
        
        Object instance;
        try {
            instance = testClass.newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error while creating test class " + testClass, e.getCause());
        }
        
        try {
            
            Class<?>[] params = testMethod.getParameterTypes();
            Object[] args = new Object[params.length];
            for(int i = 0; i < params.length; i++) {
                if(params[i].equals(GameTestHelper.class)) {
                    args[i] = gameTestHelper;
                } else if(params[i].equals(ScriptBuilder.class)) {
                    try(final MinecraftServer.ReloadableResources reloadableResources = ((AccessMinecraftServer) gameTestHelper.getLevel()
                            .getServer()).crafttweaker$getResources()) {
                        final ResourceManager resourceManager = reloadableResources.resourceManager();
                        args[i] = new ScriptBuilder();
                    }
                } else if(params[i].equals(Arguments.class)) {
                    args[i] = arguments.build();
                }
            }
            testMethod.invoke(instance, args);
            
            if(modifier.isImplicitSuccession()) {
                gameTestHelper.succeed();
            }
        } catch(IllegalAccessException e) {
            throw new RuntimeException("Failed to invoke test method (%s) in (%s) because %s".formatted(testMethod.getName(), testMethod.getDeclaringClass()
                    .getCanonicalName(), e.getMessage()), e);
        } catch(Exception e) {
            e.printStackTrace();
            if(e.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            } else {
                throw new RuntimeException(e.getCause());
            }
        } finally {
            GameTestGlobals.reset();
        }
        
    }
    
}
