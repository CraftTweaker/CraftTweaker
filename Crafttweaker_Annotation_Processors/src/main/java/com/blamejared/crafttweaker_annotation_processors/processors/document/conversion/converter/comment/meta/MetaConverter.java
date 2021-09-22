package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.meta;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterInformationList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.Example;

import javax.lang.model.element.Element;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MetaConverter {
    
    private static final String DOC_SHORT_DESC = "docShortDescription";
    
    private final ParameterReader reader;
    
    public MetaConverter(ParameterReader reader) {
        
        this.reader = reader;
    }
    
    public MetaData convertFromCommentString(String docComment, Element element) {
        
        final ParameterInformationList parameterInformationList = reader.readParametersFrom(docComment, element);
        if(hasMetaData(parameterInformationList)) {
            return getMetaDataFrom(parameterInformationList);
        }
        return MetaData.empty();
    }
    
    private boolean hasMetaData(ParameterInformationList parameterInformationList) {
        
        return parameterInformationList.hasParameterInfoWithName(DOC_SHORT_DESC);
    }
    
    private MetaData getMetaDataFrom(ParameterInformationList parameterInformationList) {
        
        final ParameterInfo docParamInfo = parameterInformationList.getParameterInfoWithName(DOC_SHORT_DESC);
        return getMetaDataFromParameterInfo(docParamInfo);
    }
    
    private MetaData getMetaDataFromParameterInfo(ParameterInfo docParamInfo) {
        return new MetaData(docParamInfo.getAnyOccurrence());
    }
    
    private Collector<Example, ?, Map<String, Example>> asMapWithMergedExamples() {
        
        final Function<Example, String> keyMapper = Example::getName;
        final Function<Example, Example> valueMapper = Function.identity();
        final BinaryOperator<Example> mergeFunction = Example::merge;
        
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction);
    }
    
    
}
