package uk.ac.uos.i2j.week10;

public class Document {
	public final Element element;
	public final String text;

	public Document(Element element) {
		this.element = element;
		this.text = null;
	}

	public Document(String text) {
		this.element = null;
		this.text = text;
	}

	@Override
	public String toString() {
		return "Document(" + element != null ? "element" : "text" + ")";
	}
}
