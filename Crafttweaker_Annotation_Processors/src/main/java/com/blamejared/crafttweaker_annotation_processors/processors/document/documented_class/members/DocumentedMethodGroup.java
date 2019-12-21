package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;

import java.io.PrintWriter;
import java.util.*;

/**
 * Groups one or more methods with the same name.
 * Can contain multiple entries when a method name has been overloaded.
 */
public class DocumentedMethodGroup implements Writable {
    private final String name;
    private final Set<DocumentedMethod> methods;

    public DocumentedMethodGroup(String name) {
        this.name = name;
        this.methods = new TreeSet<>(Comparator.comparingInt(m -> m.getParameterList()
                .size()));
    }

    private DocumentedMethodGroup(String name, Collection<DocumentedMethod> methodsA, Collection<DocumentedMethod> methodsB) {
        this.name = name;
        this.methods = new TreeSet<>(Comparator.comparingInt(m -> m.getParameterList()
                .size()));

        this.methods.addAll(methodsA);
        this.methods.addAll(methodsB);
    }

    public void add(DocumentedMethod method) {
        this.methods.add(method);
    }

    @Override
    public void write(PrintWriter writer) {
        writer.printf("### %s%n", name);
        for (DocumentedMethod method : methods) {
            method.write(writer);
        }
    }

    public DocumentedMethodGroup merge(DocumentedMethodGroup other) {
        if (Objects.equals(this.name, other.name)) {
            return new DocumentedMethodGroup(this.name, this.methods, other.methods);
        }

        if (this.name == null || other.name == null) {
            return new DocumentedMethodGroup(this.name == null ? other.name : this.name, this.methods, other.methods);
        }

        throw new IllegalStateException("Method groups have different names: " + this.name + " and " + other.name);
    }
}
