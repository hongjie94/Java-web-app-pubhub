package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class BooksbyTagServlet
 */

@WebServlet("/BooksbyTagServlet")
public class BooksbyTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag = request.getParameter("tag");
		
		BookTagDAO dao = DAOUtilities.getBookTagDAO();
		List<Book> books = dao.getAllBooksByTag(tag);

		request.setAttribute("books", books);
		request.setAttribute("book_tags", tag);
		
		request.getRequestDispatcher("booksByTagDetails.jsp").forward(request, response);
		
	}

}
