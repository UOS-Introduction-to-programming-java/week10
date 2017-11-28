package uk.ac.uos.i2j.week10;

import java.io.IOException;
import java.io.Reader;

public class Parser {
	public Document parse(Reader in) throws IOException {
		PushbackLexer lex = new PushbackLexer(new Lexer(in));
		return document(lex);
	}
	
	private Document document(PushbackLexer lex) throws IOException {
		Element element = element(lex);
		if (null == element) {
			return new Document(text(lex));
		}
		return new Document(element);
	}

	private String text(PushbackLexer lex) throws IOException {
		StringBuilder ret = new StringBuilder();
		sym: for (Symbol symbol = lex.next(); symbol != null; symbol = lex.next()) {
			switch(symbol.type) {
			case WORD: case SPACE: case OTHER: case SLASH:
				ret.append(symbol.value);
				break;
			default:
				lex.unread(symbol);
				break sym; // to stop loop
			}
		}
		return ret.toString();
	}

	private Element element(PushbackLexer lex) throws IOException {
		String start = start(lex);
		if (null == start) return null;
		Element ret = new Element(start);

		Document body = null;
		do {
			body = document(lex);
			if (null != body) {
				ret.add(body);
			}
		} while (body != null);

		String end = end(lex);
		if (!start.equals(end)) {
			throw new IOException("Expected </"+ start + ">, got </" + end + ">");
		}

		return ret;
	}

	private String tag(PushbackLexer lex, boolean expectSlash) throws IOException {
		Symbol first = lex.next(); 
		if (null == first) return null;
		if (first.type != Symbol.Type.OPEN) {
			lex.unread(first);
			return null;
		}

		Symbol symbol = lex.next();
		if (symbol.type == Symbol.Type.SLASH) {
			if (expectSlash) {
				symbol = lex.next();
			} else {
				lex.unread(symbol);
				lex.unread(first);
				return null;
			}
		}

		if (symbol.type != Symbol.Type.WORD) {
			throw new IOException("Expected WORD, got " + symbol.type);
		}
		String name = symbol.value;
		
		symbol = lex.next();
		if (symbol.type != Symbol.Type.CLOSE) {
			throw new IOException("Expected >, got " + symbol.type);
		}
		return name;
	}

	private String start(PushbackLexer lex) throws IOException {
		return tag(lex, false);
	}

	private String end(PushbackLexer lex) throws IOException {
		return tag(lex, true);
	}

}
