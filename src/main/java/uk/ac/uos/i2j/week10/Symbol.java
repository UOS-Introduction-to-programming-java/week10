package uk.ac.uos.i2j.week10;

public class Symbol {
	public enum Type { OPEN, CLOSE, SLASH, WORD, SPACE, OTHER };
	
	public final Type type;
	public final String value;
	
	public Symbol(Type type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public Symbol(Type type) {
		this(type, null);
	}
}
