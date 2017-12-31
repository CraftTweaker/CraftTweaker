package crafttweaker.mc1120.formatting;

import crafttweaker.api.formatting.IFormattedText;

/**
 * @author Stan
 */
public class FormattedString implements IMCFormattedString {

    private final String value;

    public FormattedString(String value) {
        this.value = value;
    }

    @Override
    public String getTooltipString() {
        return value;
    }

    @Override
    public String getTooltipString(String context) {
        return value;
    }

    @Override
    public IFormattedText add(IFormattedText other) {
        return cat(other);
    }

    @Override
    public IFormattedText cat(IFormattedText other) {
        return new FormattedStringJoin(this, (IMCFormattedString) other);
    }

    @Override
    public String getText() {
        return getTooltipString();
    }

    @Override
    public String toString() {
        return getTooltipString();
    }
}
