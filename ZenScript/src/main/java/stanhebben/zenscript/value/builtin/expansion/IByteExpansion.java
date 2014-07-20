/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.value.builtin.expansion;

import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stan
 */
public interface IByteExpansion {
	public IAny member(byte value, String member);
}
