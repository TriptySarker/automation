import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.concurrent.TimeUnit;

public class MyTest {
    WebDriver driver;
    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.gecko.driver","./src/test/resources/geckodriver.exe");
        FirefoxOptions ops=new FirefoxOptions();
        ops.addArguments("--headed");
        driver=new FirefoxDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com");
        String title=driver.getTitle();
        System.out.println(title);
        Assertions.assertTrue(title.contains("ToolsQA"));
    }
    @AfterEach
    public void finishTest(){
        driver.close();
    }
}