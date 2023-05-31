package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a phase in which an event is fired and can be listened to.
 *
 * <p>Various events might have different meanings for phases, so do check the documentation. Usually, you want to use
 * the {@code NORMAL} phase (which is specified for you automatically) to catch events occurring in the default
 * phase.</p>
 *
 * @since 11.0.0
 */
@ZenRegister
@Document("vanilla/api/event/EventPhase")
@ZenCodeType.Name("crafttweaker.api.events.EventPhase")
@BracketEnum("eventphase")
public enum ZenEventPhase {
    EARLIEST(Phase.EARLIEST),
    NORMAL(Phase.NORMAL),
    LATEST(Phase.LATEST);
    
    private final Phase phase;
    
    ZenEventPhase(final Phase phase) {
        this.phase = phase;
    }
    
    Phase phase() {
        return this.phase;
    }
}
