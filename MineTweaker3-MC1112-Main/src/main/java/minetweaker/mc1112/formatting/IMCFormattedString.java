package minetweaker.mc1112.formatting;

import minetweaker.api.formatting.IFormattedText;

/**
 * @author Stan
 */
public interface IMCFormattedString extends IFormattedText {

    String getTooltipString();

    String getTooltipString(String context);
}
