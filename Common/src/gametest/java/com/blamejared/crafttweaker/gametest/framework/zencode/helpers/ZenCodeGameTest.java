package com.blamejared.crafttweaker.gametest.framework.zencode.helpers;

import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.scriptingexample.tests.SharedGlobals;
import org.openzen.zenscript.scriptingexample.tests.helpers.FunctionParameterList;
import org.openzen.zenscript.scriptingexample.tests.helpers.ZenCodeTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class ZenCodeGameTest extends ZenCodeTest implements CraftTweakerGameTest {
    
    protected ZenCodeGameTest() {
        
    }
    
    public void setup() {
        
        try {
            this.logger = new ZenCodeGameTestLogger();
            this.engine = new ScriptingEngine(logger);
            engine.debug = true;
            this.testModule = engine.createNativeModule("test_module", "org.openzen.zenscript.scripting_tests");
            SharedGlobals.currentlyActiveLogger = logger;
            
            getRequiredClasses().stream().distinct().forEach(requiredClass -> {
                testModule.addGlobals(requiredClass);
                testModule.addClass(requiredClass);
            });
            engine.registerNativeProvided(testModule);
        } catch(CompileException e) {
            fail(e);
        }
    }
    
    public void executeEngine(boolean allowError) {
        
        try {
            final FunctionParameterList parameters = getParameters();
            final SemanticModule script_tests = engine.createScriptedModule("script_tests", sourceFiles.toArray(new SourceFile[0]), getBEP(), parameters.getParameters());
            final boolean scriptsValid = script_tests.isValid();
            if(allowError) {
                if(!scriptsValid) {
                    logger.setEngineComplete();
                    return;
                }
            } else {
                assertThat("Scripts are not valid!", scriptsValid, is(true));
            }
            engine.registerCompiled(script_tests);
            engine.run(parameters.getParameterMap());
        } catch(ParseException e) {
            fail("Error in Engine execution", e);
        }
        logger.setEngineComplete();
    }
    
}
