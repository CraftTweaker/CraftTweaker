/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.formatting;

import minetweaker.api.formatting.IFormattedText;

/**
 *
 * @author Stan
 */
public interface IMCFormattedString extends IFormattedText {
	public String getTooltipString();

	public String getTooltipString(String context);
}
