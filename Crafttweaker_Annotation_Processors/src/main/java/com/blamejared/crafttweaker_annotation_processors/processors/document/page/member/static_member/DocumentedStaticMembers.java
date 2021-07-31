package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.CasterMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.MethodMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.OperatorMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.PropertyMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.CasterMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.OperatorMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.VirtualMethodGroup;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DocumentedStaticMembers implements IFillMeta<MemberMeta> {
    
    protected final Map<String, PropertyMember> properties = new TreeMap<>();
    protected final Map<String, StaticMethodGroup> methodGroups = new TreeMap<>();
    
    public void write(PageOutputWriter writer) {
        writeStaticMethods(writer);
        writeProperties(writer);
    }
    
    
    private void writeStaticMethods(PageOutputWriter writer) {
        if(methodGroups.isEmpty()) {
            return;
        }
        
        writer.printf("## Static Methods%n%n");
        
        for(StaticMethodGroup value : methodGroups.values()) {
            value.writeStaticMethods(writer);
        }
    }
    
    protected void writeProperties(PageOutputWriter writer) {
        if(properties.isEmpty()) {
            return;
        }
        
        // TODO("Support deprecation and since properly, along with better documentation format")
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
    
    @Override
    public void fillMeta(MemberMeta meta) {
    
        Set<PropertyMemberMeta> propertyMeta = new HashSet<>();
        Set<MethodMemberMeta> methodMeta = new HashSet<>();
    
        for(PropertyMember value : properties.values()) {
            PropertyMemberMeta memberMeta = new PropertyMemberMeta();
            value.fillMeta(memberMeta);
            memberMeta.setStatic(true);
            propertyMeta.add(memberMeta);
        }
    
        for(StaticMethodGroup value : methodGroups.values()) {
            value.fillMeta(methodMeta);
        }
        meta.setProperties(propertyMeta);
        meta.setMethods(methodMeta);
    
    }
    
}
