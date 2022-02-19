package repository;

import java.util.List;


public interface SearchRepository {
	
	int saveSearch(Search currentSearch );
	List <Search> findLastSearches(UserAccount user);
	List<Search> selectSearch(UserAccount user,int selection);
	
	
}