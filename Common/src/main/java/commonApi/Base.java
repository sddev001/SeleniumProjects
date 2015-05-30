package commonApi;

/**
 * Created by Shibu on 5/17/2015.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.util.Date;
import atu.testrecorder.ATUTestRecorder;

import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;

import java.sql.*;


public class Base {

    public WebDriver driver = null;
    ATUTestRecorder recorder;


    @Parameters ({"url"})
    @BeforeMethod
    public void setUp(String url) throws ATUTestRecorderException {

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH-mm-ss");
        Date date = new Date();
        //Created object of ATUTestRecorder; Provide path to store videos and file name format.
        recorder = new ATUTestRecorder("C:\\Users\\Shibu\\Documents\\captured_image_video\\","TestVideo-"+dateFormat.format(date),false);
        //To start video recording.
        try {
            recorder.start();
        } catch (ATUTestRecorderException e) {
            e.printStackTrace();
        }
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.navigate().to(url);
        driver.manage().window().maximize();

    }

 /*
     @Parameters ({"useSauceLabs", "userName", "key", "os", "browserName", "browserVersion","url"})
    @BeforeMethod
    public void setUp(@Optional("false")boolean useSauceLabs,@Optional("sddev001")String userName,
                      @Optional("ssk")String key, @Optional("WIN8")String os,@Optional("firefox") String browserName,
                      @Optional("35")String browserVersion,@Optional("http://piit.us") String url)throws IOException{
                      if(useSauceLabs == true){
            setUpCloudEnvironment(userName, key, os, browserName, browserVersion, url );
        }else{
            //getLocalDriver(browserName, browserVersion, url );

            getLocalDriver(browserName, url);
        }
    }


    public void setUpCloudEnvironment(String userName, String key, String os,
                                      String browserName, String browserVersion, String url)
            throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(browserName);
        cap.setCapability("version", browserVersion);
        cap.setCapability("platform", os);
        this.driver = new RemoteWebDriver(new URL("http://"+userName + ":" +key + "@ondemand.saucelabs.com:80/wd/hub"), cap);
        driver.navigate().to(url);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public WebDriver getLocalDriver(String browserName, String url){
        if(browserName.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }else if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "../Common/seleniumdrivers/chromedriver.exe");
                                        //          ../Common/ two dot for maven,  ./common/ one dot for testNg
            driver = new ChromeDriver();
        }else if(browserName.equalsIgnoreCase("safari")){

            driver = new SafariDriver();
        }else if(browserName.equalsIgnoreCase("IE")){
            System.setProperty("webdriver.chrome.driver", "../Common/seleniumdrivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }
        return driver;
    }


 */


    @AfterMethod
    public void cleanUp() throws ATUTestRecorderException {
        driver.quit();
        //To stop video recording.
        recorder.stop();
    }

    //Utilities Methods

    public void captureElementScreenShot(String locator, String name) throws IOException {
        WebElement element = driver.findElement(By.xpath(locator));
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        Rectangle rect = new Rectangle(width, height);
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
        ImageIO.write(dest, "png", screen);
        FileUtils.copyFile(screen, new File("C:\\Users\\Shibu\\Documents\\captured_image_video\\" + name + ".png"));

    }

    public void capturePageShot(String name) throws IOException {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("C:\\Users\\Shibu\\Documents\\captured_image_video\\" + name + ".jpg"));
    }

    public void clickByCss(String locator){
        driver.findElement(By.cssSelector(locator)).click();
    }
    public void clickByCssWait(String locator){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
        driver.findElement(By.cssSelector(locator)).click();
    }
    public void clickByXpath(String locator){
        driver.findElement(By.xpath(locator)).click();
    }
    public void clickByXpathWait(String locator){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        driver.findElement(By.xpath(locator)).click();
    }
    public void clickByText(String locator){
        driver.findElement(By.linkText(locator)).click();
    }
    public void clickById(String locator){
        driver.findElement(By.id(locator)).click();
    }
    public void typeByCss(String locator, String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }
    public void typeByXpath(String locator, String value){
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }
    public void typeByXpathWait(String locator, String value){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

    public void typeAndEnterByCss(String locator, String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }
    public void typeAndEnterByXpath(String locator, String value){
        driver.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
    }
    public void typeByName(String locator1, String locator2, String studentId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:mysql:///peoplentech";
        String dbClass = "com.mysql.jdbc.Driver";
        Class.forName(dbClass).newInstance();
        Connection con = DriverManager.getConnection(url, "root", "sddev");
        ResultSet result = null;
        PreparedStatement ps = null;
        //     Statement stmt = (Statement) con.createStatement();
        //     ResultSet result = (ResultSet) stmt.executeQuery("SELECT * FROM students where id = '101'");
        String query = "select * from students where id = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, studentId);
        result = ps.executeQuery();

        while (result.next()) {

            String id = result.getString(1);
            String firstname = result.getString(2);
            String lastname = result.getString(3);
            String phone = result.getString(4);
            String email = result.getString(5);
            String username = result.getString(6);
            String password = result.getString(7);
            driver.findElement(By.name(locator1)).sendKeys(email);
            driver.findElement(By.name(locator2)).sendKeys(password);

            String expectedData = "jbrian@gmail.com";
            System.out.println("Expected data: " + expectedData + "\nActual data: " + result.getString(5));
            //    Reporter.log(("Expected data: " + expectedData + "; Actual data: " + result.getString(5)));
            Assert.assertEquals(expectedData, result.getString(5));
        }
        result.close();
    }

    public List<WebElement> getWebElements(String locator){
        List<WebElement> element = driver.findElements(By.cssSelector(locator));

        return element;
    }

    public List<String> getTextByCss(String locator){
        List<WebElement> element = driver.findElements(By.cssSelector(locator));
        List<String> text = new ArrayList<String>();

        for(WebElement st:element){
            text.add(st.getText());
        }
        return text;
    }

    public void displayText(List<String> text){
        for(String st:text){
            System.out.println(st);
        }
    }
    public void sleepFor(int sec)throws InterruptedException{
        Thread.sleep(sec * 1000);
    }
    public void mouseHover(String locator){
        //   WebElement element = driver.findElement(By.cssSelector(locator));
        WebElement element = driver.findElement(By.xpath(locator));
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();

    }

    public void navigateBack(){
        driver.navigate().back();
    }

/*    public List<String> getListOfTextByCss(String locator){
        List<WebElement> element = driver.findElements(By.cssSelector(locator));
        List<String> text = new ArrayList<String>();

        for(WebElement st:element){
            text.add(st.getText());
        }
        return text;
    }
*/


    public List<String> searchDropDownMenu(String locator){
        List<String> menuList = getTextByCss(locator);

        return menuList;

    }


    public void searchKeys(String locator1, String locator2, String locator3) throws InterruptedException {
        try {
            FileInputStream file =
                    new FileInputStream(new File("C:\\Users\\Shibu\\IdeaProjects\\ProjectAbc\\Abc\\data\\TestData.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);

            for (int i=1; i <= sheet.getLastRowNum(); i++){


                String keyword = sheet.getRow(i).getCell(0).getStringCellValue();
                driver.findElement(By.cssSelector(locator1)).click();

                Thread.sleep(4);
                driver.findElement(By.cssSelector(locator2)).sendKeys(keyword);

                Thread.sleep(4);
                driver.findElement(By.cssSelector(locator3)).click();
                Thread.sleep(4);
                capturePageShot(keyword);


            }

            System.out.println("Search test is successful.");

            workbook.close();
            file.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



}

