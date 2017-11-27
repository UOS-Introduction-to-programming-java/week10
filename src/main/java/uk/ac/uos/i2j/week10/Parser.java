package uk.ac.uos.i2j.week10;

import java.io.IOException;
import java.io.Reader;

public class Parser {
	public Object parse(Reader in) throws IOException {
		Lexer lex = new Lexer(in);
		return document(lex);
	}
	
	private Object document(Lexer lex) throws IOException {
		Object ret = element(lex);
		if (null == ret) {
			ret = text(lex);
		}
		return ret;
	}

	private String text(Lexer lex) throws IOException {
		StringBuilder ret = new StringBuilder();
		sym: for (Symbol symbol = lex.next(); symbol != null; symbol = lex.next()) {
			switch(symbol.type) {
			case WORD: case SPACE: case OTHER: case SLASH:
				ret.append(symbol.value);
				break;
			default:
				break sym; // to stop loop
			}
		}
		return ret.toString();
	}

	private Object element(Lexer lex) throws IOException {
		// TODO
		return null;
	}

}
