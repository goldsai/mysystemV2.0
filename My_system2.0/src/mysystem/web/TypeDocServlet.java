package mysystem.web;

import static mysystem.Log.logOut;
import static mysystem.Log.printInt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import mysystem.dao.TypeDocDAO;

import mysystem.model.TypeDoc;


/**
 * Servlet implementation class TypeDocServlet
 */
@WebServlet("/type_doc/*")
public class TypeDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TypeDocDAO typeDocDAO;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		logOut("TypeDocServlet", "init", "Enter in metod");
		typeDocDAO = TypeDocDAO.getInstance();
	}



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TypeDocServlet() {
		super();
		logOut("TypeDocServlet", "Constructor", "Enter in metod");
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
		logOut("TypeDocServlet", "doGet", "Enter in metod");
		String action = request.getPathInfo();
		printInt("TypeDocServlet", "doGet", request);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertTypeDoc(request, response);
				break;
			case "/delete":
				deleteTypeDoc(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateTypeDoc(request, response);
				break;
//			case "/show_user":
//				showUser(request, response);
//				break;
			default:
				listTypeDoc(request, response);
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
		logOut("TypeDocServlet", "doPost", "Enter in metod");
		doGet(request, response);

	}

	private void listTypeDoc(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		logOut("TypeDocServlet", "listTypeDoc", "Enter in metod");
		List<TypeDoc> listTypeDoc = typeDocDAO.getModelsAll();
		request.setAttribute("listTypeDoc", listTypeDoc);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/typeDoc-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logOut("TypeDocServlet", "showNewForm", "Enter in metod");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/typeDoc-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		logOut("TypeDocServlet", "showEditForm", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));
		TypeDoc existingTypeDoc = typeDocDAO.getByID(id);
		logOut("TypeDocServlet", "showEditForm", "id:" + id);
		logOut("TypeDocServlet", "showEditForm", "typeDoc:" + existingTypeDoc);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/typeDoc-form.jsp");
		request.setAttribute("typeDoc", existingTypeDoc);
		dispatcher.forward(request, response);

	}

	private void insertTypeDoc(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("TypeDocServlet", "insertTypeDoc", "Enter in metod");
		String dirPath = request.getParameter("dirPath");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");
		TypeDoc newTypeDoc = TypeDoc.NewInstance(-1, dirPath, shortName, longName, desc);
		typeDocDAO.addModel(newTypeDoc);
		response.sendRedirect("list");
	}

	private void updateTypeDoc(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("TypeDocServlet", "updateRight", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));
		String dirPath = request.getParameter("dirPath");
		String shortName = request.getParameter("shortName");
		String longName = request.getParameter("longName");
		String desc = request.getParameter("desc");
		logOut("TypeDocServlet", "updateTypeDoc", "   id: " + id);
		logOut("TypeDocServlet", "updateTypeDoc", "   dirPath: " + dirPath);
		logOut("TypeDocServlet", "updateTypeDoc", "   shortName: " + shortName);
		logOut("TypeDocServlet", "updateTypeDoc", "   longName: " + longName);
		logOut("TypeDocServlet", "updateTypeDoc", "   desc: " + desc);
		TypeDoc typeDoc = TypeDoc.storageFind(id);

		if (typeDoc == null) {
			logOut("TypeDocServlet", "updateTypeDoc", "   typeDoc = NULL");
			// если записи нет в кэше, то проверяю есть ли он
			if ((typeDoc = typeDocDAO.getByID(id)) == null)
				// создаю новый экземпляр и инициализирую его состояние
				typeDoc = TypeDoc.NewInstance(id, dirPath, shortName, longName, desc);

		}
		if (typeDoc != null) {
			// изменяю данные
			logOut("TypeDocServlet", "updateTypeDoc", "   typeDoc != NULL;  typeDoc: " + typeDoc);
			typeDoc.setDirPath(dirPath);
			typeDoc.setShortName(shortName);
			typeDoc.setLongName(longName);
			typeDoc.setDesc(desc);
			logOut("TypeDocServlet", "updateTypeDoc", "   update ...   typeDoc: " + typeDoc);
		}
		if (typeDocDAO.updateModel(typeDoc))
			logOut("TypeDocServlet", "updateTypeDoc", "   successful data update");
		else
			logOut("TypeDocServlet", "updateTypeDoc", "   data not updated");

		response.sendRedirect("list");
	}

	private void deleteTypeDoc(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		logOut("TypeDocServlet", "deleteRight", "Enter in metod");
		long id = Long.parseLong(request.getParameter("id"));

		if (typeDocDAO.deleteModel(id)) {
			TypeDoc typeDoc = TypeDoc.storageFind(id);
			if (typeDoc != null)
				TypeDoc.storageDel(typeDoc);

		}
		response.sendRedirect("list");

	}
	
}
