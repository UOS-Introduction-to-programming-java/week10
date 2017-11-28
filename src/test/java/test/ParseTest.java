package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import uk.ac.uos.i2j.week10.Document;
import uk.ac.uos.i2j.week10.Parser;

public class ParseTest {
	@Test public void testEmpty() throws IOException {
		Parser parser = new Parser();
		Document document = parser.parse(new StringReader(""));
		assertEquals("", document.text);
	}
	
	@Test public void testText() throws IOException {
		Parser parser = new Parser();
		Document document = parser.parse(new StringReader("say what?"));
		assertEquals("say what?", document.text);
	}
	
	@Test public void testEmptyElement() throws IOException {
		Parser parser = new Parser();
		Document document = parser.parse(new StringReader("<a></a>"));
		assertEquals("a", document.element.name);
		assertEquals("", document.element.body.get(0).text);
	}
	
	@Test public void testTextElement() throws IOException {
		Parser parser = new Parser();
		Document document = parser.parse(new StringReader("<a>2/3</a>"));
		assertEquals("a", document.element.name);
		assertEquals("2/3", document.element.body.get(0).text);
	}
	
	@Test public void testNestedElement() throws IOException {
		Parser parser = new Parser();
		Document document = parser.parse(new StringReader("<a><b></b> lala</a>"));
		assertEquals("a", document.element.name);
		assertEquals("b", document.element.body.get(0).element.name);
		assertEquals(" lala", document.element.body.get(1).text);
	}
	
	@Test public void testInvalidNoStartName() throws IOException {
		Parser parser = new Parser();
		try {
			parser.parse(new StringReader("<>"));
			fail("parse invalid should throw");
		} catch(IOException e) {
			assertEquals("Expected WORD, got CLOSE", e.getMessage());
		}
	}
}
