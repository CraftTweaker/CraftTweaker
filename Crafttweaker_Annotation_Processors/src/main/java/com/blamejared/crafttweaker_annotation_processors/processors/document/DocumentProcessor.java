package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.ElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.KnownElementList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document.dependencies.SingletonDependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.DocsJsonWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageWriter;
import com.sun.source.util.Trees;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document", "net.minecraftforge.fml.common.Mod"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessor extends AbstractProcessor {
    
    private static final File outputDirectory = new File("docsOut");
    
    private final DependencyContainer dependencyContainer = new SingletonDependencyContainer();
    private KnownElementList knownElementList;
    private KnownModList knownModList;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        setupDependencyContainer(processingEnv);
        
        knownElementList = dependencyContainer.getInstanceOfClass(KnownElementList.class);
        knownModList = dependencyContainer.getInstanceOfClass(KnownModList.class);
    }
    
    private void setupDependencyContainer(ProcessingEnvironment processingEnv) {
        dependencyContainer.addInstanceAs(dependencyContainer, DependencyContainer.class);
        dependencyContainer.addInstanceAs(processingEnv, ProcessingEnvironment.class);
        dependencyContainer.addInstanceAs(processingEnv.getMessager(), Messager.class);
        dependencyContainer.addInstanceAs(processingEnv.getElementUtils(), Elements.class);
        dependencyContainer.addInstanceAs(processingEnv.getTypeUtils(), Types.class);
        
        setupTrees(processingEnv);
        setupReflections();
    }
    
    private void setupReflections() {
        final ConfigurationBuilder configuration = new ConfigurationBuilder().addUrls(ClasspathHelper
                .forJavaClassPath())
                .addClassLoaders(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), getClass()
                        .getClassLoader())
                .addUrls(ClasspathHelper.forClassLoader());
        
        final Reflections reflections = new Reflections(configuration);
        dependencyContainer.addInstanceAs(reflections, Reflections.class);
    }
    
    private void setupTrees(ProcessingEnvironment processingEnv) {
        final Trees instance = Trees.instance(processingEnv);
        dependencyContainer.addInstanceAs(instance, Trees.class);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(roundEnv.processingOver()) {
            handleLastRound();
        } else {
            handleIntermediateRound(roundEnv);
        }
        
        return false;
    }
    
    public void handleIntermediateRound(RoundEnvironment roundEnvironment) {
        knownModList.fillModIdInfo(roundEnvironment);
        knownElementList.addAllForIntermediateRound(roundEnvironment);
    }
    
    public void handleLastRound() {
        convertPages();
        writePages();
        writeDocsJsonFile();
    }
    
    private void writeDocsJsonFile() {
        final DocumentRegistry documentRegistry = dependencyContainer.getInstanceOfClass(DocumentRegistry.class);
        final DocsJsonWriter docsJsonWriter = new DocsJsonWriter(outputDirectory, documentRegistry);
        try {
            docsJsonWriter.write();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    private void convertPages() {
        final ElementConverter elementConverter = dependencyContainer.getInstanceOfClass(ElementConverter.class);
        elementConverter.handleElements(knownElementList);
    }
    
    private void writePages() {
        final DocumentRegistry documentRegistry = dependencyContainer.getInstanceOfClass(DocumentRegistry.class);
        final PageWriter pageWriter = new PageWriter(documentRegistry, new File(outputDirectory, "docs"));
        try {
            pageWriter.write();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
