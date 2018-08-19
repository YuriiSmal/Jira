import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helpers {
    static WebDriver browser;

    public static void setBrowser(WebDriver _browser) {
        browser = _browser;
    }


    public static WebElement findElement(String cssSelector) {
        return browser.findElement(By.cssSelector(cssSelector));
    }

    public static void clearAndEnter(String cssSelector, String text) {
        // Find input field
        WebElement element = findElement(cssSelector);
        // Clear and enter text into the input field
        element.clear();
        element.sendKeys(text);
    }

    public static boolean elementExists(String cssSelector) {
        return browser.findElements(By.cssSelector(cssSelector)).size() != 0;
    }


    public static void deleteOpenIssue() throws InterruptedException {
        Helpers.findElement("#opsbar-operations_more > span.icon.aui-icon.aui-icon-small.aui-iconfont-more").click();
        Helpers.findElement("#delete-issue > span").click();
        Thread.sleep(1500);
        Helpers.findElement("#delete-issue-submit").click();
    }


    public static boolean shouldDeleteOpenIssue() throws NoSuchElementException {
        return issueByAuto()
                || summaryHasAutotest();

    }

    public static boolean issueByAuto() {
        if (Helpers.elementExists("#issue_summary_reporter_admin")) {
            System.out.println("Issue created by auto.");
            return true;
        }
        return false;
    }


    public static boolean summaryHasAutotest() {
        if (Helpers.findElement("#summary").getText().toLowerCase().contains("New Auto Test")) {
            System.out.println("Issue summary contains 'autotest'.");
            return true;
        }
        return false;
    }

    public static void deleteIssue() throws InterruptedException {

        for (int i = 19; i < 20; i++) {
            String issueID = "AUT-" + i;

            browser.get("https://testqasmal.atlassian.net/browse/" + issueID);
            System.out.println("Checking issue " + issueID);

            try {
                if (shouldDeleteOpenIssue()) {
                    deleteOpenIssue();
                    System.out.println("Deleted " + issueID);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        Thread.sleep(4000);
    }

}










