package pom.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pom.Pages.FormPage;
import pom.Pages.LoginPage;

public class CompleteFormTest {
	
	private WebDriver driver;
	LoginPage loginPage;
	FormPage formPage;

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage(driver);
		driver = loginPage.chromeDriverConnection();
		loginPage.visit("https://tasks.evalartapp.com/automatization/");
		formPage = new FormPage(driver);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void completeFormTest(){
		loginPage.login("428789", "10df2f32286b7120Mi00LTk4NzgyNA==30e0c83e6c29f1c3");
		for (int i = 0; i < 10; i++) {
			formPage.verifyCicloCounter();
			formPage.testcontainers();
		}
		assertEquals(formPage.getFinalMessage(), "Felicidades, has terminado la prueba exitosamente");
		System.out.println("hash de éxito: "+formPage.getHashExito());
	}

}
