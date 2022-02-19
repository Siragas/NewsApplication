package repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class jdbcUserAccountRepository implements UserAccountRepository{

@Autowired
private JdbcTemplate jdbcTemplate;
	
@Override
public UserAccount findByUsernamePass (String username, String password) {
		 
		    try {
		    	UserAccount user = jdbcTemplate.queryForObject("SELECT * FROM useraccount WHERE username=? and password=?",
		          BeanPropertyRowMapper.newInstance(UserAccount.class), username,password);//encrypt password , otherwise select will not work properly
		    	//decrypt to user.getPassword()
		      return user;
		    } catch (IncorrectResultSizeDataAccessException e) {
		      return null;
		    }
		  }


@Override
public int save(String username,String password) {
	if (checkuser (username)==true) {
		return 0;
	}
	else {
	return jdbcTemplate.update("INSERT INTO useraccount (username, password) VALUES(?,?)",
	        new Object[] { username, password});}
	///edw petaw encrypt sto user.getpassword giati kanw eisagwgh sth bash
	  }



private boolean checkuser(String username) {
	
	List<UserAccount> users =jdbcTemplate.
			query("SELECT * FROM useraccount WHERE username=? ", BeanPropertyRowMapper.newInstance(UserAccount.class),username);
	
	if (users.size()> 0) {return true; }
	else {return false;}
	
	
}


@Override
public int findUserIDbyUsername(String username) {
	
	   	UserAccount user = jdbcTemplate.queryForObject("SELECT * FROM useraccount WHERE username=?",
        BeanPropertyRowMapper.newInstance(UserAccount.class), username);
	   	return user.getId();
    
}


//private static String encrypt(String pass)
//private static String encrypt(String pass)

}
	


