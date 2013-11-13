package assignment3;

import java.util.ArrayList;
import java.util.List;

public class SimpleEmployeeDB implements EmployeeDB {
	private static SimpleEmployeeDB instance = null;
	private List<Employee> instanceList = new ArrayList<Employee>();

	private SimpleEmployeeDB() {

	}

	public synchronized static SimpleEmployeeDB getInstance() {
		if (instance == null) {
			instance = new SimpleEmployeeDB();
		}
		return instance;
	}

	@Override
	public synchronized void addEmployee(Employee emp) {
		instanceList.add(emp);
	}

	@Override
	public synchronized List<Employee> listAllEmployees() {
		
		return instanceList;
	}

	@Override
	public synchronized List<Employee> listEmployeesInDept(
			List<Integer> departmentIds) {
		
		ArrayList<Employee> employeeListInDep = new ArrayList<Employee>();
		for(int i=0; i<departmentIds.size(); i++)
		{
			for(int j=0; j<instanceList.size(); j++)
			{
				if (instanceList.get(j).getDepartment() == departmentIds.get(i))
				{
					employeeListInDep.add(instanceList.get(j));
				}
				
			}

		}
		return employeeListInDep;
	}

	@Override
	public synchronized void incrementSalaryOfDepartment(
			List<SalaryIncrement> salaryIncrements)
			throws DepartmentNotFoundException,
			NegativeSalaryIncrementException {
		for(int i=0; i<salaryIncrements.size();i++)
		{
			if(salaryIncrements.get(i).getIncrementBy() >=0)
			{
				for(int j=0; j<instanceList.size();j++)
				{
					if(salaryIncrements.get(i).getDepartment() == instanceList.get(j).getDepartment())
					{
						instanceList.get(j).setSalary(salaryIncrements.get(i).getIncrementBy());
					}
					else {throw new DepartmentNotFoundException();}
					
				}
			}
			else {throw new NegativeSalaryIncrementException();}
			
		}
	}

}
