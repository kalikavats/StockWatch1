package edu.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.stock.StockSee.perInfo;

@ManagedBean(name="registerManager")
@SessionScoped
@RequestScoped
public class RegisterManager {

	public RegisterManager() {
		// TODO Auto-generated constructor stub
	}

	
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String address;
	private String phoneNumber;
	private String role;
	private String company;
	private String fees;
	private String clientUsername;
	private String action1;
	
	
	
	public String getAction1() {
		return action1;
	}



	public void setAction1(String action1) {
		this.action1 = action1;
	}



	public String getClientUsername() {
		return clientUsername;
	}



	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}



	public  RegisterManager(String firstName,String lastName, String email,String username,String password,String address,String phoneNumber,String role,String company,String fees) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.address= address;
		this.phoneNumber= phoneNumber;
		this.role= role;
		this.company= company;
		this.fees= fees;
	
	}
	
	
	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getFees() {
		return fees;
	}


	public void setFees(String fees) {
		this.fees = fees;
	}



	public String setDetails() {
		
		
		
		Connection con =null;
		String s = System.getenv("ICSI518_SERVER");
		String p = System.getenv("ICSI518_PORT");
		String d = System.getenv("ICSI518_DB");
		String u = System.getenv("ICSI518_USER");
		String pwd = System.getenv("ICSI518_PASSWORD");
		//String ICSI518_SERVER = System.getenv("ICSI518_SERVER");
		System.out.println("VAlue of s " + s);
		System.out.println("VAlue of p " + p);
		System.out.println("VAlue of d " + d);
		System.out.println("VAlue of u " + u);
		System.out.println("VAlue of pwd " + pwd);
		
		Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                              envName,
                              env.get(envName));
        }
		
		
		
		try {
			
			//Setup the Data Source object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds= new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(s);
			ds.setPortNumber(Integer.parseInt(p));
			ds.setDatabaseName(d);
			ds.setUser(u);
			ds.setPassword(pwd);
			
			//Setup a connection object
			con=ds.getConnection();
			//Get a prepared SQL statement
			String sql ="INSERT INTO REGISTERMANAGER"
					+ "(firstname, lastname, address, phoneNumber, email, username, password,role,company,fees) VALUES"
					+ "(?,?,?,?,?,?,?,?,?,?)";
			String sql1 ="INSERT INTO REGISTERFORM1"
					+ "(username, password,role,action1) VALUES"
					+ "(?,?,?,?)";
			/*/String sql1 ="SELECT username FROM REGISTERCUSTOMER";
			//PreparedStatement st1 =con.prepareStatement(sql1);
			//ResultSet rs = st1.executeQuery();
			
			
			
			if(rs.next()) {
				FacesMessage message= new FacesMessage("User Id Exists ,choose another","INFO MESSAGE");
		    	message.setSeverity(FacesMessage.SEVERITY_INFO);
		    	FacesContext.getCurrentInstance().addMessage(null, message);
				
				
			}
			else { */
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1,this.firstName);
			st.setString(2,this.lastName);
			st.setString(3,this.address);
			st.setString(4,this.phoneNumber);
			st.setString(5,this.email);
			
			st.setString(6,this.username);
			st.setString(7,this.password);
			st.setString(8,"Manager");
			st.setString(9,this.company);
			st.setString(10,this.fees);
			st.executeUpdate();
			
			PreparedStatement st1 =con.prepareStatement(sql1);
			st1.setString(1,this.username);
			st1.setString(2,this.password);
			st1.setString(3,"Manager");
			st1.setString(4,"NotActive");
			st1.executeUpdate();
			//Execute the statement
			
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} 
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		FacesMessage message= new FacesMessage("Registered Successfully Please Wait for Admin approval","INFO MESSAGE");
    	message.setSeverity(FacesMessage.SEVERITY_INFO);
    	FacesContext.getCurrentInstance().addMessage(null, message);
		//return "registrationSuccess";
		return "login";
	
	}
	

 public String selectManager(String usrname) {


	 Connection con =null;
	 try 
	 {
	 con = DataConnect.getConnection();
	 System.out.print("Inside Select Manager ");
		String sql ="SELECT * from REGISTERMANAGER where USERNAME =?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, usrname);
		ResultSet rs = st.executeQuery();
		
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
				this.lastName =rs.getString("lastname");
				this.email =rs.getString("email");
				this.username =rs.getString("username");
				this.address =rs.getString("address");
				this.phoneNumber =rs.getString("phoneNumber");
				this.role =rs.getString("role");
				this.fees=rs.getString("fees");
				//this.clientUsername=rs.getString(Login.var);
		}
          
          String sql1 ="INSERT INTO SELECTEDMANAGER"
					+ "(firstname, lastname, address, phoneNumber, email, username,fees,clientUsername) VALUES"
					+ "(?,?,?,?,?,?,?,?)"; 
            PreparedStatement st1 =con.prepareStatement(sql1);
			st1.setString(1,this.firstName);
			st1.setString(2,this.lastName);
			st1.setString(3,this.address);
			st1.setString(4,this.phoneNumber);
			st1.setString(5,this.email);
			st1.setString(6,this.username);
			st1.setString(7,this.fees);
			st1.setString(8,Login.var);
			st1.executeUpdate();
		
		}catch(Exception e) {
			System.err.println("Exception:" +e.getMessage());
		}finally {
			
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}
	 
	 
	 return "SelectManager.xhtml";
 
 
 }
 
 public String changeStatus(String mid) {


	 Connection con =null;
	 try 
	 {
	 con = DataConnect.getConnection();
	 System.out.print("Inside View Manager ");
		String sql ="UPDATE REGISTERFORM1 SET action1='Active' where username=?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, mid);
		st.execute();
		
         
		
		}catch(Exception e) {
			System.err.println("Exception:" +e.getMessage());
		}finally {
			
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}
	 
 
	 return "ViewRequests.xhtml";
 }
 
 public String viewProfileManager(String usrname) {

	 Connection con =null;
	 try 
	 {
	 con = DataConnect.getConnection();
	 System.out.print("Inside View Manager ");
		String sql ="SELECT * from REGISTERMANAGER where USERNAME =?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, usrname);
		ResultSet rs = st.executeQuery();
		
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
				this.lastName =rs.getString("lastname");
				this.email =rs.getString("email");
				this.username =rs.getString("username");
				this.address =rs.getString("address");
				this.phoneNumber =rs.getString("phoneNumber");
				this.role =rs.getString("role");
		}
		
		}catch(Exception e) {
			System.err.println("Exception:" +e.getMessage());
		}finally {
			
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}
	 
	 
	 return"ViewManagerProfile.xhtml";
 
 }

 public String deleteSelectedManager(String usrname) {



	 Connection con =null;
	 try 
	 {
	 con = DataConnect.getConnection();
	 System.out.print("Inside delete manager ");
		String sql ="DELETE from SELECTEDMANAGER where USERNAME =?";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, usrname);
		 st.execute();
		
         
		}catch(Exception e) {
			System.err.println("Exception:" +e.getMessage());
		}finally {
			
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}
	 
	 
	 return "SelectManager.xhtml";
 
 
 
 }
}

