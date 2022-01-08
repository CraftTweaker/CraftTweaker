package com.blamejared.crafttweaker.gametest.truth.subject.type.ingredient;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.Subject;
import javax.annotation.Nullable;

public class IIngredientSubject extends Subject {
    
    private static final Factory<IIngredientSubject, IIngredient> FACTORY = IIngredientSubject::new;
    
    private final IIngredient actual;
    
    protected IIngredientSubject(FailureMetadata metadata, @Nullable IIngredient actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    public IIngredient getActual() {
        
        return actual;
    }
    
    public BooleanSubject isEmpty() {
        
        return check("isEmpty()").that(getActual().equals(IIngredientEmpty.getInstance()));
    }
    
    public SimpleSubjectBuilder<IIngredientSubject, IIngredient> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<IIngredientSubject, IIngredient> factory() {
        
        return FACTORY;
    }
    
}
