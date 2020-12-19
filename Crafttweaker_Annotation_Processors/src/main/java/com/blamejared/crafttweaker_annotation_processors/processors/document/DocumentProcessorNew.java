package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.util.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document", "net.minecraftforge.fml.common.Mod"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessorNew extends AbstractProcessor {
    
    private static final File docsOut = new File("docsOut");
    
    public static Trees tree;
    
    //private final Collection<Element> allElements = new HashSet<>();
    
    private AnnotatedElementCollection annotatedElementCollection;
    private FileHandler fileHandler;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        tree = Trees.instance(processingEnv);
        KnownModList.initializeInstance(processingEnv);
        annotatedElementCollection = new AnnotatedElementCollection(processingEnv);
        fileHandler = new FileHandler(processingEnv, docsOut);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        KnownModList.getInstance().fillModIdInfo(roundEnv);
        annotatedElementCollection.addRound(roundEnv);
        
        if(!roundEnv.processingOver()) {
            return false;
        }
        
        annotatedElementCollection.handleElements();
        
        fileHandler.clearOutputDir();
        fileHandler.writeToFiles();
        allElements.clear();
        return false;
    }
    
    
}
