package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;

public class ExampleDataConverter {
    
    public ExampleData convertFromCommentString(String docComment) {
        //TODO
        return new ExampleData(new Example("this", "TODO"));
    }
}
