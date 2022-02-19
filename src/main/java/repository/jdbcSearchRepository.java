package repository;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class jdbcSearchRepository implements SearchRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String insert = "INSERT INTO user_searches ( method,country,category,q,sources,`from`,`to`,language,accountid) values( ?,?,?,?,?,?,?,?,?) ";
	private static final String select="SELECT id,method,country,category,q as keyword,sources,`from`,`to`,language,accountid FROM user_searches WHERE accountID=? ORDER BY id DESC LIMIT 5";
	@Override
	public int saveSearch(Search search) {
		

		Object[] x = new Object[] { search.getMethod(), search.getCountry(), search.getCategory(), search.getKeyword(),
				search.getSources(), search.getFrom(), search.getTo(), search.getLanguage(), search.getAccountid() };
//		

		return jdbcTemplate.update(insert, x);

	}

	

	@Override
	public List <Search> findLastSearches(UserAccount user) {
		try {
			List <Search> s = jdbcTemplate.query(select,
	          BeanPropertyRowMapper.newInstance(Search.class), user.getId());
	      return s;
	    } catch (IncorrectResultSizeDataAccessException e) {
	      return null;
	    }
	}



	@Override
	public List<Search> selectSearch(UserAccount user, int selection) {
		
		return null;
	}
	
	

}
