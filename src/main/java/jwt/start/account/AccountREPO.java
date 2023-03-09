package jwt.start.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountREPO extends JpaRepository<AccountMODL, String>{
	
	
	public AccountMODL findByEmail(String email);

}
