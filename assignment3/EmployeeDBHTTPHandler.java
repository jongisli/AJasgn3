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
		
				
		String uri = req.getRequestURI().trim().toUpperCase();
		
		/*
				
		*/
		if (uri.equalsIgnoreCase("/addemployee")) {
			// Retrieve the employee record from req and invoke methods on
			// SimpleEmployeeDB
			// SimpleEmployeeDB.getInstance().addEmployee(emp)
			
			int id = Integer.parseInt(req.getParameter("id"));
			String name = req.getParameter("name");
			int dep = Integer.parseInt(req.getParameter("department"));
			float salary = Float.parseFloat(req.getParameter("salary"));
			
			Employee emp = new Employee();
			emp.setId(id);
			emp.setName(name);
			emp.setDepartment(dep);
			emp.setSalary(salary);
			
			SimpleEmployeeDB.getInstance().addEmployee(emp);
		
		}
		else if (uri.equalsIgnoreCase("/listallemployees")) {
			
			XStream xmlStream = new XStream(new StaxDriver());
			String xmlString;

			List<Employee> emps = new ArrayList<Employee>();
			emps = SimpleEmployeeDB.getInstance().listAllEmployees();
			
			xmlString = xmlStream.toXML(emps);
			
			res.setContentType("application/xml");
			res.getWriter().print(xmlString);

		}
		else if(uri.equalsIgnoreCase("/listemployeesindept")) {
			res.setContentType("text/html;charset=utf-8");
			res.setStatus(HttpServletResponse.SC_OK);
			
			XStream xmlStream = new XStream(new StaxDriver());
			
			String xmlString;

			int len = req.getContentLength();
			BufferedReader reqReader = req.getReader();
			char[] cbuf = new char[len];
			reqReader.read(cbuf);
			reqReader.close();
			String content = new String(cbuf);
			
			List<Integer> deps = (List<Integer>) xmlStream.fromXML(content);
			
			List<Employee> emps = new ArrayList<Employee>();
			emps = SimpleEmployeeDB.getInstance().listEmployeesInDept(deps);
			
			xmlString = xmlStream.toXML(emps);
			
			res.setContentType("application/xml");
			res.getWriter().print(xmlString);
			
		} else if(uri.equalsIgnoreCase("/incrementsalaryofdepartment")) {
			res.setContentType("text/html;charset=utf-8");
			res.setStatus(HttpServletResponse.SC_OK);
			
			XStream xmlStream = new XStream(new StaxDriver());

			int len = req.getContentLength();
			BufferedReader reqReader = req.getReader();
			char[] cbuf = new char[len];
			reqReader.read(cbuf);
			reqReader.close();
			String content = new String(cbuf);
	
			
			List<SalaryIncrement> si = (List<SalaryIncrement>) xmlStream.fromXML(content);
			
			try {
				SimpleEmployeeDB.getInstance().incrementSalaryOfDepartment(si);
			} catch (DepartmentNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeSalaryIncrementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		baseRequest.setHandled(true);
	}

}
