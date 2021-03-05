package examples.pubhub.dao;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation for the BookTagDAO, responsible for querying the database for BookTag objects.
 */

public class BookTagDAOImpl implements BookTagDAO {
	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public List<BookTag> getAllBookTags() {
		
		List<BookTag> bookTagList = new ArrayList<>();

		try {
			
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM book_tags";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				
				// We need to populate a Book Tag object with info for each row from our query result
				BookTag bookTag = new BookTag();
				
				// Each variable in our Book Tag object maps to a column in a row from our results.
				bookTag.setTagName(rs.getString("tag_name"));
				bookTag.setIsbn13(rs.getString("isbn_13"));

				// Finally we add it to the list of Book Tag objects returned by this query.
				bookTagList.add(bookTag);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return bookTagList;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<BookTag> getBookTagByISBN(String isbn) {
		List<BookTag> bookTagList = new ArrayList<>();

		try {
			
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM book_tags WHERE isbn_13 = ?)";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				
				// We need to populate a Book Tag object with info for each row from our query result
				BookTag bookTag = new BookTag();
				
				// Each variable in our Book Tag object maps to a column in a row from our results.
				bookTag.setTagName(rs.getString("tag_name"));
				bookTag.setIsbn13(rs.getString("isbn_13"));

				// Finally we add it to the list of Book Tag objects returned by this query.
				bookTagList.add(bookTag);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return bookTagList;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public List<Book> getAllBooksByTag(String tagName) {
		
		List<Book> book = new ArrayList<>();
		
			try {
				
				connection = DAOUtilities.getConnection();
				
				String sql = "SELECT * FROM books WHERE isbn_13 IN (SELECT isbn_13 from book_tags WHERE tag_name = ?)";	// Note the ? in the query
				stmt = connection.prepareStatement(sql);
				
				// This command populate the 1st '?' with the title and wildcards, between ' '
				stmt.setString(1, tagName);	
				
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()){
		        	Book book1 = new Book();
		        	
		            book1.setIsbn13(rs.getString("isbn_13"));
		 			book1.setTitle(rs.getString("title"));
		 			book1.setAuthor(rs.getString("author"));
		 			book1.setPublishDate(LocalDate.now());
		 			book1.setPrice(Double.parseDouble(rs.getString("price")));
		 			
		 			book.add(book1); 
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}
			
			return book;
	}
	

	/*------------------------------------------------------------------------------------------------*/


	@Override
	public boolean addBookTag(BookTag bTag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql ="INSERT INTO book_tags(isbn_13, tag_name) VALUES (?, ?)" ; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, bTag.getIsbn13());
			stmt.setString(2, bTag.getTagName()); 
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public boolean removeBookTag(BookTag bTag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tags WHERE isbn_13=? AND tag_name =?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, bTag.getIsbn13());
			stmt.setString(2, bTag.getTagName());
			

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}