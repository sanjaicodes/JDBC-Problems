package iNeuronJDBC_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCOperations {

	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultset = null;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		try
		{
			jdbcConnection();
			
			while(true) {
				System.out.println("1.Select\t2.Insert\t3.Update\t4.Delete\t5.Exit");
				System.out.print("Enter the operation what you want to do : ");
				int option = sc.nextInt();
				switch(option)
				{
				case 1:
					select();
					break;
				case 2:
					insert();
					break;
				case 3:
					update();
					break;
				case 4:
					delete();
					break;
				case 5:
					if(connection != null)
						connection.close();
					if(sc != null)
						sc.close();
					System.out.println("Database connection sussfully closed!");
					System.exit(0);
					break;
				default : 
					System.out.println("INVALID OPTION PLEASE ENTER OPTION BETWEEN 1 TO 4");	
					
				}
			}
			
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		
			
	}
	
	public static void jdbcConnection() 
	{
		String url = "jdbc:mysql://localhost:3306/jdbc";
		String username = "root";
		String password = "root123";
		
		try
		{
			connection = DriverManager.getConnection(url,username,password);
			if(connection != null)
			{
				statement = connection.createStatement();
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}
	
	public static void select()
	{
		try 
		{
			if(statement != null)
			{
				String quary = "select std_id,std_name,std_year,std_deptment from student ";
				resultset = statement.executeQuery(quary);
			}
			if(resultset != null)
			{
				System.out.println("std_id\t|| std_name\t|| std_year\t\t|| std_deptment");
				System.out.println("=====================================================================");
				while(resultset.next()) 
				{
					Integer sid = resultset.getInt(1);
					String syear = resultset.getString(3);
					String sname = resultset.getString(2);
					String sdept = resultset.getString(4);

					System.out.println(sid+"\t|| "+sname+"\t|| "+syear+"\t\t|| "+sdept);
					System.out.println("---------------------------------------------------------------------");
				}
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	
	}
	
	public static void insert()
	{
		System.out.print("Enter student id : ");
		int sid = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter student name : ");
		String sname = sc.nextLine();
		System.out.print("Enter student year : ");
		String syear = sc.nextLine();
		System.out.print("Enter student Deptment : ");
		String sdept = sc.nextLine();
		
		try 
		{
			if(statement != null)
			{
				String quary = String.format("Insert into student (std_id,std_name,std_year,std_deptment) values(%d,'%s','%s','%s')",
						sid,sname,syear,sdept);
				int noRowAffected = statement.executeUpdate(quary);
				System.out.println(noRowAffected+" rows affected");
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		
	}
	
	public static void update()throws SQLException
	{
		try
		{
			select();
			if(statement != null)
			{
				select();
				System.out.print("Enter the id of student you want to update : ");
				int sid = sc.nextInt();
				String quary = "select count(*) from student where std_id = "+sid;
				ResultSet resultSet = statement.executeQuery(quary);
				while(resultSet.next()) 
				{
					int c = resultSet.getInt(1);
					if(c == 0)
					{
						System.out.println("\t***    STUDENT ID NOT IN DATABASE  ***");
						System.out.println("\t***  PLEASE ENTER VALID STUDENT ID ***");
						return ;
					}
				}
				System.out.println("1.std_id\t2.std_name\t3.std_year\t4.std_deptment");
				System.out.print("Which column value you want to update : ");
				int opt = sc.nextInt();
				switch(opt)
				{
				case 1:
					System.out.print("Enter updated student id : ");
					int u_sid = sc.nextInt();
					String quary1 = "update student set std_id = "+u_sid+" where std_id = "+sid;
//					System.out.println(quary1);
					int noRowAffected1 = statement.executeUpdate(quary1);
					System.out.println(noRowAffected1+" rows affected");
					break;
				case 2:
					sc.nextLine();
					System.out.print("Enter updated student name : ");
					String sname = sc.nextLine();
					sname = "'"+sname+"'";
					String quary2 = "update student set std_name = "+sname+" where std_id = "+sid;
//					System.out.println(quary2);
					int noRowAffected2 = statement.executeUpdate(quary2);
					System.out.println(noRowAffected2+" rows affected");
					break;
				case 3:
					sc.nextLine();
					System.out.print("Enter updated student year : ");
					String syear = sc.nextLine();
					syear = "'"+syear+"'";
					String quary3 = "update student set std_year = "+syear+" where std_id = "+sid;
//					System.out.println(quary3);
					int noRowAffected3 = statement.executeUpdate(quary3);
					System.out.println(noRowAffected3+" rows affected");
					break;
				case 4:
					sc.nextLine();
					System.out.print("Enter updated student Deptment : ");
					String sdept = sc.nextLine();
					sdept = "'"+sdept+"'";
					String quary4 = "update student set std_deptment = "+sdept+" where std_id = "+sid;
//					System.out.println(quary4);
					int noRowAffected4 = statement.executeUpdate(quary4);
					System.out.println(noRowAffected4+" rows affected");
					break;
				default:
					System.out.println("INVALID OPTION PLEASE ENTER OPTION BETWEEN 1 TO 4");	
				}
				
				
			}
			
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		
	}
	
	public static void delete()
	{
		try
		{
			select();
			if(statement != null)
			{
				System.out.print("Enter the id of student you want to delete : ");
				int sid = sc.nextInt();
				String quary = "delete from student where std_id = "+sid;
				int noRowAffected = statement.executeUpdate(quary);
				System.out.println(noRowAffected+" rows affected");
			}
			
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		
	}
}
