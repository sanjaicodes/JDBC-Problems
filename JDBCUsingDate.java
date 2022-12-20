package iNeuronJDBC_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class JDBCUsingDate {

	static Connection connection = null;
	static PreparedStatement prestmt = null;
	static ResultSet resultset = null;
	static Scanner sc = new Scanner(System.in);
	
	public static void jdbcConnection() 
	{
		String url = "jdbc:mysql://localhost:3306/datetimeOperation";
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
	
	public static void main(String[] args) {
		
		try
		{
			jdbcConnection();
			
			while(true) {
				System.out.println("1.Perform insertion operation\t2.Perform retrieval operation\t3.Exit");
				System.out.print("Enter the operation what you want to do : ");
				int option = sc.nextInt();
				switch(option)
				{
				case 1:
					insertRecord();
					break;
				case 2:
					retrieveAllRecord();
					break;
				case 3:
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
	
	public static void insertRecord() 
	{
//		name varchar(20),address varchar(50),gender varchar(5),DOB date,DOJ date,DOM date
		sc.nextLine();
		System.out.print("Enter your name : ");
		String jname = sc.nextLine();
		System.out.print("Enter your address : ");
		String jaddress = sc.nextLine();
		System.out.print("Enter your gender : ");
		String jgender = sc.next();
		System.out.print("Enter your Date of Birth(dd-mm-yyyy) : ");
		String jdob = sc.next();
		System.out.print("Enter your Date of Joining(dd-mm-yyyy) : ");
		String jdoj = sc.next();
		System.out.print("Enter your Date of Marriage(dd-mm-yyyy) : ");
		String jdom = sc.next();
		
		try
		{
			if(connection != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				java.util.Date udate1 = sdf.parse(jdob);
				long t1 = udate1.getTime();
				java.sql.Date sdate1 = new java.sql.Date(t1);
				
				java.util.Date udate2 = sdf.parse(jdoj);
				long t2 = udate2.getTime();
				java.sql.Date sdate2 = new java.sql.Date(t2);
				
				java.util.Date udate3 = sdf.parse(jdom);
				long t3 = udate3.getTime();
				java.sql.Date sdate3 = new java.sql.Date(t3);
				
				String quary = "Insert into PersonDetails (name,address,gender,DOB,DOJ,DOM)values (?,?,?,?,?,?)";
				prestmt = connection.prepareStatement(quary);
				
				if(prestmt != null)
				{
					prestmt.setString(1,jname);
					prestmt.setString(2,jaddress);
					prestmt.setString(3,jgender);
					prestmt.setDate(4,sdate1);
					prestmt.setDate(5,sdate2);
					prestmt.setDate(6,sdate3);
					
					int noOfrowsAffected  = prestmt.executeUpdate();
					System.out.println(noOfrowsAffected+"row(s) affected");
				}
				
				
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void retrieveAllRecord()
	{
		
		try 
		{
			if(connection != null)
			{
				String quary = "Select name,address,gender,DOB,DOJ,DOM from PersonDetails ";
				prestmt = connection.prepareStatement(quary);
			}
			if(prestmt != null)
			{
				resultset = prestmt.executeQuery();	
			}
			if(resultset != null)
			{
				System.out.println("NAME\t\t|| ADDRESS\t\t\t\t|| GENDER\t|| DOB\t|| DOJ\t|| DOM");
				System.out.println("====================================================================================================================");
				while(resultset.next()) 
				{
					String  pname = resultset.getString(1);
					String padd = resultset.getString(2);
					String pgender = resultset.getString(3);
					
					java.sql.Date pdob = resultset.getDate(4);
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String sdob = sdf.format(pdob);
					
					java.sql.Date pdoj = resultset.getDate(5);
					SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
					String sdoj = sdf1.format(pdoj);

					java.sql.Date pdom = resultset.getDate(6);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					String sdom = sdf2.format(pdom);
					
					System.out.println(pname+"\t\t|| "+padd+"\t\t\t|| "+pgender+"\t\t|| "+sdob+"\t|| "+sdoj+"\t|| "+ sdom);
					System.out.println("-------------------------------------------------------------------------------------------------------------------");
				}
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}
}
