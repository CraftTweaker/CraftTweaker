package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.gametest.framework.FutureJUnitTestFunction;
import com.blamejared.crafttweaker.gametest.framework.Modifier;
import com.blamejared.crafttweaker.gametest.framework.ModifingConsumer;
import com.blamejared.crafttweaker.gametest.framework.SpecialCaseTestReporter;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.ScriptTestHolder;
import com.blamejared.crafttweaker.gametest.util.CraftTweakerGameTester;
import com.blamejared.crafttweaker.gametest.util.ICraftTweakerGameTester;
import com.blamejared.crafttweaker.platform.Services;
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

@CraftTweakerGameTester
public class GameTestInitializer implements ICraftTweakerGameTester {
    
    public static final Map<String, FutureJUnitTestFunction> FUTURE_TESTS = new HashMap<>();
    
    @Override
    public List<TestFunction> collectTests() {
        
        GlobalTestReporter.replaceWith(new SpecialCaseTestReporter("GameTests", new File("game-test-results.xml")));
        
        List<TestFunction> functions = new ArrayList<>();
        Services.PLATFORM.findClassesWithAnnotation(ScriptTestHolder.class).forEach(aClass -> {
            
            for(Method method : aClass.getDeclaredMethods()) {
                if(method.isAnnotationPresent(Test.class)) {
                    FutureJUnitTestFunction futureTest = FUTURE_TESTS.computeIfAbsent(aClass.getName() + "." + method.getName(), s -> new FutureJUnitTestFunction(s));
                    functions.add(futureTest);
                }
            }
        });
        
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
        return functions;
    }
    
}
