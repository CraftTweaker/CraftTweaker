package com.blamejared.crafttweaker.api.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Data that is saved and loaded with the world when it is saved and loaded from disk.
 *
 * <p>Saved Data is only on the server side, so you can only get it from a {@link com.blamejared.crafttweaker.natives.world.ExpandServerLevel}.</p>
 *
 * @docParam this level.customData
 */
@ZenRegister
@Document("vanilla/api/world/CraftTweakerSavedData")
@ZenCodeType.Name("crafttweaker.api.world.CraftTweakerSavedData")
public class CraftTweakerSavedData extends SavedData {
    
    private MapData data;
    
    public CraftTweakerSavedData() {
        
        this.data = new MapData();
    }
    
    public CraftTweakerSavedData(@Nonnull MapData data) {
        
        this.data = data;
    }
    
    public static CraftTweakerSavedData load(CompoundTag tag) {
        
        CompoundTag dataTag = tag.getCompound("data");
        Set<String> booleanKeys = tag.getList("booleanKeys", Tag.TAG_STRING)
                .stream()
                .map(Tag::getAsString)
                .collect(Collectors.toSet());
        
        return new CraftTweakerSavedData(new MapData(dataTag, booleanKeys));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public IData getData() {
        
        return data;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("data")
    public void setData(MapData data) {
        
        this.data = data;
    }
    
    @ZenCodeType.Method
    public void updateData(IData data) {
        
        this.data = (MapData) this.data.merge(data);
    }
    
    @Override
    public @NotNull CompoundTag save(CompoundTag tag) {
        
        ListTag booleanKeys = new ListTag();
        data.boolDataKeys().stream().map(StringTag::valueOf).forEach(booleanKeys::add);
        
        tag.put("data", data.getInternal());
        tag.put("booleanKeys", booleanKeys);
        return tag;
    }
    
    @Override
    public boolean isDirty() {
        
        return true;
    }
    
}
