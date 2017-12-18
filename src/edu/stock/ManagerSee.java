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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import java.io.IOException;
import java.lang.System;



@ManagedBean(name= "managerSee")
@SessionScoped
public class ManagerSee {

	
	private List mperInfoAll = new ArrayList(); 
	private List minInfo = new ArrayList();
	private List reqInfo = new ArrayList();
	private List reqInfo1 = new ArrayList();
	private List adInfo= new ArrayList();
	
	public List getAdInfo() {
		
		int i=0;
		try {
			adInfo.clear();
			System.out.println("Enteringmethod AdInfo1");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  * from REGISTERFORM1 where role='Manager' and action1='NotActive'";
			PreparedStatement st =con.prepareStatement(sql);
			
			System.out.println("User at request1" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  
				adInfo.add(i,new ManagerInfo(rs.getString(1),rs.getString(3)
				,rs.getString(4)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data at Request List: " + e.getMessage());
		}
		return adInfo;
	}
	
	
	
	public List getReqInfo1() {

		int i=0;
		try {
			reqInfo1.clear();
			System.out.println("Enteringmethod requestInfo1");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  usernameOfmanager,action1,usernameOfClient,money from requestmanager where usernameOfmanager = ? and action1='Sell'";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			System.out.println("User at request1" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  
				reqInfo1.add(i,new ManagerInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getDouble(4)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data at Request List: " + e.getMessage());
		}
		
		return reqInfo1;
		
	
	
	
	}
	
	
	public List getMperInfoAll() {
		int i=0;
		try {
			mperInfoAll.clear();
			System.out.println("Enteringmethod getManagerPerInfoAll");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT DISTINCT firstName,lastName,username,fees from REGISTERMANAGER";
			PreparedStatement st =con.prepareStatement(sql);
			//st.setString(1, Login.getVar());
			//System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  
				  mperInfoAll.add(i,new ManagerInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getString(4)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		
		return mperInfoAll;
		
	}
	
	public List getMinInfo() {

		int i=0;
		try {
			minInfo.clear();
			System.out.println("Enteringmethod getMInfo");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT DISTINCT firstName,lastName,username,fees from SELECTEDMANAGER where clientUsername = ?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			//System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  
				minInfo.add(i,new ManagerInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getString(4)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		
		return minInfo;
		
	
	}
	
	
	public List getReqInfo() {


		int i=0;
		try {
			reqInfo.clear();
			System.out.println("Enteringmethod requestInfo");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  usernameOfmanager,action1,usernameOfClient,money from requestmanager where usernameOfmanager = ? and action1='Purchase'";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			System.out.println("User at request" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  
				reqInfo.add(i,new ManagerInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getDouble(4)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data at Request List: " + e.getMessage());
		}
		
		return reqInfo;
		
	
	
	}
	
	public class ManagerInfo{
		


		String firstName;
		String lastName;
		String username;
		String fees;
		String usernameOfClient;
		String action1;
		double money;
		String role;
		
		
		
		


		public String getRole() {
			return role;
		}




		public void setRole(String role) {
			this.role = role;
		}




		public double getMoney() {
			return money;
		}




		public void setMoney(double money) {
			this.money = money;
		}




		public String getAction1() {
			return action1;
		}




		public void setAction1(String action1) {
			this.action1 = action1;
		}




		public String getUsernameOfClient() {
			return usernameOfClient;
		}




		public void setUsernameOfClient(String usernameOfClient) {
			this.usernameOfClient = usernameOfClient;
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




		public String getUsername() {
			return username;
		}




		public void setUsername(String username) {
			this.username = username;
		}




		public String getFees() {
			return fees;
		}




		public void setFees(String fees) {
			this.fees = fees;
		}




		public  ManagerInfo(String firstName,String lastName, String username,String fees) {
			this.firstName = firstName;
			this.username =username;
			this.lastName = lastName;
			this.fees = fees;
			
		
		}
		
		public  ManagerInfo(String username,String role, String action1) {
			
			this.username =username;
			this.role =role;
			this.action1=action1;
			
		
		}
		public  ManagerInfo(String username,String actionByUser, String usernameOfClient,double fees) {
			this.username = username;
			this.action1 =actionByUser;
			this.usernameOfClient = usernameOfClient;
			this.money = fees;
			
		
		}
		

	}
	

	
	
	
}
