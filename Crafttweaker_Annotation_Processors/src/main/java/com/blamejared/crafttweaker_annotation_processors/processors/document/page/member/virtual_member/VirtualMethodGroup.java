package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class VirtualMethodGroup {
    
    private final String name;
    private final AbstractTypeInfo ownerType;
    private final Set<VirtualMethodMember> virtualMethods = new TreeSet<>();
    
    public VirtualMethodGroup(String name, AbstractTypeInfo ownerType) {
        
        this.name = name;
        this.ownerType = ownerType;
    }
    
    public void addMethod(VirtualMethodMember member) {
        
        this.virtualMethods.add(member);
    }
    
    public void writeVirtualMethods(PageOutputWriter writer) {
        
        for(VirtualMethodMember method : virtualMethods) {
            writer.group(name, method.getSince(), () -> method.write(writer, ownerType));
        }
        
    }
    
}
