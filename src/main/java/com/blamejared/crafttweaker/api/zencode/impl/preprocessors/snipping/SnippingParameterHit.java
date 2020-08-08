package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

public final class SnippingParameterHit {
    final boolean validArguments;
    final int numberOfConsumedArguments;
    final boolean shouldSnip;
    
    private SnippingParameterHit(boolean validArguments, int numberOfConsumedArguments, boolean shouldSnip) {
        this.validArguments = validArguments;
        this.numberOfConsumedArguments = numberOfConsumedArguments;
        this.shouldSnip = shouldSnip;
    }
    
    public static SnippingParameterHit invalid() {
        return new SnippingParameterHit(false, -1, false);
    }
    
    public static SnippingParameterHit snip(int numberOfConsumedArguments) {
        return new SnippingParameterHit(true, numberOfConsumedArguments, true);
    }
    
    public static SnippingParameterHit noSnip(int numberOfConsumedArguments) {
        return new SnippingParameterHit(true, numberOfConsumedArguments, false);
    }
}
