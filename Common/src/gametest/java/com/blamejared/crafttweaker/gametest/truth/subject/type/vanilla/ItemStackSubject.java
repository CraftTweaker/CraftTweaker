package com.blamejared.crafttweaker.gametest.truth.subject.type.vanilla;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.Subject;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

public class ItemStackSubject extends Subject {
    
    private static final Factory<ItemStackSubject, ItemStack> FACTORY = ItemStackSubject::new;
    
    private final ItemStack actual;
    
    protected ItemStackSubject(FailureMetadata metadata, @Nullable ItemStack actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    public ItemStack getActual() {
        
        return actual;
    }
    
    public BooleanSubject isEmpty() {
        
        return check("isEmpty()").that(getActual().isEmpty());
    }
    
    public SimpleSubjectBuilder<ItemStackSubject, ItemStack> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<ItemStackSubject, ItemStack> factory() {
        
        return FACTORY;
    }
    
}
