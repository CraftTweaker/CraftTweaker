/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.logger;

import java.util.ArrayList;
import java.util.List;
import minetweaker.api.player.IPlayer;
import minetweaker.runtime.ILogger;

/**
 *
 * @author Stan
 */
public class MTLogger implements ILogger {
	private final List<ILogger> loggers = new ArrayList<ILogger>();
	private final List<IPlayer> players = new ArrayList<IPlayer>();
	private final List<String> unprocessed = new ArrayList<String>();

	public void addLogger(ILogger logger) {
		loggers.add(logger);
	}

	public void removeLogger(ILogger logger) {
		loggers.remove(logger);
	}

	public void addPlayer(IPlayer player) {
		players.add(player);

		if (!unprocessed.isEmpty()) {
			for (String message : unprocessed) {
				player.sendChat(message);
			}
		}
	}

	public void removePlayer(IPlayer player) {
		players.remove(player);
	}

	public void clear() {
		unprocessed.clear();
	}

	@Override
	public void logCommand(String message) {
		for (ILogger logger : loggers) {
			logger.logCommand(message);
		}
	}

	@Override
	public void logInfo(String message) {
		for (ILogger logger : loggers) {
			logger.logInfo(message);
		}
	}

	@Override
	public void logWarning(String message) {
		for (ILogger logger : loggers) {
			logger.logWarning(message);
		}

		String message2 = "WARNING: " + message;

		if (players.isEmpty()) {
			unprocessed.add(message2);
		} else {
			for (IPlayer player : players) {
				player.sendChat(message2);
			}
		}
	}

	@Override
	public void logError(String message) {
		logError(message, null);
	}

	@Override
	public void logError(String message, Throwable exception) {
		for (ILogger logger : loggers) {
			logger.logError(message, exception);
		}

		String message2 = "ERROR: " + message;

		if (players.isEmpty()) {
			unprocessed.add(message2);
		} else {
			for (IPlayer player : players) {
				player.sendChat(message2);
			}
		}
	}
}
