package crafttweaker.mc1120.formatting;

import crafttweaker.api.formatting.IFormattedText;

/**
 * @author Stan
 */
public interface IMCFormattedString extends IFormattedText {

    String getTooltipString();

    String getTooltipString(String context);
    
}
