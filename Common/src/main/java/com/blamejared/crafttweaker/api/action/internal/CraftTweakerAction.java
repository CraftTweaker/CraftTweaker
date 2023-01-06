package com.blamejared.crafttweaker.api.action.internal;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.platform.Services;

/**
 * Marks an action as being executed by CraftTweaker.
 *
 * <h1>IMPORTANT</h1>
 *
 * <p>This is not for usage outside of CraftTweaker: it should not be considered public API. This class is published
 * as part of it just because actions are considered API. Integration writers should not attempt to extend this
 * class!</p>
 */
// This should be sealed, but it can't be because we are not a module, so yeah, we do it like this for now
public abstract class CraftTweakerAction implements IAction {
    
    private static final String ALLOWED_PACKAGE_NAME = Services.PLATFORM.isDevelopmentEnvironment() ? findPackage() : null;
    
    public CraftTweakerAction() {
        
        if(ALLOWED_PACKAGE_NAME != null) { // Not a method call, so it can be elided for performance
            verify(this.getClass());
        }
    }
    
    private static String findPackage() {
        
        final Class<?> thisClass = CraftTweakerAction.class;
        final Package thisPackage = thisClass.getPackage();
        final String thisPackageName = thisPackage.getName();
        return thisPackageName.replace(".internal", "");
    }
    
    private static void verify(final Class<?> clazz) {
        
        final String packageName = clazz.getPackage().getName();
        if(!packageName.startsWith(ALLOWED_PACKAGE_NAME)) {
            throw new AssertionError("Action " + clazz.getName() + " extends CraftTweakerAction but it is not a CraftTweaker action");
        }
    }
    
    @Override
    public final String systemName() {
        
        return CraftTweakerConstants.MOD_NAME;
    }
    
}
