package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParameterInformationList {
    
    private final Map<String, ParameterInfo> parameterInformation = new HashMap<>();
    
    public ParameterInformationList(Map<String, ParameterInfo> parameterInformation) {
        this.parameterInformation.putAll(parameterInformation);
    }
    
    public void addParameterInfo(ParameterInfo parameterInfo) {
        this.parameterInformation.put(parameterInfo.getName(), parameterInfo);
    }
    
    public boolean hasParameterInfoWithName(String name) {
        return this.parameterInformation.containsKey(name);
    }
    
    public Optional<ParameterInfo> tryGetParameterInfoWithName(String name) {
        if(hasParameterInfoWithName(name)) {
            return Optional.of(this.parameterInformation.get(name));
        } else {
            return Optional.empty();
        }
    }
    
    public ParameterInfo getParameterInfoWithName(String name) {
        return tryGetParameterInfoWithName(name).orElseThrow(() -> new IllegalArgumentException("No such parameter registered: " + name));
    }
}
