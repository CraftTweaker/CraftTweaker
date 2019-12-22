package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;

import java.io.PrintWriter;
import java.util.*;

/**
 * Groups one or more methods with the same name.
 * Can contain multiple entries when a method name has been overloaded.
 */
public class DocumentedMethodGroup implements Writable {
    private final String name;
    private final Set<DocumentedMethod> methods;
    private final DocumentedClass containingClass;

    public DocumentedMethodGroup(String name, DocumentedClass containingClass) {
        this.name = name;
        this.containingClass = containingClass;
        this.methods = new TreeSet<>(Comparator.comparingInt(m -> m.getParameterList()
                .size()));
    }

    public void add(DocumentedMethod method) {
        if (!Objects.equals(this.containingClass, method.getContainingClass())) {
            this.methods.add(method.withCallee(this.containingClass.getDocParamThis()));
        }else {
            this.methods.add(method);
        }
    }

    public void addAll(Collection<DocumentedMethod> methods) {
        for (DocumentedMethod method : methods) {
            add(method);
        }
    }

    @Override
    public void write(PrintWriter writer) {
        writer.printf("### %s%n", name);
        for (DocumentedMethod method : methods) {
            method.write(writer);
        }
    }

    public DocumentedMethodGroup merge(DocumentedMethodGroup other) {

        final DocumentedMethodGroup documentedMethodGroup;
        if (Objects.equals(this.name, other.name)) {
            documentedMethodGroup = new DocumentedMethodGroup(this.name, containingClass);
        } else if (this.name == null || other.name == null) {
            documentedMethodGroup = new DocumentedMethodGroup(this.name == null ? other.name : this.name, containingClass);
        } else {
            throw new IllegalStateException("Method groups have different names: " + this.name + " and " + other.name);
        }

        documentedMethodGroup.addAll(this.methods);
        documentedMethodGroup.addAll(other.methods);
        return documentedMethodGroup;
    }
}
