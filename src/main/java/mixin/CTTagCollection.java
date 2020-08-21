package mixin;

import com.google.common.collect.BiMap;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public class CTTagCollection<T> implements ITagCollection<T> {
    private final BiMap<ResourceLocation, ITag<T>> bimap;
    
    public CTTagCollection(BiMap<ResourceLocation, ITag<T>> bimap) {
        this.bimap = bimap;
    }
    
    public ITag<T> func_241834_b(ResourceLocation p_241834_1_) {
        return null;//bimap.getOrDefault(p_241834_1_, this.field_242207_b);
    }
    
    @Nullable
    public ResourceLocation func_232973_a_(ITag<T> p_232973_1_) {
        return null;//p_232973_1_ instanceof ITag.INamedTag ? ((ITag.INamedTag) p_232973_1_).getName() : bimap.inverse().get(p_232973_1_);
    }
    
    public Map<ResourceLocation, ITag<T>> func_241833_a() {
        return null;//bimap;
    }
}
