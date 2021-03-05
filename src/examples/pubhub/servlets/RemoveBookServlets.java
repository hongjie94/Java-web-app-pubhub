package examples.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class RemoveBookServlets
 */
@WebServlet("/RemoveBook")
public class RemoveBookServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		
		String isbn13 = request.getParameter("isbn13");
		Book book = new Book();
		book.setIsbn13(isbn13);
		BookDAO bookDao = DAOUtilities.getBookDAO();
		isSuccess =	bookDao.deleteBookByISBN(isbn13);
		if(isSuccess){
			request.getSession().setAttribute("message", "Book successfully removed");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookPublishing");
		}else {
			request.getSession().setAttribute("message", "Please remove the Book Tag first then remove the Book!");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
		}
	}

}
