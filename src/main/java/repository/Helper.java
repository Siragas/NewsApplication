package repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;

import client.RestConsumer;
import except.NewsApiException;
import model.ErrorResponse;
import model.IPInfo;
import model.IPResult;
import model.SourcesInfo;

@Service
public class  Helper  {
	
	

	/// helper class to filter user inputs for current headlines
	// GetCountryName gets Greece and returns official code gr in order to use in API request, it also gets UNITED KINGDOM and gives gb
	//Get Category returns the appropriate category 
	// Get Language Name takes English and gives back en, French and gives back fr (official codes)
	
	public static String GetCountryName (String Country) {
	    
		Country=WordUtils.capitalizeFully(Country);
		List <CountryCode> Country_Code_List = CountryCode.findByName(Country);
		int size =Country_Code_List.size();
		
		if (size==0) {
			return ("Wrong Country Name");}
		
		else {
		
			String Country_Code =  Country_Code_List.get(0).name();
			return Country_Code;
			
		}
		
	}
	
	
	public static boolean GetCategory (String Category) {
		
		Category=Category.toLowerCase();
		
		List<String> Categories =new ArrayList<String>();
		Categories.add("business");
		Categories.add("entertainment");
		Categories.add("general");
		Categories.add("health");
		Categories.add("science");
		Categories.add("sports");
		Categories.add("technology");
		 
		if (Categories.contains(Category)){
			return true;
		}
		else {
			return false;
		}
		
			
	}
	
	
	public static String GetSourcesByCategory(String category) throws NewsApiException{
		
		List <SourcesInfo> x= RestConsumer.get_Data_sources();
		String sourcesbycategory = x
				  .stream()
				  .filter(c -> c.getCategory().equals(category))
				  .map(c -> c.getId())
				  .limit(20)
				  .collect(Collectors.joining(","));
		return sourcesbycategory;
		
		
	}
	

	
	
	
	
	public static String GetLanguageName (String Language) {
	    
		Language=WordUtils.capitalizeFully(Language);
		List<LanguageCode> Language_Code_List = LanguageCode.findByName(Language);
		int size =Language_Code_List.size();
		
		if (size==0) {
			return ("Wrong Language Name");}
		
		else {
		
			String Language_Code =  Language_Code_List.get(0).name();
			return Language_Code;
			
		}
		
	}
	
	
	public static String get_CountryIP () throws NewsApiException {
		 
		 
		 
		 String uri = "http://ip-api.com/json/";
		 
		 HttpEntity entity;
		 
		 final HttpGet getRequest = new HttpGet(uri);
//		 uri.substring(uri.indexOf('?'), uri.length());
		 
		 final CloseableHttpClient httpclient = HttpClients.createDefault();
		 try (CloseableHttpResponse response = httpclient.execute(getRequest)) {
				  entity = response.getEntity();
				 ObjectMapper mapper = new ObjectMapper();

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
					if (errorResponse.getMessage() != null)
						throw new NewsApiException ("Error occurred on API call: " + errorResponse.getMessage());
				}
				
				IPResult result = mapper.readValue(entity.getContent(), IPResult .class);
				String theResult= new IPInfo(result).getCountry();
				return theResult;
			} 
		 
		 catch (IOException e) {
			 	
				throw new NewsApiException ("Error requesting data from the IP API.", e);
			}
		
		 
		 
	  }
		
	}
	

	
