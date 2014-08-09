/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import stanhebben.zenscript.ZenParsedFile;

/**
 *
 * @author Stanneke
 */
public class ZenPosition {
	private final ZenParsedFile file;
	private final int line;
	private final int offset;
	
	public ZenPosition(ZenParsedFile file, int line, int offset) {
		if (file != null && line <= 0) throw new IllegalArgumentException("Line must be positive");
		
		this.file = file;
		this.line = line;
		this.offset = offset;
	}
	
	public ZenParsedFile getFile() {
		return file;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getLineOffset() {
		return offset;
	}
	
	@Override
	public String toString() {
		return (file == null ? "?" : file.getFileName()) + ":" + Integer.toString(line);
	}
}
