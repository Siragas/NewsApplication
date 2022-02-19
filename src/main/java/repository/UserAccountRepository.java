package repository;

public interface UserAccountRepository {
	UserAccount findByUsernamePass(String username,String password);
//	boolean checkuser(String username, String password);
	int save(String username,String password);
	int findUserIDbyUsername(String username);
	
}
