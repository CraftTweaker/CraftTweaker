/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Stan
 */
public interface IScriptIterator {
	public String getGroupName();

	public boolean next();

	public String getName();

	public InputStream open() throws IOException;
}
