package pom.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pom.Pages.LoginPage;
import pom.Pages.MatrizPage;

public class MatrizTest {
	
	private WebDriver driver;
	LoginPage loginPage;
	MatrizPage matrizPage;

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage(driver);
		driver = loginPage.chromeDriverConnection();
		loginPage.visit("https://tasks.evalartapp.com/automatization/");
		driver.manage().window().maximize();
		matrizPage = new MatrizPage(driver);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void navigateMatrizTest() throws InterruptedException {
		loginPage.login("428789", "10df2f32286b7120My0zLTk4NzgyNA==30e0c83e6c29f1c3");
		for (int i = 0; i < 10; i++) {
			matrizPage.verifyCicloCounter();	
			loginPage.zoomInOrOut();
			matrizPage.clickMatrixButton();
			matrizPage.solveQuestion();
		}
		assertEquals(matrizPage.getFinalMessage(), "Felicidades, has terminado la prueba exitosamente");
		System.out.println("hash de éxito: "+matrizPage.getHashExito());
	}

}
