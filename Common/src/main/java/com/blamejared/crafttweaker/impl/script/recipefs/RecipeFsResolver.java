package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.impl.script.ScriptRecipe;

import java.io.IOException;

@FunctionalInterface
interface RecipeFsResolver {
    
    @FunctionalInterface
    interface Bound {
        
        ScriptRecipe resolve() throws IOException;
        
        default String resolveContents() throws IOException {
            
            return this.resolve().getContent();
        }
        
    }
    
    ScriptRecipe resolve(final RecipePath path) throws IOException;
    
    default String resolveContents(final RecipePath path) throws IOException {
        
        return this.resolve(path).getContent();
    }
    
    default Bound bind(final RecipePath path) {
        
        return () -> this.resolve(path);
    }
    
}
