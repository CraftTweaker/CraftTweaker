package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

@SuppressWarnings("ClassCanBeRecord")
public final class OnlyIfParameterHit {
    
    private final boolean validArguments;
    private final int numberOfConsumedArguments;
    private final boolean conditionMet;
    
    private OnlyIfParameterHit(boolean validArguments, int numberOfConsumedArguments, boolean conditionMet) {
        
        this.validArguments = validArguments;
        this.numberOfConsumedArguments = numberOfConsumedArguments;
        this.conditionMet = conditionMet;
    }
    
    public static OnlyIfParameterHit invalid() {
        
        return new OnlyIfParameterHit(false, -1, false);
    }
    
    public static OnlyIfParameterHit conditionPassed(final int numberOfConsumedArguments) {
        
        return new OnlyIfParameterHit(true, numberOfConsumedArguments, true);
    }
    
    public static OnlyIfParameterHit conditionFailed(final int numberOfConsumedArguments) {
        
        return new OnlyIfParameterHit(true, numberOfConsumedArguments, false);
    }
    
    public static OnlyIfParameterHit basedOn(final boolean conditionMet, final int numberOfConsumedArguments) {
        
        return conditionMet ? conditionPassed(numberOfConsumedArguments) : conditionFailed(numberOfConsumedArguments);
    }
    
    public boolean validArguments() {
        
        return this.validArguments;
    }
    
    public int numberOfConsumedArguments() {
        
        return this.numberOfConsumedArguments;
    }
    
    public boolean conditionMet() {
        
        return this.conditionMet;
    }
    
}
