package crafttweaker.mc1120.commands.dumpzscommand;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.zenscript.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.Pair;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getLinkToCraftTweakerLog;

public class TargetLog extends DumpZsTarget {
    
    public TargetLog() {
        super("log");
    }
    
    @Override
    public String getDescription() {
        return "Dumps to the log";
    }
    
    @Override
    public void execute(ICommandSender sender, MinecraftServer Server) {
        CraftTweakerAPI.logCommand("\nBracket Handlers:");
        for(Pair<Integer, IBracketHandler> pair : GlobalRegistry.getPrioritizedBracketHandlers()) {
            CraftTweakerAPI.logCommand(pair.getValue().getClass().getName() + ", priority: " + pair.getKey());
        }
        
        CraftTweakerAPI.logCommand("\nTypes:");
        GlobalRegistry.getTypes().getTypeMap().forEach((aClass, zenType) -> CraftTweakerAPI.logCommand(aClass.getName() + ": " + zenType.getName()));
        
        CraftTweakerAPI.logCommand("\nGlobals:");
        GlobalRegistry.getGlobals().forEach((s, iZenSymbol) -> CraftTweakerAPI.logCommand(s + ": " + iZenSymbol.toString()));
        
        CraftTweakerAPI.logCommand("\nExpansions:");
        GlobalRegistry.getExpansions().forEach((s, typeExpansion) -> CraftTweakerAPI.logCommand(s + ": " + typeExpansion.toString()));
        
        CraftTweakerAPI.logCommand("\nRoot (Symbol Package):");
        GlobalRegistry.getRoot().getPackages().forEach(this::printZenSymbol);
        
        sender.sendMessage(getLinkToCraftTweakerLog("Dumped content of the GlobalRegistry", sender));
    }
    
    /**
     * Recursivly prints all zenSymbols if they are Symbol Packages
     */
    private void printZenSymbol(String s, IZenSymbol zenSymbol) {
        if(zenSymbol instanceof SymbolPackage) {
            printZenSymbolHelper(zenSymbol, 0);
        } else {
            CraftTweakerAPI.logCommand(s + ": " + zenSymbol.toString());
        }
    }
    
    /**
     * Helper functions for printing the zenSymbols
     *
     * @param zenSymbol
     * @param depth
     */
    private void printZenSymbolHelper(IZenSymbol zenSymbol, final int depth) {
        int finalDepth = depth + 1;
        
        if(zenSymbol instanceof SymbolPackage) {
            SymbolPackage symbolPackage = (SymbolPackage) zenSymbol;
            
            symbolPackage.getPackages().forEach((s1, symbol) -> {
                CraftTweakerAPI.logCommand(StringUtils.repeat("\t", finalDepth) + s1 + ": " + symbol.toString());
                printZenSymbolHelper(symbol, finalDepth);
            });
        } else if(zenSymbol instanceof SymbolType || zenSymbol instanceof ZenTypeNative) {
            ZenTypeNative typeNative = null;
            if(zenSymbol instanceof SymbolType) {
                ZenType type = ((SymbolType) zenSymbol).getType();
                if(type instanceof ZenTypeNative) {
                    typeNative = (ZenTypeNative) type;
                }
            } else {
                typeNative = (ZenTypeNative) zenSymbol;
            }
            
            if(typeNative != null) {
                for(String s : typeNative.dumpTypeInfo()) {
                    CraftTweakerAPI.logCommand(StringUtils.repeat("\t", finalDepth) + s);
                }
            }
        }
    }
    
}
