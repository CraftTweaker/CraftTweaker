package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.enum_constant;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.DocumentMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentedEnumConstants implements IFillMeta {
    
    private final List<EnumConstant> constants = new ArrayList<>();
    private final TypeName typeName;
    
    public DocumentedEnumConstants(TypeName typeName) {
        
        this.typeName = typeName;
    }
    
    public void addConstant(EnumConstant constant) {
        
        constants.add(constant);
    }
    
    public void write(PageOutputWriter writer) {
        
        if(constants.isEmpty()) {
            return;
        }
        
        writer.printf("## Enum Constants%n%n");
        // I think we never expose a singleton enum to zencode... right?
        writer.printf("%s is an enum. It has %s enum constants. They are accessible using the code below.%n%n", typeName.getSimpleName(), constants.size());
        writer.zenBlock(() -> constants.forEach(constant -> writeConstant(constant, writer)));
    }
    
    private void writeConstant(EnumConstant constant, PageOutputWriter writer) {
        
        writeConstantComment(constant.getComment(), writer);
        if (constant.isBracket()) {
            writer.println(constant.getName());
        } else {
            writer.println(typeName.getSimpleName() + "." + constant.getName());
        }
    }
    
    private void writeConstantComment(DocumentationComment comment, PageOutputWriter writer) {
        
        if(comment == null || !comment.hasDescription()) {
            return;
        }
        writer.printf("%n");
        String description = Arrays.stream(comment.getDescription().split("\n"))
                .map(s -> "// " + s.trim() + "\n")
                .collect(Collectors.joining());
        writer.printf(description);
        
        comment.getOptionalSince().ifPresent(since -> writer.printf("// since %s", since));
    }
    
    @Override
    public void fillMeta(DocumentMeta meta) {
        
        constants.forEach(constant -> constant.fillMeta(meta));
    }
    
}