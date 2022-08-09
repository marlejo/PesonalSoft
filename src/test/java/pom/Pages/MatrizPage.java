package pom.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pom.Base.Base;

public class MatrizPage extends Base {

	public MatrizPage(WebDriver driver) {
		super(driver);
	}
	
	int cicloCounter=1;
	By btn_matrix = By.xpath("//button");
	int matrix[][];
	By txt_coordinates = By.xpath("//p[contains(@class,'text-2xl')]/following-sibling::p");
	By txt_question = By.xpath("//h2");
	By input_answer = By.name("modal_answer");
	By btn_enviar = By.xpath("//button[@type='submit']");
	By txt_finalMessage = By.xpath("//p[contains(@class,'break-all')]");
	By txt_finalText = By.xpath("//h1[text()='Felicidades, has terminado la prueba exitosamente']");
	
	
	public String getFinalMessage() {
		return getText(txt_finalText);
	}
	
	public String getHashExito() {
		return getText(txt_finalMessage);
	}
	
	public void setUpMatrix() {
		List<WebElement> listQuestions= findElements(btn_matrix);
		int rowColumn = (int) Math.sqrt(listQuestions.size());
		matrix = new int[rowColumn][rowColumn];
	}
	
	public void verifyCicloCounter(){
		By txt_cicloCounter = By.xpath("//p[text()='Se encuentra en el ciclo "+cicloCounter+" de 10']");
		waitForElementVisible(txt_cicloCounter);
		cicloCounter++;
	}

	public void fillMatrix() {
		setUpMatrix();
		List<WebElement> listQuestions= findElements(btn_matrix);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = Integer.parseInt(getText(listQuestions.get(0)));
				listQuestions.remove(0);
			}
		}
	}
	
	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public String getPositionInMatrix(String element) {
		int xPosition = 0;
		int yPosition = 0;
		int elementToFind = Integer.parseInt(element);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] == elementToFind) {
					xPosition = i;
					yPosition = j;
				}
			}
		}
		return xPosition+","+yPosition;
	}
	
	public String[] readCoordinates() {
		String fullText = getText(txt_coordinates);
		String[] fullTextSplited = new String[3];
		if(fullText.contains(");(")) {
			fullText = fullText.replace(");(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		if(fullText.contains("),(")){			
			fullText = fullText.replace("),(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		if(fullText.contains(")-(")){			
			fullText = fullText.replace(")-(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		if(fullText.contains(")_(")){			
			fullText = fullText.replace(")_(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		if(fullText.contains(").(")){			
			fullText = fullText.replace(").(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		if(fullText.contains(")*(")){			
			fullText = fullText.replace(")*(", " ");
			fullText = fullText.replace("(", "");
			fullText = fullText.replace(")", "");
			fullTextSplited = fullText.split(" ");
			return fullTextSplited;
		}
		else {
			return null;
		}
	}
	
	public int getValueOfCoordinateInMatrix(String finalCoordinate) {
		String[] fullTextSplited = new String[3];
		fullTextSplited = finalCoordinate.split(",");
		int btnValue = matrix[Integer.parseInt(fullTextSplited[1])][Integer.parseInt(fullTextSplited[0])];
		return btnValue;
	}
	
	public String getFinalCoordinate() {
		String[] listCoordinates = readCoordinates();
		int finalXCoordinate = 0;
		int finalYCoordiante = 0;
		String[] fullTextSplited = new String[3];
		for (int i = 0; i < listCoordinates.length; i++) {
			fullTextSplited = listCoordinates[i].split(",");
			finalXCoordinate = finalXCoordinate + Integer.parseInt(fullTextSplited[0]);
			finalYCoordiante = finalYCoordiante + Integer.parseInt(fullTextSplited[1]);
		}
		return finalXCoordinate+","+finalYCoordiante;
	}
	
	public void clickMatrixButton() throws InterruptedException {
		fillMatrix();
		printMatrix();
		int finalValue = getValueOfCoordinateInMatrix(getFinalCoordinate());
		By btn_toClick = By.xpath("//button[@value='"+finalValue+"']");
		clickByJS(btn_toClick);		
	}
	
	public void addAllFromRow() {
		String[] fullTextSplited = new String[3];
		fullTextSplited = getFinalCoordinate().split(",");
		int sumAllRow = 0;
		for (int i = 0; i < matrix.length; i++) {
			sumAllRow = sumAllRow + matrix[Integer.parseInt(fullTextSplited[1])][i];
		}
		type(sumAllRow+"", input_answer);
		clickByJS(btn_enviar);
	}
	
	public void solveQuestion() {
		String question = getText(txt_question);
		if (question.equals("Suma todos los numeros en la fila de la respuesta")) {
			addAllFromRow();
		}
	}
	
}
