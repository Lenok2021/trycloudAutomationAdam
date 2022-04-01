package com.trycloud.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    //1. create private Constructor = we close access to the Object outside the Class.
    private Driver() {
    }

    /*2. we make Webdriver private, because we want to close access outside the class.
    we make it static because we will use it in a static method.
     */
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();


    /*
    create a reusable utility  method which will
   return same driver instance when we will call that

   the  value == null  by default
   */
    public static WebDriver getDriver() {

        if (driverPool.get() == null) {  // if driver ==  null go here and create a new WebDriver instance
                          /*
             we get browser type from configuration property file
             we can control which browser is opened from outside of code,
             from configuration properties file
             */
            String browserType = ConfigurationReader.getProperty("browser");

            // we  say program  go  to Configuration property file I indicated key  as
            //browser =>  in Configuration file we see browser = "chrome"
            //  and then in  switch statement we find the matching case
            /*
            Depending on the browserType that will be return from configuration.properties
            file switch statement  will determine the
             */

            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driverPool.set(new OperaDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    driverPool.set(new SafariDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
            }
        }
        return driverPool.get();  //  if the Driver is not null ==> return me the existing one
        // if  driver already exist return me existing driver.
    }


    /*
    if my session Id  is not null (completely terminates)
    I want to set  the value == null
    This method will make sure our Driver values always == null
    after quite method
     */
    public static void closeDriver(){
        if(driverPool.get() != null){
            driverPool.get().quit(); // this lines will terminate the exiting session.value will not even be null
            driverPool.remove();
        }


    }


}
/**
 * Adam's Driver
 */
/*
 private Driver() {
    }

   // private static WebDriver driver; //single
   private static InheritableThreadLocal<WebDriver> driver=new InheritableThreadLocal<>(); //parallel

    public static WebDriver get() {
        //if (driver == null) { //single
        if (driver.get() == null) { //parallel
        String browser=System.getProperty("browser")!=null? System.getProperty("browser"): ConfigurationReader.get("browser");

            switch (browser) {

                case "chrome":
                    WebDriverManager.chromedriver().setup();
                  //  driver = new ChromeDriver(); //single
                    driver.set(new ChromeDriver()); //parallel
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                  //  driver = new ChromeDriver(new ChromeOptions().setHeadless(true)); //single
                    driver.set(new ChromeDriver(new ChromeOptions().setHeadless(true))); //parallel
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                  //  driver = new FirefoxDriver(); //single
                    driver.set( new FirefoxDriver()); //parallel
                    break;
                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                   // driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true)); //single
                    driver.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true))); //parallel
                    break;

                case "ie":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support IE");
                    WebDriverManager.iedriver().setup();
                   // driver = new InternetExplorerDriver(); // single
                    driver.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                  //  driver = new EdgeDriver(); //single
                    driver.set(new EdgeDriver()); //parallel
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                  //  driver = new SafariDriver(); //single
                    driver.set(new SafariDriver()); //parallel
                    break;
   }

        }
      //  return driver; //single
      return   driver.get(); //parallel
    }
    public static void closeDriver(){
        if (driver!=null){
          //  driver.quit(); //single
            driver.get().quit(); //parallel
           // driver=null; //single
            driver.remove();//parallel
        }
    }
}
```
 */