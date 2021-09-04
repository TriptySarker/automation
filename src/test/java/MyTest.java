import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyTest {
    WebDriver driver;
    WebDriverWait wait;
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
        ChromeOptions ops=new ChromeOptions ();
        ops.addArguments("--headed");
        driver= new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com");
        String title=driver.getTitle();
        System.out.println(title);
        Assert.assertTrue(title.contains("ToolsQA"));
    }
    @Test
    public void checkifElementExists(){
        driver.get("https://demoqa.com");
        wait=new WebDriverWait(driver,20);
        Boolean status=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//header/a[1]/img[1]"))).isDisplayed();
        Assert.assertEquals(true,status);
    }
   //Click on button_from multiple elements
    @Test
    public void clickOnMultipleButton(){
        driver.get("https://demoqa.com/buttons");
        Actions action = new Actions(driver);
        List<WebElement> list= driver.findElements(By.cssSelector("button"));
        action.doubleClick(list.get(1)).perform();
        String text= driver.findElement(By.id("doubleClickMessage")).getText();
        Assert.assertTrue(text.contains("You have done a double click"));

        action.contextClick(list.get(2)).perform();
        String text2= driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(text2.contains("You have done a right click"));

        list.get(3).click();
        String text3= driver.findElement(By.id("dynamicClickMessage")).getText();
        Assert.assertTrue(text3.contains("You have done a dynamic click"));
    }
    //Write_on_textbox
    @Test
    public void writeOnTextBox(){
        driver.get("https://demoqa.com/elements");
        driver.findElement(By.xpath("//span[contains(text(),'Text Box')]")).click();
        wait= new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=userName]"))).sendKeys("Tripty");
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submit']"))).click();
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        String text= driver.findElement(By.xpath("//p[@id='name']")).getText();
        Assert.assertTrue(text.contains("Tripty"));
    }
    //Select_Dropdown
    @Test
    public void selectDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select color=new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("1");
        Select cars=new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo"); cars.selectByValue("audi");
        }
    }
    //Select_Date
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).clear();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("23/09/2093");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }
    //Handle_Alerts
   @Test
    public void handleAlerts() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id(("alertButton"))).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.id(("promtButton"))).click();
        driver.switchTo().alert().sendKeys("Tripty");
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        String text= driver.findElement(By.id("promptResult")).getText();
        Assert.assertTrue(text.contains("Tripty"));
    }

    //Handle multiple tabs
   @Test
    public void handleTabs() throws InterruptedException {
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        Thread.sleep(5000);
        ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(w.get(1));
        Boolean status = driver.findElement(By.xpath("//header/a[1]/img[1]")).isDisplayed();
        Assert.assertEquals(true,status);
        driver.close();
        driver.switchTo().window(w.get(0));
    }
    @After
    public void finishTest(){
        //driver.close();
    }
}