/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.resource;

/**
 *
 * @author Stan
 */
public interface IResourceManager {
	/**
	 * Retrieves a resource file. Returns null if the resource dosen't exist.
	 * 
	 * @param module
	 * @param name
	 * @return
	 */
	public IResourceFile getResource(String module, String name);
}
