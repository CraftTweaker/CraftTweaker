package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.example;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter.ParameterInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter.ParameterInformationList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter.ParameterReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;

import javax.lang.model.element.Element;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ExampleDataConverter {
    
    private static final String DOC_PARAM = "docParam";
    
    private final ParameterReader reader;
    
    public ExampleDataConverter(ParameterReader reader) {
        this.reader = reader;
    }
    
    public ExampleData convertFromCommentString(String docComment, Element element) {
        final ParameterInformationList parameterInformationList = reader.readParametersFrom(docComment, element);
        if(hasExampleData(parameterInformationList)) {
            return getExampleDataFrom(parameterInformationList);
        }
        return ExampleData.empty();
    }
    
    private boolean hasExampleData(ParameterInformationList parameterInformationList) {
        return parameterInformationList.hasParameterInfoWithName(DOC_PARAM);
    }
    
    private ExampleData getExampleDataFrom(ParameterInformationList parameterInformationList) {
        final ParameterInfo docParamInfo = parameterInformationList.getParameterInfoWithName(DOC_PARAM);
        return getExampleDataFromParameterInfo(docParamInfo);
    }
    
    private ExampleData getExampleDataFromParameterInfo(ParameterInfo docParamInfo) {
        final Map<String, Example> examples = getExamplesFrom(docParamInfo);
        return new ExampleData(examples);
    }
    
    private Map<String, Example> getExamplesFrom(ParameterInfo docParamInfo) {
        return docParamInfo.getOccurrences()
                .stream()
                .map(this::getExampleFromOccurrence)
                .collect(asMapWithMergedExamples());
    }
    
    private Collector<Example, ?, Map<String, Example>> asMapWithMergedExamples() {
        final Function<Example, String> keyMapper = Example::getName;
        final Function<Example, Example> valueMapper = Function.identity();
        final BinaryOperator<Example> mergeFunction = Example::merge;
        
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction);
    }
    
    private Example getExampleFromOccurrence(String occurrence) {
        verifyThatOccurenceCanBeSplit(occurrence);
        
        final String exampleName = getExampleNameFrom(occurrence);
        final String textValue = getExampleTextValueFrom(occurrence);
        
        return new Example(exampleName, textValue);
    }
    
    private void verifyThatOccurenceCanBeSplit(String occurrence) {
        if(splitOccurence(occurrence).length != 2) {
            throw new IllegalArgumentException("Cannot split exampleData " + occurrence);
        }
    }
    
    private String[] splitOccurence(String occurrence) {
        return occurrence.split(" ", 2);
    }
    
    private String getExampleNameFrom(String occurrence) {
        return splitOccurence(occurrence)[0];
    }
    
    private String getExampleTextValueFrom(String occurrence) {
        return splitOccurence(occurrence)[1];
    }
}
