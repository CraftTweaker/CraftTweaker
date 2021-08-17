package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/NamePlateResult")
@ZenCodeType.Name("crafttweaker.api.entity.NamePlateResult")
public class NamePlateResult {
    
    private Event.Result result;
    private MCTextComponent content;
    private final MCTextComponent originalContent;
    
    public NamePlateResult(Event.Result result, MCTextComponent content, MCTextComponent originalContent) {
        
        this.result = result;
        this.content = content;
        this.originalContent = originalContent;
    }
    
    /**
     * Gets the current content of the nameplate.
     * This can be changed by mods.
     *
     * @return The current nameplate content.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("content")
    public MCTextComponent getContent() {
        
        return content;
    }
    
    
    /**
     * Sets the new content of the nameplate.
     *
     * @param content The new nameplate contents.
     *
     * @docParam contents "Creator"
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("content")
    public void setContent(MCTextComponent content) {
        
        this.content = content;
    }
    
    /**
     * Gets the original content of the nameplate.
     * This can not be changed by mods.
     *
     * @return The original nameplate content.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalContent")
    public MCTextComponent getOriginalContent() {
        
        return originalContent;
    }
    
    /**
     * Forces the nameplate to be visible even when it would otherwise not be.
     */
    @ZenCodeType.Method
    public void setAllow() {
        
        this.result = Event.Result.ALLOW;
    }
    
    /**
     * Forces the display name to not render even when it should.
     */
    @ZenCodeType.Method
    public void setDeny() {
        
        this.result = Event.Result.DENY;
    }
    
    /**
     * Sets the default vanilla behaviour of rendering nameplates.
     */
    @ZenCodeType.Method
    public void setDefault() {
        
        this.result = Event.Result.DEFAULT;
    }
    
    public Event.Result getResult() {
        
        return result;
    }
    
}
