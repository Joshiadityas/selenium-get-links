import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrokenLinks {
    static {
        WebDriverManager.chromedriver().setup();
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //options.setLogLevel(ChromeDriverLogLevel.ALL);
        options.setHeadless(true);
        return options;
    }

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver(BrokenLinks.getChromeOptions());

        driver.get("https://bbc.com");
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        List<WebElement> allURLs = driver.findElements(By.tagName("a"));
        System.out.println("Total Links on the Web Page: " + allURLs.size());

        List<String> brokenLinks = new ArrayList<>();

        allURLs.forEach(webElement -> {
            String linkText = webElement.getAttribute("href");
            System.out.println("Link has been taken for processing: " + linkText);
            if (!BrokenLinks.verifyLinks(linkText)) {
                brokenLinks.add(linkText);
            }
        });
        System.out.println("Broken links: " + brokenLinks);
        driver.close();
    }

    public static boolean verifyLinks(String linkUrl) {
        Boolean flag = false;
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            int statusCode = httpURLConnect.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + "is not a broken link");
                flag = true;
            } else {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            }
        } catch (Exception exception) {
            System.out.println("Exception has been occurred: " + exception.getMessage());
        }
        return flag;
    }
}
