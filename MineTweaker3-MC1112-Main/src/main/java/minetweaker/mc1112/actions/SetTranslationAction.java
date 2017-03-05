package minetweaker.mc1112.actions;

import minetweaker.IUndoableAction;
import minetweaker.mc1112.util.MineTweakerHacks;
import net.minecraft.util.text.translation.LanguageMap;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * @author Stan
 */
public class SetTranslationAction implements IUndoableAction {

    private static final LanguageMap INSTANCE = MineTweakerHacks.getStringTranslateInstance();
    private static final Charset UTF8 = Charset.forName("utf-8");

    private final String key;
    private final String newValue;
    private final String oldValue;

    public SetTranslationAction(String key, String value) {
        this.key = key;
        newValue = value;
        oldValue = INSTANCE.translateKey(key);
    }

    private static void set(String key, String value) {
        if(value.contains("\\\"")) {
            value = value.replace("\\\"", "\"");
        }
        LanguageMap.inject(new ByteArrayInputStream((key + "=" + value).getBytes(UTF8)));
    }

    @Override
    public void apply() {
        set(key, newValue);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set(key, oldValue);
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
