package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import assignment3.EmployeeDB;

/**
 * Generated JUnit test class to test the EmployeeDB interface. It can be used
 * to test both the client and the server implementation.
 * 
 * @author bonii
 * 
 */
public class EmployeeDBTest {
	private EmployeeDB employeeDB = null;

	@Before
	public void setUp() throws Exception {
		// employeeDB = SimpleEmployeeDB.getInstance();By setting the employeeDB
		// to either the server implementation first, you can write the server
		// implementation and test it. You dont even have to run the Jetty
		// HTTP server, you can just implement SimpleEmployeeDB and test it
		//
		// employeeDB = new EmployeeDBHTTPClient(); Once you have written the
		// client you can test the full system for the same test cases and it
		// should work, welcome to Unit testing :-)
		//
	}

	@Test
	public void testAddEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testListAllEmployees() {
		fail("Not yet implemented");
	}

	@Test
	public void testListEmployeesInDept() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrementSalaryOfDepartment() {
		fail("Not yet implemented");
	}

}
