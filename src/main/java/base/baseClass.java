package base;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class baseClass 
{
	public static AppiumDriver<MobileElement> driver;
	public static AppiumDriverLocalService server;
	public static WebDriverWait wait;
}