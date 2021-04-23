package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.api.util.MethodHandleHelper;
import com.blamejared.crafttweaker.impl.tag.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class ActionTagCreate<T extends ForgeRegistryEntry<?>> extends ActionTag<T> {
    private static final MethodHandle ID_TAG_MAP_SETTER = link();
    
    private final ITagCollection<T> collection;
    
    public ActionTagCreate(ITagCollection<T> collection, ITag<T> tag, MCTag<?> theTag) {
        super(tag, theTag);
        this.collection = collection;
    }
    
    private static MethodHandle link() {
        try {
            final Class<?> type = Class.forName(ITagCollection.class.getName() + "$1");
            
            return Arrays.stream(type.getDeclaredFields())
                    .filter(it -> BiMap.class.isAssignableFrom(it.getType()))
                    .findFirst()
                    .map(Field::getName)
                    .map(it -> MethodHandleHelper.linkSetter(type, it))
                    .orElseThrow(NoSuchFieldException::new);
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException("Unable to identify field to link to", e);
        }
    }
    
    @Override
    public void apply() {
        getIdTagMap(collection).put(getId(), tag);
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(collection.get(getId()) != null) {
            logger.error(getType() + " Tag: " + mcTag + " already exists!");
            return false;
        }
        if(getIdTagMap(collection) instanceof ImmutableMap) {
            logger.error(getType() + " Tag Internal error: TagMap is " + collection.getIDTagMap().getClass().getCanonicalName());
            return false;
        }
        
        return true;
    }
    
    @Override
    public String describe() {
        return "Registering new " + getType() + " tag with name " + mcTag;
    }
    
    private Map<ResourceLocation, ITag<T>> getIdTagMap(final ITagCollection<T> collection) {
        Map<ResourceLocation, ITag<T>> map = collection.getIDTagMap();
        if (map instanceof ImmutableBiMap<?, ?>) {
            final BiMap<ResourceLocation, ITag<T>> newMap = HashBiMap.create(map);
            map = newMap;
            MethodHandleHelper.invokeVoid(() -> this.setIdTagMap(collection, newMap));
        }
        return map;
    }
    
    private void setIdTagMap(final ITagCollection<T> collection, final BiMap<ResourceLocation, ITag<T>> newMap) throws Throwable {
        // We are unable to use invokeExact due to the need of type conversion from ITagCollection to ITagCollection$1
        // Nevertheless, the performance is comparable to an invokeExact with around 1 nanosecond more due to type
        // conversion, effectively the same cost of a native invocation. And effectively, I doubt anyone would ever
        // add more than one million different tag managers
        ID_TAG_MAP_SETTER.invoke(collection, newMap);
    }
}
