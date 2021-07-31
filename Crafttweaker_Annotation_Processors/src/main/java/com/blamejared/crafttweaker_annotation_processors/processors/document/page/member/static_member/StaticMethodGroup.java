package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.MethodMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.VirtualMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.util.Set;
import java.util.TreeSet;

public class StaticMethodGroup implements IFillMeta<Set<MethodMemberMeta>>{
    
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
    
    public void writeStaticMethods(PageOutputWriter writer) {
        
        for(StaticMethodMember method : staticMethods) {
            writer.group(name, method.getSince(), () -> method.write(writer, ownerType));
        }
    }
    
    @Override
    public void fillMeta(Set<MethodMemberMeta> meta) {
        for(StaticMethodMember virtualMethod : staticMethods) {
            MethodMemberMeta memberMeta = new MethodMemberMeta();
            virtualMethod.fillMeta(memberMeta);
            meta.add(memberMeta);
        }
    }
    
}
