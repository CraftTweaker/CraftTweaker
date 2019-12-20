package com.blamejared.crafttweaker_annotation_processors.processors.document;

import java.util.ArrayList;
import java.util.List;

public class DocumentedScriptingExample {
    private final List<String> content;

    public DocumentedScriptingExample() {
        this(new ArrayList<>());
    }

    public DocumentedScriptingExample(List<String> content) {
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

    public static DocumentedScriptingExample fromFile(String fileName) {
        //TODO
        return new DocumentedScriptingExample();
    }
}
