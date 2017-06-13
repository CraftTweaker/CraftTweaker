package minetweaker.mc1120.formatting;

import minetweaker.api.formatting.IFormattedText;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Stan
 */
public class FormattedMarkupString implements IMCFormattedString {

    private final TextFormatting markup;
    private final IMCFormattedString contents;

    public FormattedMarkupString(TextFormatting markup, IMCFormattedString contents) {
        this.markup = markup;
        this.contents = contents;
    }

    @Override
    public String getTooltipString() {
        return markup + contents.getTooltipString(markup.toString());
    }

    @Override
    public String getTooltipString(String context) {
        return markup + contents.getTooltipString(context + markup);
    }

    @Override
    public IFormattedText add(IFormattedText other) {
        return cat(other);
    }

    @Override
    public IFormattedText cat(IFormattedText other) {
        return new FormattedStringJoin(this, (IMCFormattedString) other);
    }
}
