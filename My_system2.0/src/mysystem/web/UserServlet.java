package mysystem.web;

import static mysystem.Log.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mysystem.dao.RightDAO;
import mysystem.dao.UserDAO;
import mysystem.model.Right;
import mysystem.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;
	private RightDAO rightDAO;
	private static final String CLS_NAME = "UserServlet";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		logEntering(CLS_NAME, "init");

		super.init();

		userDAO = UserDAO.getInstance();
		rightDAO = RightDAO.getInstance();

		logExiting(CLS_NAME, "init");
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
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/show_user":
				showUser(request, response);
				break;
			default:
				listUser(request, response);
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
//		logEntering(CLS_NAME, "showUser");
//
//		long idRight = Long.parseLong(request.getParameter("id"));
//
//		Right existingRight = rightDAO.getByID(idRight);
//		List<User> listUser = rightDAO.getUserForRight(idRight);
//
//		request.setAttribute("listUser", listUser);
//		request.setAttribute("right", existingRight);
//
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-user-list.jsp");
//		dispatcher.forward(request, response);
//
//		logExiting(CLS_NAME, "showUser");
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

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		logEntering(CLS_NAME, "listUser");

		List<User> listUser = userDAO.getModelsAll();

		request.setAttribute("listUser", listUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user-list.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "listUser");
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logEntering(CLS_NAME, "showNewForm");

		request.setAttribute("listAllRights", rightDAO.getModelsAll());

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user-form.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "showNewForm");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		logEntering(CLS_NAME, "showEditForm");

		long id = Long.parseLong(request.getParameter("id"));

		User existingUser = userDAO.getByID(id);

		logp(CLS_NAME, "showEditForm", "id:" + id);
		logp(CLS_NAME, "showEditForm", "user:" + existingUser);

		request.setAttribute("user", existingUser);
		request.setAttribute("listAllRights", rightDAO.getModelsAll());

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user-form.jsp");
		dispatcher.forward(request, response);

		logExiting(CLS_NAME, "showEditForm");
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		logEntering(CLS_NAME, "insertUser");

		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		List<Right> lRight = new ArrayList<>();
		String[] listIDRight = request.getParameterValues("right");

		if (listIDRight != null && listIDRight.length > 1)
			for (int i = 0; i < listIDRight.length; i++) {
				try {
					Long id_right = Long.valueOf(listIDRight[i]);
					Right r = rightDAO.getByID(id_right);
					if (r != null)
						lRight.add(r);
				} catch (Exception e) {

				}

			}

		User newUser = User.newInstance(-1, login, pass, lRight);
		userDAO.addModel(newUser);

		response.sendRedirect("list");

		logExiting(CLS_NAME, "insertUser");
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		logEntering(CLS_NAME, "updateUser");

		long id = Long.parseLong(request.getParameter("id"));
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");

		logp(CLS_NAME, "updateUser", "   id: " + id);
		logp(CLS_NAME, "updateUser", "   login: " + login);
		List<Right> lRight = new ArrayList<>();
		String[] listIDRight = request.getParameterValues("right");

		if (listIDRight != null && listIDRight.length > 1)
			for (int i = 0; i < listIDRight.length; i++) {
				try {
					Long id_right = Long.valueOf(listIDRight[i]);
					Right r = rightDAO.getByID(id_right);
					if (r != null)
						lRight.add(r);
				} catch (Exception e) {

				}

			}

		// logp(CLS_NAME, "updateUser", " pass: " + pass);

		User user = User.storageFind(id);

		if (user == null) {
			logp(CLS_NAME, "updateUser", "   user = NULL");
			// если записи нет в кэше, то проверяю есть ли он
			if ((user = userDAO.getByID(id)) == null)
				// создаю новый экземпляр и инициализирую его состояние
				user = User.newInstance(id, login, pass, lRight);

		}
		if (user != null) {
			// изменяю данные
			logp(CLS_NAME, "updateUser", "   user != NULL;  user: " + user);
			user.setLogin(login);
			//user.set
			if (pass.length() > 0)
				try {
					user.setPassHesh(pass);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			logp(CLS_NAME, "updateUser", "   update ...   user: " + user);
		}
		if (userDAO.updateModel(user))
			logp(CLS_NAME, "updateUser", "   successful data update");
		else
			logp(CLS_NAME, "updateUser", "   data not updated");

		response.sendRedirect("list");

		logExiting(CLS_NAME, "updateUser");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		logEntering(CLS_NAME, "deleteUser");

		long id = Long.parseLong(request.getParameter("id"));

		logp(CLS_NAME, "deleteUser", "id=" + id);

		if (userDAO.deleteModel(id)) {

			User user = User.storageFind(id);
			if (user != null)
				User.storageDel(user);

		}

		response.sendRedirect("list");

		logExiting(CLS_NAME, "deleteUser");
	}
}
