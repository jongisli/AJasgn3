package assignment3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * EmployeeDBHTTPClient implements the client side methods of EmployeeDB
 * interface using HTTP protocol. The methods must send HTTP requests to the
 * EmployeeDBHTTPServer
 * 
 * @author bonii
 * 
 */
public class EmployeeDBHTTPClient implements EmployeeDBClient, EmployeeDB {
	private HttpClient client = null;
	private static final String SPLIT_DEPT = ";";
	private static final String filePath = "/Users/jonegilsson/Dropbox/KU/Courses/advanced_java_programming/workspace/assignment3/src/assignment3/departmentservermapping.properties";
	private Map<Integer, String> departmentServerURLMap;

	public EmployeeDBHTTPClient() throws Exception {
		initMappings();
		client = new HttpClient();
		client.setTimeout(3000);
		client.start();
	}

	public void initMappings() throws IOException, IllegalArgumentException {
		Properties props = new Properties();
		departmentServerURLMap = new ConcurrentHashMap<Integer, String>(); // Now
																			// my
																			// lookups
																			// are
																			// thread-safe
																			// even
																			// if
																			// there
																			// is
																			// weird
																			// client
																			// :-)

		props.load(new FileInputStream(filePath));
		for (String serverURL : props.stringPropertyNames()) {
			String departmentString = props.getProperty(serverURL);
			if (!serverURL.startsWith("http://")) {
				serverURL = new String("http://" + serverURL);
			}
			if (!serverURL.endsWith("/")) {
				serverURL = new String(serverURL + "/");
			}

			String[] departments = departmentString.split(SPLIT_DEPT);
			for (String department : departments) {
				int departmentValue = Integer.parseInt(department);
				if (departmentServerURLMap.containsKey(Integer
						.valueOf(departmentValue))) {
					throw new IllegalArgumentException("Duplicate key found");
				}
				departmentServerURLMap.put(Integer.valueOf(departmentValue),
						serverURL);
			}
		}
	}

	@Override
	public void addEmployee(Employee emp) {
		String serverURL = "";
		try {
			serverURL = getServerURLForDepartment(emp.department);
		} catch (DepartmentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ContentExchange exchange = new ContentExchange();
		String requestURL = String.format(
				serverURL + "addemployee?id=%d&name=%s&department=%d&salary=%d", 
				emp.id, emp.name, emp.department, (int) emp.salary);
		exchange.setURL(requestURL);
		try {
			client.send(exchange);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> listAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		List<String> visitedServers = new ArrayList<String>();
		for (String serverURL : departmentServerURLMap.values())
		{
			if (visitedServers.contains(serverURL))
				continue;
			else
				visitedServers.add(serverURL);
			
			ContentExchange exchange = new ContentExchange();
			exchange.setMethod("POST");
			exchange.setURL(serverURL + "listallemployees");
			try 
			{
				client.send(exchange);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			try 
			{
				exchange.waitForDone();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
			String response = null;
			try {
				response = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
			XStream xmlStream = new XStream(new StaxDriver());
			
			List<Employee> subEmployee = new ArrayList<Employee>();
			subEmployee = (ArrayList<Employee>) xmlStream.fromXML(response);
			employees.addAll(subEmployee);
		}
		return employees;
	}

	@Override
	public List<Employee> listEmployeesInDept(List<Integer> departmentIds) {
		if (departmentIds.size() == 0)
		{
			return null;
		}
		String serverURL = "";
		try {
			serverURL = getServerURLForDepartment(departmentIds.get(0));
		} catch (DepartmentNotFoundException e) {
			e.printStackTrace();
		}
		XStream xmlStream = new XStream(new StaxDriver());
		String deptIdsString = xmlStream.toXML(departmentIds);
		ContentExchange exchange = new ContentExchange();
		Buffer buffer = new ByteArrayBuffer(deptIdsString);
		exchange.setMethod("POST");
		exchange.setRequestContent(buffer);
		exchange.setURL(serverURL + "listemployeesindept");
		try 
		{
			client.send(exchange);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try 
		{
			exchange.waitForDone();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String response = "";
		try {
			response = exchange.getResponseContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Employee> employees = (List<Employee>) xmlStream.fromXML(response);

		return employees;
	}

	@Override
	public void incrementSalaryOfDepartment(
			List<SalaryIncrement> salaryIncrements)
			throws DepartmentNotFoundException,
			NegativeSalaryIncrementException {
		// TODO Auto-generated method stub
	}

	/**
	 * Returns the server URL (starting with http:// and ending with /) to
	 * contact for a department
	 */
	public String getServerURLForDepartment(int departmentId)
			throws DepartmentNotFoundException {
		if (!departmentServerURLMap.containsKey(departmentId)) {
			throw new DepartmentNotFoundException("department " + departmentId
					+ " does not exist in mapping");
		}
		return departmentServerURLMap.get(departmentId);
	}

}
