package com.blamejared.crafttweaker.gametest.framework;

import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.gametest.framework.annotation.ScriptTestHolder;
import com.mojang.datafixers.util.Either;
import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.util.List;
import java.util.Map;

public class TestRunner {
    
    private final String loader;
    private final List<Class<?>> testClasses;
    
    private final ReporterListener listener = new ReporterListener();
    
    public TestRunner(String loader) {
        
        this.loader = loader;
        this.testClasses = gatherTests();
    }
    
    private List<Class<?>> gatherTests() {
        
        return (List<Class<?>>) ClassUtil.findClassesWithAnnotation(ScriptTestHolder.class, this::isSuitable).toList();
    }
    
    private boolean isSuitable(final Either<ScriptTestHolder, Map<String, Object>> data) {
        
        return this.loader.equals(data.map(ScriptTestHolder::loader, map -> map.get("loader")));
    }
    
    
    public void runTests() {
        
        LauncherDiscoveryRequestBuilder requestbuilder = LauncherDiscoveryRequestBuilder.request();
        this.testClasses.stream()
                .map(aClass -> DiscoverySelectors.selectClass(aClass))
                .forEach(requestbuilder::selectors);
        LauncherDiscoveryRequest request = requestbuilder.build();
        Launcher launcher = LauncherFactory.create(LauncherConfig.builder()
                .enableTestEngineAutoRegistration(false).addTestEngines(new JupiterTestEngine())
                .build());
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
    
}
