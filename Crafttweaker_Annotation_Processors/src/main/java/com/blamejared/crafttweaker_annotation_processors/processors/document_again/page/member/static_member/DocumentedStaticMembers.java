package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class DocumentedStaticMembers {
    
    protected final Map<String, PropertyMember> properties = new TreeMap<>();
    protected final Map<String, StaticMethodGroup> methodGroups = new TreeMap<>();
    
    public void write(PrintWriter writer) {
        writeStaticMethods(writer);
        writeProperties(writer);
    }
    
    
    private void writeStaticMethods(PrintWriter writer) {
        if(methodGroups.isEmpty()) {
            return;
        }
        
        writer.printf("## Methods%n%n");
        
        for(StaticMethodGroup value : methodGroups.values()) {
            value.writeStaticMethods(writer);
        }
    }
    
    protected void writeProperties(PrintWriter writer) {
        if(properties.isEmpty()) {
            return;
        }
        
        writer.printf("## Properties%n%n");
        writer.println("| Name | Type | Has Getter | Has Setter |");
        writer.println("|------|------|------------|------------|");
        for(PropertyMember value : properties.values()) {
            value.writeTableRow(writer);
        }
    }
    
    public void addProperty(PropertyMember propertyMember) {
        properties.put(propertyMember.getName(), propertyMember);
    }
    
    public void addMethod(StaticMethodMember staticMethodMember, AbstractTypeInfo ownerType) {
        final StaticMethodGroup group = methodGroups.computeIfAbsent(staticMethodMember.getName(), name -> new StaticMethodGroup(name, ownerType));
        group.addMethod(staticMethodMember);
    }
}
