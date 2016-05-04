/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.formatting;

import java.util.Arrays;
import minetweaker.api.formatting.IFormattedText;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author Stan
 */
public class FormattedStringJoin implements IMCFormattedString {
	private final IMCFormattedString[] values;

	public FormattedStringJoin(IMCFormattedString first, IMCFormattedString second) {
		values = new IMCFormattedString[] { first, second };
	}

	public FormattedStringJoin(IMCFormattedString[] values) {
		this.values = values;
	}

	@Override
	public String getTooltipString() {
		StringBuilder result = new StringBuilder();
		for (IMCFormattedString value : values) {
			result.append(value.getTooltipString());
		}
		return result.toString();
	}

	@Override
	public String getTooltipString(String context) {
		boolean first = true;

		StringBuilder result = new StringBuilder();
		for (IMCFormattedString value : values) {
			if (first) {
				first = false;
			} else {
				result.append(context);
			}

			result.append(value.getTooltipString());
			result.append(EnumChatFormatting.RESET);
		}

		return result.toString();
	}

	@Override
	public IFormattedText add(IFormattedText other) {
		return cat(other);
	}

	@Override
	public IFormattedText cat(IFormattedText other) {
		IMCFormattedString[] newValues = Arrays.copyOf(values, values.length + 1);
		newValues[values.length] = (IMCFormattedString) other;
		return new FormattedStringJoin(newValues);
	}
}
