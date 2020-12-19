package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class VirtualMethodGroup {
    
    private final String name;
    private final Set<VirtualMethodMember> virtualMethods = new TreeSet<>();
    
    public VirtualMethodGroup(String name) {
        this.name = name;
    }
    
    public void addMethod(VirtualMethodMember member) {
        this.virtualMethods.add(member);
    }
    
    public void writeVirtualMethods(PrintWriter writer) {
        writer.printf("### %s%n%n", name);
        for(VirtualMethodMember method : virtualMethods) {
            method.write(writer);
        }
    }
}
