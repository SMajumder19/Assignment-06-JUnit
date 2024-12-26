import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Support {
    public static void scrollDown(WebDriver driver, int height){
        JavascriptExecutor jsExecuter = (JavascriptExecutor) driver;
        jsExecuter.executeScript("window.scrollBy(0," + height + ")");
    }
}
