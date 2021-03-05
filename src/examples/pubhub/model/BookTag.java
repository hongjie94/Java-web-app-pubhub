package examples.pubhub.model;

// POJO for BookTag
public class BookTag {
	
	private String isbn13;
	private String tagName;

	public BookTag(String isbn13, String tag) {
		this.isbn13 = isbn13;
		this.tagName = tag;
	}
	
	// Default constructor
	public BookTag() {
		this.isbn13 = null;
		this.tagName = null;
	}
	
	public String getIsbn13() {
		return isbn13;
	}
	
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}