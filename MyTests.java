import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyTests {
    static WebDriver browser;

    static String newIssueSummary = "New Auto Test " + new Date();
    static String newIssueURL;
    static String testDataPath = "C:\\";
    static String testFileName = "test.pdf";


    @BeforeTest
    public static void openBrowser() throws InterruptedException {
        // Setting path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "driver/webdriver/chromedriver.exe");

        // Open browser
        browser = new ChromeDriver();
        // Maximize the browser window
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        Helpers.setBrowser(browser);
        browser.get("https://testqasmal.atlassian.net/secure/Dashboard.jspa");


    }

    @AfterTest
    public static void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        browser.close();
    }

    @Test()
    public static void jiraLogin() throws Exception {
        Helpers.findElement("span.sc-dxgOiQ.jMfAZr").click();
        Thread.sleep(4000);
        Helpers.clearAndEnter("input#username", "smalyurii@gmail.com \n");
        Thread.sleep(2000);
        Helpers.clearAndEnter("input#password", "12345678");
        Helpers.findElement("button#login-submit").click();
        Thread.sleep(6000);

         Assert.assertTrue(Helpers.elementExists("#add-gadget"), "Login test failed");
    }

    @Test(dependsOnMethods = {"jiraLogin"})
    public static void createIssue() throws InterruptedException {
        for(int a = 1; a<2; a++) {
            Helpers.findElement("#navigation-app > div > div > div:nth-child(1) > div.sc-cTjmhe.dxxXmZ > div > div.sc-cmjSyW.iUAVtP > div > div.sc-kcDeIU.kZOcST > div > div.sc-cBdUnI.cJXchM > div > div:nth-child(3) > div > button > span > span > svg").click();
            Thread.sleep(5000);
            Helpers.clearAndEnter("input#project-field", "AutoTests \n");
            Thread.sleep(5000);
            Helpers.clearAndEnter("input#issuetype-field", "Problem \n");
            Thread.sleep(5000);
            Helpers.clearAndEnter("input#summary", newIssueSummary);
            Thread.sleep(4000);
            Helpers.findElement("input#create-issue-submit").click();
            Thread.sleep(10000);

            Assert.assertTrue(Helpers.elementExists("a.issue-created-key"));
            newIssueURL = Helpers.findElement("a.issue-created-key").getAttribute("href");
        }
    }

    @Test(dependsOnMethods = "createIssue")
    public static void viewIssue() throws InterruptedException {
        browser.get(newIssueURL);
        Thread.sleep(4000);
        Assert.assertEquals(newIssueSummary, Helpers.findElement("h1#summary-val").getText());
    }


    @Test(dependsOnMethods = "viewIssue")
    public static void uploadFile() throws InterruptedException {
        Thread.sleep(2000);
        Helpers.findElement("#attachmentmodule > div.mod-content.issue-drop-zone > div > span > input").sendKeys(testDataPath + testFileName);
        Thread.sleep(5000);
        Helpers.findElement("#button-private").click();
        Thread.sleep(5000);

    }

    @Test(dependsOnMethods = "uploadFile")
    public static void downloadFile() throws IOException {
        Helpers.findElement("#attachment_thumbnails > li > dl > dt > a").click();
        Helpers.findElement("#cp-control-panel-download").click();

    }

    @Test(dependsOnMethods = "downloadFile")

    public static void deleteIssue () throws InterruptedException {
        Helpers.deleteIssue();

    }
}











