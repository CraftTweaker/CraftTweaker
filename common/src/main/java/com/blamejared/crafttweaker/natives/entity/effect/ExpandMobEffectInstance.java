package com.blamejared.crafttweaker.natives.entity.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/effect/MobEffectInstance")
@NativeTypeRegistration(value = MobEffectInstance.class, zenCodeName = "crafttweaker.api.entity.effect.MobEffectInstance", constructors = {
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>")
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", examples = "100"),
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", examples = "100"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "amplifier", examples = "2"),
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", examples = "100"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "amplifier", examples = "2"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "ambient", examples = "true"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "visible", examples = "false"),
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", examples = "100"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "amplifier", examples = "2"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "ambient", examples = "true"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "visible", examples = "false"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "showIcon", examples = "false"),
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "mobEffect", examples = "<mobeffect:minecraft:haste>"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", examples = "100"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "amplifier", examples = "2"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "ambient", examples = "true"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "visible", examples = "false"),
                @NativeConstructor.ConstructorParameter(type = boolean.class, name = "showIcon", examples = "false"),
                @NativeConstructor.ConstructorParameter(type = MobEffectInstance.class, name = "hiddenEffect", examples = "new MobEffectInstance(<mobeffect:minecraft:haste>, 200, 3)"),
        })
})
public class ExpandMobEffectInstance {
    
    @ZenCodeType.Method
    public static boolean update(MobEffectInstance internal, MobEffectInstance instance) {
        
        return internal.update(instance);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effect")
    public static MobEffect getEffect(MobEffectInstance internal) {
        
        return internal.getEffect();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("duration")
    public static int getDuration(MobEffectInstance internal) {
        
        return internal.getDuration();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("amplifier")
    public static int getAmplifier(MobEffectInstance internal) {
        
        return internal.getAmplifier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ambient")
    public static boolean isAmbient(MobEffectInstance internal) {
        
        return internal.isAmbient();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("visible")
    public static boolean isVisible(MobEffectInstance internal) {
        
        return internal.isVisible();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("showIcon")
    public static boolean showIcon(MobEffectInstance internal) {
        
        return internal.showIcon();
    }
    
    @ZenCodeType.Method
    public static boolean tick(MobEffectInstance internal, LivingEntity entity, @ZenCodeType.Optional("null") @ZenCodeType.Nullable Runnable onFinish) {
        
        if(onFinish == null) {
            onFinish = () -> {};
        }
        return internal.tick(entity, onFinish);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(MobEffectInstance internal) {
        
        return internal.getDescriptionId();
    }
    
    @ZenCodeType.Method
    public static MapData save(MobEffectInstance internal, @ZenCodeType.Optional @ZenCodeType.Nullable MapData data) {
        
        if(data == null) {
            data = new MapData();
        }
        return new MapData(internal.save(data.getInternal()));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectInstance load(MapData data) {
        
        return MobEffectInstance.load(data.getInternal());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.COMPARE)
    public static int compareTo(MobEffectInstance internal, MobEffectInstance other) {
        
        return internal.compareTo(other);
    }
    
}
