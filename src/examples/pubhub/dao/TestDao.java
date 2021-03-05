package examples.pubhub.dao;
import java.util.List;
import examples.pubhub.model.*;

public class TestDao {
	public static void main(String[] args){
	    BookTagDAO bookTagDao = new BookTagDAOImpl();
	    BookDAO bookDao = new BookDAOImpl();
	    
	    List<Book> bookList = bookDao.getAllBooks();
	    List<BookTag> tagList = bookTagDao.getAllBookTags();
	    
	    // Test Book
	    for (int i = 0; i < bookList.size(); i++){
	    	Book book = bookList.get(i);
	    	System.out.println(book.getIsbn13());
	    	System.out.println(book.getTitle());
	    	System.out.println(book.getAuthor());
	    	System.out.println(book.getPublishDate());
	    	System.out.println(book.getPrice());
	    	System.out.println(book.getContent()+"\n");
	    	
	    }
	    
	    // Test Book Tag
	    for (int i = 0; i < tagList.size(); i++){
	    	BookTag tag = tagList.get(i);
	    	System.out.println(tag.getIsbn13());
	    	System.out.println(tag.getTagName()+"\n");
	    }
	  }
}
