package com.blamejared.crafttweaker.gametest.framework;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestFunction;

public class FutureJUnitTestFunction extends TestFunction {
    
    private ReporterListener.TestResult result;
    
    public FutureJUnitTestFunction(String name) {
        
        super("junit", name, "crafttweaker:empty", 1, 0, true, helper -> {
            helper.fail("future test never completed!");
        });
    }
    
    @Override
    public void run(GameTestHelper helper) {
        
        if(this.result == null) {
            helper.fail("Future test '" + this.getTestName() + "' never completed!");
        }
        if(this.result().success()) {
            helper.succeed();
        } else {
            helper.fail(this.result().error().getMessage());
        }
    }
    
    public void result(ReporterListener.TestResult result) {
        
        this.result = result;
    }
    
    public ReporterListener.TestResult result() {
        
        if(result == null) {
            throw new RuntimeException("future test '" + this.getTestName() + "' has not been ran!");
        }
        return result;
    }
    
}
