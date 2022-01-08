package com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif;

public final class OnlyIfParameterHit {
    
    final boolean validArguments;
    final int numberOfConsumedArguments;
    final boolean conditionMet;
    
    private OnlyIfParameterHit(boolean validArguments, int numberOfConsumedArguments, boolean conditionMet) {
        
        this.validArguments = validArguments;
        this.numberOfConsumedArguments = numberOfConsumedArguments;
        this.conditionMet = conditionMet;
    }
    
    public static OnlyIfParameterHit invalid() {
        
        return new OnlyIfParameterHit(false, -1, false);
    }
    
    public static OnlyIfParameterHit conditionPassed(int numberOfConsumedArguments) {
        
        return new OnlyIfParameterHit(true, numberOfConsumedArguments, true);
    }
    
    public static OnlyIfParameterHit conditionFailed(int numberOfConsumedArguments) {
        
        return new OnlyIfParameterHit(true, numberOfConsumedArguments, false);
    }
    
}
