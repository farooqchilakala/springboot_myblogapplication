package net.farooq.Storify.playload;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
	
    public LoginDto() {
  		
  	}
    
    public LoginDto(String usernameOrEmail, String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
