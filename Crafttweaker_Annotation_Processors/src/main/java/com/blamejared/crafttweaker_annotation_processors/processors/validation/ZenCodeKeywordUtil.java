package com.blamejared.crafttweaker_annotation_processors.processors.validation;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;

public class ZenCodeKeywordUtil {
    
    private static final Set<String> knownKeywords;
    static {
        knownKeywords = new TreeSet<>();
        knownKeywords.add("import");
        knownKeywords.add("alias");
        knownKeywords.add("class");
        knownKeywords.add("function");
        knownKeywords.add("interface");
        knownKeywords.add("enum");
        knownKeywords.add("struct");
        knownKeywords.add("expand");
        knownKeywords.add("variant");
        knownKeywords.add("abstract");
        knownKeywords.add("final");
        knownKeywords.add("override");
        knownKeywords.add("const");
        knownKeywords.add("private");
        knownKeywords.add("public");
        knownKeywords.add("export");
        knownKeywords.add("internal");
        knownKeywords.add("static");
        knownKeywords.add("protected");
        knownKeywords.add("implicit");
        knownKeywords.add("virtual");
        knownKeywords.add("extern");
        knownKeywords.add("immutable");
        knownKeywords.add("val");
        knownKeywords.add("var");
        knownKeywords.add("get");
        knownKeywords.add("implements");
        knownKeywords.add("set");
        knownKeywords.add("void");
        knownKeywords.add("bool");
        knownKeywords.add("byte");
        knownKeywords.add("sbyte");
        knownKeywords.add("short");
        knownKeywords.add("ushort");
        knownKeywords.add("int");
        knownKeywords.add("uint");
        knownKeywords.add("long");
        knownKeywords.add("ulong");
        knownKeywords.add("usize");
        knownKeywords.add("float");
        knownKeywords.add("double");
        knownKeywords.add("char");
        knownKeywords.add("string");
        knownKeywords.add("if");
        knownKeywords.add("else");
        knownKeywords.add("do");
        knownKeywords.add("while");
        knownKeywords.add("for");
        knownKeywords.add("throw");
        knownKeywords.add("panic");
        knownKeywords.add("lock");
        knownKeywords.add("try");
        knownKeywords.add("catch");
        knownKeywords.add("finally");
        knownKeywords.add("return");
        knownKeywords.add("break");
        knownKeywords.add("continue");
        knownKeywords.add("switch");
        knownKeywords.add("case");
        knownKeywords.add("default");
        knownKeywords.add("in");
        knownKeywords.add("is");
        knownKeywords.add("as");
        knownKeywords.add("match");
        knownKeywords.add("throws");
        knownKeywords.add("super");
        knownKeywords.add("this");
        knownKeywords.add("null");
        knownKeywords.add("true");
        knownKeywords.add("false");
        knownKeywords.add("new");
    }
    
    public static boolean isKeyword(String name) {
        return knownKeywords.contains(name);
    }
    
    public static void checkName(String name, Element element, ProcessingEnvironment environment) {
        if(isKeyword(name)) {
            final Messager messager = environment.getMessager();
            final String message = String.format("Name '%s' is a ZenCode keyword!", name);
            messager.printMessage(Diagnostic.Kind.ERROR, message, element);
        }
    }
}
