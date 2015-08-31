/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.formatting;

import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
public interface IFormatter {
	public IFormattedText string(String text);

	@ZenMethod
	public IFormattedText black(IFormattedText text);

	@ZenMethod
	public IFormattedText darkBlue(IFormattedText text);

	@ZenMethod
	public IFormattedText darkGreen(IFormattedText text);

	@ZenMethod
	public IFormattedText darkAqua(IFormattedText text);

	@ZenMethod
	public IFormattedText darkRed(IFormattedText text);

	@ZenMethod
	public IFormattedText darkPurple(IFormattedText text);

	@ZenMethod
	public IFormattedText gold(IFormattedText text);

	@ZenMethod
	public IFormattedText gray(IFormattedText text);

	@ZenMethod
	public IFormattedText darkGray(IFormattedText text);

	@ZenMethod
	public IFormattedText blue(IFormattedText text);

	@ZenMethod
	public IFormattedText green(IFormattedText text);

	@ZenMethod
	public IFormattedText aqua(IFormattedText text);

	@ZenMethod
	public IFormattedText red(IFormattedText text);

	@ZenMethod
	public IFormattedText lightPurple(IFormattedText text);

	@ZenMethod
	public IFormattedText yellow(IFormattedText text);

	@ZenMethod
	public IFormattedText white(IFormattedText text);

	@ZenMethod
	public IFormattedText obfuscated(IFormattedText text);

	@ZenMethod
	public IFormattedText bold(IFormattedText text);

	@ZenMethod
	public IFormattedText strikethrough(IFormattedText text);

	@ZenMethod
	public IFormattedText underline(IFormattedText text);

	@ZenMethod
	public IFormattedText italic(IFormattedText text);
}
