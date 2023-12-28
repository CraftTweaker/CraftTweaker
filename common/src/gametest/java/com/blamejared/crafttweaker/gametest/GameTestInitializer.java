package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.gametest.framework.FutureJUnitTestFunction;
import com.blamejared.crafttweaker.gametest.framework.Modifier;
import com.blamejared.crafttweaker.gametest.framework.ModifingConsumer;
import com.blamejared.crafttweaker.gametest.framework.SpecialCaseTestReporter;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.ScriptTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.parametized.ParameterizedGameTest;
import com.blamejared.crafttweaker.gametest.framework.parameterized.Arguments;
import com.blamejared.crafttweaker.gametest.framework.parameterized.ParameterizedConsumer;
import com.blamejared.crafttweaker.gametest.util.CraftTweakerGameTester;
import com.blamejared.crafttweaker.gametest.util.ICraftTweakerGameTester;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.world.level.block.Rotation;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@CraftTweakerGameTester
public class GameTestInitializer implements ICraftTweakerGameTester {
    
    public static final Map<String, FutureJUnitTestFunction> FUTURE_TESTS = new HashMap<>();
    
    @Override
    public List<TestFunction> collectTests() {
        
        GlobalTestReporter.replaceWith(new SpecialCaseTestReporter("GameTests", new File("game-test-results.xml")));
        
        List<TestFunction> functions = new ArrayList<>();
        ClassUtil.findClassesWithAnnotation(ScriptTestHolder.class).forEach(aClass -> {
            
            for(Method method : aClass.getDeclaredMethods()) {
                if(method.isAnnotationPresent(Test.class)) {
                    FutureJUnitTestFunction futureTest = FUTURE_TESTS.computeIfAbsent(aClass.getName() + "." + method.getName(), s -> new FutureJUnitTestFunction(s));
                    functions.add(futureTest);
                }
            }
        });
        
        ClassUtil.findClassesWithAnnotation(CraftTweakerGameTestHolder.class).forEach(aClass -> {
            
            for(Method method : aClass.getDeclaredMethods()) {
                if(method.isAnnotationPresent(GameTest.class)) {
                    GameTest annotation = method.getAnnotation(GameTest.class);
                    String className = aClass.getSimpleName().toLowerCase();
                    String testName = className + "." + method.getName().toLowerCase();
                    String template = annotation.template().isEmpty() ? testName : annotation.template();
                    String batch = annotation.batch().startsWith(CraftTweakerConstants.MOD_ID + ".") ? annotation.batch() : CraftTweakerConstants.MOD_ID + "." + annotation.batch();
                    Rotation rotation = StructureUtils.getRotationForRotationSteps(annotation.rotationSteps());
                    if(method.isAnnotationPresent(ParameterizedGameTest.class)) {
                        ParameterizedGameTest.Source argumentSource = method.getAnnotation(ParameterizedGameTest.class)
                                .argumentSource();
                        Stream<Arguments.Builder> arguments = Stream.empty();
                        try {
                            arguments = ((Stream<Arguments.Builder>) aClass.getMethod(argumentSource.method())
                                    .invoke(null));
                        } catch(Exception e) {
                            throw new RuntimeException("Error while getting argument source for test: " + testName, e);
                        }
                        arguments.forEach(arguments1 -> {
                            ParameterizedConsumer consumer = new ParameterizedConsumer(aClass, method, Modifier.from(method), arguments1);
                            functions.add(new TestFunction(batch, testName + "(" + arguments1.name() + ")", template, rotation, annotation.timeoutTicks(), annotation.setupTicks(), annotation.required(), annotation.requiredSuccesses(), annotation.attempts(), consumer));
                        });
                    } else {
                        ModifingConsumer consumer = new ModifingConsumer(aClass, method, Modifier.from(method));
                        functions.add(new TestFunction(batch, testName, template, rotation, annotation.timeoutTicks(), annotation.setupTicks(), annotation.required(), annotation.requiredSuccesses(), annotation.attempts(), consumer));
                    }
                }
            }
        });
        return functions;
    }
    
}
