package mysystem.web;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Log {
	public static void logOut(String cls, String metod, String msg) {
		System.out.println("[" + cls + "." + metod + " - " + new Date() + "] " + msg);
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

		logOut(cls, metod, "getAuthType: '" + req.getAuthType() + "'");
		logOut(cls, metod, "getContentLength: '" + req.getContentLength() + "'");
		logOut(cls, metod, "getContentLengthLong: '" + req.getContentLengthLong() + "'");
		logOut(cls, metod, "getContentType: '" + req.getContentType() + "'");
		logOut(cls, metod, "getContextPath: '" + req.getContextPath() + "'");
		logOut(cls, metod, "getLocalAddr: '" + req.getLocalAddr() + "'");
		logOut(cls, metod, "getLocalName: '" + req.getLocalName() + "'");
		logOut(cls, metod, "getLocalPort: '" + req.getLocalPort() + "'");
		logOut(cls, metod, "getMethod: '" + req.getMethod() + "'");
		logOut(cls, metod, "getPathInfo: '" + req.getPathInfo() + "'");
		logOut(cls, metod, "getPathTranslated: '" + req.getPathTranslated() + "'");
		logOut(cls, metod, "getProtocol: '" + req.getProtocol() + "'");
		logOut(cls, metod, "getQueryString: '" + req.getQueryString() + "'");
		logOut(cls, metod, "getRemoteAddr: '" + req.getRemoteAddr() + "'");
		logOut(cls, metod, "getRemoteHost: '" + req.getRemoteHost() + "'");
		logOut(cls, metod, "getRemotePort: '" + req.getRemotePort() + "'");
		logOut(cls, metod, "getRemoteUser: '" + req.getRemoteUser() + "'");
		logOut(cls, metod, "getRequestedSessionId: '" + req.getRequestedSessionId() + "'");
		logOut(cls, metod, "getRequestURI: '" + req.getRequestURI() + "'");
		logOut(cls, metod, "getScheme: '" + req.getScheme() + "'");
		logOut(cls, metod, "getServerName: '" + req.getServerName() + "'");
		logOut(cls, metod, "getServerPort: '" + req.getServerPort() + "'");
		logOut(cls, metod, "getServletPath: '" + req.getServletPath() + "'");

		logOut(cls, metod, "changeSessionId: '" + req.changeSessionId() + "'");

		Enumeration<String> paramName = req.getParameterNames();
		if (paramName.hasMoreElements())
			logOut(cls, metod, "List Parameter: ");
		else
			logOut(cls, metod, "Parameter - Not elements. ");
		while (paramName.hasMoreElements()) {
			String st = paramName.nextElement();

			logOut(cls, metod, "  >> [" + st + "]='" + req.getParameter(st) + "'");
		}

		Enumeration<String> attrName = req.getAttributeNames();
		if (attrName.hasMoreElements())
			logOut(cls, metod, "List Attribute: ");
		else
			logOut(cls, metod, "Attribute - Not elements. ");
		while (attrName.hasMoreElements()) {
			String st = attrName.nextElement();

			logOut(cls, metod, "  >> [" + st + "]='" + req.getAttribute(st) + "'");
		}

		Cookie[] cook = req.getCookies();
		if (cook == null)
			logOut(cls, metod, "Cookies - Not elements. ");
		else {
			for (int i = 0; i < cook.length; i++) {
				logOut(cls, metod, "  > cook["+i+"]:'" + cook[i].toString() + "'");
				logOut(cls, metod, "  >>> getComment:'" + cook[i].getComment() + "'");
				logOut(cls, metod, "  >>> getDomain:'" + cook[i].getDomain() + "'");
				logOut(cls, metod, "  >>> getMaxAge:'" + cook[i].getMaxAge() + "'");
				logOut(cls, metod, "  >>> getName:'" + cook[i].getName() + "'");
				logOut(cls, metod, "  >>> getPath:'" + cook[i].getPath() + "'");
				logOut(cls, metod, "  >>> getValue:'" + cook[i].getValue() + "'");
				logOut(cls, metod, "  >>> getVersion:'" + cook[i].getVersion() + "'");
				logOut(cls, metod, "  >>> getSecure:'" + cook[i].getSecure() + "'");
				logOut(cls, metod, "  >>> isHttpOnly:'" + cook[i].isHttpOnly() + "'");

			}

		}
//		logOut(cls, metod, "getRemoteUser: '" + req. + "'");
		logOut(cls, metod, "getRemoteUser: '" + req.getRemoteUser() + "'");
		logOut(cls, metod, "getRemoteUser: '" + req.getRemoteUser() + "'");
		logOut(cls, metod, "getRemoteUser: '" + req.getRemoteUser() + "'");
		logOut(cls, metod, "getRemoteUser: '" + req.getRemoteUser() + "'");

	}
}
