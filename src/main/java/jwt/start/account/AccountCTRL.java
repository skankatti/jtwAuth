package jwt.start.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountCTRL {
	
	@GetMapping("/welcome")
	public String WelcomePage() {
		return "Welcome to JWT Auth Page";
	}

}
