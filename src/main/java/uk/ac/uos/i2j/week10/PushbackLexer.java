package uk.ac.uos.i2j.week10;

import java.io.IOException;
import java.util.Stack;

public class PushbackLexer {
	private Lexer lexer;
	private Stack<Symbol> symbols;
	
	public PushbackLexer(Lexer lexer) {
		this.lexer = lexer;
		this.symbols = new Stack<>();
	}
	
	public Symbol next() throws IOException {
		if (!symbols.isEmpty()) return symbols.pop();
		return lexer.next();
	}
	
	public void unread(Symbol symbol) {
		symbols.push(symbol);
	}
}
