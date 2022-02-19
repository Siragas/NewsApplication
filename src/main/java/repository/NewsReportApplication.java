package repository;

import java.io.InputStreamReader;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan("gr.news.reports")
public class NewsReportApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(NewsReportApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(NewsReportApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

//	@Autowired
//	jdbcUserAccountRepository repo;

	@Autowired
	UserAccountService service;

	@Autowired
	SearchService service2;

	@Override
	public void run(String... args) throws Exception {

		Scanner input = new Scanner(System.in);
		InputStreamReader inp = new InputStreamReader(System.in);
		UserAccount myUser = null;

		String choice;

		while (true) {
			mainMenu();
			choice = input.next();

			if (choice.equals("1")) {
				myUser = service.login(input);
				if (myUser != null) {
					System.out.println("Successful login!");

					break;
				} else {
					System.out.println("Failed to login!");
					continue;
				}
			} else if (choice.equals("2")) {

				int signup_status = 0;
				signup_status = service.signup(input);
				if (signup_status != 0) {
					System.out.println("You have succesfully signed up! Login in to use the news-app");

					continue;
				} else {
					System.out.println("Failed to sign up: Please try a different username");
					continue;
				}

			} else if (choice.equals("3")) {
				System.out.println("Exit program... ");
				System.exit(0);
			} else {
				System.out.println("This is not a valid Menu Option! Please Select Another:");
				continue;
			}

		}

		while (true) {
			appMenu(myUser);
			choice = input.next();
			if (choice.equals("1")) {
				service2.searchTopHeadlinesLocal(myUser);
				continue;
			}

			else if (choice.equals("2")) {
				service2.searchTopHeadlines(myUser,inp, input);
				continue;
			} 
			
			else if (choice.equals("3")) {
				service2.searchAllbySource(myUser,inp, input);
				continue;
			} 
			
			else if (choice.equals("4")) {
				service2.searchAllbyCategory(myUser,inp, input);
				continue;
			} 
			
			else if (choice.equals("5")) {
				service2.findLastSearches(myUser, input);
				continue;
			} 
			
			else if (choice.equals("6")) {
				System.out.println("Exit program... ");
				System.exit(0);
			} 
			
			else {
				System.out.println("This is not a valid Menu Option! Please Select Another:");
				continue;
			}

		}

	}

	private void mainMenu() {
		System.out.println("\nWelcome to the news-application Main Menu");
		System.out.print("1.) Login \n");
		System.out.print("2.) Signup.\n");
		System.out.print("3.) Exit.\n");
		System.out.print("\nEnter Your Menu Choice: ");

	}

	private void appMenu(UserAccount user) {
		System.out.println("\nHello " + user.getUsername() + ", welcome to the newsapp");
		System.out.print("1.) Top Headlines \n");
		System.out.print("2.) Top Headlines (filter by category and/or country) \n");
		System.out.print("3.) Search Articles based on keyword and/or source, \n");
		System.out.print("4.) Search Articles based on keyword and/or category.\n");
		System.out.print("5.) Search based on past results\n");
		System.out.print("6.) Exit.\n");
		System.out.print("\nEnter Your Menu Choice: ");
	}

}
