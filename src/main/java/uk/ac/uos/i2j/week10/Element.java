package uk.ac.uos.i2j.week10;

import java.util.ArrayList;
import java.util.List;

public class Element {
	public final String name;
	public final List<Document> body;
	
	public Element(String name) {
		this.name = name;
		this.body = new ArrayList<>();
	}
	
	public void add(Document document) {
		body.add(document);
	}
}
