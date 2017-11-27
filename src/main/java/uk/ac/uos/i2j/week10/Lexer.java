package uk.ac.uos.i2j.week10;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class Lexer {
	private final PushbackReader reader;
	
	public Lexer(Reader reader) {
		this.reader = new PushbackReader(reader);
	}
	
	public Symbol next() throws IOException {
		int c = reader.read();
		if (-1 == c) return null; // no more symbols
		if ('<' == c) return new Symbol(Symbol.Type.OPEN, "<");
		if ('>' == c) return new Symbol(Symbol.Type.CLOSE, ">");
		if ('/' == c) return new Symbol(Symbol.Type.SLASH, "/");
		if (Character.isWhitespace(c)) {
			while (Character.isWhitespace(c)) {
				c = reader.read();
			}
			if (-1 != c) reader.unread(c);
			return new Symbol(Symbol.Type.SPACE, " ");
		}
		if (Character.isLetterOrDigit(c)) {
			StringBuffer value = new StringBuffer();
			while (Character.isLetterOrDigit(c)) {
				value.append((char)c);
				c = reader.read();
			}
			if (-1 != c) reader.unread(c);
			return new Symbol(Symbol.Type.WORD, value.toString());
		}
		StringBuffer value = new StringBuffer();
		while (-1 != c && '<' != c && '>' != c && !Character.isWhitespace(c) && !Character.isLetterOrDigit(c)) {
			value.append((char)c);
			c = reader.read();
		}
		if (-1 != c) reader.unread(c);
		return new Symbol(Symbol.Type.OTHER, value.toString());
	}
}
