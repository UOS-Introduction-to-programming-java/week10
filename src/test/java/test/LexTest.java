package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import uk.ac.uos.i2j.week10.Lexer;
import uk.ac.uos.i2j.week10.Symbol;

public class LexTest {
	
	void assertNextSymbol(Lexer lex, Symbol.Type type, String value) throws IOException {
		Symbol symbol = lex.next();
		assertEquals(type, symbol.type);
		if (null != value) assertEquals(value, symbol.value);
	}
	
	void assertNextSymbol(Lexer lex, Symbol.Type type) throws IOException {
		assertNextSymbol(lex, type, null);
	}

	@Test
	public void testEmpty() throws IOException {
		Lexer lex = new Lexer(new StringReader(""));
		assertNull(lex.next());
	}
	@Test
	public void testSingleOpen() throws IOException {
		Lexer lex = new Lexer(new StringReader("<"));
		assertNextSymbol(lex, Symbol.Type.OPEN, "<");
	}
	@Test
	public void testSingleClose() throws IOException {
		Lexer lex = new Lexer(new StringReader(">"));
		assertNextSymbol(lex, Symbol.Type.CLOSE, ">");
	}
	@Test
	public void testSingleSlash() throws IOException {
		Lexer lex = new Lexer(new StringReader("/"));
		assertNextSymbol(lex, Symbol.Type.SLASH, "/");
	}
	@Test
	public void testSingleSpace() throws IOException {
		Lexer lex = new Lexer(new StringReader(" "));
		assertNextSymbol(lex, Symbol.Type.SPACE, " ");
	}
	@Test
	public void testSingleTab() throws IOException {
		Lexer lex = new Lexer(new StringReader("\t"));
		assertNextSymbol(lex, Symbol.Type.SPACE, " ");
	}
	@Test
	public void testSingleNewline() throws IOException {
		Lexer lex = new Lexer(new StringReader("\n"));
		assertNextSymbol(lex, Symbol.Type.SPACE, " ");
	}
	@Test
	public void testSingleOther() throws IOException {
		Lexer lex = new Lexer(new StringReader("!"));
		assertNextSymbol(lex, Symbol.Type.OTHER, "!");
	}

	@Test
	public void testMixedWhitespace() throws IOException {
		Lexer lex = new Lexer(new StringReader(" \n\t  "));
		assertNextSymbol(lex, Symbol.Type.SPACE, " ");
		assertNull(lex.next());
	}
	
	@Test
	public void testWord() throws IOException {
		Lexer lex = new Lexer(new StringReader("hello"));
		assertNextSymbol(lex, Symbol.Type.WORD, "hello");
	}

	@Test
	public void testMixedOther() throws IOException {
		Lexer lex = new Lexer(new StringReader("#@%!"));
		assertNextSymbol(lex, Symbol.Type.OTHER, "#@%!");
		assertNull(lex.next());
	}
	
	@Test
	public void testCombination() throws IOException {
		Lexer lex = new Lexer(new StringReader("<ugh>"));
		assertNextSymbol(lex, Symbol.Type.OPEN);
		assertNextSymbol(lex, Symbol.Type.WORD, "ugh");
		assertNextSymbol(lex, Symbol.Type.CLOSE);
		assertNull(lex.next());
	}
	
	@Test
	public void testSomeOfEach() throws IOException {
		Lexer lex = new Lexer(new StringReader("<ugh>\nWhat is that?</ugh>"));
		assertNextSymbol(lex, Symbol.Type.OPEN);
		assertNextSymbol(lex, Symbol.Type.WORD, "ugh");
		assertNextSymbol(lex, Symbol.Type.CLOSE);
		assertNextSymbol(lex, Symbol.Type.SPACE);
		assertNextSymbol(lex, Symbol.Type.WORD, "What");
		assertNextSymbol(lex, Symbol.Type.SPACE);
		assertNextSymbol(lex, Symbol.Type.WORD, "is");
		assertNextSymbol(lex, Symbol.Type.SPACE);
		assertNextSymbol(lex, Symbol.Type.WORD, "that");
		assertNextSymbol(lex, Symbol.Type.OTHER, "?");
		assertNextSymbol(lex, Symbol.Type.OPEN);
		assertNextSymbol(lex, Symbol.Type.SLASH);
		assertNextSymbol(lex, Symbol.Type.WORD, "ugh");
		assertNextSymbol(lex, Symbol.Type.CLOSE);
		assertNull(lex.next());
	}	
}
