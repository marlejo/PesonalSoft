package pom.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import pom.Base.Base;

public class LoginPage extends Base{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	By inputUserName = By.name("username");
	By inputPassword = By.name("password");
	By btnEnviar = By.xpath("//button[@type='submit']");
	
	public void login(String userName, String password) {
		type(userName, inputUserName);
		type(password, inputPassword);
		click(btnEnviar);
	}
	

}
