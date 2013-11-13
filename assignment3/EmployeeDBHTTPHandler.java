package assignment3;

//import StaxDriver;
//import XStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * EmployeeDBHTTPHandler is invoked when an HTTP request is received by the
 * EmployeeDBHTTPServer
 * 
 * @author bonii
 * 
 */
public class EmployeeDBHTTPHandler extends AbstractHandler {

	/**
	 * Although this method is thread-safe, what it invokes is not thread-safe
	 */
	public void handle(String target, Request baseRequest,
			HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		res.setContentType("text/html;charset=utf-8");
		res.setStatus(HttpServletResponse.SC_OK);
		
		String uri = req.getRequestURI().trim().toUpperCase();
		
		XStream xmlStream = new XStream(new StaxDriver());
		
		int len = req.getContentLength();
		BufferedReader reqReader = req.getReader();
		char[] cbuf = new char[len];
		reqReader.read(cbuf);
		reqReader.close();
		String content = new String(cbuf);
		
		String xmlString;
		
		
		if (uri.equalsIgnoreCase("/addemployee")) {
			Employee emp = (Employee)xmlStream.fromXML(content);
			SimpleEmployeeDB.getInstance().addEmployee(emp);
			// Retrieve the employee record from req and invoke methods on
			// SimpleEmployeeDB
			// SimpleEmployeeDB.getInstance().addEmployee(emp)
		}
		else if (uri.equalsIgnoreCase("/listallemployees")) {
			List<Employee> emps = new ArrayList<Employee>();
			emps = SimpleEmployeeDB.getInstance().listAllEmployees();
			xmlString = xmlStream.toXML(emps);
			
		}
		
		
		baseRequest.setHandled(true);
	}

}
