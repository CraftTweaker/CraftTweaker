package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class DocumentedTypeVirtualMembers extends DocumentedVirtualMembers {
    
    public final Set<ConstructorMember> constructors = new TreeSet<>();
    
    @Override
    public void write(PrintWriter writer) {
        writeConstructors(writer);
        super.write(writer);
    }
    
    private void writeConstructors(PrintWriter writer) {
        if(constructors.isEmpty()) {
            return;
        }
        writer.printf("## Constructors%n%n");
        for(ConstructorMember constructor : constructors) {
            constructor.write(writer);
        }
    }
}
