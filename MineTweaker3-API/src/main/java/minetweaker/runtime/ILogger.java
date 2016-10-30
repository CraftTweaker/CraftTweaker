/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
public interface ILogger {
	@ZenMethod
    void logCommand(String message);

	@ZenMethod
    void logInfo(String message);

	@ZenMethod
    void logWarning(String message);

	@ZenMethod
    void logError(String message);

	@ZenMethod
    void logError(String message, Throwable exception);
}
