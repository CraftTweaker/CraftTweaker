package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.ZenClassRegistry;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.javashared.JavaClass;

class CrTJavaNativeClassConverter extends JavaNativeClassConverter {
    
    private final ZenClassRegistry zenClassRegistry;
    
    public CrTJavaNativeClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, ZenClassRegistry zenClassRegistry) {
        super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
        this.zenClassRegistry = zenClassRegistry;
    }
    
    @Override
    public String getNameForScripts(Class<?> cls) {
        if(getNativeTypeRegistry().hasInfoFor(cls)) {
            return getNativeTypeRegistry().getCrTNameFor(cls);
        }
        
        if(cls.getCanonicalName().startsWith("net.minecraft")){
            CraftTweakerAPI.logger.trace("Minecraft Type referenced but not registered: " + cls.getCanonicalName());
        }
        
        return super.getNameForScripts(cls);
    }
    
    private NativeTypeRegistry getNativeTypeRegistry() {
        return zenClassRegistry.getNativeTypeRegistry();
    }
    
    @Override
    public boolean shouldLoadClass(Class<?> cls) {
        if(zenClassRegistry.isBlacklisted(cls)) {
            CraftTweakerAPI.logInfo("Not loading class because of blacklist: " + cls.getCanonicalName());
            return false;
        }
        return super.shouldLoadClass(cls);
    }
}
