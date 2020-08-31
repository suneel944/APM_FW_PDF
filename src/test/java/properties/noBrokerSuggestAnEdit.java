package properties;

import org.openqa.selenium.By;

public class noBrokerSuggestAnEdit 
{
	public static By imgGreenTick = By.id("com.nobroker.app:id/img_check");
	
	public static By lstBhkType = By.id("com.nobroker.app:id/sp_bhk_type");
	
	public static By ddl(String text)
	{
		return By.xpath("//android.widget.TextView[@text='"+text+"']");
	}
	
	public static By btnSaveChanges = By.id("com.nobroker.app:id/btn_save");
	
	public static By edtAddANote = By.id("com.nobroker.app:id/edt_others");
	
	public static By txtThankYou = By.id("com.nobroker.app:id/img_close");
}