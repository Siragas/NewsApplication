package repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import client.RestConsumer;
import except.NewsApiException;
import model.NewsInfo;

@Service
public class SearchService {
	@Autowired
	SearchRepository searchRepository;
	

	public void searchTopHeadlines(UserAccount user ,InputStreamReader input, Scanner in2) {
		System.out.println(
				"Please select country and/or category.For default country or category enter 1.\nCategory options:business,entertainment,general,health,science,sports,technology"
				+ "\nCountry options:only formal country names are accepted,for example UNITED KINGDOM, United KINGDOM etc");

		System.out.print("\nPlease enter country or '1' for default:");

		BufferedReader br = new BufferedReader(input);

		StringTokenizer St = null;
		try {
			St = new StringTokenizer(br.readLine());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String country = "";

		while (St.hasMoreTokens()) {
			String token = St.nextToken();
			country += token + " ";
		}

		country = country.strip();

		
		System.out.print("Please enter category or '1' for every category:");
		String category = in2.next();
		
		Map<String, String> params = new HashMap<String, String>();

		if (country.equals("1") && category.equals("1")) {
			
			try {
				String myCountry = Helper.get_CountryIP();
				params.put("country", myCountry);
				Search s = new Search(0,"v2/top-headlines", params.get("country"),params.get("category"),null,null,null,null,null, user.getId() );
				searchRepository.saveSearch(s);
				List<NewsInfo> lista = new ArrayList<>();
				lista = RestConsumer.get_Data(params, "v2/top-headlines");
				lista.stream().forEach(System.out::println);
			} catch (NewsApiException e) {
				System.out.println(e.getMessage());

			}

		} else if (country.equals("1")) {

			if (Helper.GetCategory(category)) {

				try {
					params.put("category", category);
					List<NewsInfo> lista = new ArrayList<>();
					String myCountry = Helper.get_CountryIP();
					params.put("country", myCountry);
					Search s = new Search(0,"v2/top-headlines", params.get("country"),params.get("category"),null,null,null,null,null, user.getId() );
					searchRepository.saveSearch(s);
					lista = RestConsumer.get_Data(params, "v2/top-headlines");
					lista.stream().forEach(System.out::println);
				} catch (NewsApiException e) {
					System.out.println(e.getMessage());
				}

			} else {
				System.out.println("Wrong category input,please try again");
			}

		} else {

			if (Helper.GetCategory(category) && !Helper.GetCountryName(country).equals("Wrong Country Name")) {
				params.put("category", category);
				params.put("country", Helper.GetCountryName(country));
				List<NewsInfo> lista = new ArrayList<>();
				try {
					Search s = new Search(0,"v2/top-headlines", params.get("country"),params.get("category"),null,null,null,null,null, user.getId() );
					searchRepository.saveSearch(s);
					lista = RestConsumer.get_Data(params, "v2/top-headlines");
					lista.stream().forEach(System.out::println);
				} catch (NewsApiException e) {
					System.out.println(e.getMessage());
				}

			}

			else if (!Helper.GetCountryName(country).equals("Wrong Country Name") && category.equals("1")) {
				params.put("country", Helper.GetCountryName(country));
				List<NewsInfo> lista = new ArrayList<>();
				try {
					Search s = new Search(0,"v2/top-headlines", params.get("country"),params.get("category"),null,null,null,null,null, user.getId() );
					searchRepository.saveSearch(s);
					lista = RestConsumer.get_Data(params, "v2/top-headlines");
					lista.stream().forEach(System.out::println);
				} catch (NewsApiException e) {
					System.out.println(e.getMessage());
				}

			}

			else if (Helper.GetCountryName(country).equals("Wrong Country Name")) {
				System.out.println("Wrong country input,please try again");

			}

			else if (!Helper.GetCategory(category)) {
				System.out.println("Wrong category input,please try again");
			
				
			}
			
		}

	}

	public void searchTopHeadlinesLocal(UserAccount user) {
		Map<String, String> params = new HashMap<String, String>();
		
		
		try {
			String myCountry = Helper.get_CountryIP();
			params.put("country", myCountry);
			List<NewsInfo> lista = new ArrayList<>();
			Search s = new Search(0,"v2/top-headlines", myCountry,null,null,null,null,null,null, user.getId() );
			searchRepository.saveSearch(s);
			lista = RestConsumer.get_Data(params, "v2/top-headlines");
			lista.stream().forEach(System.out::println);
		} catch (NewsApiException e) {
			System.out.println(e.getMessage());

		}
	}
	
	public void searchAllbySource(UserAccount user,InputStreamReader input, Scanner in2) {
		System.out.print(
				"Keyword options: you can search via keywords or phrases, you can also use AND / OR / NOT keywords\n"
				+ "Source options:the search is limited to only the sources recognized by the app.For example 'New York Times' is not recognized as\n"
				+ "a source.The app will return articles from 'New York Times' but it cannot search specifically for this source. \n"
				+"You can search for articles published by 'New York Times' via the keyword option, but results will most definitely be ambiguous\n"
				+ "Some news sources you can search for are reuters,cnn etc\n"
				+"Finally, you can search for multiple sources by seperating them with commas\n"
				+ "Language options:filter the results based on language.Only official language names are accepted, for example 'French','English' etc\n"
				+ "Date options:filter the results from-to specific dates.You can access articles up to a month old"); 
		
		System.out.print("\nPlease enter keyword or phrase or '1' to search only by source:");

		BufferedReader br = new BufferedReader(input);

		StringTokenizer St = null;
		try {
			St = new StringTokenizer(br.readLine());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String keyword = "";

		while (St.hasMoreTokens()) {
			String token = St.nextToken();
			keyword += token + " ";
		}

		keyword = keyword.strip();
		
		keyword=URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		
		System.out.print("Please enter source or '1' for all sources.If you have not selected a keyword this field is mandatory:");
		
		BufferedReader br1 = new BufferedReader(input);

		StringTokenizer St1 = null;
		try {
			St1 = new StringTokenizer(br1.readLine());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String sources = "";

		while (St1.hasMoreTokens()) {
			String token = St1.nextToken();
			sources += token + " ";
		}

		sources = sources.strip();
		
		System.out.print("Please enter language(for example 'English') or '1' for all:");  
	    
		String language = in2.next().trim();
		
		System.out.print("Please the oldest date to search from or '1' for default"
				+ "Date format:YY/MM/DD	Year-Month-Day with leading zeros (2009-02-17) :");
		
		String from_date = in2.next().trim();
		
		System.out.print("Please the latest date to search from or '1' for default"
				+ "Date format:YY/MM/DD	Year-Month-Day with leading zeros (2009-02-17) :");
		
		String to_date = in2.next().trim();
		
		Map<String, String> params = new HashMap<String, String>();
		
		if (!sources.equals("1")) {
			params.put("sources", sources);
		}
		
		
		
		if (!language.equals("1") && !Helper.GetLanguageName(language).equals("Wrong Language Name")) {
			params.put("language", Helper.GetLanguageName(language));
		}
		
		
		if (!from_date.equals("1")) {
			params.put("from", from_date);
		}
		
		if (!to_date.equals("1")) {
			params.put("to", to_date);
		}
		
		if (!keyword.equals("1")) {
			params.put("q", keyword);
		}
		//method,country,category,q,sources,`from`,`to`,language,accountid)
		Search s = new Search(0,"v2/everything", null,null,params.get("q"),params.get("sources"),params.get("from"),params.get("to"),params.get("language"), user.getId() );
		
		
		try {
			searchRepository.saveSearch(s);
			List<NewsInfo> lista = new ArrayList<>();
			lista = RestConsumer.get_Data(params, "v2/everything");
			lista.stream().forEach(System.out::println);
		} catch (NewsApiException e) {
			System.out.println(e.getMessage());
		}
	}

	public void searchAllbyCategory(UserAccount user,InputStreamReader input, Scanner in2) {
		

	 System.out.print(
			"Keyword options: you can search via keywords or phrases, you can also use AND / OR / NOT keywords\n"
			+ "Category options:the search is limited to only the sources recognized from the app who fall under the category of your choice\n"
			+ "Language options:filter the results based on language.Only official language names are accepted, for example 'French','English' etc\n"
			+ "Date options:filter the results from-to specific dates.You can access articles up to a month old"); 
	
	 System.out.print("\nPlease enter keyword or phrase or '1' to search only by category:");
	 
		BufferedReader br = new BufferedReader(input);

		StringTokenizer St = null;
		try {
			St = new StringTokenizer(br.readLine());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String keyword = "";

		while (St.hasMoreTokens()) {
			String token = St.nextToken();
			keyword += token + " ";
		}

		keyword = keyword.strip();
		
		keyword=URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		
		String category="";
		while (category.equals("")) {
		System.out.print("Please enter category or '1' for all categories.Category options:business,entertainment,general,health,science,sports,technology\n"
				+"If you have not selected a keyword this field is mandatory:");
		String cat; 
		cat = in2.next().trim();
		if (cat.equals("1" )|| Helper.GetCategory(cat)) {
			category=cat;
		}
		else {
			category="";
		}
			
		}
		
		
		System.out.print("Please enter language(for example 'English') or '1' for all:");  
	    
		String language = in2.next().trim();
		
		System.out.print("Please the oldest date to search from or '1' for default"
				+ "Date format:YY/MM/DD	Year-Month-Day with leading zeros (2009-02-17) :");
		
		String from_date = in2.next().trim();
		
		System.out.print("Please the latest date to search from or '1' for default"
				+ "Date format:YY/MM/DD	Year-Month-Day with leading zeros (2009-02-17) :");
		
		String to_date = in2.next().trim();
		
		Map<String, String> params = new HashMap<String, String>();

		
		if (!language.equals("1") && !Helper.GetLanguageName(language).equals("Wrong Language Name")) {
			params.put("language", Helper.GetLanguageName(language));
		}
		
		
		if (!from_date.equals("1")) {
			params.put("from", from_date);
		}
		
		if (!to_date.equals("1")) {
			params.put("to", to_date);
		}
		
		if (!keyword.equals("1")) {
			params.put("q", keyword);
		}
		
		
		try {
			
			if (!category.equals("1")) {
			String sources=Helper.GetSourcesByCategory(category);
			params.put("sources", sources);
			
			}	
			Search s = new Search(0,"v2/everything", null,null,params.get("q"),params.get("sources"),params.get("from"),params.get("to"),params.get("language"), user.getId() );
			searchRepository.saveSearch(s);
			List<NewsInfo> lista = new ArrayList<>();
			lista = RestConsumer.get_Data(params, "v2/everything");
			lista.stream().forEach(System.out::println);
		} catch (NewsApiException e) {
			System.out.println(e.getMessage());
		}




	}
	
	public void findLastSearches(UserAccount user,Scanner input) {
		
		List<Search> lista = searchRepository.findLastSearches(user);
		lista.stream().forEach(System.out::println);
		List<Object> lista2 =new ArrayList<>();
		for (Search result : lista) {
			lista2.add(result.getId());
		}
		System.out.println("Select one of the following to repeat search,or anything to return back to the menu"+lista2);
		System.out.print("Enter your input:");
		String choice = input.next().trim();
		
		int choice2;
		if (StringUtils.isNumeric(choice)) {
			 choice2= Integer.valueOf(choice);	 
		}
		else {choice2=0;}
		
		if (lista2.contains(choice2)) {
			//Predicate <Search> selection=e->(e.getId()==choice2);
			//lista.stream().filter(selection).forEach(System.out::println);
			List<Search> selection = lista.stream()
				    .filter(p -> p.getId() == choice2).collect(Collectors.toList());
			System.out.println(selection);
			Search mySearch = (Search) selection.get(0);
			Map<String, String> params = new HashMap<String, String>();
			params.put("country", mySearch.getCountry());
			params.put("category", mySearch.getCategory());
			params.put("q", mySearch.getKeyword());
			params.put("sources", mySearch.getSources());
			params.put("from", mySearch.getFrom());
			params.put("to", mySearch.getTo());
			params.put("language", mySearch.getLanguage());
			
			params.values().removeIf(Objects::isNull);
			
			
			try {
				
				List<NewsInfo> results = new ArrayList<>();
				results = RestConsumer.get_Data(params, mySearch.getMethod());
				results.stream().forEach(System.out::println);
			} catch (NewsApiException e) {
				System.out.println(e.getMessage());
			}
			
			
			
		}
		
		
		
	}
	
	
	
	

}




