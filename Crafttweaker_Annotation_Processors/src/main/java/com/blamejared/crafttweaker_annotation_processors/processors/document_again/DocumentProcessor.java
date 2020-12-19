package com.blamejared.crafttweaker_annotation_processors.processors.document_again;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.ElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.element.KnownElementList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.file.PageWriter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document", "net.minecraftforge.fml.common.Mod"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessor extends AbstractProcessor {
    
    private static final File outputDirectory = new File("docsOut");
    
    private KnownElementList knownElementList;
    private KnownModList knownModList;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        knownElementList = new KnownElementList(processingEnv);
        knownModList = new KnownModList(processingEnv);
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
        final DocumentRegistry documentRegistry = new DocumentRegistry();
        convertPages(documentRegistry);
        writePages(documentRegistry);
    }
    
    private void convertPages(DocumentRegistry documentRegistry) {
        final TypeConverter typeConverter = new TypeConverter(documentRegistry);
        final CommentConverter commentConverter = new CommentConverter(processingEnv);
        final ElementConverter elementConverter = new ElementConverter(knownModList, documentRegistry, commentConverter, typeConverter);
        elementConverter.handleElements(knownElementList);
    }
    
    private void writePages(DocumentRegistry documentRegistry) {
        final PageWriter pageWriter = new PageWriter(documentRegistry, outputDirectory);
        try {
            pageWriter.write();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
