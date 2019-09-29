package mysystem.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mysystem.dao.RightDAO;
import mysystem.model.Right;
import mysystem.model.User;

import static mysystem.web.Log.*;

/**
 * Servlet implementation class RightServlet
 */
@WebServlet("/right/*")
public class RightServlet extends HttpServlet {
	RightDAO rightDAO;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		logOut("RightServlet", "init", "Enter in metod");
		rightDAO = RightDAO.getInstance();
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RightServlet() {
		super();
		logOut("RightServlet", "Constructor", "Enter in metod");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		logOut("RightServlet", "doGet", "Enter in metod");
		String action = request.getPathInfo();
		printInt("RightServlet", "doGet", request);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertRight(request, response);
				break;
			case "/delete":
				deleteRight(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateRight(request, response);
				break;
			case "/show_user":
				showUser(request, response);
				break;
			default:
				listRight(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logOut("RightServlet", "showUser", "Enter in metod");
		long idRight = Long.parseLong(request.getParameter("id"));
		Right existingRight = rightDAO.getByID(idRight);
		List<User> listUser = rightDAO.getUserForRight(idRight);
		request.setAttribute("listUser", listUser);
		request.setAttribute("right", existingRight);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-user-list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logOut("RightServlet", "doPost", "Enter in metod");
		doGet(request, response);

	}

	private void listRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		logOut("RightServlet", "listRight", "Enter in metod");
		List<Right> listRight = rightDAO.getModelsAll();
		request.setAttribute("listRight", listRight);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logOut("RightServlet", "showNewForm", "Enter in metod");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		logOut("RightServlet", "showEditForm", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));
		Right existingRight = rightDAO.getByID(id);
		logOut("RightServlet", "showEditForm", "id:" + id);
		logOut("RightServlet", "showEditForm", "right:" + existingRight);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		request.setAttribute("right", existingRight);
		dispatcher.forward(request, response);

	}

	private void insertRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("RightServlet", "insertRight", "Enter in metod");
		String uri = request.getParameter("uri");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");
		Right newRight = Right.NewInstance(-1, uri, shortName, longName, desc);
		rightDAO.addModel(newRight);
		response.sendRedirect("list");
	}

	private void updateRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("RightServlet", "updateRight", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));
		String uri = request.getParameter("uri");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");
		logOut("RightServlet", "updateRight", "   id: " + id);
		logOut("RightServlet", "updateRight", "   uri: " + uri);
		logOut("RightServlet", "updateRight", "   shortName: " + shortName);
		logOut("RightServlet", "updateRight", "   longName: " + longName);
		logOut("RightServlet", "updateRight", "   desc: " + desc);
		Right right = Right.storageFind(id);

		if (right == null) {
			logOut("RightServlet", "updateRight", "   right = NULL");
			// если записи нет в кэше, то проверяю есть ли он
			if ((right = rightDAO.getByID(id)) == null)
				// создаю новый экземпляр и инициализирую его состояние
				right = Right.NewInstance(id, uri, shortName, longName, desc);

		}
		if (right != null) {
			// изменяю данные
			logOut("RightServlet", "updateRight", "   right != NULL;  right: " + right);
			right.setUri(uri);
			right.setShortName(shortName);
			right.setLongName(longName);
			right.setDesc(desc);
			logOut("RightServlet", "updateRight", "   update ...   right: " + right);
		}
		if (rightDAO.updateModel(right))
			logOut("RightServlet", "updateRight", "   successful data update");
		else
			logOut("RightServlet", "updateRight", "   data not updated");

		response.sendRedirect("list");
	}

	private void deleteRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("RightServlet", "deleteRight", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));

		if (rightDAO.deleteModel(id)) {
			Right right = Right.storageFind(id);
			if (right != null)
				Right.storageDel(right);

		}
		response.sendRedirect("list");

	}
}
