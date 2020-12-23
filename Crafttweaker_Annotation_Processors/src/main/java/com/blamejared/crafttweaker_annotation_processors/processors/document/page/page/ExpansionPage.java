package com.blamejared.crafttweaker_annotation_processors.processors.document.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.io.PrintWriter;

public final class ExpansionPage extends DocumentationPage {
    
    private final AbstractTypeInfo expandedType;
    
    public ExpansionPage(AbstractTypeInfo expandedType, DocumentationPageInfo pageInfo, DocumentedVirtualMembers members, DocumentedStaticMembers staticMembers) {
        super(pageInfo, members, staticMembers);
        this.expandedType = expandedType;
    }
    
    @Override
    protected void writeTitle(PrintWriter writer) {
        writer.printf("# Expansion for %s%n%n", expandedType.getDisplayName());
    }
    
    @Override
    protected void writeOwnerModId(PrintWriter writer) {
        writer.printf("This expansion was added by a mod with mod-id `%s`. So you need to have this mod installed if you want to use this feature.%n%n", pageInfo.declaringModId);
    }
}
