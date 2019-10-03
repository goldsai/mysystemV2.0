package mysystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Log {
	public static void logOut(String cls, String metod, String msg) {
		System.out.println(
				"[" + cls + "." + metod + " - " + String.format("%1$tH:%1$tM:%1$tS.%1$tL", new Date()) + "] " + msg);
	}

	public static void printEnumeration(String cls, String metod, String param, Enumeration<String> en) {

		if (en.hasMoreElements())
			logOut(cls, metod, "List " + param + ": ");
		else
			logOut(cls, metod, param + " - Not elements ");
		while (en.hasMoreElements()) {

		}
	}

	public static void printInt(String cls, String metod, HttpServletRequest req) {

		logOut(cls, metod, String.format("%-23s: '%s'", "AuthType", req.getAuthType()));
		logOut(cls, metod, String.format("%-23s: '%s'", "ContentLength", req.getContentLength()));
		logOut(cls, metod, String.format("%-23s: '%s'", "ContentLengthLong", req.getContentLengthLong()));
		logOut(cls, metod, String.format("%-23s: '%s'", "ContentType", req.getContentType()));
		logOut(cls, metod, String.format("%-23s: '%s'", "ContextPath", req.getContextPath()));
		logOut(cls, metod, String.format("%-23s: '%s'", "RemoteUser", req.getDispatcherType()));
		logOut(cls, metod, String.format("%-23s: '%s'", "LocalAddr", req.getLocalAddr()));
		logOut(cls, metod, String.format("%-23s: '%s'", "LocalName", req.getLocalName()));
		logOut(cls, metod, String.format("%-23s: '%s'", "LocalPort", req.getLocalPort()));
		logOut(cls, metod, String.format("%-23s: '%s'", "Method", req.getMethod()));
		logOut(cls, metod, String.format("%-23s: '%s'", "PathInfo", req.getPathInfo()));
		logOut(cls, metod, String.format("%23s: '%s'", "PathTranslated", req.getPathTranslated()));
		logOut(cls, metod, String.format("%23s: '%s'", "Protocol", req.getProtocol()));
		logOut(cls, metod, String.format("%23s: '%s'", "QueryString", req.getQueryString()));
		logOut(cls, metod, String.format("%23s: '%s'", "RemoteAddr", req.getRemoteAddr()));
		logOut(cls, metod, String.format("%23s: '%s'", "RemoteHost", req.getRemoteHost()));
		logOut(cls, metod, String.format("%23s: '%s'", "RemotePort", req.getRemotePort()));
		logOut(cls, metod, String.format("%23s: '%s'", "RemoteUser", req.getRemoteUser()));
		logOut(cls, metod, String.format("%23s: '%s'", "RequestedSessionId", req.getRequestedSessionId()));
		logOut(cls, metod, String.format("%23s: '%s'", "RequestURI", req.getRequestURI()));
		logOut(cls, metod, String.format("%23s: '%s'", "RequestURL", req.getRequestURL()));
		logOut(cls, metod, String.format("%23s: '%s'", "Scheme", req.getScheme()));
		logOut(cls, metod, String.format("%23s: '%s'", "ServerName", req.getServerName()));
		logOut(cls, metod, String.format("%23s: '%s'", "ServerPort", req.getServerPort()));
		logOut(cls, metod, String.format("%23s: '%s'", "ServletPath", req.getServletPath()));

		// logOut(cls, metod, "changeSessionId: '" + req.changeSessionId() + "'");

		Enumeration<String> paramName = req.getParameterNames();
		if (paramName.hasMoreElements())
			logOut(cls, metod, "List Parameter: ");
		else
			logOut(cls, metod, "Parameter - Not elements. ");
		while (paramName.hasMoreElements()) {
			String st = paramName.nextElement();
			String[] val_st=req.getParameterValues(st);
			logOut(cls, metod, String.format("%4s %25s: '%s'", ">>", "[" + st + "]", req.getParameter(st)));
			if (val_st!=null && val_st.length>1)
				for(int i=0;i<val_st.length;i++)
					logOut(cls, metod, String.format("%7s %22s: '%s'", ">>>>", "[" + st + "]", val_st[i]));
		}

		Enumeration<String> attrName = req.getAttributeNames();
		if (attrName.hasMoreElements())
			logOut(cls, metod, "List Attribute: ");
		else
			logOut(cls, metod, "Attribute - Not elements. ");
		while (attrName.hasMoreElements()) {
			String st = attrName.nextElement();

			logOut(cls, metod, String.format("%4s %25s: '%s'", ">>", "[" + st + "]", req.getAttribute(st)));
		}

		Cookie[] cook = req.getCookies();
		if (cook == null)
			logOut(cls, metod, "Cookies - Not elements. ");
		else {
			for (int i = 0; i < cook.length; i++) {
				logOut(cls, metod, String.format("%4s %-14s: '%s'", ">", "cook[" + i + "]", cook[i]));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getComment", cook[i].getComment()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getDomain", cook[i].getDomain()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getMaxAge", cook[i].getMaxAge()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getName", cook[i].getName()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getPath", cook[i].getPath()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getValue", cook[i].getValue()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getVersion", cook[i].getVersion()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "getSecure", cook[i].getSecure()));
				logOut(cls, metod, String.format("%6s %-12s: '%s'", ">>>", "isHttpOnly", cook[i].isHttpOnly()));

			}

		}

		Enumeration<String> headerNames = req.getHeaderNames();
		if (headerNames != null && headerNames.hasMoreElements())
			logOut(cls, metod, "List Header Names: ");
		else
			logOut(cls, metod, "Header Names - Not elements. ");
		while (headerNames.hasMoreElements()) {
			String st = headerNames.nextElement();

			logOut(cls, metod, String.format("%4s %25s: '%s'", ">>", "[" + st + "]", req.getHeader(st)));
//			Enumeration<String> headers = req.getHeaders(st);
//			if (headers != null)
//				while (headers.hasMoreElements())
//					logOut(cls, metod, "  >>>> [" + st + "] value='" + headers.nextElement() + "'");
		}

		logOut(cls, metod, String.format("%30s: '%s'", "AsyncStarted", req.isAsyncStarted()));
		logOut(cls, metod, String.format("%30s: '%s'", "AsyncSupported", req.isAsyncSupported()));
		logOut(cls, metod,
				String.format("%30s: '%s'", "RequestedSessionIdFromCookie", req.isRequestedSessionIdFromCookie()));
		// logOut(cls, metod, "isRequestedSessionIdFromUrl: '" +
		// req.isRequestedSessionIdFromUrl() + "'");
		logOut(cls, metod, String.format("%30s: '%s'", "RequestedSessionIdFromURL", req.isRequestedSessionIdFromURL()));
		logOut(cls, metod, String.format("%30s: '%s'", "RequestedSessionIdValid", req.isRequestedSessionIdValid()));
		logOut(cls, metod, String.format("%30s: '%s'", "Secure", req.isSecure()));
		logOut(cls, metod, String.format("%30s: '%s'", "TrailerFieldsReady", req.isTrailerFieldsReady()));
		logOut(cls, metod, "=========================================== end ===");
	}

	/**
	 * Логгер
	 */
	public static final Logger log = Logger.getLogger("mysystem");

	// статический блок инициализации логгера
	static {
		log.setLevel(Level.ALL);
		log.setUseParentHandlers(false);
		try {
			Handler handler = new FileHandler("%h/server.log", 2 * 1024 * 1024, 10, true);
			System.out.println("User.home:"+System.getProperty("user.home"));
			log.addHandler(handler);

		} catch (IOException e) {
			log.log(Level.SEVERE, "Can't  create log file handler", e);
			System.out.println("SAI> ERROR log");
		}
	}

	/**
	 * Логирует вход в метод param cls- имя класса
	 * 
	 * @param sourceMethod - имя метода
	 */
	public static void logEntering(String cls, String sourceMethod) {
		log.entering(cls, sourceMethod);
		logOut(cls, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует вход в метод с одним параметром param cls- имя класса
	 * 
	 * @param sourceMethod - имя метода
	 * @param params       - значение параметра (который передали в метод)
	 */
	public static void logEntering(String cls, String sourceMethod, Object params) {
		log.entering(cls, sourceMethod, params);
		logOut(cls, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует вход в метод с несколькими параметрами param cls- имя класса
	 * 
	 * @param sourceMethod - имя метода
	 * @param params       - массив значений параметров переданных в метод
	 */
	public static void logEntering(String cls, String sourceMethod, Object[] params) {
		log.entering(cls, sourceMethod, params);
		logOut(cls, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует выход из метода
	 * 
	 * @param cls-          имя класса
	 * @param sourceMethod- имя метода
	 */
	public static void logExiting(String cls, String sourceMethod) {
		log.exiting(cls, sourceMethod);
		logOut(cls, sourceMethod, "Exit metod");
	}

	/**
	 * Логирует выход из метода
	 * 
	 * @param cls-          имя класса
	 * @param sourceMethod- имя метода
	 * @param result-       объект возвращаемый из класса
	 */
	public static void logExiting(String cls, String sourceMethod, Object result) {
		log.exiting(cls, sourceMethod, result);
		logOut(cls, sourceMethod, "Exit metod (" + result + ")");
	}

	/**
	 * Логирует сообщение
	 * @param cls - имя класса
	 * @param sourceMethod - имя метода из которого переданно сообщение для записи в
	 *                     лог
	 * @param msg          - сообщение для логирования
	 */
	public static void logp(String cls, String sourceMethod, String msg) {
		log.logp(Level.SEVERE, cls, sourceMethod, msg);
		logOut(cls, sourceMethod, msg);
	}
	
	/**
	 * Логирует исключение
	 * @param cls -имя класса
	 * @param sourceMethod - имя метода
	 * @param e - исключение
	 */
	public static void logThrowing(String cls, String sourceMethod, Throwable e) {
		log.throwing(cls, sourceMethod, e);
		logOut(cls,sourceMethod,e.toString());
	}
	/**
	 * Логирует SQL исключения
	 * 
	 * @param ex
	 * @param sourceMethod
	 */
	public static void printSQLException(String cls, SQLException ex, String sourceMethod) {
		StringBuffer buf = new StringBuffer();
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				buf.append("SQLState: " + ((SQLException) e).getSQLState() + "\nError Code: "
						+ ((SQLException) e).getErrorCode() + "\nMessage: " + e.getMessage() + "\n");
				Throwable t = ex.getCause();
				while (t != null) {

					buf.append("Cause: " + t + "\n");
					t = t.getCause();
				}
				buf.append("\n");
			}
		}
		log.logp(Level.SEVERE, cls, sourceMethod, buf.toString(), ex);
		logOut(cls, sourceMethod, buf.toString());
	}

}
