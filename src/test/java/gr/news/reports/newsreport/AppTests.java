package gr.news.reports.newsreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import client.RestConsumer;
import except.NewsApiException;
import model.NewsInfo;
import model.SourcesInfo;
import repository.Helper;

public class AppTests {
	@Test

	public void GetCountryFromIP() throws NewsApiException {
		final String countryIP = Helper.get_CountryIP();
		Assert.assertFalse(!countryIP.equals("GR"));

	}

	@Test

	public void GetApiSources() throws NewsApiException {

		final List<SourcesInfo> restSources = RestConsumer.get_Data_sources();
		Assert.assertFalse(restSources.isEmpty());
		

	}
	
	@Test

	public void GetData() throws NewsApiException {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("country","GB");
		params.put("category", "sports");
		final List<NewsInfo> rest = RestConsumer.get_Data(params, "v2/top-headlines");
		Assert.assertFalse(rest.isEmpty());
		

	}
	
	@Test
	public void GetSources() throws NewsApiException {

		final String sportsCategories = Helper.GetSourcesByCategory("sports");
		Assert.assertFalse(sportsCategories.isEmpty());
		System.out.println(sportsCategories);
		

	}

	
	@Test
	public void GetLanguageName() {

		String correctcountry = Helper.GetCountryName("uniTED kingDOM");
		Assert.assertFalse(!correctcountry.equals("GB"));
		

	}
	
	
	
}
