package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.JUnitLikeTestReporter;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.world.level.block.Rotation;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CraftTweakerGameTests {
    
    @GameTestGenerator
    public static List<TestFunction> getTests() {
        
        List<TestFunction> functions = new ArrayList<>();
        Services.PLATFORM.findClassesWithAnnotation(CraftTweakerGameTestHolder.class).forEach(aClass -> {
            
            for(Method method : aClass.getDeclaredMethods()) {
                if(!method.isAnnotationPresent(GameTest.class)) {
                    continue;
                }
                GameTest annotation = method.getAnnotation(GameTest.class);
                
                String className = aClass.getSimpleName().toLowerCase();
                String testName = className + "." + method.getName().toLowerCase();
                String template = annotation.template().isEmpty() ? testName : annotation.template();
                String batch = annotation.batch();
                Rotation rotation = StructureUtils.getRotationForRotationSteps(annotation.rotationSteps());
                ModifingConsumer consumer = new ModifingConsumer(aClass, method, Modifier.from(method));
                functions.add(new TestFunction(batch, testName, template, rotation, annotation.timeoutTicks(), annotation.setupTicks(), annotation.required(), annotation.requiredSuccesses(), annotation.attempts(), consumer));
            }
        });
        // We will only find tests in our own gametest environment, so this should only run if we are in our own environment
        if(!functions.isEmpty()) {
            try {
                GlobalTestReporter.replaceWith(new JUnitLikeTestReporter(new File("game-test-results.xml")));
            } catch(ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return functions;
    }
    
    public static class ModifingConsumer implements Consumer<GameTestHelper> {
        
        private final Class<?> testClass;
        private final Method testMethod;
        private final Modifier modifier;
        
        public ModifingConsumer(Class<?> testClass, Method testMethod, Modifier modifier) {
            
            this.testClass = testClass;
            this.testMethod = testMethod;
            this.modifier = modifier;
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
                
                testMethod.invoke(instance, gameTestHelper);
                
                if(modifier.isImplicitSuccession()) {
                    gameTestHelper.succeed();
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException("Failed to invoke test method (%s) in (%s) because %s".formatted(testMethod.getName(), testMethod.getDeclaringClass()
                        .getCanonicalName(), e.getMessage()), e);
            } catch(InvocationTargetException e) {
                if(e.getCause() instanceof RuntimeException runtimeException) {
                    throw runtimeException;
                } else {
                    throw new RuntimeException(e.getCause());
                }
            }
            
            
        }
        
    }
    
}
