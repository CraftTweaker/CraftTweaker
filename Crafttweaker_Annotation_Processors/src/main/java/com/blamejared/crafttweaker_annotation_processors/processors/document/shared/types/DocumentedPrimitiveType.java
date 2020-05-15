package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

import javax.lang.model.type.*;

public class DocumentedPrimitiveType extends DocumentedType {
    
    private final TypeMirror typeMirror;
    
    public DocumentedPrimitiveType(TypeMirror typeMirror) {
        super();
        this.typeMirror = typeMirror;
    }
    
    @Override
    public String getZSName() {
        return typeMirror.toString().toLowerCase();
    }
    
    @Override
    public String getClickableMarkdown() {
        return getZSName();
    }
    
    @Override
    public String getZSShortName() {
        return getZSName();
    }
    
    @Override
    public String getDocParamThis() {
        switch(typeMirror.getKind()) {
            case BOOLEAN:
                return "false";
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
                return "0 as " + getZSName();
            case CHAR:
                return "'\\0' as char";
            case FLOAT:
                return "0.0F";
            case DOUBLE:
                return "0.0D";
        }
        return "my" + getZSName();
    }
}
