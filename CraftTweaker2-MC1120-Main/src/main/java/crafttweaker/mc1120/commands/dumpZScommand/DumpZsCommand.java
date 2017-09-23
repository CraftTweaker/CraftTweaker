package crafttweaker.mc1120.commands.dumpZScommand;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.zenscript.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.StringUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class DumpZsCommand extends CraftTweakerCommand {
    private final String HTML_HEADER = "<!DOCTYPE html>\n"
            + "<head><link rel=\"stylesheet\" href=\"tree3.css\"></head>";
    
    private final String HTML_BODY_START = "<p>CraftTweaker ZenScript language, refer to <a href=\"http://crafttweaker.readthedocs.io/\" target=\"_blank\">this page</a> for more help.</p>";
    
    private final String HTML_DIV_START = "<div class=\"css-treeview\"><ul>";
    
    private final String HTML_DIV_END = "</ul></div>";
    
    private final String HTML_BODY_END = "<p>This File was created using the dumpzs command.</p>";
    
    
    public DumpZsCommand() {
        super("dumpzs");
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        TreeNode<String> root = new TreeNode<>("root");
        
        File zsDataFolder = new File("crtdata");
        if(!zsDataFolder.exists()) {
            zsDataFolder.mkdirs();
        }
        
        URL inputUrlPNG = getClass().getResource("/assets/crafttweaker/icons.png");
        try {
            FileUtils.copyURLToFile(inputUrlPNG, new File("crtdata/icons.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        URL inputUrlCSS = getClass().getResource("/assets/crafttweaker/tree3.css");
        try {
            FileUtils.copyURLToFile(inputUrlCSS, new File("crtdata/tree3.css"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        TreeNode<String> bracketNode = root.addChild("Bracket Handlers");
        for(IBracketHandler iBracketHandler : GlobalRegistry.getBracketHandlers()) {
            bracketNode.addChild(iBracketHandler.getClass().getName());
        }
        
        TreeNode<String> types = root.addChild("Types");
        GlobalRegistry.getTypes().getTypeMap().forEach((aClass, zenType) -> {
            types.addChild(aClass.getName() + ": " + zenType.getName());
        });
    
        TreeNode<String> globals = root.addChild("Globals");
        GlobalRegistry.getGlobals().forEach((s, iZenSymbol) -> globals.addChild(s + ": " + iZenSymbol.toString()));
        
        TreeNode<String> expansions = root.addChild("Expansions:");
        GlobalRegistry.getExpansions().forEach((s, typeExpansion) -> expansions.addChild(s + ": " + typeExpansion.toString()));
        
        CraftTweakerAPI.logCommand("\nRoot (Symbol Package):");
        GlobalRegistry.getRoot().getPackages().forEach(this::printZenSymbol);
    
    
    
        try {
            List<String> lines = Arrays.asList(
                    HTML_HEADER,
                    HTML_BODY_START,
                    HTML_DIV_START,
                    convertToFlatHTMLStrings(root),
                    HTML_DIV_END,
                    HTML_BODY_END
            );
            
            Path file = Paths.get("crtdata/tree3.html");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        
        
        
        sender.sendMessage(getLinkToCraftTweakerLog("Dumped content of the GlobalRegistry", sender));
        
    }
    
    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct dumpzs", "/ct dumpzs", true),
                getNormalMessage(" \u00A73Dumps the whole ZenScript Registry to the crafttweaker log"));
    }
    
    private int itemCounter = 0;
    private String convertToFlatHTMLStrings(TreeNode<String> root){
        itemCounter = 0;
        StringBuilder sb = new StringBuilder();
    
        for(TreeNode<String> child : root.children) {
            sb.append(createListBodyString(child));
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    // <li><input type="checkbox" id="item-0-0" /><label for="item-0-0">Ooops! A Nested Folder</label><ul> .......... </ul></li>
    private String createListBodyString(TreeNode<String> node){
        if (node.isLeaf()){
            return createLeaveString(node);
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append("<li><input type=\"checkbox\" id=\"item-").append(itemCounter).append("\" /><label for=\"item-").append(itemCounter).append("\">").append(node.data).append("</label><ul>");
            itemCounter++;
            for(TreeNode<String> child : node.children) {
                sb.append(createListBodyString(child));
            }
            sb.append("</ul></li>");
            
            return sb.toString();
        }
    }
    
    private String createLeaveString(TreeNode<String> node){
        if (node.isLeaf()){
            return "<li>" + node.data + "</li>";
        }
        
        return "";
    }
    
    /**
     * Recursivly prints all zenSymbols if they are Symbol Packages
     */
    private void printZenSymbol(String s, IZenSymbol zenSymbol){
        if (zenSymbol instanceof SymbolPackage){
            printZenSymbolHelper(zenSymbol, 0);
        } else {
            CraftTweakerAPI.logCommand(s + ": " + zenSymbol.toString());
        }
    }
    
    /**
     * Helper functions for printing the zenSymbols
     * @param zenSymbol
     * @param depth
     */
    private void printZenSymbolHelper(IZenSymbol zenSymbol, final int depth){
        int finalDepth = depth + 1;
        
        if (zenSymbol instanceof SymbolPackage){
            SymbolPackage symbolPackage = (SymbolPackage) zenSymbol;
            
            symbolPackage.getPackages().forEach((s1, symbol) -> {
                CraftTweakerAPI.logCommand(StringUtils.repeat("\t", finalDepth) + s1 + ": " + symbol.toString());
                printZenSymbolHelper(symbol, finalDepth);
            });
        } else if (zenSymbol instanceof SymbolType || zenSymbol instanceof ZenTypeNative){
            ZenTypeNative typeNative = null;
            if (zenSymbol instanceof SymbolType){
                ZenType type = ((SymbolType) zenSymbol).getType();
                if (type instanceof ZenTypeNative){
                    typeNative = (ZenTypeNative) type;
                }
            }else {
                typeNative = (ZenTypeNative) zenSymbol;
            }
            
            if (typeNative != null){
                for(String s : typeNative.dumpTypeInfo()) {
                    CraftTweakerAPI.logCommand(StringUtils.repeat("\t", finalDepth) + s);
                }
            }
        }
    }

}
