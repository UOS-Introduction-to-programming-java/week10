package uk.ac.uos.i2j.week10;

public class Element {
	public final String name;
	public final Document body;
	
	public Element(String name, Document body) {
		this.name = name;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "Element(name=" + name + ",body=" + body + ")";
	}
}
