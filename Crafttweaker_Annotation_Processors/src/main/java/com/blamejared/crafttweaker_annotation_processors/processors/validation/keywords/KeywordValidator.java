package com.blamejared.crafttweaker_annotation_processors.processors.validation.keywords;

import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class KeywordValidator extends AbstractProcessor {
    
    private final ZenCodeKeywordUtil keywordUtil = new ZenCodeKeywordUtil();
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final HashSet<String> result = new HashSet<>();
        addSupportedType(result, ZenCodeType.Method.class);
        addSupportedType(result, ZenCodeType.Getter.class);
        addSupportedType(result, ZenCodeType.Setter.class);
        addSupportedType(result, ZenCodeType.Field.class);
        return result;
    }
    
    private void addSupportedType(HashSet<String> result, Class<? extends Annotation> supportedType) {
        result.add(supportedType.getCanonicalName());
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement annotation : annotations) {
            for(Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                verifyElementNameUseNoKeywordAsName(element);
            }
        }
        
        return false;
    }
    
    private void verifyElementNameUseNoKeywordAsName(Element element) {
        keywordUtil.checkName(element, processingEnv.getMessager());
    }
}
