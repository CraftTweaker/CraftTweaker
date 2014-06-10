/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.util.Iterator;

/**
 *
 * @author Stan
 */
public interface IScriptProvider {
	public Iterator<IScriptIterator> getScripts();
}
