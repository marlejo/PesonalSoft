package pom.Pages;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import org.apache.commons.lang3.StringUtils;

import pom.Base.Base;

public class FormPage extends Base{

	public FormPage(WebDriver driver) {
		super(driver);
	}
	
	By btn_Enviar = By.xpath("//button[@type='submit']");
	By txt_dateFormQuestion = By.xpath("//p[contains(text(), 'Indique la fecha correspondiente ')]");
	By txt_TitleFormPage = By.xpath("//h1[contains(text(),'Prueba de automatización')]");
	By input_dateFormQuestion = By.name("date");
	By dropbox_selectResultOfExpression = By.name("select");
	By container_questionsText = By.xpath("//div[contains(@class,'bg-white')]//p[@class='text-center text-xl'][1]");
	By container_questions = By.xpath("//div[contains(@class,'items')]");
	By txt_problemFormExpression = By.xpath("//p[contains(@class,'font-bold')]");
	By input_symbolCounter = By.name("number");
	By input_textArea = By.xpath("//textarea");
	By checkbox = By.name("checkbox");
	int cicloCounter=1;
	By txt_finalMessage = By.xpath("//p[contains(@class,'break-all')]");
	By txt_finalText = By.xpath("//h1[text()='Felicidades, has terminado la prueba exitosamente']");
	
	public String getFinalMessage() {
		return getText(txt_finalText);
	}
	
	public String getHashExito() {
		return getText(txt_finalMessage);
	}
	
	public int readDate(String questionText) {
		String[] fullTextSplited = new String[3];
		fullTextSplited = questionText.split(" ");
		if(fullTextSplited[4].equals("de")) {
			return -1*Integer.parseInt(fullTextSplited[6].trim());
		}else {
			return Integer.parseInt(fullTextSplited[6].trim());
		}		
	}
	
	public void calculateDate(String questionText) {
		int numberOfDays = readDate(questionText);
		String dateModify = LocalDate.now().plusDays(numberOfDays).format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
		type(dateModify, input_dateFormQuestion);
	}
	
	public void verifyFormPageIsDisplayed() {
		assertTrue(isDisplayed(txt_TitleFormPage));
	}
	
	public void verifyCicloCounter(){
		By txt_cicloCounter = By.xpath("//p[text()='Se encuentra en el ciclo "+cicloCounter+" de 10']");
		waitForElementVisible(txt_cicloCounter);
		cicloCounter++;
	}	
	
	public void resolveExpression(String problem){
		String expression;
		if(problem.contains("=?")) {
			expression = problem.substring(0, problem.length()-2);
			String result = new DecimalFormat("#").format(new DoubleEvaluator().evaluate(expression)).toString();
			By checkbox_correctResult = By.xpath("//input[contains(@value,'"+result+"' )]");
			click(checkbox_correctResult);
		}else {
			expression = problem;
			Select objSelect = new Select(findElement(dropbox_selectResultOfExpression));
			objSelect.selectByValue(new DecimalFormat("#").format(new DoubleEvaluator().evaluate(expression)).toString());
		}
	}
	
	public void countImages(String questionText, String list) {
		String[] fullTextSplited = new String[3];
		fullTextSplited = questionText.split(" ");
		String symbol = fullTextSplited[2];
		type(StringUtils.countMatches(list, symbol)+"", input_symbolCounter);
	}
	
	public void typeNumberOfTimes(String questionText) {
		String[] fullTextSplited = new String[3];
		fullTextSplited = questionText.split(" ");
		int numberOfRepetions = Integer.parseInt(fullTextSplited[1]) ;
		String nChar = StringUtils.repeat(fullTextSplited[5].replace("'", ""), numberOfRepetions);
		type(nChar, input_textArea);
	}
	
	public void findMultiplos(String questionText) {
		String[] fullTextSplited = new String[3];
		fullTextSplited = questionText.split(" ");
		int multiplo = Integer.parseInt(fullTextSplited[7].replace("?", ""));
		List<WebElement> listNumbers = findElements(checkbox);
		for (int i = 0; i < listNumbers.size(); i++) {
			int numberToEvaluate = Integer.parseInt(listNumbers.get(i).getAttribute("value"));
			if(numberToEvaluate%multiplo==0) {
				click(listNumbers.get(i));
			}
		}
	}
		
	public void testcontainers() {
		List<WebElement> listQuestions= findElements(container_questionsText);
		for (int i = 0; i < listQuestions.size(); i++) {
			if(getText(listQuestions.get(i)).contains("Indique la fecha correspondiente")) {
				calculateDate(getText(listQuestions.get(i)));
			}
			if(getText(listQuestions.get(i)).contains("Complete la siguiente operación matemática:")) {
				String problem = getText(By.xpath("//div[contains(@class,'bg-white')and contains(@class,'flex-col')]["+(i+1)+"]//p[@class='text-center text-xl font-bold']"));
				resolveExpression(problem);
			}
			if(getText(listQuestions.get(i)).contains("Indique cuantos ")) {
				countImages(getText(listQuestions.get(i)), getText(By.xpath("//div[contains(@class,'bg-white') and contains(@class,'flex-col')]["+(i+1)+"]//p[2]")));
			}
			if(getText(listQuestions.get(i)).contains("Escriba ")) {
				typeNumberOfTimes(getText(listQuestions.get(i)));
			}
			if(getText(listQuestions.get(i)).contains("¿Cuál de estos números es múltiplo de")) {
				findMultiplos(getText(listQuestions.get(i)));
			}
		}
		click(btn_Enviar);
	}
	
	

}
