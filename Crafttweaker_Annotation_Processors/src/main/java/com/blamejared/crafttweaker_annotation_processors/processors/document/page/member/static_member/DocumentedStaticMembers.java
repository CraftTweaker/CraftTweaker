package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

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
        
        writer.printf("## Static Methods%n%n");
        
        for(StaticMethodGroup value : methodGroups.values()) {
            value.writeStaticMethods(writer);
        }
    }
    
    protected void writeProperties(PrintWriter writer) {
        if(properties.isEmpty()) {
            return;
        }
        
        writer.printf("## Static Properties%n%n");
        writer.println("| Name | Type | Has Getter | Has Setter | Description |");
        writer.println("|------|------|------------|------------|-------------|");
        for(PropertyMember value : properties.values()) {
            value.writeTableRow(writer);
        }
        writer.println();
    }
    
    public void addProperty(PropertyMember propertyMember) {
        properties.put(propertyMember.getName(), propertyMember);
    }
    
    public void addMethod(StaticMethodMember staticMethodMember, AbstractTypeInfo ownerType) {
        final StaticMethodGroup group = methodGroups.computeIfAbsent(staticMethodMember.getName(), name -> new StaticMethodGroup(name, ownerType));
        group.addMethod(staticMethodMember);
    }
}
