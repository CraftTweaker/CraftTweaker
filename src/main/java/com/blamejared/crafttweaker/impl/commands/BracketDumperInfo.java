package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BracketDumperInfo implements CTCommands.CommandCallerPlayer {
    
    private final String bepHandlerName;
    private final String dumpedFileName;
    private final String subCommandName;
    private final List<MethodHandle> methodHandles = new ArrayList<>(1);
    
    
    public BracketDumperInfo(String bepHandlerName, String subCommandName, String dumpedFileName) {
        
        this.bepHandlerName = bepHandlerName;
        this.subCommandName = subCommandName.isEmpty() ? makePlural(bepHandlerName) : subCommandName;
        this.dumpedFileName = dumpedFileName.isEmpty() ? bepHandlerName.toLowerCase() : dumpedFileName;
    }
    
    private static String makePlural(String s) {
        
        if(s.endsWith("s")) {
            return s;
        } else if(s.endsWith("x")) {
            return s;
        } else {
            return s + "s";
        }
    }
    
    public void addMethodHandle(MethodHandle methodHandle) {
        
        this.methodHandles.add(methodHandle);
    }
    
    public String getSubCommandName() {
        
        return subCommandName;
    }
    
    public String getDescription() {
        
        return String.format("Outputs a list of all known '%s' BEPs", bepHandlerName);
    }
    
    public String getDumpedFileName() {
        
        return dumpedFileName;
    }
    
    public Stream<String> getDumpedValuesStream() {
        
        return methodHandles.stream().flatMap(methodHandle -> {
            try {
                final Collection<String> invoke = (Collection<String>) methodHandle.invokeExact();
                return invoke.stream();
            } catch(Throwable throwable) {
                CraftTweakerAPI.logThrowing("Error executing Bracket dumper '%s'", throwable, bepHandlerName);
                return Stream.empty();
            }
        });
    }
    
    @Override
    public int executeCommand(PlayerEntity player, ItemStack stack) {
        
        getDumpedValuesStream().forEach(bepCall -> CraftTweakerAPI.logDump("- " + bepCall));
        
        final String message = String.format("List of '%s' BEPs generated! Check the crafttweaker.log file!", bepHandlerName);
        CTCommands.send(new StringTextComponent(CTCommands.color(message, TextFormatting.GREEN)), player);
        return 0;
    }
    
}
