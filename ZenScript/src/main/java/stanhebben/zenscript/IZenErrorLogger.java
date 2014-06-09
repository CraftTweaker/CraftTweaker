/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript;

import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public interface IZenErrorLogger {
	public void error(ZenPosition position, String message);
	
	public void warning(ZenPosition position, String message);
}
