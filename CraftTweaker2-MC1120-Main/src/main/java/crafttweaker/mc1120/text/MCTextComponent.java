package crafttweaker.mc1120.text;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.text.IStyle;
import crafttweaker.api.text.ITextComponent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class MCTextComponent implements ITextComponent {
    private final net.minecraft.util.text.ITextComponent text;

    public MCTextComponent(net.minecraft.util.text.ITextComponent text) {
        this.text = text;
    }

    @Override
    public ITextComponent append(ITextComponent text) {
        this.text.appendSibling(CraftTweakerMC.getITextComponent(text));
        return CraftTweakerMC.getITextComponent(this.text);
    }

    @Override
    public void setStyle(IStyle style) {
        text.setStyle(CraftTweakerMC.getStyle(style));
    }

    @Override
    public IStyle getStyle() {
        return CraftTweakerMC.getIStyle(text.getStyle());
    }

    @Override
    public String getUnformattedComponentText() {
        return text.getUnformattedComponentText();
    }

    @Override
    public String getUnformattedText() {
        return text.getUnformattedText();
    }

    @Override
    public String getFormattedText() {
        return text.getFormattedText();
    }

    @Override
    public List<ITextComponent> getSiblings() {
        return text.getSiblings().stream().map(CraftTweakerMC::getITextComponent).collect(Collectors.toList());
    }

    @Override
    public ITextComponent createCopy() {
        return CraftTweakerMC.getITextComponent(text.createCopy());
    }

    @Override
    public Object getInternal() {
        return text;
    }
}
