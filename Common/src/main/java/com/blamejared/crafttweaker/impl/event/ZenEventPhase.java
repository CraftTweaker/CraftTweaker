package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

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
