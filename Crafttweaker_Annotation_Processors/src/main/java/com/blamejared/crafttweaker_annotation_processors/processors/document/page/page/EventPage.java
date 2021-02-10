package com.blamejared.crafttweaker_annotation_processors.processors.document.page.page;

import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.EventHasResult;

import java.io.PrintWriter;

public class EventPage extends DocumentationPage {
    
    private final TypePage baseTypePage;
    private final EventCancelable cancelableAnnotation;
    private final EventHasResult hasResultAnnotation;
    
    public EventPage(TypePage baseTypePage, EventCancelable cancelableAnnotation, EventHasResult hasResultAnnotation) {
        
        super(baseTypePage.pageInfo, baseTypePage.virtualMembers, baseTypePage.staticMembers);
        this.baseTypePage = baseTypePage;
        this.cancelableAnnotation = cancelableAnnotation;
        this.hasResultAnnotation = hasResultAnnotation;
    }
    
    @Override
    protected void writeTitle(PrintWriter writer) {
        
        baseTypePage.writeTitle(writer);
    }
    
    @Override
    protected void writeOwnerModId(PrintWriter writer) {
        
        baseTypePage.writeTitle(writer);
    }
    
    @Override
    protected void beforeWritingMembers(PrintWriter writer) {
        
        baseTypePage.beforeWritingMembers(writer);
    }
    
    @Override
    protected void writeMembers(PrintWriter writer) {
        
        baseTypePage.writeMembers(writer);
    }
    
    @Override
    protected void writeDescription(PrintWriter writer) {
        
        baseTypePage.writeDescription(writer);
        if(cancelableAnnotation == null || !cancelableAnnotation.value()) {
            writer.println("The event is not cancelable.");
            writer.println();
        } else {
            writer.println("The class is cancelable.");
            writer.println();
            if(!cancelableAnnotation.canceledDescription().isEmpty()) {
                writer.println("If the event is canceled, " + cancelableAnnotation.canceledDescription());
                writer.println();
            }
            if(!cancelableAnnotation.notCanceledDescription().isEmpty()) {
                writer.println("If the event is not canceled, " + cancelableAnnotation.notCanceledDescription());
                writer.println();
            }
        }
        if(hasResultAnnotation == null || !hasResultAnnotation.value()) {
            writer.println("The event does not have result.");
            writer.println();
        } else {
            writer.println("The event has a result.");
            writer.println();
            if(!hasResultAnnotation.defaultDescription().isEmpty()) {
                writer.println("If result is set to `default`, " + hasResultAnnotation.defaultDescription());
                writer.println();
            }
            if(!hasResultAnnotation.allowDescription().isEmpty()) {
                writer.println("If result is set to `allow`, " + hasResultAnnotation.allowDescription());
                writer.println();
            }
            if(!hasResultAnnotation.denyDescription().isEmpty()) {
                writer.println("If result is set to `deny`, " + hasResultAnnotation.denyDescription());
                writer.println();
            }
        }
    }
    
}
