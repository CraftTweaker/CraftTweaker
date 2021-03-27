package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.mods.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.sun.source.util.*;
import org.reflections.*;
import org.reflections.util.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.io.*;
import java.lang.annotation.*;
import java.util.*;

public class DocumentProcessor extends AbstractCraftTweakerProcessor {
    
    private static final String defaultOutputDirectory = "docsOut";
    private static final String outputDirectoryOptionName = "crafttweaker.processor.document.output_directory";
    private File outputDirectory;
    
    private KnownElementList knownElementList;
    private KnownModList knownModList;
    
    @Override
    public Set<String> getSupportedOptions() {
        
        final Set<String> supportedOptions = new HashSet<>(super.getSupportedOptions());
        supportedOptions.add(outputDirectoryOptionName);
        return supportedOptions;
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        
        final HashSet<String> result = new HashSet<>(2);
        result.add(Document.class.getCanonicalName());
        result.add("net.minecraftforge.fml.common.Mod");
        return result;
    }
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        //Ignored since we need the other method here, due to @Mod
        throw new IllegalStateException("Should not be called!");
    }
    
    @Override
    protected void setupDependencyContainer() {
        
        super.setupDependencyContainer();
        setupTrees(processingEnv);
        setupReflections();
    }
    
    @Override
    protected void performInitialization() {
        
        knownElementList = dependencyContainer.getInstanceOfClass(KnownElementList.class);
        knownModList = dependencyContainer.getInstanceOfClass(KnownModList.class);
        final String docsOut = processingEnv.getOptions()
                .getOrDefault(outputDirectoryOptionName, defaultOutputDirectory);
        outputDirectory = new File(docsOut);
    }
    
    @Override
    protected boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        if(roundEnv.processingOver()) {
            handleLastRound();
        } else {
            handleIntermediateRound(roundEnv);
        }
        
        return false;
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
        
        final String environmentClassName = processingEnv.getClass().getName();
        
        if(!environmentClassName.equals("com.sun.tools.javac.processing.JavacProcessingEnvironment")) {
            //Let's throw the exception ourselves, since Trees.instance just throws an empty IllegalArgumentException which is harder to trace down
            throw new IllegalArgumentException("Processing environment must be JavacProcessingEnvironment, but is " + environmentClassName + "! Make sure you use gradle for compilation.");
        }
        
        final Trees instance = Trees.instance(processingEnv);
        dependencyContainer.addInstanceAs(instance, Trees.class);
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
