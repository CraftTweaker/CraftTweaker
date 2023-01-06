package com.blamejared.crafttweaker.mixin.common.transform.tags;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.function.Consumer;

@Mixin(TagManager.class)
public class MixinTagManager {
    
    @Shadow
    private List<TagManager.LoadResult<?>> results;
    
    @Shadow
    @Final
    private RegistryAccess registryAccess;
    
    @ModifyArg(method = "reload", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenAcceptAsync(Ljava/util/function/Consumer;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    public <T> Consumer<? super T> crafttweaker$appendConsumer(Consumer<? super T> action) {
        
        return action.andThen(o -> {
            CraftTweakerTagRegistry.INSTANCE.bind(results, new CraftTweakerTagRegistry.BindContext(false));
            
            IAccessibleServerElementsProvider asep = CraftTweakerAPI.getAccessibleElementsProvider().server();
            asep.registryAccess(this.registryAccess);
            
            
            final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                    CraftTweakerConstants.TAGS_LOADER_NAME,
                    CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID, // TODO("Custom load source?")
                    ScriptRunConfiguration.RunKind.EXECUTE
            );
            
            try {
                CraftTweakerAPI.getScriptRunManager()
                        .createScriptRun(configuration)
                        .execute();
            } catch(final Throwable e) {
                CraftTweakerCommon.logger().error("Unable to run tag scripts due to an error", e);
            }
            asep.registryAccess(null);
        });
    }
    
}
