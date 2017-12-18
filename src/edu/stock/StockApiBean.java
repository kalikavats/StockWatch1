package edu.stock;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@ManagedBean(name="stockApiBean")
@SessionScoped
public class StockApiBean {

    private static final long serialVersionUID = 1L;
    static final String API_KEY = "0TA7F4H75L983RJZ";

    private String symbol;
    private double price;
    private int qty;
    private double amt;
    private String table1Markup;
    private String table2Markup;
    private List StockInfoAll = new ArrayList();
    private String selectedSymbol;
    private List<SelectItem> availableSymbols;
    
    
    
    public String selected() {


    	Connection con =null;
    	con = DataConnect.getConnection();
    	String symbol = null;
    	try 
    	{
    	String sql ="SELECT symbol selectedStock where username=?";
    			
    	PreparedStatement st =con.prepareStatement(sql);
    	st.setString(1,Login.var);
    	st.executeUpdate();
    	ResultSet rs = st.executeQuery();
    	
        if (rs.next()) {
        	symbol=rs.getString("symbol");
        }
    	}catch (SQLException e) {

    		System.out.println(e.getMessage());

    	} 
   return symbol;
    }
    
    
    public String getPurchaseSymbol() {
        if (getRequestParameter("symbol") != null) {
            symbol = getRequestParameter("symbol");
        }
        return symbol;
    }
    
    public void setPurchaseSymbol(String purchaseSymbol) {
        System.out.println("func setPurchaseSymbol()" + purchaseSymbol);  //check
    }

    public double getPurchasePrice() {
        if (getRequestParameter("price") != null) {
            price = Double.parseDouble(getRequestParameter("price"));
            System.out.println("price: " + price);
        }
        return price;
    }

    public void setPurchasePrice(double purchasePrice) {
        System.out.println("func setPurchasePrice()");  //check
    }
    
    private String getRequestParameter(String name) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(name);
    }

    @PostConstruct
    public void init() {
        //initially populate stock list
        availableSymbols = new ArrayList<SelectItem>();
        availableSymbols.add(new SelectItem("AAPL", "Apple"));
        availableSymbols.add(new SelectItem("AMZN", "Amazon LLC"));
        availableSymbols.add(new SelectItem("AR", "Antero Resources"));
        availableSymbols.add(new SelectItem("EBAY", "Ebay"));
        availableSymbols.add(new SelectItem("FB", "Facebook, Inc."));
        availableSymbols.add(new SelectItem("GOLD", "Gold"));
        availableSymbols.add(new SelectItem("GOOGL", "Google"));
        availableSymbols.add(new SelectItem("MSFT", "Microsoft"));
        availableSymbols.add(new SelectItem("SLV", "Silver"));
        availableSymbols.add(new SelectItem("TWTR", "Twitter, Inc."));

        //initially populate intervals for stock api
        availableIntervals = new ArrayList<SelectItem>();
        availableIntervals.add(new SelectItem("1min", "1min"));
        availableIntervals.add(new SelectItem("5min", "5min"));
        availableIntervals.add(new SelectItem("15min", "15min"));
        availableIntervals.add(new SelectItem("30min", "30min"));
        availableIntervals.add(new SelectItem("60min", "60min"));
    }

    private String selectedInterval;
    private List<SelectItem> availableIntervals;

    public String getSelectedInterval() {
        return selectedInterval;
    }

    public void setSelectedInterval(String selectedInterval) {
        this.selectedInterval = selectedInterval;
    }

    public List<SelectItem> getAvailableIntervals() {
        return availableIntervals;
    }

    public void setAvailableIntervals(List<SelectItem> availableIntervals) {
        this.availableIntervals = availableIntervals;
    }

    public String getSelectedSymbol() {
        return selectedSymbol;
    }

    public void setSelectedSymbol(String selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    public List<SelectItem> getAvailableSymbols() {
        return availableSymbols;
    }

    public void setAvailableSymbols(List<SelectItem> availableSymbols) {
        this.availableSymbols = availableSymbols;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getTable1Markup() {
        return table1Markup;
    }

    public void setTable1Markup(String table1Markup) {
        this.table1Markup = table1Markup;
    }

    public String getTable2Markup() {
        return table2Markup;
    }

    public void setTable2Markup(String table2Markup) {
        this.table2Markup = table2Markup;
    }

    public String createDbRecord(String symbol, double price, int qty, double amt) {
        try {
        	System.out.println("Enter createDbRecord");
            System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");

            Connection conn = DataConnect.getConnection();
            Statement statement = conn.createStatement();
            Statement statement1 = conn.createStatement();
            Login login = new Login();
            System.out.println(login.getVar());
           
            //get userid
           // Integer uid = Integer.parseInt((String) FacesContext.getCurrentInstance()
                    //.getExternalContext()
                   // .getSessionMap().get("uid"));
          
             
            
            
           String uid = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
            
            if(uid == null)
            	uid =Login.getVar();
		
            int quantity =(int) (amt/price);
            System.out.println("Quantity"+ quantity);
            System.out.println("username" +uid);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            boolean check =true;
           if(Login.varrol.equalsIgnoreCase("Manager")) {
            	amt =amt + Login.varfees;
            	System.out.println("Fees added");
            	uid=Login.varuser;
            	System.out.println("Inside manager function");
            	System.out.println("Check at manager function" + Login.money);
            	if(Login.money<amt) {
            		check=false;
            	}
            }
           if(check == true) {
            statement.executeUpdate("INSERT INTO `purchase` (`id`, `uid`, `stock_symbol`, `qty`, `price`, `amt`) "
                    + "VALUES (NULL,'" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"')");
            //statement1.executeUpdate("INSERT INTO `accountHistoryCustomer` (`qty`, `priceOfOne`, `symbol`, `username`, `amount`, `actionByUser`) "
                   // + "VALUES (this.qty,'"  + price + "','" + symbol + "','" + uid + "','" + amt + "','" + "purchase" +"')");
            statement.close();
            String sql2 ="INSERT INTO accountHistoryCustomer"
					+ "(qty, priceOfOne,symbol, username, amount, actionByUser) VALUES"
					+ "(?,?,?,?,?,?)";
            
			PreparedStatement st2 =conn.prepareStatement(sql2);
			st2.setInt(1,qty);
			st2.setDouble(2,price);
			st2.setString(3,symbol);
			st2.setString(4,uid);
			st2.setDouble(5,amt);
			st2.setString(6,"Purchase");
			st2.executeUpdate();
            
            if(Login.varrol.equalsIgnoreCase("Manager")) {
            	
            	String sql3 ="INSERT INTO purchaseForClient"
    					+ "(uid, stock_symbol,qty, price, amt,username,action1,fees) VALUES"
    					+ "(?,?,?,?,?,?,?,?)";
                
    			PreparedStatement st3 =conn.prepareStatement(sql3);
    			st3.setString(1,uid);
    			st3.setString(2,symbol);
    			st3.setInt(3,qty);
    			st3.setDouble(4,price);
    			st3.setDouble(5,amt);
    			st3.setString(6,Login.var);
    			st3.setString(7,"Purchase");
    			st3.setDouble(8,Login.varfees);
    			st3.executeUpdate();
                 login.del(uid, Login.var, "Purchase");
                System.out.println("Inside manager function");
            }
            conn.close();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock",""));
            }
           else {
           	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Client mentioned amount exceeded ",""));
           }
            
            //statement1.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "purchase";
    }

    public void installAllTrustingManager() {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return;
    }

    public void timeseries() throws MalformedURLException, IOException {
    	
    	
    	

        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = this.selectedSymbol;
        String interval = this.selectedInterval;
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='bta btnlg btn-primary' href='" + path + "/faces/purchase.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                    }
                    this.table2Markup += "</tr>";
                    i++;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return;
    }

    public void purchaseStock() {
        System.out.println("Calling function purchaseStock()");
        System.out.println("stockSymbol: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockSymbol"));
        System.out.println("stockPrice" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockPrice"));
        return;
    }
    
    public String sellStock(String id,int qty,String symbl,String username) {
try {
	System.out.println("Enter sellstock()");
			Connection con =null;
    		con = DataConnect.getConnection();
    		boolean check = true;
    		
    		SearchBySymbol sy = new SearchBySymbol();
    		double priceofOne=sy.searchstock(id, qty, symbl);
    		System.out.println("Price of one" + priceofOne );
    		Double totalAmount = qty * priceofOne;
           if(Login.varrol.equalsIgnoreCase("Manager")) {
        	   if(totalAmount>Login.money) {
					check =false;
				}
    		}
    		if(check == true) {
    		String sql =" DELETE from purchase where id =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, id);
			System.out.println("User" + Login.getVar());
			 st.execute();
    		}
			 if(Login.varrol.equalsIgnoreCase("Manager")) {
					totalAmount=totalAmount -Login.varfees;
					System.out.println("Inside Manager Function"+ Login.varfees);
					if(totalAmount>Login.money) {
						check =false;
					}
				}
				
			 
			 //Insert for sold stock soldStock
			 try {

				
					 con = DataConnect.getConnection();
					//Get a prepared SQL statement
					 if(check ==true) {
					String sql1 ="INSERT INTO soldStock"
							+ "(id, uid, stock_symbol, qty, price, amt) VALUES"
							+ "(?,?,?,?,?,?)";
					
					PreparedStatement st1 =con.prepareStatement(sql1);
					st1.setString(1,id);
					st1.setString(2,username);
					st1.setString(3,symbl);
					st1.setInt(4,qty);
					st1.setDouble(5,priceofOne);
					st1.setDouble(6,totalAmount);
					st1.executeUpdate();
					
					String sql2 ="INSERT INTO accountHistoryCustomer"
							+ "(qty, priceOfOne,symbol, username, amount, actionByUser) VALUES"
							+ "(?,?,?,?,?,?)";
					System.out.println("Values in sequence"+ qty+priceofOne+symbl+username+totalAmount);
					PreparedStatement st2 =con.prepareStatement(sql2);
					st2.setInt(1,qty);
					st2.setDouble(2,priceofOne);
					st2.setString(3,symbl);
					st2.setString(4,username);
					st2.setDouble(5,totalAmount);
					st2.setString(6,"Sold");
					st2.executeUpdate();
					
					 if(Login.varrol.equalsIgnoreCase("Manager")) {
			            	
			            	String sql3 ="INSERT INTO purchaseForClient"
			    					+ "(uid, stock_symbol,qty, price, amt,username,action1,fees) VALUES"
			    					+ "(?,?,?,?,?,?,?,?)";
			                Login login = new Login();
			    			PreparedStatement st3 =con.prepareStatement(sql3);
			    			st3.setString(1,Login.varuser);
			    			st3.setString(2,symbl);
			    			st3.setInt(3,qty);
			    			st3.setDouble(4,priceofOne);
			    			st3.setDouble(5,totalAmount);
			    			st3.setString(6,Login.var);
			    			st3.setString(7,"Sold");
			    			st3.setDouble(8,Login.varfees);
			    			st3.executeUpdate();
			    			 login.del1(Login.varuser, Login.var, "Sell");
			                System.out.println("Inside manager function");
			                
					 }
					   FacesMessage message= new FacesMessage("Stock Sold Successfully");
		                message.setSeverity(FacesMessage.SEVERITY_ERROR);
		                FacesContext.getCurrentInstance().addMessage(null, message);
			            }
					 else {
						 FacesMessage message= new FacesMessage("Cannot be sold ,Client amount exceeded");
						 message.setSeverity(FacesMessage.SEVERITY_ERROR);
						 FacesContext.getCurrentInstance().addMessage(null, message);
					 }
					
			 }catch(Exception e) {
					System.out.println("Error pakad liya" + e);
			 }
			 
			
}catch(Exception e) {
	System.out.println("Error pakad liya" + e);
}


//HttpSession session1 = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//session1.invalidate();
/*Login lo= new Login();
   String returnpage =lo.getResults();
    return returnpage;*/	
    return "SellStock.xhtml";
    }
     
public String getDeleteSelectedStock(String symbl) {

try {
	System.out.println("Enter sellstock()");
			Connection con =null;
    		con = DataConnect.getConnection();
    		String sql =" DELETE from selectedStock where symbol =?";
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, symbl);
			System.out.println("User" + Login.getVar());
			 st.execute();
			
			
}catch(Exception e) {
	System.out.println("Error pakad liya" + e);
}

FacesMessage message= new FacesMessage("De selected Stock");
message.setSeverity(FacesMessage.SEVERITY_ERROR);
FacesContext.getCurrentInstance().addMessage(null, message);
//HttpSession session1 = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//session1.invalidate();
/*Login lo= new Login();
   String returnpage =lo.getResults();
    return returnpage;*/	
    return "StockWatch.xhtml";
    
		
	}
	
    
    
}
