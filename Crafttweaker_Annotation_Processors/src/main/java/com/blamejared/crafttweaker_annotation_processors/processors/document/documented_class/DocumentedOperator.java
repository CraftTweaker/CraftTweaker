package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import org.openzen.zencode.java.ZenCodeType;

import java.util.Comparator;

public class DocumentedOperator {
    private final ZenCodeType.OperatorType type;
    public static Comparator<DocumentedOperator> compareByOp = Comparator.comparing(DocumentedOperator::getType);

    public DocumentedOperator(ZenCodeType.OperatorType type) {
        this.type = type;
    }

    public ZenCodeType.OperatorType getType() {
        return type;
    }
}
