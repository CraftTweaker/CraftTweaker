package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.network.chat.Component;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@ZenRegister
@Document("vanilla/api/entity/NamePlateResult")
@ZenCodeType.Name("crafttweaker.api.entity.NamePlateResult")
public class NamePlateResult {
    
    @Nullable
    private Boolean result;
    private Component content;
    private final Component originalContent;
    
    public NamePlateResult(@Nullable Boolean result, Component content, Component originalContent) {
        
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
    public Component getContent() {
        
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
    public void setContent(Component content) {
        
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
    public Component getOriginalContent() {
        
        return originalContent;
    }
    
    /**
     * Forces the nameplate to be visible even when it would otherwise not be.
     */
    @ZenCodeType.Method
    public void alwaysRender() {
        
        this.result = true;
    }
    
    /**
     * Forces the display name to not render even when it should.
     */
    @ZenCodeType.Method
    public void noRender() {
        
        this.result = false;
    }
    
    /**
     * Sets the default vanilla behaviour of rendering nameplates.
     */
    @ZenCodeType.Method
    public void setDefault() {
        
        this.result = null;
    }
    
    @Nullable
    public Boolean getResult() {
        
        return result;
    }
    
}
