package basics;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AlertsTest {
    private WebDriver d;
    private String url = "https://demoqa.com/alerts";

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeTest
    public void setupTest() {
        d = new ChromeDriver();
        d.get(url);
        d.manage().window().maximize();
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass
    public void tearDown() {
        if (d != null) {
            d.quit();
        }
    }

    @Test
    public void TC01_Regular_alert() {
        d.findElement(By.id("alertButton")).click();
        String alertMsg = d.switchTo().alert().getText();
        d.switchTo().alert().accept();
        Assert.assertEquals(alertMsg, "You clicked a button");
    }

    @Test
    public void TC02_Timer_alert() throws Exception {
        d.findElement(By.id("timerAlertButton")).click();

        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.alertIsPresent());

        String alertMsg = d.switchTo().alert().getText();
        d.switchTo().alert().accept();
        Assert.assertEquals(alertMsg, "This alert appeared after 5 seconds");
    }

    @Test
    public void TC03_Accept_alert_message() {
        d.findElement(By.id("confirmButton")).click();
        d.switchTo().alert().accept();
        String confirmMsg = d.findElement(By.id("confirmResult")).getText();
        Assert.assertEquals(confirmMsg, "You selected Ok");
    }

    @Test
    public void TC04_Dismiss_alert_message() {
        d.findElement(By.id("confirmButton")).click();
        d.switchTo().alert().dismiss();
        String confirmMsg = d.findElement(By.id("confirmResult")).getText();
        Assert.assertEquals(confirmMsg, "You selected Cancel");
    }

    @Test
    public void TC05_Prompt_alert() {
        d.findElement(By.id("promtButton")).click();
        String sendMsg = "Spencer";
        d.switchTo().alert().sendKeys(sendMsg);
        d.switchTo().alert().accept();
        String confirmMsg = d.findElement(By.id("promptResult")).getText();
        Assert.assertEquals(confirmMsg, "You entered " + sendMsg);
    }

}
