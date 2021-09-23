package com.blamejared.crafttweaker_annotation_processors.processors.validation.keywords;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.TreeSet;

public class ZenCodeKeywordUtil {
    
    private final Set<String> knownKeywords;
    {
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
    
    public boolean isKeyword(String name) {
        
        return knownKeywords.contains(name);
    }
    
    public void checkName(Element element, Messager messager) {
        
        final String name = element.getSimpleName().toString();
        if(isKeyword(name)) {
            final String message = String.format("Name '%s' is a ZenCode keyword!", name);
            messager.printMessage(Diagnostic.Kind.ERROR, message, element);
        }
    }
    
}
