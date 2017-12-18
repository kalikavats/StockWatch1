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

import edu.stock.StockSee.perInfo;

import java.io.IOException;
import java.lang.System;



@ManagedBean(name= "stockSee")
@SessionScoped
public class StockSee {
	
	private List perInfoAll= new ArrayList(); 
	private List perInfoIn = new ArrayList();
	private List agInfoAll = new ArrayList();
	private List selectInfoAll = new ArrayList();
	private List selectInfoAll1 = new ArrayList();
	private List dpInfoAll = new ArrayList();
	private List selectInfoAll2 =new ArrayList();
	
	public List getSelectInfoAll2() {


		int i=0;
		try {
			selectInfoAll2.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  * from purchaseForClient ";
			PreparedStatement st =con.prepareStatement(sql);
			
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  selectInfoAll2.add(i,new perInfo(rs.getString(1),rs.getString(2),rs.getString(3)
				,rs.getInt(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7),rs.getString(8),rs.getDouble(9)));

				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return selectInfoAll2;
	
	
		
	}
	
	
	
	
	public List getDpInfoAll() {

		int i=0;
		try {
			dpInfoAll.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  * from accountHistoryCustomer";
			PreparedStatement st =con.prepareStatement(sql);
			
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  dpInfoAll.add(i,new perInfo(rs.getInt(1),rs.getDouble(2)
				,rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getString(6)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return dpInfoAll;
	
		
	}
	
	
	
	
	public List getSelectInfoAll1() {

		int i=0;
		try {
			selectInfoAll1.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  * from purchaseForClient where username =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  selectInfoAll1.add(i,new perInfo(rs.getString(1),rs.getString(2),rs.getString(3)
				,rs.getInt(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7),rs.getString(8),rs.getDouble(9)));

				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return selectInfoAll1;
	
	}
	
	
	public List getSelectInfoAll() {
		int i=0;
		try {
			selectInfoAll.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT DISTINCT * from selectedStock where username =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  selectInfoAll.add(i,new perInfo(rs.getString(1),rs.getString(2)
				));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return selectInfoAll;
	}
	
	
	
	public List getPerInfoAll() {
		int i=0;
		try {
			perInfoAll.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT DISTINCT * from purchase where uid =?";
			PreparedStatement st =con.prepareStatement(sql);
			if(Login.varrol.equalsIgnoreCase("Manager")) {
				st.setString(1, Login.getVaruser());
			}
			else
			{
			st.setString(1, Login.getVar());
			}
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  perInfoAll.add(i,new perInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getInt(4),rs.getDouble(5),rs.getDouble(6)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return perInfoAll;
	}
	
	
	public List getPerInfoIn() {
		int i=0;
		try {
			perInfoIn.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT DISTINCT * from soldStock where uid =?";
			PreparedStatement st =con.prepareStatement(sql);
			if(Login.varrol.equalsIgnoreCase("Manager")) {
				st.setString(1, Login.getVaruser());
			}
			else
			{
			st.setString(1, Login.getVar());
			}
			//st.setString(1, Login.getVar());
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  perInfoIn.add(i,new perInfo(rs.getString(1),rs.getString(2)
				,rs.getString(3),rs.getInt(4),rs.getDouble(5),rs.getDouble(6)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return perInfoIn;
	}
	
	public List getAgInfoAll () {
		int i=0;
		try {
			agInfoAll.clear();
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql ="SELECT  * from accountHistoryCustomer where username =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, Login.getVar());
			System.out.println("User" + Login.getVar());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				  System.out.println(rs.getString(1));
				  agInfoAll.add(i,new perInfo(rs.getInt(1),rs.getDouble(2)
				,rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getString(6)));
				  i++;
		}
			}
		catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return agInfoAll;
	}
	
	public double calculateBalanceofManager() {
		

		
		double calculatedFees =0.0;
		
		try {
		Connection con =null;
		con = DataConnect.getConnection();
		String sql ="SELECT SUM(fees) FROM purchaseForClient  where username =? ";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, Login.getVar());
		ResultSet rs = st.executeQuery();
		while(rs.next()){
			calculatedFees=rs.getDouble("SUM(fees)");
		}
		System.out.println("Calculate calculatedFees" + calculatedFees);
	
		
		
		}catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return calculatedFees;
	
	}
	
	
	
	public double calculateBalance() {
		
		double calculatedAmountbySold =0.0;
		double calculatedAmountbyPurchase =0.0;
		double  finalBalance=0.0;
		try {
		Connection con =null;
		con = DataConnect.getConnection();
		String sql ="SELECT SUM(amount) FROM accountHistoryCustomer  where username =? and actionByUser='Sold' ";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1, Login.getVar());
		ResultSet rs = st.executeQuery();
		while(rs.next()){
		calculatedAmountbySold=rs.getDouble("SUM(amount)");
		}
		System.out.println("Calculate amount Sold" + calculatedAmountbySold);
		
		String sql1 ="SELECT SUM(amount) FROM accountHistoryCustomer  where username =? and actionByUser='Purchase'";
		PreparedStatement st1 =con.prepareStatement(sql1);
		st1.setString(1, Login.getVar());
		ResultSet rs1 = st1.executeQuery();
		while(rs1.next()){
		calculatedAmountbyPurchase=rs1.getDouble("SUM(amount)");
		
		}
		System.out.println("Calculated amount purchase " + calculatedAmountbyPurchase);
		System.out.println("Total Sold Total Purchase" + calculatedAmountbySold + calculatedAmountbyPurchase);
		
		finalBalance= 100000.00 - calculatedAmountbyPurchase + calculatedAmountbySold;
		
		}catch(Exception e) {
			System.out.println("Error Data : " + e.getMessage());
		}
		return finalBalance;
	}
	
	public void selectToWatch(String symbol) {

		Connection con =null;
		con = DataConnect.getConnection();
		try 
		{
		String sql ="INSERT INTO selectedStock"
				+ "(symbol,username) VALUES"
				+ "(?,?)";
		PreparedStatement st =con.prepareStatement(sql);
		st.setString(1,symbol);
		st.setString(2,Login.var);
		
		st.executeUpdate();
		}catch (SQLException e) {

			System.out.println(e.getMessage());

		} 
	}
	
	
	
	public class perInfo{
		


		String id;
		String username;
		String stockSymbol;
		int qty;
		double price;
		double amount;
		String actionByUser;
		double initialBalance=100000.00;
		String usernameOfClient;
		double fees;
		
		
		
		
		public double getFees() {
			return fees;
		}


		public void setFees(double fees) {
			this.fees = fees;
		}


		public String getUsernameOfClient() {
			return usernameOfClient;
		}


		public void setUsernameOfClient(String usernameOfClient) {
			this.usernameOfClient = usernameOfClient;
		}


		public double getInitialBalance() {
			return initialBalance;
		}


		public void setInitialBalance(double initialBalance) {
			this.initialBalance = initialBalance;
		}


		public String getActionByUser() {
			return actionByUser;
		}


		public void setActionByUser(String actionByUser) {
			this.actionByUser = actionByUser;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getStockSymbol() {
			return stockSymbol;
		}


		public void setStockSymbol(String stockSymbol) {
			this.stockSymbol = stockSymbol;
		}


		public int getQty() {
			return qty;
		}


		public void setQty(int qty) {
			this.qty = qty;
		}


		public double getPrice() {
			return price;
		}


		public void setPrice(double price) {
			this.price = price;
		}


		public double getAmount() {
			return amount;
		}


		public void setAmount(double amount) {
			this.amount = amount;
		}


		public  perInfo(String id,String username, String stockSymbol,int qty,double price,double amount) {
			this.id = id;
			this.username =username;
			this.stockSymbol = stockSymbol;
			this.qty = qty;
			this.price = price;
			this.amount= amount;
		
		}
		public  perInfo(String id,String usernameOfClient, String stockSymbol,int qty,double price,double amount,String username,String actionByUser,double fees) {
			this.id = id;
			this.username =username;
			this.stockSymbol = stockSymbol;
			this.qty = qty;
			this.price = price;
			this.amount= amount;
			this.usernameOfClient =usernameOfClient;
			this.fees=fees;
			this.actionByUser=actionByUser;
		
		}
		
		public  perInfo(String stocksymbol,String username) {
			
			this.stockSymbol = stocksymbol;
			this.username =username;
		}
		
		
		public  perInfo(int qty,double price,String stockSymbol,String username,double amount,String actionByUser) {
			
			this.username =username;
			this.stockSymbol = stockSymbol;
			this.qty = qty;
			this.price = price;
			this.amount= amount;
			this.actionByUser= actionByUser;
		}
		

	}
	
}
