package com.blamejared.crafttweaker.gametest.api.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("test.api.TestEvent")
public class TestEvent extends Event {
    
    private final String testName;
    private final Level level;
    private final BlockPos pos;
    private final Player player;
    
    public TestEvent(String testName, Level level, BlockPos pos, Player player) {
        
        this.testName = testName;
        this.level = level;
        this.pos = pos;
        this.player = player;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("testName")
    public String testName() {
        
        return testName;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public Level level() {
        
        return level;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pos")
    public BlockPos pos() {
        
        return pos;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("innerPos")
    public BlockPos innerPos() {
        
        return pos().above(2);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public Player player() {
        
        return player;
    }
    
}
