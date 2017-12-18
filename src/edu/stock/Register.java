package edu.stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@ManagedBean(name="register")
@SessionScoped
@RequestScoped
public class Register {

	public Register() {
		// TODO Auto-generated constructor stub
	}

	
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String username;
	protected String password;
	protected String address;
	protected String phoneNumber;
	protected String role;
	protected String company;
	protected String fees;
	private String action1;
	

	

	public String getAction1() {
		return action1;
	}


	public void setAction1(String action1) {
		this.action1 = action1;
	}


	public String getFees() {
		return fees;
	}


	public void setFees(String fees) {
		this.fees = fees;
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


	public String setDetails() {
		
		
		
		Connection con =null;
		String s = System.getenv("ICSI518_SERVER");
		String p = System.getenv("ICSI518_PORT");
		String d = System.getenv("ICSI518_DB");
		String u = System.getenv("ICSI518_USER");
		String pwd = System.getenv("ICSI518_PASSWORD");
		//String ICSI518_SERVER = System.getenv("ICSI518_SERVER");
		
		
		
		
		try {
			
			
			 HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			            .getExternalContext().getSession(false);
			        session.setAttribute("username", username);
			        System.out.println("Session at point 1" + session);
			        
			//Setup the Data Source object
			/*com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds= new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(s);
			ds.setPortNumber(Integer.parseInt(p));
			ds.setDatabaseName(d);
			ds.setUser(u);
			ds.setPassword(pwd);*/
			
			//Setup a connection object
			//con=ds.getConnection();
			 con = DataConnect.getConnection();
			//Get a prepared SQL statement
			String sql ="INSERT INTO REGISTERCUSTOMER"
					+ "(firstname, lastname, address, phoneNumber, email, username, password,role) VALUES"
					+ "(?,?,?,?,?,?,?,?)";
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
			st.setString(8,"Customer");
			st.executeUpdate();
			
			PreparedStatement st1 =con.prepareStatement(sql1);
			st1.setString(1,this.username);
			st1.setString(2,this.password);
			st1.setString(3,"Customer");
			st1.setString(4,"Active");
			st1.executeUpdate();
			//Execute the statement
			//System.out.println("Username" + this.username);
			
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} 
		HttpSession session = SessionUtils.getSession();
		//session.invalidate();
		//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		FacesMessage message= new FacesMessage("Registered Successfully Please Sign In to proceed","INFO MESSAGE");
    	message.setSeverity(FacesMessage.SEVERITY_INFO);
    	FacesContext.getCurrentInstance().addMessage(null, message);
		//return "registrationSuccess";
		return "login";
	
	}
	
	public String updateDetails() {
		

		Connection con =null;
		
	    String s = System.getenv("ICSI518_SERVER");
		String p = System.getenv("ICSI518_PORT");
		String d = System.getenv("ICSI518_DB");
		String u = System.getenv("ICSI518_USER");
		String pwd = System.getenv("ICSI518_PASSWORD"); 
		FacesContext context = FacesContext.getCurrentInstance();
		//HttpSession session = SessionUtils.getSession();
		
		 HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
		            .getExternalContext().getSession(false);
		        session.setAttribute("username", username);
		
		        System.out.println("Session at point 2" + session);
		try {
			
			con = DataConnect.getConnection();
			
			if(this.getLastName() !="") {
			
			String sql ="UPDATE REGISTERCUSTOMER set lastname =?  where USERNAME =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1,this.lastName);
			st.setString(2,Login.var); 
			st.executeUpdate();
			}
			if(this.getAddress() !="") {
				
				String sql ="UPDATE REGISTERCUSTOMER set address =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.address);
				st.setString(2,Login.var); 
				st.executeUpdate(); 
				}
	        if(this.getPhoneNumber() !="") {
				
				String sql ="UPDATE REGISTERCUSTOMER set phoneNumber =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.phoneNumber);
				st.setString(2,Login.var); 
				st.executeUpdate();
				}
            if(this.getPassword() !="") {
				
				String sql ="UPDATE REGISTERCUSTOMER set password =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.password);
				st.setString(2,Login.var); 
				st.executeUpdate();
				String sql1 ="UPDATE REGISTERFORM1 set password =?  where USERNAME =?";
				PreparedStatement st1 =con.prepareStatement(sql1);
				st1.setString(1,this.password);
				st1.setString(2,Login.var); 
				st1.executeUpdate();
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
		return "CustomerMain.xhtml";
		
	}	
	
	public String updateManagerDetails() {

		

		Connection con =null;
		
	    
		FacesContext context = FacesContext.getCurrentInstance();
		//HttpSession session = SessionUtils.getSession();
		
		 HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
		            .getExternalContext().getSession(false);
		        session.setAttribute("username", username);
		
		        
		try {
			
			con = DataConnect.getConnection();
			
			if(this.getLastName() !="") {
			
			String sql ="UPDATE REGISTERMANAGER set lastname =?  where USERNAME =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1,this.lastName);
			st.setString(2,Login.var); 
			st.executeUpdate();
			}
			if(this.getAddress() !="") {
				
				String sql ="UPDATE REGISTERMANAGER set address =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.address);
				st.setString(2,Login.var); 
				st.executeUpdate(); 
				}
	        if(this.getPhoneNumber() !="") {
				
				String sql ="UPDATE REGISTERMANAGER set phoneNumber =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.phoneNumber);
				st.setString(2,Login.var); 
				st.executeUpdate();
				}
            if(this.getPassword() !="") {
				
				String sql ="UPDATE REGISTERMANAGER set password =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.password);
				st.setString(2,Login.var); 
				st.executeUpdate();
				String sql1 ="UPDATE REGISTERFORM1 set password =?  where USERNAME =?";
				PreparedStatement st1 =con.prepareStatement(sql1);
				st1.setString(1,this.password);
				st1.setString(2,Login.var); 
				st1.executeUpdate();
				}	
            if(this.getFees() !="") {
				
				String sql ="UPDATE REGISTERMANAGER set fees =?  where USERNAME =?";
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1,this.fees);
				st.setString(2,Login.var); 
				st.executeUpdate();
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
		return "ManagerMain.xhtml";
		
	
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
		////ttpSession session = SessionUtils.getSession();
		
		
		//System.out.println("rightNow" + username);
		
		try {
			//Setup the Data Source object
			 HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			            .getExternalContext().getSession(false);
			        session.setAttribute("username", username);
			        System.out.println("Session at point 3" + session);
			
			 con = DataConnect.getConnection();
			
			String sql ="SELECT * from REGISTERCUSTOMER where USERNAME =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, this.username);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				
					this.username=rs.getString("username");
					this.firstName=rs.getString("firstname");
					this.lastName=rs.getString("lastname");
					this.address=rs.getString("address");
					this.email=rs.getString("email");
					this.phoneNumber=rs.getString("phoneNumber");
					this.password=rs.getString("password");
				
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
	
	
}
