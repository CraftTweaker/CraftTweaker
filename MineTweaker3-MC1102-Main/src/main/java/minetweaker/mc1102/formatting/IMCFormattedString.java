package minetweaker.mc1102.formatting;

import minetweaker.api.formatting.IFormattedText;

/**
 * @author Stan
 */
public interface IMCFormattedString extends IFormattedText {

    String getTooltipString();

    String getTooltipString(String context);
}
