import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
    static {
        WebDriverManager.chromedriver().setup();
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        //options.setLogLevel(ChromeDriverLogLevel.ALL);
        options.setHeadless(false);

        return options;
    }

    public static void main(String[] args) {


        ChromeDriver driver = new ChromeDriver(Main.getChromeOptions());

        driver.get("https://amazon.com");
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        String url = "";
        List<WebElement> allURLs = driver.findElements(By.tagName("a"));
        System.out.println("Total Links on the Web Page: " + allURLs.size());
//System.out.println(driver.findElement(By.xpath("//*[@id=\"navbarExample\"]/ul/li[3]/a")).getCssValue("font-size"));
            driver.close();
        }
    }

