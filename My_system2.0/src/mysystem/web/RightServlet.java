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

/**
 * Servlet implementation class RightServlet
 */
@WebServlet("/")
public class RightServlet extends HttpServlet {
	RightDAO rightDAO;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		rightDAO = new RightDAO();
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RightServlet() {
		super();
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
		String action = request.getServletPath();

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
			default:
				listRight(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void listRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Right> listRight = rightDAO.getModelsAll();
		request.setAttribute("listRight", listRight);
		RequestDispatcher dispatcher = request.getRequestDispatcher("views/right-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		Right existingRight = rightDAO.getByID(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/right-form.jsp");
		request.setAttribute("right", existingRight);
		dispatcher.forward(request, response);

	}

	private void insertRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
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
		long id = Long.parseLong(request.getParameter("id"));
		String uri = request.getParameter("uri");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");

		Right right = Right.storageFind(id);

		if (right == null) {
			// если записи нет в кэше, то проверяю есть ли он
			if ((right = rightDAO.getByID(id)) == null)
				// создаю новый экземпляр и инициализирую его состояние
				right = Right.NewInstance(id, uri, shortName, longName, desc);
			else {
				// изменяю данные
				right.setUri(uri);
				right.setShortName(shortName);
				right.setLongName(longName);
				right.setDesc(desc);

			}
		}
		rightDAO.updateModel(right);

		response.sendRedirect("list");
	}

	private void deleteRight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		long id = Long.parseLong(request.getParameter("id"));

		if (rightDAO.deleteModel(id)) {
			Right right = Right.storageFind(id);
			if (right != null)
				Right.storageDel(right);

		}
		response.sendRedirect("list");

	}
}
