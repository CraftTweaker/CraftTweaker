package minetweaker.mc1120.actions;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.mc1120.util.MineTweakerHacks;
import net.minecraft.util.text.translation.LanguageMap;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * @author Stan
 */
public class SetTranslationAction implements IUndoableAction {

    private static final LanguageMap INSTANCE = MineTweakerHacks.getStringTranslateInstance();
    private static final Charset UTF8 = Charset.forName("utf-8");

    private final IItemStack stack;
    private final String key;
    private final String newValue;
    private final String oldValue;

    public SetTranslationAction(IItemStack stack, String key, String value) {
        this.stack = stack;
        this.key = key;
        newValue = value;
        oldValue = INSTANCE.translateKey(key);
    }

    private static void set(IItemStack stack, String key, String value) {
        if(value.contains("\\\"")) {
            value = value.replace("\\\"", "\"");
        }
        MineTweakerAPI.getIjeiRecipeRegistry().removeItem(stack.getInternal());
        LanguageMap.inject(new ByteArrayInputStream((key + "=" + value).getBytes(UTF8)));
        MineTweakerAPI.getIjeiRecipeRegistry().addItem(stack.getInternal());
    }

    @Override
    public void apply() {
        set(stack, key, newValue);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set(stack, key, oldValue);
    }

    @Override
    public String describe() {
        return "Translating " + key + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting " + key + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
