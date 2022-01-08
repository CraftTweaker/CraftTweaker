package com.blamejared.crafttweaker.gametest.truth.subject.type.ingredient;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.gametest.truth.subject.type.vanilla.ItemStackSubject;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.Subject;
import javax.annotation.Nullable;

public class IItemStackSubject extends Subject {
    
    private static final Factory<IItemStackSubject, IItemStack> FACTORY = IItemStackSubject::new;
    
    private final IItemStack actual;
    
    protected IItemStackSubject(FailureMetadata metadata, @Nullable IItemStack actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    public IItemStack getActual() {
        
        return actual;
    }
    
    public ItemStackSubject internal() {
        
        return check("getInternal()").about(ItemStackSubject.factory()).that(getActual().getInternal());
    }
    
    public BooleanSubject isEmpty() {
        
        return check("isEmpty()").that(getActual().isEmpty());
    }
    
    public SimpleSubjectBuilder<IItemStackSubject, IItemStack> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<IItemStackSubject, IItemStack> factory() {
        
        return FACTORY;
    }
    
}
