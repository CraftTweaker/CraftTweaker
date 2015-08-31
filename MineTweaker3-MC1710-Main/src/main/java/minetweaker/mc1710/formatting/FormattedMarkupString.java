/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.formatting;

import minetweaker.api.formatting.IFormattedText;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author Stan
 */
public class FormattedMarkupString implements IMCFormattedString {
	private final EnumChatFormatting markup;
	private final IMCFormattedString contents;

	public FormattedMarkupString(EnumChatFormatting markup, IMCFormattedString contents) {
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
