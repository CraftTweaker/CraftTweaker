package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class StaticMethodGroup {
    
    private final String name;
    private final AbstractTypeInfo ownerType;
    private final Set<StaticMethodMember> staticMethods = new TreeSet<>();
    
    public StaticMethodGroup(String name, AbstractTypeInfo ownerType) {
        
        this.name = name;
        this.ownerType = ownerType;
    }
    
    public void addMethod(StaticMethodMember member) {
        
        this.staticMethods.add(member);
    }
    
    public void writeStaticMethods(PrintWriter writer) {
        
        for(StaticMethodMember method : staticMethods) {
            writer.printf(":::group{name=%s}%n%n", name);
            method.write(writer, ownerType);
            writer.printf(":::%n%n");
        }
    }
    
}
