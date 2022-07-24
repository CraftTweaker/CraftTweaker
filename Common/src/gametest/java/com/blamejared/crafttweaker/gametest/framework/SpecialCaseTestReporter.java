package com.blamejared.crafttweaker.gametest.framework;

import com.google.common.base.Stopwatch;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestReporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SpecialCaseTestReporter implements TestReporter {
    
    private final Stopwatch stopwatch;
    private final String name;
    private final File destination;
    private final Instant timestamp;
    
    private final List<GameTestInfo> successfulTests;
    private final List<GameTestInfo> failedTests;
    
    public SpecialCaseTestReporter(String name, File destination) {
        
        this.name = name;
        this.destination = destination;
        this.timestamp = Instant.now();
        this.successfulTests = new ArrayList<>();
        this.failedTests = new ArrayList<>();
        this.stopwatch = Stopwatch.createStarted();
    }
    
    private void createTestCase(Document document, Element testSuite, GameTestInfo info) {
        
        Element testCase = document.createElement("testcase");
        testCase.setAttribute("name", info.getTestName());
        testCase.setAttribute("classname", info.getStructureName());
        testCase.setAttribute("time", String.valueOf(getRunTime(info) / 1000.0));
        if(info.hasFailed()) {
            String errorMessage = info.getError() == null ? "NA" : info.getError().getMessage();
            Element child = document.createElement(info.isRequired() ? "failure" : "skipped");
            child.setAttribute("message", errorMessage);
            testCase.appendChild(child);
        }
        testSuite.appendChild(testCase);
    }
    
    public void onTestFailed(GameTestInfo info) {
        
        this.failedTests.add(info);
    }
    
    public void onTestSuccess(GameTestInfo info) {
        
        this.successfulTests.add(info);
    }
    
    public void finish() {
        
        this.stopwatch.stop();
        
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element testSuite = document.createElement("testsuite");
            document.appendChild(testSuite);
            testSuite.setAttribute("timestamp", DateTimeFormatter.ISO_INSTANT.format(this.timestamp));
            testSuite.setAttribute("name", name);
            testSuite.setAttribute("time", String.valueOf(this.getTests()
                    .map(this::getRunTime)
                    .reduce(Long::sum)
                    .orElse(0L) / 1000.0));
            
            testSuite.setAttribute("tests", String.valueOf(this.successfulTests.size() + this.failedTests.size()));
            testSuite.setAttribute("failures", String.valueOf(this.failedTests.size()));
            
            this.getTests().forEach(gameTestInfo -> this.createTestCase(document, testSuite, gameTestInfo));
            
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(destination);
            transformer.transform(source, result);
        } catch(TransformerException | ParserConfigurationException e) {
            throw new Error("Couldn't save test report", e);
        }
    }
    
    private long getRunTime(GameTestInfo info) {
        
        if(info.getTestFunction() instanceof FutureJUnitTestFunction jUnitTestFunction) {
            return Duration.ofNanos(jUnitTestFunction.result().endTime() - jUnitTestFunction.result().startTime())
                    .toMillis();
        }
        
        return info.getRunTime();
    }
    
    private Stream<GameTestInfo> getTests() {
        
        return Stream.concat(this.successfulTests.stream(), this.failedTests.stream());
    }
    
}
