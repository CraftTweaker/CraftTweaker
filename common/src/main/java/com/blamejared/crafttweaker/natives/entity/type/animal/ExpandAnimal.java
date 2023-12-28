package com.blamejared.crafttweaker.natives.entity.type.animal;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/animal/Animal")
@NativeTypeRegistration(value = Animal.class, zenCodeName = "crafttweaker.api.entity.type.animal.Animal")
public class ExpandAnimal {

    @ZenCodeType.Method
    public static boolean isFood(Animal internal, ItemStack stack) {
        return internal.isFood(stack);
    }

    @ZenCodeType.Getter("canFAllInLove")
    public static boolean canFallInLove(Animal internal) {
        return internal.canFallInLove();
    }

    @ZenCodeType.Method
    public static void setInLove(Animal internal, @ZenCodeType.Optional @ZenCodeType.Nullable Player loveCause) {
        internal.setInLove(loveCause);
    }

    @ZenCodeType.Setter("inLoveTime")
    public static void setInLoveTime(Animal internal, int inLove) {
        internal.setInLoveTime(inLove);
    }

    @ZenCodeType.Getter("inLoveTime")
    public static int getInLoveTime(Animal internal) {
        return internal.getInLoveTime();
    }

    @ZenCodeType.Nullable
    @ZenCodeType.Getter("loveCause")
    public static ServerPlayer getLoveCause(Animal internal) {
        return internal.getLoveCause();
    }

    @ZenCodeType.Getter("isInLove")
    public static boolean isInLove(Animal internal) {
        return internal.isInLove();
    }

    @ZenCodeType.Method
    public static void resetLove(Animal internal) {
        internal.resetLove();
    }

    @ZenCodeType.Method
    public static boolean canMate(Animal internal, Animal other) {
        return internal.canMate(other);
    }

    @ZenCodeType.Method
    public static void spawnChildFromBreeding(Animal internal, ServerLevel level, Animal otherParent) {
        internal.spawnChildFromBreeding(level, otherParent);
    }

    @ZenCodeType.Method
    public static void finalizeSpawnChildFromBreeding(Animal internal, ServerLevel level, Animal otherParent, @ZenCodeType.Nullable AgeableMob child) {
        internal.finalizeSpawnChildFromBreeding(level, otherParent, child);
    }

}
