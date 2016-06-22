import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class hoodPopperTest {

	static WebDriver driver = new FirefoxDriver();
	
	// Start at the home page for hoodpopper for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}
	
	
	/*
	User Test 1:
  	As a user
	I want to check the operations shown during compilation
	So that I can make sure my operators have the correct operation associated with them
    */
	
	/*
	Scenario 1
	Given that the program contains a multiplication using the “*” operator
	When I check the compile output
	Then the “opt_mlt” operation should be called
	*/
	@Test
	public void testHasMultOperator(){
		driver.findElement(By.id("code_code")).sendKeys("a=5*4");
		WebElement compileDiv = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		compileDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("opt_mult"));
		
	}
	/*
	Scenario 2:
	Given that the program contains an addition and a subtraction using the “+” and “-“ operators respectively
	When I check the compile output
	Then the “opt_plus” and “opt_minus” operations should be called
	 */
	@Test
	public void testHasAddAndSubOperator(){
		driver.findElement(By.id("code_code")).sendKeys("a=5+4-3");
		WebElement compileDiv = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		compileDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("opt_plus")&&bodyText.contains("opt_minus"));
	}
	
	/*
	Scenario 3:
	Given that the program contains no operators
	When I check the compile output
	Then none of the operator operations should be called
	 */
	@Test
	public void testHasNoOperator(){
		WebElement compileDiv = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		compileDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertFalse(bodyText.contains("opt_mult")||bodyText.contains("opt_minus")||bodyText.contains("opt_plus")||bodyText.contains("opt_div"));
	}
	
	
	/*
	 Scenario 4:
	Given that the program contains a variable “a” that is initialized to 5
	When I check the compile output
	Then the “putobject” operation should be called on 5
	 */
	@Test
	public void testHasObject(){
		driver.findElement(By.id("code_code")).sendKeys("a=5");
		WebElement compileDiv = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
		compileDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("putobject"));
	}
	
	
	/*
	User Story 2:
	As a user
	I want to check the identifiers shown during tokenization
	So that I can make sure that the functions used in the program are correctly identified
	 */
	
	/*
	Scenario 1:
	Given that the programs contains the “puts” function
	When I check the tokenize output
	Then the “puts” function should be identified with the “:on_ident” identifier
	 */
	@Test
	public void testHasPuts(){
		driver.findElement(By.id("code_code")).sendKeys("puts \" hownowbrowncow \" ");
		WebElement tokenDiv = driver.findElement(By.name("commit"));
		tokenDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("on_ident"));
	}
	
	
	/*
	Scenario 2:
	Given that the program contains two variables “a” and “b”
	When I check the tokenize output
	Then the variables “a” and “b” should each be identified with the “:on_ident” identifier
	 */
	@Test
	public void testHasIdentifier(){
		driver.findElement(By.id("code_code")).sendKeys("a=5 \n b=3 ");
		WebElement tokenDiv = driver.findElement(By.name("commit"));
		tokenDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("on_ident, \"b\"")&&bodyText.contains("on_ident, \"a\""));
	}
	/*
	Scenario 3:
	Given that the program contains no functions or variables
	When I check the tokenize output
	Then there should be no “:on_ident” identifiers 

	 */
	@Test
	public void testHasNoIdentifier(){
		WebElement tokenDiv = driver.findElement(By.name("commit"));
		tokenDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertFalse(bodyText.contains("on_ident"));
	}
	/*
	Scenario 4:
	Given that the program contains a new line
	When I check the tokenize output
	Then there should be a “:on_nl” identifier
	 */
	@Test
	public void testHasNewLine(){
		driver.findElement(By.id("code_code")).sendKeys("a=5\nb=4");
		WebElement tokenDiv = driver.findElement(By.name("commit"));
		tokenDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("on_nl"));
	}
	
	/*
	User Story 3:
	As a user
	I want to check the tokens that are in the AST during parsing
	So that I can make sure that the appropriate tokens are being parsed
	 */
	
	/*
	Scenario 1:
	Given that the program contains a “puts” function
	When I check the parse output 
	Then the “puts” function should appear in the AST
	 */
	@Test
	public void testPutsInAST(){
		driver.findElement(By.id("code_code")).sendKeys("puts \" hownowbrowncow\"");
		WebElement tokenDiv = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
		tokenDiv.click();
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue(bodyText.contains("puts"));
	}
}//end of class
