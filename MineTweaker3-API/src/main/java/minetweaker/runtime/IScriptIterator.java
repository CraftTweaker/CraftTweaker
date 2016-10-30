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
	String getGroupName();

	boolean next();

	String getName();

	InputStream open() throws IOException;
}
