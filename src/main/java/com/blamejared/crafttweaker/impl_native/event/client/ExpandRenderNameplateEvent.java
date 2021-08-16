package com.blamejared.crafttweaker.impl_native.event.client;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.client.event.RenderNameplateEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * RenderNameplateEvent is fired whenever the entity renderer attempts to render a name plate/tag of an entity.
 *
 * This event has a result.
 * ALLOW will force-render name plate/tag.
 * DEFAULT will ignore the hook and continue using the vanilla check.
 * DENY will prevent name plate/tag from rendering.
 */
@ZenRegister
@Document("vanilla/api/event/client/RenderNameplateEvent")
@NativeTypeRegistration(value = RenderNameplateEvent.class, zenCodeName = "crafttweaker.api.event.client.RenderNameplateEvent")
public class ExpandRenderNameplateEvent {
    
    /**
     * Sets the new content of the nameplate.
     *
     * @param contents The new nameplate contents.
     *
     * @docParam contents "Creator"
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("content")
    public static void setContent(RenderNameplateEvent internal, MCTextComponent contents) {
        
        internal.setContent(contents.getInternal());
    }
    
    /**
     * Gets the current content of the nameplate.
     * This can be changed by mods.
     *
     * @return The current nameplate content.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("content")
    public static MCTextComponent getContent(RenderNameplateEvent internal) {
        
        return new MCTextComponent(internal.getContent());
    }
    
    /**
     * Gets the original content of the nameplate.
     * This can not be changed by mods.
     *
     * @return The original nameplate content.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalContent")
    public static MCTextComponent getOriginalContent(RenderNameplateEvent internal) {
        
        return new MCTextComponent(internal.getOriginalContent());
    }
    
}
