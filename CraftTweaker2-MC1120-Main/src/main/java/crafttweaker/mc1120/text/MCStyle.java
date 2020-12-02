package crafttweaker.mc1120.text;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.formatting.IFormatter;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.text.IStyle;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

/**
 * @author youyihj
 */
public class MCStyle implements IStyle {
    private final Style style;

    public MCStyle(Style style) {
        this.style = style;
    }

    @Override
    public IStyle getParent() {
        return CraftTweakerMC.getIStyle(this.style.getParent());
    }

    @Override
    public void setParent(IStyle style) {
        this.style.setParentStyle(CraftTweakerMC.getStyle(style));
    }

    @Override
    public boolean getBold() {
        return style.getBold();
    }

    @Override
    public void setBold(boolean bold) {
        style.setBold(bold);
    }

    @Override
    public boolean getItalic() {
        return style.getItalic();
    }

    @Override
    public void setItalic(boolean italic) {
        style.setItalic(italic);
    }

    @Override
    public boolean getUnderline() {
        return style.getUnderlined();
    }

    @Override
    public void setUnderline(boolean underline) {
        style.setUnderlined(underline);
    }

    @Override
    public boolean getStrikethrough() {
        return style.getStrikethrough();
    }

    @Override
    public void setStrikethrough(boolean strikethrough) {
        style.setStrikethrough(strikethrough);
    }

    @Override
    public boolean getObfuscated() {
        return style.getObfuscated();
    }

    @Override
    public void setObfuscated(boolean obfuscated) {
        style.setObfuscated(obfuscated);
    }

    @Override
    public String getInsertion() {
        return style.getInsertion();
    }

    @Override
    public void setInsertion(String insertion) {
        style.setInsertion(insertion);
    }

    @Override
    public String getColor() {
        return style.getColor() == null ? "null" : style.getColor().toString();
    }

    @Override
    public void setColor(String color) {
        TextFormatting formatting = TextFormatting.getValueByName(color);
        if (formatting == null) {
            CraftTweakerAPI.logError("Invalid Color Name: " + color);
        } else {
            style.setColor(formatting);
        }
    }

    @Override
    public IStyle createDeepCopy() {
        return CraftTweakerMC.getIStyle(style.createDeepCopy());
    }

    @Override
    public IStyle createShallowCopy() {
        return CraftTweakerMC.getIStyle(style.createShallowCopy());
    }

    @Override
    public Object getInternal() {
        return style;
    }
}
