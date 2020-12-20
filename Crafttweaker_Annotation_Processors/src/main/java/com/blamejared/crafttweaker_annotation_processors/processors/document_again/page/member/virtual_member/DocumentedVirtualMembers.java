package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DocumentedVirtualMembers {
    
    protected final Set<CasterMember> casters = new TreeSet<>();
    protected final Map<String, VirtualMethodGroup> methodGroups = new TreeMap<>();
    protected final Set<OperatorMember> operators = new TreeSet<>();
    protected final Map<String, PropertyMember> properties = new TreeMap<>();
    
    public DocumentedVirtualMembers() {
    }
    
    public void addCaster(CasterMember casterMember) {
        casters.add(casterMember);
    }
    
    public void addMethod(VirtualMethodMember methodMember, AbstractTypeInfo ownerType) {
        final VirtualMethodGroup group = methodGroups.computeIfAbsent(methodMember.getName(), name -> new VirtualMethodGroup(name, ownerType));
        group.addMethod(methodMember);
    }
    
    public void addOperator(OperatorMember operatorMember) {
        operators.add(operatorMember);
    }
    
    public void addProperty(PropertyMember propertyMember) {
        properties.merge(propertyMember.getName(), propertyMember, PropertyMember::merge);
    }
    
    public void write(PrintWriter writer) {
        writeCasters(writer);
        writeMethods(writer);
        writeOperators(writer);
        writeProperties(writer);
    }
    
    protected void writeCasters(PrintWriter writer) {
        if(casters.isEmpty()) {
            return;
        }
        
        writer.printf("## Casters%n%n");
        writer.println("| Result type | Is Implicit |");
        writer.println("|-------------|-------------|");
        for(CasterMember caster : casters) {
            caster.writeTableRow(writer);
        }
    }
    
    protected void writeMethods(PrintWriter writer) {
        if(methodGroups.isEmpty()) {
            return;
        }
        writer.printf("## Methods%n%n");
        
        for(VirtualMethodGroup value : methodGroups.values()) {
            value.writeVirtualMethods(writer);
        }
    }
    
    protected void writeOperators(PrintWriter writer) {
        if(operators.isEmpty()) {
            return;
        }
        
        writer.printf("## Operators%n%n");
        for(OperatorMember operator : operators) {
            operator.write(writer);
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
    
    
}
