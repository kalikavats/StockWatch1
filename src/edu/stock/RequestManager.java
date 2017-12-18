package edu.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="requestManager")
@SessionScoped
@RequestScoped
public class RequestManager {

	static String usernameOfManager;
	private String username;
	private double moneyGiven;
	private String action;
	private String purchase;
	private String sell;
	private static double moneyG;
	
	
	
	

	public static double getMoneyG() {
		return moneyG;
	}

	public static void setMoneyG(double moneyG) {
		RequestManager.moneyG = moneyG;
	}

	public String getPurchase() {
		return purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public static String getUsernameOfManager() {
		return usernameOfManager;
	}

	public static void setUsernameOfManager(String usernameOfManager) {
		RequestManager.usernameOfManager = usernameOfManager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getMoneyGiven() {
		return moneyGiven;
	}

	public void setMoneyGiven(double moneyGiven) {
		this.moneyGiven = moneyGiven;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String RequestManagerok(String user) {
		
		usernameOfManager=user;
		return "RequestManager.xhtml";
	}
	
	public void requestRedirect(double money) {
		setMoneyG(money);
		System.out.println("Money"+ moneyG);
		RequestManagerji();
	}
	
	public String RequestManagerji(){
		
		try {
			//Setup the Data Source object
			Connection con =null;
    		con = DataConnect.getConnection();
			
			//Setup a connection object
			
			//Get a prepared SQL statement
			String sql ="INSERT INTO requestManager"
					+ "(money,usernameOfmanager,usernameOfClient,action1) VALUES"
					+ "(?,?,?,?)";
			
			
			PreparedStatement st =con.prepareStatement(sql);
			st.setDouble(1,this.moneyGiven);
			st.setString(2,getUsernameOfManager());
			st.setString(3,Login.var);
			st.setString(4,this.action);
			
			st.executeUpdate();
			
			
			//Execute the statement
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} 
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		FacesMessage message= new FacesMessage("Successfully requested","INFO MESSAGE");
    	message.setSeverity(FacesMessage.SEVERITY_INFO);
    	FacesContext.getCurrentInstance().addMessage(null, message);
		//return "registrationSuccess";
		
		return"RequestManager.xhtml";
	}
}
