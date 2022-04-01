package com.trycloud.step_definitions;

import com.trycloud.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @After  //  this  method will  be executed after my scenario is done
    public void teardownScenario(Scenario scenario) {  //Scenario scenario comes from java cucumber

        // in Selenium Library there is a method take screenshot

        // scenario.isFailed  ----> this method will return TRUE boolean value
        if (scenario.isFailed()) {
            byte[] screenshort = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshort, "image/png", scenario.getName());
        }
        Driver.closeDriver();

        
    }


}
