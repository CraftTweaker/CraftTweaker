package com.blamejared.crafttweaker.natives.world.map;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/map/MapDecorationType")
@NativeTypeRegistration(value = MapDecoration.Type.class, zenCodeName = "crafttweaker.api.world.map.MapDecorationType")
@BracketEnum("minecraft:world/map/decorationtype")
public class ExpandMapDecorationType {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("icon")
    public static byte getIcon(MapDecoration.Type internal) {
        
        return internal.getIcon();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isRenderedOnFrame")
    public static boolean isRenderedOnFrame(MapDecoration.Type internal) {
        
        return internal.isRenderedOnFrame();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasMapColor")
    public static boolean hasMapColor(MapDecoration.Type internal) {
        
        return internal.hasMapColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("getMapColor")
    public static int getMapColor(MapDecoration.Type internal) {
        
        return internal.getMapColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldTrackCount")
    public static boolean shouldTrackCount(MapDecoration.Type internal) {
        
        return internal.shouldTrackCount();
    }
    
}
