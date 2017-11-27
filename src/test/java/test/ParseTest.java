package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import uk.ac.uos.i2j.week10.Parser;

public class ParseTest {
	@Test public void testEmpty() throws IOException {
		Parser parser = new Parser();
		Object data = parser.parse(new StringReader(""));
		assertEquals("", data);
	}
	@Test public void testText() throws IOException {
		Parser parser = new Parser();
		Object data = parser.parse(new StringReader("say what?"));
		assertEquals("say what?", data);
	}
}
