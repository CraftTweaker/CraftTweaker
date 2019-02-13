package crafttweaker.mc1120.commands.dumpzscommand;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.zenscript.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import stanhebben.zenscript.dump.TreeNode;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.Pair;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getFileOpenText;

public class TargetHtml extends DumpZsTarget {
    
    private static final String HTML_HEADER = "<!DOCTYPE html>\n" + "<head><link rel=\"stylesheet\" href=\"tree3.css\">" + " <title>CraftTweaker: ZenScript Language Dump</title>" + "</head>";
    
    private static final String HTML_BODY_START = "<p>CraftTweaker ZenScript language, refer to <a href=\"http://crafttweaker.readthedocs.io/\" target=\"_blank\">this page</a> for more help.</p>";
    
    private static final String HTML_DIV_START = "<div class=\"css-treeview\"><ul>";
    
    private static final String HTML_DIV_END = "</ul></div>";
    
    private static final String HTML_BODY_END = "<p>This File was created using the dumpzs command.</p>";
    
    private static final Comparator<TreeNode<String>> STRING_TREE_COMPARATOR = (o1, o2) -> o1.data.compareToIgnoreCase(o2.data);
    
    
    public TargetHtml() {
        super("html");
    }
    
    @Override
    public String getDescription() {
        return "Dumps to a local HTML file";
    }
    
    @Override
    public void execute(ICommandSender sender, MinecraftServer Server) {
        TreeNode<String> root = new TreeNode<>("root");
        File zsDataFolder;
        //CraftTweaker.LOG.info("args = " + Arrays.toString(args));
        //if (args == null || args.length <= 0){
        zsDataFolder = new File("crafttweaker_dump");
        //}else {
        //    zsDataFolder = new File(Strings.join(args, " "));
        //}
        
        if(!zsDataFolder.exists()) {
            zsDataFolder.mkdirs();
        }
        
        URL inputUrlPNG = getClass().getResource("/assets/crafttweaker/icons.png");
        try {
            FileUtils.copyURLToFile(inputUrlPNG, new File(zsDataFolder, "icons.png"));
        } catch(IOException e) {
            CraftTweaker.LOG.catching(e);
        }
        
        URL inputUrlCSS = getClass().getResource("/assets/crafttweaker/tree3.css");
        try {
            FileUtils.copyURLToFile(inputUrlCSS, new File(zsDataFolder, "tree3.css"));
        } catch(IOException e) {
            CraftTweaker.LOG.catching(e);
        }
        
        TreeNode<String> bracketNode = root.addChild("Bracket Handlers");
        for(Pair<Integer, IBracketHandler> pair : GlobalRegistry.getPrioritizedBracketHandlers()) {
            bracketNode.addChild(pair.getValue().getClass().getName() + ", priority: " + pair.getKey());
        }
        
        TreeNode<String> types = root.addChild("Types");
        GlobalRegistry.getTypes().getTypeMap().forEach((aClass, zenType) -> {
            TreeNode<String> zsType = types.addChild(zenType.getName());
            zsType.addChild(aClass.getName());
        });
        types.children.sort(STRING_TREE_COMPARATOR);
        
        TreeNode<String> globals = root.addChild("Globals");
        GlobalRegistry.getGlobals().forEach((s, iZenSymbol) -> {
            TreeNode<String> globalName = globals.addChild(s);
            globalName.addChild(iZenSymbol.toString());
        });
        globals.children.sort(STRING_TREE_COMPARATOR);
        
        
        TreeNode<String> expansions = root.addChild("Expansions");
        GlobalRegistry.getExpansions().forEach((s, typeExpansion) -> {
            TreeNode<String> exp = expansions.addChild(s);
            exp.addChild(typeExpansion.getClass().getName());
        });
        expansions.children.sort(STRING_TREE_COMPARATOR);
        
        TreeNode<String> symbols = root.addChild("Root (Symbol Package)");
        GlobalRegistry.getRoot().getPackages().forEach((s, zenSymbol) -> printZenSymbol(s, zenSymbol, symbols));
        sortTreeNodes(symbols);
        
        
        try {
            List<String> lines = Arrays.asList(HTML_HEADER, HTML_BODY_START, HTML_DIV_START, convertToFlatHTMLStrings(root), HTML_DIV_END, HTML_BODY_END);
            
            Path file = Paths.get(zsDataFolder.getAbsolutePath() + "/tree3.html");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch(IOException e) {
            CraftTweaker.LOG.catching(e);
        }
        //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        
        sender.sendMessage(getFileOpenText("Dumped content of the GlobalRegistry to a html file \u00A7r[\u00A76Click here to open\u00A7r]", zsDataFolder.getAbsolutePath() + "/tree3.html"));
    }
    
    private void sortTreeNodes(TreeNode<String> parentNode) {
        if(parentNode.isLeaf())
            return;
        parentNode.children.sort(STRING_TREE_COMPARATOR);
        
        for(TreeNode<String> child : parentNode.children) {
            sortTreeNodes(child);
        }
    }
    
    private int itemCounter = 0;
    
    private String convertToFlatHTMLStrings(TreeNode<String> root) {
        itemCounter = 0;
        StringBuilder sb = new StringBuilder();
        
        for(TreeNode<String> child : root.children) {
            sb.append(createListBodyString(child));
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    // <li><input type="checkbox" id="item-0-0" /><label for="item-0-0">Ooops! A Nested Folder</label><ul> .......... </ul></li>
    private String createListBodyString(TreeNode<String> node) {
        if(node.isLeaf()) {
            return createLeaveString(node);
        } else {
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
    
    private String createLeaveString(TreeNode<String> node) {
        if(node.isLeaf()) {
            return "<li>" + node.data + "</li>";
        }
        
        return "";
    }
    
    /**
     * Recursivly prints all zenSymbols if they are Symbol Packages
     */
    private void printZenSymbol(String s, IZenSymbol zenSymbol, TreeNode<String> node) {
        if(zenSymbol instanceof SymbolPackage) {
            printZenSymbolHelper(zenSymbol, node);
        } else {
            TreeNode<String> n = node.addChild(s);
            n.addChild(zenSymbol.toString());
        }
    }
    
    /**
     * Helper functions for printing the zenSymbols
     *
     * @param zenSymbol
     */
    private void printZenSymbolHelper(IZenSymbol zenSymbol, TreeNode<String> node) {
        if(zenSymbol instanceof SymbolPackage) {
            SymbolPackage symbolPackage = (SymbolPackage) zenSymbol;
            
            symbolPackage.getPackages().forEach((s1, symbol) -> {
                
                TreeNode<String> childNode = addToTree(symbol, node);
                printZenSymbolHelper(symbol, childNode);
                
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
                addTypeNativeToTree(typeNative, node);
            }
        }
    }
    
    public void addTypeNativeToTree(ZenTypeNative typeNative, TreeNode<String> node) {
        typeNative.getMembers().forEach((s, zenNativeMember) -> {
            TreeNode<String> chNode = node.addChild(s);
            
            for(IJavaMethod iJavaMethod : zenNativeMember.getMethods()) {
                TreeNode<String> n = chNode.addChild("Java Method");
                n.addChild(methodToString(iJavaMethod));
            }
            
            if(zenNativeMember.getGetter() != null) {
                TreeNode<String> n = chNode.addChild("Getter");
                n.addChild(methodToString(zenNativeMember.getGetter()));
            }
            if(zenNativeMember.getSetter() != null) {
                TreeNode<String> n = chNode.addChild("Setter");
                n.addChild(methodToString(zenNativeMember.getSetter()));
            }
        });
        
        typeNative.getStaticMembers().forEach((s, zenNativeMember) -> {
            TreeNode<String> chNode = node.addChild("[STATIC] " + s);
            
            for(IJavaMethod iJavaMethod : zenNativeMember.getMethods()) {
                TreeNode<String> n = chNode.addChild("Java Method");
                n.addChild(methodToString(iJavaMethod));
            }
            
            if(zenNativeMember.getGetter() != null) {
                TreeNode<String> n = chNode.addChild("Getter");
                n.addChild(methodToString(zenNativeMember.getGetter()));
            }
            if(zenNativeMember.getSetter() != null) {
                TreeNode<String> n = chNode.addChild("Setter");
                n.addChild(methodToString(zenNativeMember.getSetter()));
            }
        });
    }
    
    
    private String methodToString(IJavaMethod javaMethod) {
        if(javaMethod == null)
            return "";
        
        if(javaMethod instanceof JavaMethod) {
            Method jm = ((JavaMethod) javaMethod).getMethod();
            StringBuilder sb = new StringBuilder();
            
            ZenDoc[] doc = jm.getAnnotationsByType(ZenDoc.class);
            if(doc.length > 0) {
                sb.append(encaplseInSpan("########", "green"));
                
                for(ZenDoc zenDoc : doc) {
                    sb.append("<br/>");
                    sb.append(encaplseInSpan(zenDoc.value(), "green"));
                }
                
                sb.append(encaplseInSpan("<br/>########<br/>", "green"));
            }
            
            sb.append(Modifier.toString(jm.getModifiers()));
            
            sb.append(" ");
            sb.append(createHoverText(jm.getReturnType().getName(), jm.getReturnType().getSimpleName()));
            
            sb.append(" ");
            sb.append(encaplseInSpan(jm.getName(), "red"));
            sb.append("(");
            
            Class<?>[] paras = jm.getParameterTypes();
            Annotation[][] annos = jm.getParameterAnnotations();
            for(int i = 0; i < paras.length; i++) {
                
                for(int j = 0; j < annos[i].length; j++) {
                    sb.append(encaplseInSpan("@" + annos[i][j].annotationType().getSimpleName() + " ", "gold"));
                }
                
                sb.append(createHoverText(paras[i].getName(), paras[i].getSimpleName()));
                if(i != paras.length - 1) {
                    sb.append(", ");
                }
            }
            
            sb.append(")");
            
            return sb.toString();
        } else {
            return javaMethod.toString();
        }
        
    }
    
    private String colorClassName(String name) {
        int lastDot = name.lastIndexOf(".");
        if(lastDot > 0) {
            return name.substring(0, lastDot + 1) + encaplseInSpan(name.substring(lastDot + 1, name.length()), "DarkOrange");
        } else {
            return encaplseInSpan(name, "DarkOrange");
        }
        
    }
    
    private String encaplseInSpan(String value, String color) {
        return "<span style=\"color:" + color + "\">" + value + "</span>";
    }
    
    private String createHoverText(String hoverText, String mainText) {
        return "<span title=\"" + hoverText + "\">" + mainText + "</span>";
    }
    
    private TreeNode<String> addToTree(IZenSymbol symbol, TreeNode<String> node) {
        String name;
        if(symbol instanceof SymbolPackage) {
            // name = "(SP) " + ((SymbolPackage) symbol).getName();
            name = null; // ignoring SymbolPackages as only Symbol Types are what we want
        } else if(symbol instanceof SymbolType) {
            name = ((SymbolType) symbol).getType().getName();
        } else {
            name = symbol.toString();
        }
        
        if(name != null) {
            String[] split = name.split("\\.");
            
            for(String s : split) {
                TreeNode<String> childNode = node.findTreeNode(s);
                if(childNode == null) {
                    childNode = node.addChild(s);
                }
                
                node = childNode;
            }
        }
        return node;
    }
}
