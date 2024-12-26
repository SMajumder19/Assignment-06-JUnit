import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JUnitTests {
    WebDriver driver;

    @BeforeAll
    public void setup(){
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headed");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterAll
    public void closeDriver() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @DisplayName("Check if table data is scrapped properly")
    @Test
    public void scrapTableData() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        FileWriter writer = new FileWriter("./src/test/resources/ScrappedData.txt", true);

        List<WebElement> tableElement = driver.findElements(By.className("table"));
        //List<WebElement> rowBodyElements = tableElement.get(1).findElements(By.tagName("tbody"));
        List<WebElement> rowElements = tableElement.get(1).findElements(By.tagName("tr"));
        //System.out.println(rowElements.size());

        for(WebElement row : rowElements){
            List<WebElement> cellElements = row.findElements(By.tagName("td"));
            for(WebElement cell : cellElements){
                System.out.print(cell.getText() + "  ");
                writer.write(cell.getText() + "  ");
            }
            System.out.println();
            writer.write("\n");
        }

        writer.close();
    }

    @DisplayName("Check if registration form is submitted properly")
    @Test
    public void submitRegistrationForm() throws InterruptedException {
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        List<WebElement> typeElements = driver.findElements(By.cssSelector("[type=text]"));
        List<WebElement> classElements = driver.findElements(By.className("input-text"));
        List<WebElement> radioElements = driver.findElements(By.cssSelector("[type=radio]"));
        Select options = new Select(driver.findElement(By.id("country_1665629257")));


        Random random = new Random();
        //Text Boxes
        typeElements.get(0).sendKeys("Test FirstName " + random.nextInt(10501, 99999)); //First Name
        Thread.sleep(1000);
        typeElements.get(1).sendKeys("Test LastName" + random.nextInt(10501, 99999)); //Last Name
        Thread.sleep(1000);
        classElements.get(1).sendKeys("user" + random.nextInt(10500, 99999) + "@me.com"); //Email
        Thread.sleep(1000);
        classElements.get(2).sendKeys("@1234ABcd!"); //Password
        typeElements.get(6).sendKeys("Bangladeshi"); //Nationality
        Thread.sleep(1000);
        typeElements.get(4).sendKeys("0171575757"); //Phone
        Thread.sleep(1000);
        //typeElements.get(5).sendKeys("0191575757"); //Emergency Contact
        //classElements.get(7).sendKeys("3"); //Intended Length of Stay
        //classElements.get(8).sendKeys("Room: 1, Bed: 1"); //Room and Bed Number
        //classElements.get(9).sendKeys("Occupation: Mentor, Office: ABC Company"); //Occupation & Place of Employment

        //Radio Buttons
        radioElements.get(0).click(); //Gender
        Thread.sleep(1000);
        //radioElements.get(3).click();; //Parking
        //radioElements.get(5).click(); //Room
        //radioElements.get(13).click(); //Dietary

        //Dropdown
        options.selectByValue("BD"); //Country
        Thread.sleep(1000);

        //Dates
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('readonly','readonly')", typeElements.get(2));
        typeElements.get(2).sendKeys("1998-12-01"); //Date of Birth
        Thread.sleep(1000);
        //jsExecutor.executeScript("arguments[0].removeAttribute('readonly','readonly')", typeElements.get(7));
        //typeElements.get(7).sendKeys("2024-12-26"); //Date of Arrival

        //Checkbox
        WebElement checkBoxElement = driver.findElement(By.cssSelector("[type=checkbox]"));
        checkBoxElement.click();

        //Submit
        WebElement submitElement = driver.findElement(By.className("ur-submit-button"));
        submitElement.click();
        Thread.sleep(5000);

        //Registration Successful
        WebElement submitMessageElement = driver.findElement(By.className("user-registration-message"));
        String actualMessage = submitMessageElement.getText();
        //System.out.println(actualMessage);
        String expectedMessage = "User successfully registered";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Check if webform is submitted properly")
    @Test
    public void submitWebform() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        List<WebElement> formElements = driver.findElements(By.className("form-control"));
        Random random = new Random();

        //Cancel Cookies
        WebElement cookiesElement = driver.findElement(By.className("banner-close-button"));
        cookiesElement.click();
        Thread.sleep(1000);

        //Filling up form
        //WebElement nameElement = driver.findElement(By.id("edit-name"));
        //nameElement.sendKeys("Test User Name");
        formElements.get(0).sendKeys("Test User " + random.nextInt(10501, 99999));
        Thread.sleep(1000);

        //WebElement phoneElement = driver.findElement(By.id("edit-number"));
        //phoneElement.sendKeys("01715757575");
        formElements.get(1).sendKeys("01715757575");
        Thread.sleep(1000);

        formElements.get(3).sendKeys("testuser" + random.nextInt(10501, 99999) + "@test.com");
        Thread.sleep(1000);
        formElements.get(4).sendKeys("Hello! I am a test user. I am being used to test this webform via automation. Thanks!");
        Thread.sleep(1000);

        //formElements.get(2).sendKeys("26-Dec-2024");
        //JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        //jsExecutor.executeScript("document.getElementById('edit-date').setAttribute('type', 'text')", formElements.get(2));
        //formElements.get(2).sendKeys("26-Dec-2024"); //Date of Birth

        Actions actions = new Actions(driver);
        formElements.get(2).click();
        for(int i = 1; i < 7; i++){
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(500);
        }
        actions.sendKeys(Keys.TAB).perform();
        Thread.sleep(500);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(500);
        actions.sendKeys(Keys.TAB).perform();
        Thread.sleep(500);
        actions.sendKeys(Keys.ARROW_DOWN).perform();

        Support.scrollDown(driver, 500);
        Thread.sleep(1000);

        WebElement uploadElement = driver.findElement(By.id("edit-uploadocument-upload"));
        uploadElement.sendKeys(System.getProperty("user.dir") + "/src/test/resources/upload_01.png");
        Thread.sleep(1000);

        WebElement checkboxElement = driver.findElement(By.id("edit-age"));
        checkboxElement.click();
        Thread.sleep(5000);

        //submit form
        WebElement submitElement = driver.findElement(By.id("edit-submit"));
        submitElement.click();
        Thread.sleep(500);

        //Submit successful
        WebElement messageElement = driver.findElement(By.id("block-pagetitle-2"));
        String actualMessage = messageElement.getText();
        //System.out.println(actualMessage);
        String expectedMessage = "Thank you for your submission";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
