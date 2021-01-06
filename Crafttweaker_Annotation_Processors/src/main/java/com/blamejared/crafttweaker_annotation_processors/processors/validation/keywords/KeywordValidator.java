package com.blamejared.crafttweaker_annotation_processors.processors.validation.keywords;

import com.blamejared.crafttweaker_annotation_processors.processors.AbstractCraftTweakerProcessor;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class KeywordValidator extends AbstractCraftTweakerProcessor {
    
    private ZenCodeKeywordUtil keywordUtil;
    
    @Override
    protected void performInitialization() {
        this.keywordUtil = dependencyContainer.getInstanceOfClass(ZenCodeKeywordUtil.class);
    }
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        return Arrays.asList(ZenCodeType.Method.class, ZenCodeType.Getter.class, ZenCodeType.Setter.class, ZenCodeType.Field.class);
    }
    
    @Override
    public boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
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
