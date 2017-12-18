package edu.stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="searchBySymbol")
public class SearchBySymbol {
private String symbol;
private String AAPL;
private String AMZN;
private String AR;
private String EBAY;
private String FB;
private String GOLD;
private String GOOGL;
private String MSFT;
private String SLV;
private String TWTR;
private double currentPrice;




public double getCurrentPrice() {
	return currentPrice;
}

public void setCurrentPrice(double currentPrice) {
	this.currentPrice = currentPrice;
}

public String getAAPL() {
	return AAPL;
}

public void setAAPL(String aAPL) {
	AAPL = aAPL;
}

public String getAMZN() {
	return AMZN;
}

public void setAMZN(String aMZN) {
	AMZN = aMZN;
}

public String getAR() {
	return AR;
}

public void setAR(String aR) {
	AR = aR;
}

public String getEBAY() {
	return EBAY;
}

public void setEBAY(String eBAY) {
	EBAY = eBAY;
}

public String getFB() {
	return FB;
}

public void setFB(String fB) {
	FB = fB;
}

public String getGOLD() {
	return GOLD;
}

public void setGOLD(String gOLD) {
	GOLD = gOLD;
}

public String getGOOGL() {
	return GOOGL;
}

public void setGOOGL(String gOOGL) {
	GOOGL = gOOGL;
}

public String getMSFT() {
	return MSFT;
}

public void setMSFT(String mSFT) {
	MSFT = mSFT;
}

public String getSLV() {
	return SLV;
}

public void setSLV(String sLV) {
	SLV = sLV;
}

public String getTWTR() {
	return TWTR;
}

public void setTWTR(String tWTR) {
	TWTR = tWTR;
}

public String getSymbol() {
	return symbol;
}

public void setSymbol(String symbol) {
	this.symbol = symbol;
}

public void search() {


	System.out.println("Calling AlphaVantage API...");
	Client client= ClientBuilder.newClient();

	// Core settings are here, put what ever API parameter you want to use
	WebTarget target= client.target("https://www.alphavantage.co/query")
	   .queryParam("function", "TIME_SERIES_WEEKLY")
	   .queryParam("symbol", getSymbol())
	   .queryParam("apikey", "0TA7F4H75L983RJZ");
	// Actually calling API here, Use HTTP GET method
	// data is the response JSON string
	String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
	
	try {
		// Use Jackson to read the JSON into a tree like structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data);
		
		// Make sure the JSON is an object, as said in their documents
		assert root.isObject();
		// Read the "Meta Data" property of JSON object
		JsonNode metadata = root.get("Meta Data");
		assert metadata.isObject();
		// Read "2. Symbol" property of "Meta Data" property
		if (metadata.get("2. Symbol").isValueNode()) {
			System.out.println(metadata.get("2. Symbol").asText());
		}
		// Print "4. Time Zone" property of "Meta Data" property of root JSON object
		System.out.println(root.at("/Meta Data/4. Time Zone").asText());
		// Read "Weekly Time Series" property of root JSON object
		Iterator<String> dates = root.get("Weekly Time Series").fieldNames();
		while(dates.hasNext()) {
			// Read the first date's open price
			String n = root.at("/Weekly Time Series/" + dates.next() + "/1. open").asText();
			System.out.println(Double.parseDouble(n));
			// remove break if you wan't to print all the open prices.
			break;
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


public void searchBycurrent() {

	  double price = 0.0;
		System.out.println("Calling AlphaVantage API...");
		Client client= ClientBuilder.newClient();

		// Core settings are here, put what ever API parameter you want to use
		WebTarget target= client.target("https://www.alphavantage.co/query")
		   .queryParam("function", "TIME_SERIES_INTRADAY")
		   .queryParam("symbol", getSymbol())
		   .queryParam("interval", "1min")
		   .queryParam("apikey", "0TA7F4H75L983RJZ");
		// Actually calling API here, Use HTTP GET method
		// data is the response JSON string
		String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		try {// Use Jackson to read the JSON into a tree like structure
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(data);
			
			// Make sure the JSON is an object, as said in their documents
			assert root.isObject();
			// Read the "Meta Data" property of JSON object
			JsonNode metadata = root.get("Meta Data");
			assert metadata.isObject();
			// Read "2. Symbol" property of "Meta Data" property
			if (metadata.get("2. Symbol").isValueNode()) {
				System.out.println(metadata.get("2. Symbol").asText());
			}
			// Print "4. Time Zone" property of "Meta Data" property of root JSON object
			System.out.println(root.at("/Meta Data/4. Interval").asText());
			// Read "Weekly Time Series" property of root JSON object
			Iterator<String> dates = root.get("Time Series (1min)").fieldNames();
			while(dates.hasNext()) {
				// Read the first date's open price
				String n = root.at("/Time Series (1min)/" + dates.next() + "/4. close").asText();
				System.out.println("Price of One"+ Double.parseDouble(n));
				price =Double.parseDouble(n);
				setCurrentPrice(price);
				// remove break if you wan't to print all the open prices.
				break;
			}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  

}

public double searchstock(String id,int qty,String symbl) {


  double price = 0.0;
	System.out.println("Calling AlphaVantage API...");
	Client client= ClientBuilder.newClient();

	// Core settings are here, put what ever API parameter you want to use
	WebTarget target= client.target("https://www.alphavantage.co/query")
	   .queryParam("function", "TIME_SERIES_INTRADAY")
	   .queryParam("symbol", symbl)
	   .queryParam("interval", "1min")
	   .queryParam("apikey", "0TA7F4H75L983RJZ");
	// Actually calling API here, Use HTTP GET method
	// data is the response JSON string
	String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
	
	try {// Use Jackson to read the JSON into a tree like structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data);
		
		// Make sure the JSON is an object, as said in their documents
		assert root.isObject();
		// Read the "Meta Data" property of JSON object
		JsonNode metadata = root.get("Meta Data");
		assert metadata.isObject();
		// Read "2. Symbol" property of "Meta Data" property
		if (metadata.get("2. Symbol").isValueNode()) {
			System.out.println(metadata.get("2. Symbol").asText());
		}
		// Print "4. Time Zone" property of "Meta Data" property of root JSON object
		System.out.println(root.at("/Meta Data/4. Interval").asText());
		// Read "Weekly Time Series" property of root JSON object
		Iterator<String> dates = root.get("Time Series (1min)").fieldNames();
		while(dates.hasNext()) {
			// Read the first date's open price
			String n = root.at("/Time Series (1min)/" + dates.next() + "/4. close").asText();
			System.out.println("Price of One"+ Double.parseDouble(n));
			price =Double.parseDouble(n);
			// remove break if you wan't to print all the open prices.
			break;
		}} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  return price;
}



public String searchHistoryForSelected(String symbl) 
{

	System.out.println("Calling AlphaVantage API...");
	Client client= ClientBuilder.newClient();

	// Core settings are here, put what ever API parameter you want to use
	WebTarget target= client.target("https://www.alphavantage.co/query")
	   .queryParam("function", "TIME_SERIES_WEEKLY")
	   .queryParam("symbol", symbl)
	   .queryParam("apikey", "0TA7F4H75L983RJZ");
	// Actually calling API here, Use HTTP GET method
	// data is the response JSON string
	String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
	
	try {
		// Use Jackson to read the JSON into a tree like structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data);
		
		// Make sure the JSON is an object, as said in their documents
		assert root.isObject();
		// Read the "Meta Data" property of JSON object
		JsonNode metadata = root.get("Meta Data");
		assert metadata.isObject();
		// Read "2. Symbol" property of "Meta Data" property
		if (metadata.get("2. Symbol").isValueNode()) {
			System.out.println(metadata.get("2. Symbol").asText());
		}
		// Print "4. Time Zone" property of "Meta Data" property of root JSON object
		System.out.println(root.at("/Meta Data/4. Time Zone").asText());
		// Read "Weekly Time Series" property of root JSON object
		Iterator<String> dates = root.get("Weekly Time Series").fieldNames();
		while(dates.hasNext()) {
			// Read the first date's open price
			String n = root.at("/Weekly Time Series/" + dates.next() + "/1. open").asText();
			System.out.println(Double.parseDouble(n));
			// remove break if you wan't to print all the open prices.
			
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return "ViewStockHistory.xhtml";

}


public double searchPrice(String symbl) {


	  double price = 0.0;
		System.out.println("Calling AlphaVantage API...");
		Client client= ClientBuilder.newClient();

		// Core settings are here, put what ever API parameter you want to use
		WebTarget target= client.target("https://www.alphavantage.co/query")
		   .queryParam("function", "TIME_SERIES_INTRADAY")
		   .queryParam("symbol", symbl)
		   .queryParam("interval", "1min")
		   .queryParam("apikey", "0TA7F4H75L983RJZ");
		// Actually calling API here, Use HTTP GET method
		// data is the response JSON string
		String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		try {// Use Jackson to read the JSON into a tree like structure
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(data);
			
			// Make sure the JSON is an object, as said in their documents
			assert root.isObject();
			// Read the "Meta Data" property of JSON object
			JsonNode metadata = root.get("Meta Data");
			assert metadata.isObject();
			// Read "2. Symbol" property of "Meta Data" property
			if (metadata.get("2. Symbol").isValueNode()) {
				System.out.println(metadata.get("2. Symbol").asText());
			}
			// Print "4. Time Zone" property of "Meta Data" property of root JSON object
			System.out.println(root.at("/Meta Data/4. Interval").asText());
			// Read "Weekly Time Series" property of root JSON object
			Iterator<String> dates = root.get("Time Series (1min)").fieldNames();
			while(dates.hasNext()) {
				// Read the first date's open price
				String n = root.at("/Time Series (1min)/" + dates.next() + "/4. close").asText();
				System.out.println("Price of One"+ Double.parseDouble(n));
				price =Double.parseDouble(n);
				//setCurrentPrice(price);
				// remove break if you wan't to print all the open prices.
				break;
			}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  
return price;

}


public double searchPriceOpen(String symbl) {



	  double price = 0.0;
		System.out.println("Calling AlphaVantage API...");
		Client client= ClientBuilder.newClient();

		// Core settings are here, put what ever API parameter you want to use
		WebTarget target= client.target("https://www.alphavantage.co/query")
		   .queryParam("function", "TIME_SERIES_INTRADAY")
		   .queryParam("symbol", symbl)
		   .queryParam("interval", "1min")
		   .queryParam("apikey", "0TA7F4H75L983RJZ");
		// Actually calling API here, Use HTTP GET method
		// data is the response JSON string
		String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		try {// Use Jackson to read the JSON into a tree like structure
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(data);
			
			// Make sure the JSON is an object, as said in their documents
			assert root.isObject();
			// Read the "Meta Data" property of JSON object
			JsonNode metadata = root.get("Meta Data");
			assert metadata.isObject();
			// Read "2. Symbol" property of "Meta Data" property
			if (metadata.get("2. Symbol").isValueNode()) {
				System.out.println(metadata.get("2. Symbol").asText());
			}
			// Print "4. Time Zone" property of "Meta Data" property of root JSON object
			System.out.println(root.at("/Meta Data/4. Interval").asText());
			// Read "Weekly Time Series" property of root JSON object
			Iterator<String> dates = root.get("Time Series (1min)").fieldNames();
			while(dates.hasNext()) {
				// Read the first date's open price
				String n = root.at("/Time Series (1min)/" + dates.next() + "/1. open").asText();
				System.out.println("Price of One"+ Double.parseDouble(n));
				price =Double.parseDouble(n);
				//setCurrentPrice(price);
				// remove break if you wan't to print all the open prices.
				break;
			}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  
return price;


}
}
