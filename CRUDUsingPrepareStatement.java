package iNeuronJDBC_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUDUsingPrepareStatement {

	static Connection connection = null;
	static PreparedStatement prestmt = null;
	static ResultSet resultset = null;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
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
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}
	
	public static void selectOneRecord()
	{
		try 
		{
			System.out.print("Enter Student id you want to select : ");
			int sid1 = sc.nextInt();
			if(connection != null)
			{
				String quary = "select std_id,std_name,std_year,std_deptment from student where std_id = ?";
				prestmt = connection.prepareStatement(quary);
			}
			if(prestmt != null)
			{
				prestmt.setInt(1,sid1);
				resultset = prestmt.executeQuery();	
			}
			if(resultset != null)
			{
				if(resultset.next()) 
				{
					System.out.println("std_id\t|| std_name\t|| std_year\t\t|| std_deptment");
					System.out.println("=====================================================================");
					Integer  sid = resultset.getInt(1);
					String syear = resultset.getString(3);
					String sname = resultset.getString(2);
					String sdept = resultset.getString(4);

					System.out.println(sid+"\t|| "+sname+"\t|| "+syear+"\t\t|| "+sdept);
					System.out.println("---------------------------------------------------------------------");
				}
				else
				{
					System.out.println("\t *** SORRY ENTERD STUDENT ID NOT MATCH WITH ANY STUDENT IN DATABASE***");
				}
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}
	
	public static void selectAllRecord()
	{
		try 
		{
			if(connection != null)
			{
				String quary = "select std_id,std_name,std_year,std_deptment from student ";
				prestmt = connection.prepareStatement(quary);
			}
			if(prestmt != null)
			{
				resultset = prestmt.executeQuery();	
			}
			if(resultset != null)
			{
				System.out.println("std_id\t|| std_name\t|| std_year\t\t|| std_deptment");
				System.out.println("=====================================================================");
				while(resultset.next()) 
				{
					Integer  sid = resultset.getInt(1);
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
	
	public static void select()
	{
		System.out.println("1.Select One record \t 2.Select All record from Database");
		System.out.print("Pleace enter the option : ");
		int opt = sc.nextInt();
		
		switch(opt)
		{
		case 1:
			selectOneRecord();
			break;
		case 2:
			selectAllRecord();
			break;
		default : 
			System.out.println("INVALID OPTION PLEASE ENTER OPTION 1 or 2");	
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
			if(connection != null)
			{
				String quary = "Insert into student (std_id,std_name,std_year,std_deptment) values(?,?,?,?)";
				prestmt = connection.prepareStatement(quary);
			}
			if(prestmt != null)
			{
				prestmt.setInt(1,sid);
				prestmt.setString(2,sname);
				prestmt.setString(3,syear);
				prestmt.setString(4,sdept);
			}
			int noRowAffected = prestmt.executeUpdate();
			System.out.println(noRowAffected+" rows affected");
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
			selectAllRecord();
			System.out.print("Enter the id of student you want to update : ");
			int sid = sc.nextInt();
			String quary = "select count(*) from student where std_id = "+sid;
			ResultSet resultSet = prestmt.executeQuery(quary);
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
				String quary1 = "update student set std_id = ? where std_id = ?";
//				System.out.println(quary1);
				if(connection != null)
				{
					prestmt = connection.prepareStatement(quary1);
					prestmt.setInt(1,u_sid);
					prestmt.setInt(2,sid);
				}
				int noRowAffected1 = prestmt.executeUpdate();
				System.out.println(noRowAffected1+" rows affected");
				break;
			case 2:
				sc.nextLine();
				System.out.print("Enter updated student name : ");
				String sname = sc.nextLine();
				String quary2 = "update student set std_name = ? where std_id = ? ";
//				System.out.println(quary2);
				if(connection != null)
				{
					prestmt = connection.prepareStatement(quary2);
					prestmt.setString(1,sname);
					prestmt.setInt(2,sid);
				}
				int noRowAffected2 = prestmt.executeUpdate(quary2);
				System.out.println(noRowAffected2+" rows affected");
				break;
			case 3:
				sc.nextLine();
				System.out.print("Enter updated student year : ");
				String syear = sc.nextLine();
				String quary3 = "update student set std_year = ? where std_id = ?";
//				System.out.println(quary3);
				if(connection != null)
				{
					prestmt = connection.prepareStatement(quary3);
					prestmt.setString(1,syear);
					prestmt.setInt(2,sid);
				}
				int noRowAffected3 = prestmt.executeUpdate();
				System.out.println(noRowAffected3+" rows affected");
				break;
			case 4:
				sc.nextLine();
				System.out.print("Enter updated student Deptment : ");
				String sdept = sc.nextLine();
				String quary4 = "update student set std_deptment = ? where std_id = ?";
//				System.out.println(quary4);
				if(connection != null)
				{
					prestmt = connection.prepareStatement(quary4);
					prestmt.setString(1,sdept);
					prestmt.setInt(2,sid);
				}
				int noRowAffected4 = prestmt.executeUpdate();
				System.out.println(noRowAffected4+" rows affected");
				break;
			default:
				System.out.println("INVALID OPTION PLEASE ENTER OPTION BETWEEN 1 TO 4");	
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
			selectAllRecord();
			if(prestmt != null)
			{
				System.out.print("Enter the id of student you want to delete : ");
				int sid = sc.nextInt();
				String quary = "delete from student where std_id = ?";
				if(connection != null)
				{
					prestmt = connection.prepareStatement(quary);
					prestmt.setInt(1,sid);
				}
				int noRowAffected = prestmt.executeUpdate();
				System.out.println(noRowAffected+" rows affected");
			}
			
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}

}
