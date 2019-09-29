package mysystem.web;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletMapping;
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

			logOut(cls, metod, String.format("%4s %25s: '%s'", ">>", "[" + st + "]", req.getParameter(st)));
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
}
