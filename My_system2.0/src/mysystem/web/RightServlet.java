package mysystem.web;

import static mysystem.Log.*;

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

/**
 * Servlet implementation class RightServlet
 */
@WebServlet("/right/*")
public class RightServlet extends HttpServlet {
	private RightDAO rightDAO;
	private static final String CLS_NAME = "RightServlet";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		logEntering(CLS_NAME, "init");
		super.init();

		rightDAO = RightDAO.getInstance();

		logExiting(CLS_NAME, "init");
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RightServlet() {
		super();
		logEntering(CLS_NAME, "Constructor");

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
		logEntering(CLS_NAME, "doGet");

		String action = request.getPathInfo();
		printInt(CLS_NAME, "doGet", request);
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

		logExiting(CLS_NAME, "doGet");
	}

	private void showUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logEntering(CLS_NAME, "showUser");

		long idRight = Long.parseLong(request.getParameter("id"));

		Right existingRight = rightDAO.getByID(idRight);
		List<User> listUser = rightDAO.getUserForRight(idRight);

		request.setAttribute("listUser", listUser);
		request.setAttribute("right", existingRight);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-user-list.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "showUser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logEntering(CLS_NAME, "doPost");

		doGet(request, response);

		logExiting(CLS_NAME, "doPost");
	}

	private void listRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		logEntering(CLS_NAME, "listRight");

		List<Right> listRight = rightDAO.getModelsAll();

		request.setAttribute("listRight", listRight);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-list.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "listRight");
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logEntering(CLS_NAME, "showNewForm");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "showNewForm");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		logEntering(CLS_NAME, "showEditForm");

		long id = Long.parseLong(request.getParameter("id"));

		Right existingRight = rightDAO.getByID(id);

		logp(CLS_NAME, "showEditForm", "id:" + id);
		logp(CLS_NAME, "showEditForm", "right:" + existingRight);

		request.setAttribute("right", existingRight);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "showEditForm");
	}

	private void insertRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logEntering(CLS_NAME, "insertRight");

		String uri = request.getParameter("uri");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");

		Right newRight = Right.NewInstance(-1, uri, shortName, longName, desc);
		rightDAO.addModel(newRight);

		response.sendRedirect("list");

		logExiting(CLS_NAME, "insertRight");
	}

	private void updateRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logEntering(CLS_NAME, "updateRight");

		long id = Long.parseLong(request.getParameter("id"));
		String uri = request.getParameter("uri");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");

		logp(CLS_NAME, "updateRight", "   id: " + id);
		logp(CLS_NAME, "updateRight", "   uri: " + uri);
		logp(CLS_NAME, "updateRight", "   shortName: " + shortName);
		logp(CLS_NAME, "updateRight", "   longName: " + longName);
		logp(CLS_NAME, "updateRight", "   desc: " + desc);

		Right right = Right.storageFind(id);

		if (right == null) {
			logp(CLS_NAME, "updateRight", "   right = NULL");
			// если записи нет в кэше, то проверяю есть ли он
			if ((right = rightDAO.getByID(id)) == null)
				// создаю новый экземпляр и инициализирую его состояние
				right = Right.NewInstance(id, uri, shortName, longName, desc);

		}
		if (right != null) {
			// изменяю данные
			logp(CLS_NAME, "updateRight", "   right != NULL;  right: " + right);
			right.setUri(uri);
			right.setShortName(shortName);
			right.setLongName(longName);
			right.setDesc(desc);
			logp(CLS_NAME, "updateRight", "   update ...   right: " + right);
		}
		if (rightDAO.updateModel(right))
			logp(CLS_NAME, "updateRight", "   successful data update");
		else
			logp(CLS_NAME, "updateRight", "   data not updated");

		response.sendRedirect("list");

		logExiting(CLS_NAME, "updateRight");
	}

	private void deleteRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logEntering(CLS_NAME, "deleteRight");

		long id = Long.parseLong(request.getParameter("id"));

		logp(CLS_NAME, "deleteRight", "id=" + id);

		if (rightDAO.deleteModel(id)) {

			Right right = Right.storageFind(id);
			if (right != null)
				Right.storageDel(right);

		}

		response.sendRedirect("list");

		logExiting(CLS_NAME, "deleteRight");
	}
}
