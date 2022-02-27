package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Preprocessor
public class PriorityPreprocessor implements IPreprocessor {
    
    public static final PriorityPreprocessor INSTANCE = new PriorityPreprocessor();
    
    private PriorityPreprocessor() {}
    
    
    @Override
    public String getName() {
        
        return "priority";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return "0";
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        if(preprocessorMatches.size() > 1) {
            CraftTweakerAPI.LOGGER.warn("There are more than one #priority preprocessors in the file {}", file.getFileName());
        }
        
        try {
            Integer.parseInt(preprocessorMatches.get(0).getContent().trim());
        } catch(NumberFormatException ex) {
            CraftTweakerAPI.LOGGER.warn("Incorrect Priority value: {}", StringUtils.wrap(preprocessorMatches.get(0)
                    .getContent()
                    .trim(), "`", false));
            preprocessorMatches.set(0, new PreprocessorMatch(this, -1, getDefaultValue()));
        }
        
        return true;
    }
    
    @Override
    public int compare(FileAccessSingle o1, FileAccessSingle o2) {
        //We know PriorityPreprocessor has a default value, so it will always have a "match"
        //Otherwise we'd need to check if the file has a match
        final int i1 = Integer.parseInt(o1.getMatchesFor(this).get(0).getContent().trim());
        final int i2 = Integer.parseInt(o2.getMatchesFor(this).get(0).getContent().trim());
        
        return Integer.compare(i2, i1);
    }
    
    @Override
    public int getPriority() {
        
        return 100;
    }
    
}
