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
}
