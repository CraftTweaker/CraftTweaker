package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

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
    
    public void writeVirtualMethods(PrintWriter writer) {
        writer.printf("### %s%n%n", name);
        for(StaticMethodMember method : staticMethods) {
            method.write(writer, ownerType);
        }
    }
}
