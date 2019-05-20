import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;


public class WhatsappWebTest {

    private static WebDriver browser;
    private String searchInputXpath = "//input[@class='jN-F5 copyable-text selectable-text']";
    private String chatElementXpath = "//div[@class='_2wP_Y']";
    private String groupChatInputXpath = "//div[@class='_2S1VP copyable-text selectable-text']";
    private String sendButtonXpath = "//span[@data-icon='send']";
    private static String chromeDriverPath = "/home/bizi/Documents/Richi/chromedriver";

    @BeforeClass
    public static void setup() {
        //absoluter pfad von driver
        // System.setProperty() speichert einen Key Value Pair und setzt “global” properties, auf die wir später zugreifen können.
        // Hier sagen wir dem Driver wo sich unser Executeable befindet
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        browser = new ChromeDriver(); //<- hier wird die property verwendet

        // Launch Website get() auch möglich aber navigate to ist lesbarer
        browser.navigate().to("https://web.whatsapp.com");
        //Maximize the browser
        browser.manage().window().maximize();
        // time to scan qr code.
        browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }
    @Test
    public void testGroupChat() {
        WebElement searchInput =  browser.findElement(By.xpath(searchInputXpath));
        assertTrue(searchInput.isDisplayed());
        //gruppenname besteht nur aus emojis (facepalm) 🖐🏻🅰♓💻 <- momentan kein support für chrome driver
        searchInput.click();
        searchInput.sendKeys("♓");
        // Thread.sleep(1000);
        // wait to load contacts/groups
        WebDriverWait wait=new WebDriverWait(browser, 20);
        // 2 weil das chat element ebenfalls diese klasse benützt
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(chatElementXpath),2));
        // bekommen hier das erste element, funktioniert hier weil in der dom dieses element vor dem chat element kommt
        WebElement groupchat =  browser.findElement(By.xpath(chatElementXpath));
        groupchat.click();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(groupChatInputXpath),1));
        WebElement groupchatinput =  browser.findElement(By.xpath(groupChatInputXpath));
        groupchatinput.sendKeys("Hallo i bims ein Bot :)");

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(sendButtonXpath),1));
        WebElement sendicon = browser.findElement(By.xpath(sendButtonXpath));
        sendicon.click();


    }

    @AfterClass
    public static void tearDown(){
        browser.close();
    }
}
