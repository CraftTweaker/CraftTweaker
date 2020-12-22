package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterInfo {
    
    private final String name;
    private final List<String> occurrences = new ArrayList<>();
    
    private ParameterInfo(String name) {
        this.name = name;
    }
    
    public ParameterInfo(String name, String text) {
        this.name = name;
        addOccurrence(text);
    }
    
    public static ParameterInfo merge(ParameterInfo left, ParameterInfo right) {
        if(!left.name.equals(right.name)) {
            throw new IllegalArgumentException("Parameter names do not match! " + left.name + " != " + right.name);
        }
        
        final ParameterInfo result = new ParameterInfo(left.name);
        result.occurrences.addAll(left.occurrences);
        result.occurrences.addAll(right.occurrences);
        
        return result;
    }
    
    public void addOccurrence(String text) {
        this.occurrences.add(text);
    }
    
    public String getAnyOccurrence() {
        return this.occurrences.get(0);
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getOccurrences() {
        return occurrences;
    }
}
