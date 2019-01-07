package com.crafttweaker.crafttweaker.main.brackets;

import com.crafttweaker.crafttweaker.api.liquids.MCFluidStack;
import com.crafttweaker.crafttweaker.main.items.MCItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.JavaNativeModule;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.parser.*;

import java.util.*;

public class BracketExpressionParsers {
    
    public static Map<String, BracketExpressionParser> collectParsers(GlobalTypeRegistry registry, JavaNativeModule module) {
        Map<String, BracketExpressionParser> out = new HashMap<>();
        try {
            out.put("item", new SimpleBracketParser(registry, module.loadStaticMethod(BracketExpressionParsers.class.getMethod("createItemStack", String.class))));
            {
                final SimpleBracketParser fluidParser = new SimpleBracketParser(registry, module.loadStaticMethod(BracketExpressionParsers.class.getMethod("createFluidStack", String.class)));
                out.put("fluid", fluidParser);
                out.put("liquid", fluidParser);
            }
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    
    public static MCItemStack createItemStack(String input) {
        //TODO actually parse this ^^
        return new MCItemStack(new ItemStack(Items.DIAMOND));
    }
    
    public static MCFluidStack createFluidStack(String input) {
        //TODO actually parse this or even return any fluid
        return new MCFluidStack(null);
    }
}
