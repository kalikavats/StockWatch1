package edu.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import java.io.IOException;
import java.lang.System;

@ManagedBean(name="login")
@SessionScoped
@RequestScoped
public class Login extends Register {

	public Login() {
		// TODO Auto-generated constructor stub
	}

	Register r = new Register();
	private String username;
	private String password;
	private String role;
	private String firstName;
	protected static String var;
	protected static String varpas;
	protected static String varrol;
	protected static double varfees;
	protected static String varuser;
	protected static double money;
	
	
	public static double getMoney() {
		return money;
	}
	public static void setMoney(double money) {
		Login.money = money;
	}
	public String redirect(String usernameOfClient,String username,String action1,double money) {
		setMoney(money);
		setVaruser(usernameOfClient);
		return "StockWatchAndPurchase.xhtml";
		
		
	}
	public String del1(String usernameOfClient,String username,String action1) {
		
		try {
			Connection con =null;
			con = DataConnect.getConnection();
			String sql ="DELETE from requestmanager where usernameOfmanager =? and usernameOfClient =? and action1= ? and money = ?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, username); 
			st.setString(2, usernameOfClient); 
			st.setString(3, action1); 
			st.setDouble(4, money); 
			st.execute();
			
		}catch (Exception ex) {
            System.out.println("Error catched" + ex.getMessage());
            
        }
		return "SellPurchaseForCustomer.xhtml";
}

	
	public String del(String usernameOfClient,String username,String action1) {
		
			try {
				Connection con =null;
				con = DataConnect.getConnection();
				String sql ="DELETE from requestmanager where usernameOfmanager =? and usernameOfClient =? and action1= ? and money = ?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1, username); 
				st.setString(2, usernameOfClient); 
				st.setString(3, action1); 
				st.setDouble(4, money); 
				st.execute();
				
			}catch (Exception ex) {
	            System.out.println("Error catched" + ex.getMessage());
	            
	        }
			return "SellPurchaseForCustomer.xhtml";
	}
	
   public String redirect1(String usernameOfClient,String username,String action1,double money) {
	    setMoney(money);
		setVaruser(usernameOfClient);
		return "SellStockForCustomer.xhtml";
	}
	

	public static String getVaruser() {
		return varuser;
	}
	public static void setVaruser(String varuser) {
		Login.varuser = varuser;
	}
	public static double getVarfees() {
		return varfees;
	}
	public static void setVarfees(double varfees) {
		Login.varfees = varfees;
	}
	public static String getVarrol() {
		return varrol;
	}
	public static void setVarrol(String varrol) {
		Login.varrol = varrol;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public static String getVar() {
		return var;
	}
	public static void setVar(String username) {
		Login.var = username;
	}
	
	
	
public String getLoginDetails() {
		

		Connection con =null;
		String redirectPage = null;
		String s = System.getenv("ICSI518_SERVER");
		String p = System.getenv("ICSI518_PORT");
		String d = System.getenv("ICSI518_DB");
		String u = System.getenv("ICSI518_USER");
		String pwd = System.getenv("ICSI518_PASSWORD");
		//String ICSI518_SERVER = System.getenv("ICSI518_SERVER");
		double fees =0.0;
		
		
		try {
			//Setup the Data Source object
			
			
			/*com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds= new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(s);
			ds.setPortNumber(Integer.parseInt(p));
			ds.setDatabaseName(d);
			ds.setUser(u);
			ds.setPassword(pwd); */
			con = DataConnect.getConnection();
			//Setup a connection object
			//con=ds.getConnection();
			//Get a prepared SQL statement
			String sql ="SELECT USERNAME,PASSWORD,ROLE from REGISTERFORM1 where USERNAME =? and PASSWORD=? and action1 ='Active'";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, this.username);
			st.setString(2, this.password);
			String sql1 ="SELECT * from REGISTERCUSTOMER where USERNAME =?";
			PreparedStatement st1 =con.prepareStatement(sql1);
			st1.setString(1, this.username);
			
			String sql2 ="SELECT * from REGISTERMANAGER where USERNAME =?";
			PreparedStatement st2 =con.prepareStatement(sql2);
			st2.setString(1, this.username);
			
			//st.setString(3, this.role);
			//Execute the statement
			ResultSet rs = st.executeQuery();
			 ResultSet rs1 = st1.executeQuery();
			 ResultSet rs2 = st2.executeQuery();
			//Iterate through results
			 FacesContext context = FacesContext.getCurrentInstance();
				HttpSession session = SessionUtils.getSession(); 
			 
			if (rs.next()) {
				/*HttpSession hs =SessionUtils.getSession();
				hs.setAttribute("username",this.username);
			SessionUtils session = new SessionUtils();
			//try
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
			sessionMap.put("username", this.username);
			System.out.println("Data entered at getLogindetails()" +hs);
			System.out.println(" getLogindetails()" + session.getUsername()); */
			
			//try
				/*HttpSession hs =SessionUtils.getSession();
				hs.setAttribute("username",username);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				sessionMap.put("username", username);*/
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
                
				
				
				
				this.username=rs.getString("USERNAME");
				this.role=rs.getString("ROLE");
				
				String role = this.role;
                //System.out.println("Correct login credentials");
               // System.out.println("this.role" + role);
                
				if(role.equals("Customer"))
				{
				redirectPage="CustomerMain";
                }
				if (role.equals("Admin"))
				{
					  //System.out.println("here" + role);
				redirectPage="AdminMain";
                }
				if(role.equals("Manager")) 
				{
			    redirectPage="ManagerMain";	
				}
				
				//System.out.println("here" + this.username);
				//Login login =new Login();
				//login.setUsername(this.username);
				
				
				
            } 
            else {
            	//FacesContext.getCurrentInstance().addMessage(null,
            	//new FacesMessage(FacesMessage.SEVERITY_WARN,"Incorrect User ID and Password","Enter Correct Credentials"));
            	FacesMessage message= new FacesMessage("Incorrect Username or Password or not an activated yet","ERROR MESSAGE");
            	message.setSeverity(FacesMessage.SEVERITY_ERROR);
            	FacesContext.getCurrentInstance().addMessage(null, message);
            	
            	
                //System.out.println("Incorrect login credentials");
                redirectPage="login";
            }
			if(rs1.next()) {
				 this.firstName =rs1.getString("firstname");
				 super.lastName =rs1.getString("lastname");
				 super.email =rs1.getString("email");
				 super.username =rs1.getString("username");
				 super.address =rs1.getString("address");
				 super.phoneNumber =rs1.getString("phoneNumber");
				 super.role =rs1.getString("role");
				 
				
				 
			 }
			if(rs2.next()) {
				fees= rs2.getDouble("fees");
			}
			
			setVar(this.username);
			setVarpas(this.password);
			setVarrol(this.role);
			setVarfees(fees);
		} catch(Exception e) {
			System.err.println("Exception:" +e.getMessage());
		}finally {
			
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}
		
		//return "result";
		//HttpSession session = SessionUtils.getSession();
		//session.invalidate();
		
		//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return redirectPage;
	
	}
public static String getVarpas() {
	return varpas;
}
public static void setVarpas(String varpas) {
	Login.varpas = varpas;
}
public String getResults() {
	System.out.println("Result inside");
	Connection con =null;
	String redirectPage = null;
	try {
		
		con = DataConnect.getConnection();
		
		String sql ="SELECT USERNAME,PASSWORD,ROLE from REGISTERFORM1 where USERNAME =? and PASSWORD=?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, Login.getVar());
		st.setString(2, Login.getVarpas());
		
		String sql1 ="SELECT * from REGISTERCUSTOMER where USERNAME =?";
		PreparedStatement st1 =con.prepareStatement(sql1);
		st1.setString(1, Login.getVar());
		
		ResultSet rs = st.executeQuery();
		 ResultSet rs1 = st1.executeQuery();
		System.out.println("Login.getVar()" + Login.getVar());
		System.out.println("Login.getVar()" + Login.getVarpas());
		  
		 
		if (rs.next()) {
			
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = SessionUtils.getSession();
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
            
			
			this.username=rs.getString("USERNAME");
			this.role=rs.getString("ROLE");
			 System.out.println("here" + role);
			String role = this.role;
           
			if(role.equals("Customer"))
			{
			redirectPage="CustomerMain";
            }
			if (role.equals("Admin"))
			{
				 
			redirectPage="AdminMain";
            }
			if(role.equals("Manager")) 
			{
		    redirectPage="ManagerMain";	
			}
			
			//System.out.println("here" + this.username);
			//Login login =new Login();
			//login.setUsername(this.username);
			
			
			
        } 
        else {
        	//FacesContext.getCurrentInstance().addMessage(null,
        	//new FacesMessage(FacesMessage.SEVERITY_WARN,"Incorrect User ID and Password","Enter Correct Credentials"));
        	FacesMessage message= new FacesMessage("Incorrect Username or Password","ERROR MESSAGE");
        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
        	FacesContext.getCurrentInstance().addMessage(null, message);
        	
        	
            //System.out.println("Incorrect login credentials");
            redirectPage="login";
        }
		if(rs1.next()) {
			 this.firstName =rs1.getString("firstname");
			 super.lastName =rs1.getString("lastname");
			 super.email =rs1.getString("email");
			 super.username =rs1.getString("username");
			 super.address =rs1.getString("address");
			 super.phoneNumber =rs1.getString("phoneNumber");
			 super.role =rs1.getString("role");
			 
			
			 
		 }
		
	} catch(Exception e) {
		System.err.println("Exception:" +e.getMessage());
	}finally {
		
		try {
			con.close();
		}catch(SQLException e) {
			
		}
	}
	
	return redirectPage;
     
	
}
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = "username";
    request.setAttribute("username", username); // add to request
    request.getSession().setAttribute("username", username); // add to session
    //this.getServletConfig().getServletContext().setAttribute("username", username); // add to application context
    request.getRequestDispatcher("/URLofOtherServlet").forward(request, response);
}

public String getManagerDetails() {

	Connection con =null;
	 
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = SessionUtils.getSession();
	
	this.username=getUsername();
	
	
	try {
		
		con = DataConnect.getConnection();
		
		String sql ="SELECT * from REGISTERMANAGER where USERNAME =?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, var); // trial removal
		
		ResultSet rs = st.executeQuery();
		//Iterate through results
		if (rs.next()) {
			
			//try
			HttpSession hs =SessionUtils.getSession();
			hs.setAttribute("username",username);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("username", username);
			//try
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
				this.username=rs.getString("username");
				this.firstName =rs.getString("firstname");
				 super.lastName =rs.getString("lastname");
				 super.email =rs.getString("email");
				 super.username =rs.getString("username");
				 super.address =rs.getString("address");
				 super.phoneNumber =rs.getString("phoneNumber");
				 super.role =rs.getString("role");
				 super.fees=rs.getString("fees");
		}
		
	} catch(Exception e) {
		System.err.println("Exception:" +e.getMessage());
	}
	finally {
		try {
			con.close();
		}catch(SQLException e) {
			
		}
	}
	//return "result";
	//HttpSession session = SessionUtils.getSession();
	//session.invalidate();
	//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "UpdateManagerProfile.xhtml";
	
	
}


public String getUserDetails()	{
	Connection con =null;
	//String redirectPage = null;
    String s = System.getenv("ICSI518_SERVER");
	String p = System.getenv("ICSI518_PORT");
	String d = System.getenv("ICSI518_DB");
	String u = System.getenv("ICSI518_USER");
	String pwd = System.getenv("ICSI518_PASSWORD"); 
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = SessionUtils.getSession();
	
	this.username=getUsername();
	//String value = session.getAttribute(username).toString();
	
	//System.out.println("Username at login" + value);
	
	try {
		//Setup the Data Source object
		
		
		/*com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds= new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
		ds.setServerName(s);
		ds.setPortNumber(Integer.parseInt(p));
		ds.setDatabaseName(d);
		ds.setUser(u);
		ds.setPassword(pwd); */
		
		//Setup a connection object
		//con=ds.getConnection();
		//Get a prepared SQL statement
		//Login login = new Login(); 
		
		con = DataConnect.getConnection();
		
		String sql ="SELECT * from REGISTERCUSTOMER where USERNAME =?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, var); // trial removal
		
		//st.setString(2, this.password);
		//st.setString(3, this.role);
		//Execute the statement
		ResultSet rs = st.executeQuery();
		//Iterate through results
		if (rs.next()) {
			
			//try
			HttpSession hs =SessionUtils.getSession();
			hs.setAttribute("username",username);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("username", username);
			//try
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
				this.username=rs.getString("username");
				this.firstName =rs.getString("firstname");
				 super.lastName =rs.getString("lastname");
				 super.email =rs.getString("email");
				 super.username =rs.getString("username");
				 super.address =rs.getString("address");
				 super.phoneNumber =rs.getString("phoneNumber");
				 super.role =rs.getString("role");
		}
		
	} catch(Exception e) {
		System.err.println("Exception:" +e.getMessage());
	}
	finally {
		try {
			con.close();
		}catch(SQLException e) {
			
		}
	}
	//return "result";
	//HttpSession session = SessionUtils.getSession();
	//session.invalidate();
	//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "UpdateCustomerProfile.xhtml";
	
	}



public String logout() throws IOException {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "login.xhtml";
}
	
}
