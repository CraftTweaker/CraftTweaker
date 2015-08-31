/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import stanhebben.zenscript.ZenParsedFile;

/**
 *
 * @author Stan
 */
public class ParseException extends RuntimeException {
	private final ZenParsedFile file;
	private final int line;
	private final int lineOffset;

	private final Token token;
	private final String message;

	public ParseException(Token token, String error) {
		super("Error parsing line " + token.getPosition().getLine() + ":" + token.getPosition().getLineOffset() + " - " + error + " (last token: " + token.getValue() + ")");

		this.file = token.getPosition().getFile();
		this.line = token.getPosition().getLine();
		this.lineOffset = token.getPosition().getLineOffset();

		this.token = token;
		this.message = error;
	}

	public ParseException(ZenParsedFile file, int line, int lineOffset, String error) {
		this.file = file;
		this.line = line;
		this.lineOffset = lineOffset;

		token = null;
		message = error;
	}

	public ZenParsedFile getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}

	public int getLineOffset() {
		return lineOffset;
	}

	public String getExplanation() {
		return message;
	}
}
