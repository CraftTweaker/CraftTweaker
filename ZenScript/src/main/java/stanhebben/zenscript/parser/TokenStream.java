/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.util.ZenPosition;

/**
 * Represents a token stream. A token stream reads characters from a reader and
 * presents it as a series of tokens.
 *
 * Token classes with a negative class are considered to be whitespace.
 *
 * @author Stan Hebben
 */
public class TokenStream implements Iterator<Token> {
	private ZenParsedFile file;
	
    private CountingReader reader;
    private CompiledDFA dfa;
    private Token next;
    private int nextChar;
    private int line;
    private int lineOffset;

    private int tokenMemoryOffset;
    private LinkedList<Token> tokenMemory;
    private Stack<Integer> marks;
    private int tokenMemoryCurrent;
    
    /**
     * Creates a token stream using the specified reader and DFA.
     *
     * @param reader reader to read characters from
     * @param dfa DFA to tokenize the stream
     * @throws IOException
     */
    public TokenStream(Reader reader, CompiledDFA dfa) throws IOException {
        tokenMemoryOffset = 0;
        tokenMemoryCurrent = 0;
        tokenMemory = new LinkedList<Token>();
        marks = new Stack<Integer>();
        
        this.reader = new CountingReader(reader);
        this.dfa = dfa;
        nextChar = this.reader.read();
        line = 1;
        lineOffset = 1;
        advance();
    }

    /**
     * Creates a token stream which reads data from the specified string.
     *
     * @param data data to read
     * @param dfa DFA to tokenize the stream
     * @throws IOException
     */
    public TokenStream(String data, CompiledDFA dfa) throws IOException {
        this(new StringReader(data), dfa);
    }
	
	public void setFile(ZenParsedFile file) {
		this.file = file;
	}
	
	public ZenParsedFile getFile() {
		return file;
	}
    
    public Token peek() {
        if (tokenMemoryCurrent < tokenMemoryOffset + tokenMemory.size()) {
            return tokenMemory.get((tokenMemoryCurrent) - tokenMemoryOffset);
        } else {
            return next;
        }
    }

    public boolean isNext(int type) {
        return peek().getType() == type;
    }

    public Token optional(int type) {
        if (peek() != null && peek().getType() == type) {
            return next();
        } else {
            return null;
        }
    }

    public Token required(int type, String error) {
		Token t = peek();
        if (t != null && t.getType() == type) {
            return next();
        } else {
            throw new ParseException(t, error);
        }
    }

    // LL(*) ability

    /**
     * Pushes a mark on the mark stack.
     */
    public void pushMark() {
        marks.push(tokenMemoryCurrent);
    }

    /**
     * Pops a mark from the mark stack without reset.
     */
    public void popMark() {
        marks.pop();

        if (marks.isEmpty()) {
            tokenMemoryOffset = tokenMemoryCurrent;
            tokenMemory.clear();
        }
    }

    /**
     * Pops a mark from the mark stack and resets the stream's position to it
     */
    public void reset() {
        tokenMemoryCurrent = marks.pop();
    }


    ////////////////////////////
    // Iterator implementation
    ////////////////////////////

    public boolean hasNext() {
        return next != null;
    }

    public Token next() {
        if (tokenMemoryCurrent < tokenMemoryOffset + tokenMemory.size()) {
            return tokenMemory.get((tokenMemoryCurrent++) - tokenMemoryOffset);
        } else {
            Token result = next;

            if (marks.isEmpty()) {
                tokenMemoryOffset++;
            } else {
                tokenMemory.add(result);
            }
            tokenMemoryCurrent++;

            advance();
            return result;
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }

    //////////////////////
    // Protected methods
    //////////////////////

    /**
     * Can be implemented by subclasses to post-process tokens.
     *
     * @param token
     * @return processed token
     */
    protected Token process(Token token) {
        return token;
    }

    ////////////////////
    // Private methods
    ////////////////////

    /**
     * Advances to the next non - whitespace token.
     */
    private void advance() {
        do {
            advanceToken();
        } while (next != null && next.getType() < 0);
    }

    /**
     * Advances to the next token.
     */
    private void advanceToken() {
        if (nextChar < 0) {
            next = null;
            return;
        }
        try {
            int state = 0;
            StringBuilder value = new StringBuilder();
            int tLine = line;
            int tLineOffset = lineOffset;
            while (dfa.transitions[state].containsKey(nextChar)) {
                value.append((char)nextChar);
                state = dfa.transitions[state].get(nextChar);
                line = reader.line;
                lineOffset = reader.lineOffset;
                nextChar = reader.read();
            }
            if (dfa.finals[state] != CompiledDFA.NOFINAL) {
                if (state == 0) throw new TokenException(line, lineOffset, (char)nextChar);
                next = process(new Token(value.toString(), dfa.finals[state], new ZenPosition(file, tLine, tLineOffset)));
            } else {
				if (nextChar < 0 && value.length() == 0) {
					return; // happens on comments at the end of files
				}
				System.out.println("Value: " + value.length() + " characters: " + value);
                throw new TokenException(line, lineOffset, (char)nextChar);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////
    // Private inner classes
    //////////////////////////

    /**
     * Keeps a line and line offset count.
     */
    private class CountingReader {
        private int line;
        private int lineOffset;
        private Reader reader;
        private boolean eof;

        public CountingReader(Reader reader) {
            this.reader = reader;
            line = 1;
            lineOffset = 1;
        }

        public int read() throws IOException {
            int ch = reader.read();
            if (ch == -1) {
                eof = true;
                return ch;
            }
            if (ch == '\n') {
                line++;
                lineOffset = 1;
            } else {
                lineOffset++;
            }
            return ch;
        }
    }
}
