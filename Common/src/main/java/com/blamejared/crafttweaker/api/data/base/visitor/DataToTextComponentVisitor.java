package com.blamejared.crafttweaker.api.data.base.visitor;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.google.common.base.Strings;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class DataToTextComponentVisitor implements DataVisitor<Component> {
    
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_KEY = ChatFormatting.AQUA;
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_STRING = ChatFormatting.GREEN;
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_QUOTE = ChatFormatting.GRAY;
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER = ChatFormatting.GOLD;
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_TYPE = ChatFormatting.RED;
    private static final ChatFormatting SYNTAX_HIGHLIGHTING_AS = ChatFormatting.GRAY;
    
    private static final Component KEYWORD_AS = new TextComponent(" as ").withStyle(SYNTAX_HIGHLIGHTING_AS);
    private static final Component MAP_EMPTY = new TextComponent("{}");
    private static final String MAP_OPEN = "{";
    private static final String MAP_CLOSE = "}";
    private static final Component LIST_EMPTY = new TextComponent("[]");
    private static final String LIST_OPEN = "[";
    private static final String LIST_CLOSE = "]";
    private static final String LIST_TYPE_SEPARATOR = ";";
    private static final int INLINE_LIST_THRESHOLD = 8;
    private static final ByteCollection INLINE_ELEMENT_TYPES = new ByteOpenHashSet(new byte[] {1, 2, 3, 4, 5, 6});
    private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    private static final String NAME_VALUE_SEPARATOR = ":";
    private static final String ELEMENT_SEPARATOR = ",";
    private static final String ELEMENT_SPACING = " ";
    private static final String NEWLINE = "\n";
    private final String indentation;
    private final int depth;
    
    public static final Map<IData.Type, Component> DATA_TO_COMPONENT = Util.make(new HashMap<>(), map -> {
        map.put(IData.Type.BOOL, new TextComponent("bool").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.BYTE_ARRAY, new TextComponent("byte[]").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.BYTE, new TextComponent("byte").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.DOUBLE, new TextComponent("double").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.FLOAT, new TextComponent("float").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.INT_ARRAY, new TextComponent("int[]").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.INT, new TextComponent("int").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.LONG_ARRAY, new TextComponent("long[]").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.LONG, new TextComponent("long").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.SHORT, new TextComponent("short").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        map.put(IData.Type.STRING, new TextComponent("string").withStyle(SYNTAX_HIGHLIGHTING_TYPE));
        
        // There is no simple string representation of these types (such as "as list" or "as map").
        map.put(IData.Type.LIST, TextComponent.EMPTY);
        map.put(IData.Type.MAP, TextComponent.EMPTY);
    });
    
    
    public DataToTextComponentVisitor(String indentation, int depth) {
        
        this.indentation = indentation;
        this.depth = depth;
    }
    
    private Component getComponent(IData data) {
        
        return DATA_TO_COMPONENT.get(data.getType());
    }
    
    private Component visitSimple(IData data) {
        
        return new TextComponent(data.accept(DataToJsonStringVisitor.INSTANCE))
                .append(KEYWORD_AS)
                .append(getComponent(data))
                .withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }
    
    private Component visitCollection(ICollectionData data) {
        
        MutableComponent component = new TextComponent(LIST_OPEN);
        
        for(int i = 0; i < data.size(); i++) {
            MutableComponent child = new TextComponent(data.getAt(i)
                    .accept(DataToJsonStringVisitor.INSTANCE))
                    .withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            component.append(i == 0 ? "" : ELEMENT_SPACING).append(child);
            if(i != data.size() - 1) {
                component.append(ELEMENT_SEPARATOR);
            }
        }
        
        component.append(LIST_CLOSE).append(KEYWORD_AS).append(getComponent(data));
        return component;
    }
    
    public Component visit(IData data) {
        
        return data.accept(this);
    }
    
    public Component visitBool(BoolData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitByteArray(ByteArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public Component visitByte(ByteData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitDouble(DoubleData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitFloat(FloatData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitIntArray(IntArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public Component visitInt(IntData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitList(ListData data) {
        
        if(data.isEmpty()) {
            return LIST_EMPTY;
        } else if(INLINE_ELEMENT_TYPES.contains(data.getInternal()
                .getElementType()) && data.size() <= INLINE_LIST_THRESHOLD) {
            String seperator = ELEMENT_SEPARATOR + ELEMENT_SPACING;
            MutableComponent component = new TextComponent(LIST_OPEN);
            
            for(int i = 0; i < data.size(); ++i) {
                if(i != 0) {
                    component.append(seperator);
                }
                
                component.append(new DataToTextComponentVisitor(this.indentation, this.depth).visit(data.getAt(i)));
            }
            
            component.append(LIST_CLOSE);
            return component;
        } else {
            MutableComponent component = new TextComponent(LIST_OPEN);
            if(!this.indentation.isEmpty()) {
                component.append(NEWLINE);
            }
            
            for(int i = 0; i < data.size(); ++i) {
                int nextDepth = this.depth + 1;
                MutableComponent child = new TextComponent(Strings.repeat(this.indentation, nextDepth));
                child.append(new DataToTextComponentVisitor(this.indentation, nextDepth).visit(data.getAt(i)));
                if(i != data.size() - 1) {
                    child.append(ELEMENT_SEPARATOR).append(this.indentation.isEmpty() ? ELEMENT_SPACING : NEWLINE);
                }
                
                component.append(child);
            }
            
            if(!this.indentation.isEmpty()) {
                component.append(NEWLINE).append(Strings.repeat(this.indentation, this.depth));
            }
            
            component.append(LIST_CLOSE);
            return component;
        }
    }
    
    
    @Override
    public Component visitLongArray(LongArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public Component visitLong(LongData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitMap(MapData data) {
        
        if(data.isEmpty()) {
            return MAP_EMPTY;
        }
        
        MutableComponent component = new TextComponent(MAP_OPEN);
        Collection<String> keys = data.getKeySet();
        
        if(!this.indentation.isEmpty()) {
            component.append(NEWLINE);
        }
        
        MutableComponent nextComponent;
        for(Iterator<String> iter = keys.iterator(); iter.hasNext(); component.append(nextComponent)) {
            String next = iter.next();
            int nextDepth = this.depth + 1;
            nextComponent = (new TextComponent(Strings.repeat(this.indentation, nextDepth)))
                    .append(handleEscapePretty(next))
                    .append(NAME_VALUE_SEPARATOR)
                    .append(ELEMENT_SPACING)
                    .append((new DataToTextComponentVisitor(this.indentation, nextDepth)).visit(data.getAt(next)));
            if(iter.hasNext()) {
                nextComponent.append(ELEMENT_SEPARATOR)
                        .append(this.indentation.isEmpty() ? ELEMENT_SPACING : NEWLINE);
            }
        }
        
        if(!this.indentation.isEmpty()) {
            component.append(NEWLINE).append(Strings.repeat(this.indentation, this.depth));
        }
        
        component.append(MAP_CLOSE);
        return component;
        
    }
    
    @Override
    public Component visitShort(ShortData data) {
        
        return visitSimple(data);
    }
    
    @Override
    public Component visitString(StringData data) {
        
        String str = data.accept(DataToJsonStringVisitor.INSTANCE);
        String quote = str.substring(0, 1);
        Component component = new TextComponent(str.substring(1, str.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_STRING);
        return new TextComponent(quote).withStyle(SYNTAX_HIGHLIGHTING_QUOTE)
                .append(component)
                .append(new TextComponent(quote).withStyle(SYNTAX_HIGHLIGHTING_QUOTE));
    }
    
    
    private Component handleEscapePretty(String str) {
        
        if(SIMPLE_VALUE.matcher(str).matches()) {
            return (new TextComponent(str)).withStyle(SYNTAX_HIGHLIGHTING_KEY);
        } else {
            String escapedStr = StringTag.quoteAndEscape(str);
            String quote = escapedStr.substring(0, 1);
            Component var2 = new TextComponent(escapedStr.substring(1, escapedStr.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_KEY);
            return (new TextComponent(quote)).append(var2).append(quote);
        }
    }
    
}
