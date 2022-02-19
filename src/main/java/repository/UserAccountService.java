package repository;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserAccountService {

	@Autowired
	UserAccountRepository userAccountRepository;
	
	public  UserAccount getUserByUserPass(String username, String password) {
	
		return userAccountRepository.findByUsernamePass(username,password);
	    
	}
	
	public  int saveUser(String username, String password) {
		
		return userAccountRepository.save(username,password);
	    
	}
	
	public  int findUserID(String username) {
		
		return userAccountRepository.findUserIDbyUsername(username);
	    
	}
	
	
	public  UserAccount login(Scanner in1) {
		
		UserAccount user = null;
		
		System.out.print("Please enter username:");  
	    String givenUsername = in1.next().trim();
	    
	    System.out.print("Please enter password:");
	    String givenPass = in1.next().trim();
	    
	     user = getUserByUserPass(givenUsername, givenPass);
	    
		
	    return user; 
		
	}
	
public  int signup(Scanner in1) {
		
		int signup_status = 0;
		
		System.out.print("Please enter username:");  
	    String givenUsername = in1.next().trim();
	    
	    System.out.print("Please enter password:");
	    String givenPass = in1.next().trim();
	    
	    signup_status = saveUser(givenUsername, givenPass);
	    
		
	    return signup_status; 
		
	}
	
	
	
	

}
