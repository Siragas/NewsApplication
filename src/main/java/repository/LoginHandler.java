package repository;

public class LoginHandler {

	private String username;
	private String password;
	private jdbcUserAccountRepository repo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public jdbcUserAccountRepository getRepo() {
		return repo;
	}

	public void setRepo(jdbcUserAccountRepository repo) {
		this.repo = repo;
	}

	public LoginHandler(String username, String password, jdbcUserAccountRepository repo) {
		super();
		this.username = username;
		this.password = password;
		this.repo = repo;
	}

//	public boolean checklogin() {
//
//		System.out.print("\nPlease enter your username: ");
//		username = input.nextLine();
//		System.out.print("Please enter your password : ");
//		password = input.nextLine();
//		if (repo.findByUsernamePass(username, password) == null) {
//			return false;
//		} else {
//			return false;
//		}
//
//	}
}