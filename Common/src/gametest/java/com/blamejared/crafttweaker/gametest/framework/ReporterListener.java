package com.blamejared.crafttweaker.gametest.framework;

import com.blamejared.crafttweaker.gametest.GameTestInitializer;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporterListener implements TestExecutionListener {
    
    class TestResult {
        
        private final String name;
        private final long startTime;
        private boolean success;
        private Throwable error;
        
        private long endTime;
        
        public TestResult(String name, long startTime) {
            
            this.name = name;
            this.startTime = startTime;
        }
        
        public String name() {
            
            return name;
        }
        
        public long startTime() {
            
            return startTime;
        }
        
        public long endTime() {
            
            return endTime;
        }
        
        public void endTime(long endTime) {
            
            this.endTime = endTime;
        }
        
        public boolean success() {
            
            return success;
        }
        
        public void success(boolean success) {
            
            this.success = success;
        }
        
        public Throwable error() {
            
            return error;
        }
        
        public void error(Throwable error) {
            
            this.error = error;
        }
        
        @Override
        public boolean equals(Object o) {
            
            if(this == o) {
                return true;
            }
            if(o == null || getClass() != o.getClass()) {
                return false;
            }
            
            TestResult that = (TestResult) o;
            
            if(startTime != that.startTime) {
                return false;
            }
            if(success != that.success) {
                return false;
            }
            if(endTime != that.endTime) {
                return false;
            }
            if(name != null ? !name.equals(that.name) : that.name != null) {
                return false;
            }
            return error != null ? error.equals(that.error) : that.error == null;
        }
        
        @Override
        public int hashCode() {
            
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (int) (startTime ^ (startTime >>> 32));
            result = 31 * result + (success ? 1 : 0);
            result = 31 * result + (error != null ? error.hashCode() : 0);
            result = 31 * result + (int) (endTime ^ (endTime >>> 32));
            return result;
        }
        
    }
    
    private Map<String, TestResult> results;
    
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        
        this.results = new HashMap<>();
    }
    
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
    
        for(String name : this.results.keySet()) {
            FutureJUnitTestFunction futureTest = GameTestInitializer.FUTURE_TESTS.computeIfAbsent(name, s1 -> new FutureJUnitTestFunction(name));
            futureTest.result(this.results.get(name));
        }
    }
    
    
    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        
        if(testIdentifier.isTest()) {
            this.results.put(getName(testIdentifier), new TestResult(getName(testIdentifier), System.nanoTime()));
        }
    }
    
    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        
        TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);
        if(testIdentifier.isTest()) {
            TestResult result = this.results.get(getName(testIdentifier));
            result.endTime(System.nanoTime());
            result.success(testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL);
            testExecutionResult.getThrowable().ifPresent(throwable -> {
                result.error(throwable);
            });
        }
    }
    
    private String getName(TestIdentifier identifier) {
        
        if(identifier.getSource().isPresent() && identifier.getSource().get() instanceof MethodSource mSource) {
            return mSource.getJavaClass().getName() + "." + mSource.getMethodName();
        }
        return identifier.getUniqueIdObject()
                .getSegments()
                .stream()
                .filter(segment -> !segment.getType().equals("engine"))
                .map(segment -> segment.getValue())
                .collect(Collectors.joining("."));
    }
    
}
