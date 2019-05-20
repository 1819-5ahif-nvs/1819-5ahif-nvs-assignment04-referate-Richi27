import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class CookieClicker {

    public static void main(String[]args){

        System.setProperty("webdriver.chrome.driver", " /home/bizi/Documents/Richi/chromedriver");
        WebDriver browser = new ChromeDriver();
        browser.navigate().to("http://orteil.dashnet.org/cookieclicker/");
        //Maximize the browser
        //browser.manage().window().maximize();
        // Bei langsamen Internet
        // browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement cookie = browser.findElement(By.id("bigCookie"));
        for(int i = 0; i<10000; i++)
            new Actions(browser).doubleClick(cookie).build().perform();
        cookie.click();



    }


}
