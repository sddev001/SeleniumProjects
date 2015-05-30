import commonApi.Base;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Created by Shibu on 5/17/2015.
 */
public class AbcHome extends Base {

    @Test (priority = 1)
    public void show() throws InterruptedException, IOException {
        captureElementScreenShot(".//*[@id='wrapper']/nav/header/ul/li[1]/a/h1/img", "abc");
        clickByCss(".shows");
        sleepFor(2);
        capturePageShot("abcShow");
        List<String> listMenu = searchDropDownMenu(".listing li");
        displayText(listMenu);
    }

    @Test (priority = 2)
    public void search() throws InterruptedException, IOException {

        searchKeys(".search", "#searchInput", ".searchButton");
    }

    @Test (priority = 3)
    public void menu() throws InterruptedException {
        for(int i = 1; i <= 8; i++){
            clickByCssWait(".dropdownTrigger");
            sleepFor(4);
            clickByXpathWait(".//*[@id='wrapper']/nav/div/div/div[1]/ul/li[4]/div/ul/li[" + i + "]/a");
            sleepFor(4);
            navigateBack();
        }
    }

    @Test (priority = 4)
    public void schedule()throws InterruptedException {
        clickByCssWait(".dropdownTrigger");
        clickByXpathWait(".//*[@id='wrapper']/nav/div/div/div[1]/ul/li[4]/div/ul/li[2]/a");

        List<String> listMenu = searchDropDownMenu(".schedWrap li");
        displayText(listMenu);
    }

    @Test (priority = 5)
    public void databaseTest() throws Exception {
        clickByCssWait(".dropdownTrigger");
        sleepFor(2);
        clickByXpathWait(".//*[@id='wrapper']/nav/div/div/div[1]/ul/li[4]/div/ul/li[6]/a");
        sleepFor(2);

        clickByXpathWait(".//*[@id='homeResults']/ul/li[1]/a[2]");
        captureElementScreenShot(".//*[@id='mainimg']", "shoppingItem");
        sleepFor(2);

        clickByCssWait("#AddToCartButtonId");
        sleepFor(3);
        clickByCssWait("#ViewCartContentControl_btnCheckout2");
        sleepFor(3);
        clickByCssWait("#checkoutSignIn");
        sleepFor(3);
        typeByName("txtLoginEmail", "txtLoginPassword", "101");

        System.out.println("Database testing successful");

    }
}
