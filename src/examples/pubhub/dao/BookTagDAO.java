package examples.pubhub.dao;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;


public interface BookTagDAO {
	
	public List<BookTag> getAllBookTags(); // to retrieve all tags that have been added to a given book
	public List<Book> getAllBooksByTag(String tagName); // to retrieve all books that have a given tag
	public List<BookTag> getBookTagByISBN(String isbn);
	

	
	public boolean addBookTag(BookTag bookTag); // to add a tag to a book, given the tag name and a reference to a book
	public boolean removeBookTag(BookTag bookTag); // to remove a tag from a book, given the tag name and a reference to a book
}
