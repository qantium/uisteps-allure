package com.qantium.uisteps.allure.browser;

import com.qantium.uisteps.core.browser.pages.Page;
import com.qantium.uisteps.core.browser.pages.UIElement;
import com.qantium.uisteps.core.browser.pages.UIObject;
import com.qantium.uisteps.core.browser.pages.elements.CheckBox;
import com.qantium.uisteps.core.browser.pages.elements.FileInput;
import com.qantium.uisteps.core.browser.pages.elements.RadioButtonGroup;
import com.qantium.uisteps.core.browser.pages.elements.Select;
import com.qantium.uisteps.core.screenshots.Ignored;
import com.qantium.uisteps.core.screenshots.Photographer;
import com.qantium.uisteps.core.screenshots.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsElement;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.coordinates.Coords;

/**
 * Created by Anton Solyankin
 */
public class Browser extends com.qantium.uisteps.core.browser.Browser {

    @Step("Open {0}")
    @Override
    public <T extends Page> T openPage(T page) {
        return super.openPage(page);
    }

    @Step
    @Override
    public void deleteAllCookies() {
        super.deleteAllCookies();
    }

    @Step
    @Override
    public void deleteCookieNamed(String name) {
        super.deleteCookieNamed(name);
    }

    @Step
    @Override
    public void refreshPage() {
        super.refreshPage();
    }

    @Step("On displayed \"{0}\"")
    @Override
    public <T extends UIObject> T onDisplayed(T uiObject) {
        return super.onDisplayed(uiObject);
    }

    //Navigation
    @Step
    @Override
    public void goBack() {
        super.goBack();
    }

    @Step
    @Override
    public void goForward() {
        super.goForward();
    }

    //Window
    @Step
    @Override
    public void openNewWindow() {
        super.openNewWindow();
    }

    @Step
    @Override
    public void switchToWindowByIndex(int index) {
        super.switchToWindowByIndex(index);
    }

    @Step
    @Override
    public void switchToDefaultWindow() {
        super.switchToDefaultWindow();
    }

    @Step
    @Override
    public void switchToPreviousWindow() {
        super.switchToPreviousWindow();
    }

    @Step
    @Override
    public void switchToNextWindow() {
        super.switchToNextWindow();
    }

    //Window position
    @Step("Set window position to point ({0}; {1})")
    @Override
    public void setWindowPosition(int newX, int newY) {
        super.setWindowPosition(newX, newY);
    }

    @Step("Move window with offset ({0}; {1})")
    @Override
    public void moveWindowBy(int xOffset, int yOffset) {
        super.moveWindowBy(xOffset, yOffset);
    }

    @Step("Move window to point ({0}; {1})")
    @Override
    public void moveWindowTo(int newX, int newY) {
        super.moveWindowTo(newX, newY);
    }

    //Window size
    @Step("Set window size to {0} x {1}")
    @Override
    public void setWindowSize(int width, int height) {
        super.setWindowSize(width, height);
    }

    @Step("Set window width to {0}")
    @Override
    public void setWindowWidth(int width) {
        super.setWindowWidth(width);
    }

    @Step("Set window height to {0}")
    @Override
    public void setWindowHeight(int height) {
        super.setWindowHeight(height);
    }

    @Step
    @Override
    public void maximizeWindow() {
        super.maximizeWindow();
    }

    //Elements
    @Step
    @Override
    public void clear(WrapsElement input) {
        super.clear(input);
    }

    @Override
    @Step("Type into \"{0}\" value \"{1}\" META[type=action]")
    public void typeInto(WrapsElement input, String text) {
        super.typeInto(input, text);
    }

    @Override
    @Step("Enter into \"{0}\" value \"{1}\" META[type=action]")
    public void enterInto(WrapsElement input, String text) {
        super.enterInto(input, text);
    }

    @Override
    @Step("Click \"{0}\" on point ({1};{2})")
    public void clickOnPoint(WrapsElement element, int x, int y) {
        super.clickOnPoint(element, x, y);
    }

    @Step("Click \"{0}\"")
    @Override
    public void click(WrapsElement element) {
        super.click(element);
    }

    @Step
    @Override
    public void click() {
        super.click();
    }

    @Step
    @Override
    public void clickAndHold() {
        super.clickAndHold();
    }

    @Step("Click by right button")
    @Override
    public void contextClick() {
        super.contextClick();
    }

    @Step("Click by right button over {0}")
    @Override
    public void contextClick(WrapsElement element) {
        super.contextClick(element);
    }

    @Step("Depress mouse left button")
    @Override
    public void releaseMouse() {
        super.releaseMouse();
    }

    @Step("Depress mouse left button over {0}")
    @Override
    public void releaseMouse(WrapsElement element) {
        super.releaseMouse(element);
    }

    @Override
    @Step("Click and hold on \"{0}\"")
    public void clickAndHold(WrapsElement element) {
        super.clickAndHold(element);
    }

    @Step
    @Override
    public void doubleClick() {
        super.doubleClick();
    }

    @Step("Double click on \"{0}\"")
    @Override
    public void doubleClick(WrapsElement element) {
        super.doubleClick(element);
    }

    @Step("Double and drop \"{0}\" to \"{1}\"")
    @Override
    public void dragAndDrop(WrapsElement source, WrapsElement target) {
        super.dragAndDrop(source, target);
    }

    @Step("Double and drop \"{0}\" with offset ({1}; {2})")
    @Override
    public void dragAndDrop(WrapsElement element, int xOffset, int yOffset) {
        super.dragAndDrop(element, xOffset, yOffset);
    }

    @Step("Press the key \"{0}\"")
    @Override
    public void keyDown(Keys theKey) {
        super.keyDown(theKey);
    }

    @Step("Click \"{0}\" and press the key \"{1}\"")
    @Override
    public void keyDown(WrapsElement element, Keys theKey) {
        super.keyDown(element, theKey);
    }

    @Step("Lift the key \"{0}\"")
    @Override
    public void keyUp(Keys theKey) {
        super.keyUp(theKey);
    }

    @Step("Click \"{0}\" and lift the key \"{1}\"")
    @Override
    public void keyUp(WrapsElement element, Keys theKey) {
        super.keyUp(element, theKey);
    }

    @Step("Move mouse with offset ({0}; {1})")
    @Override
    public void moveMouseByOffset(int xOffset, int yOffset) {
        super.moveMouseByOffset(xOffset, yOffset);
    }

    @Step("Move mouse to \"{0}\" with offset ({1}; {2})")
    @Override
    public void moveToElement(WrapsElement element, int xOffset, int yOffset) {
        super.moveToElement(element, xOffset, yOffset);
    }

    @Step("Move mouse over \"{0}\"")
    @Override
    public void moveMouseOver(WrapsElement element) {
        super.moveMouseOver(element);
    }

    //Scroll
    @Step("Scroll window by offset ({0}; {1})")
    @Override
    public void scrollWindowByOffset(int x, int y) {
        super.scrollWindowByOffset(x, y);
    }

    @Step("Scroll window to {0}")
    @Override
    public void scrollWindowToTarget(WrapsElement element) {
        super.scrollWindowToTarget(element);
    }

    @Step("Scroll window to {0} by offset ({1}; {2})")
    @Override
    public void scrollWindowToTargetByOffset(WrapsElement element, int x, int y) {
        super.scrollWindowToTargetByOffset(element, x, y);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void scrollToTarget(WrapsElement scroll, WrapsElement target) {
        super.scrollToTarget(scroll, target);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void horizontalScroll(WrapsElement scroll, int pixels) {
        super.horizontalScroll(scroll, pixels);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void verticalScroll(WrapsElement scroll, int pixels) {
        super.verticalScroll(scroll, pixels);
    }

    @Step("Move \"{0}\" by offset ({1}; {2})")
    @Override
    public void scroll(WrapsElement scroll, int x, int y) {
        super.scroll(scroll, x, y);
    }

    //Select
    @Step("Select \"{0}\"")
    @Override
    public void select(Select.Option option) {
        super.select(option);
    }

    @Step("Deselect ol values from \"{0}\"")
    @Override
    public void deselectAllValuesFrom(Select select) {
        super.deselectAllValuesFrom(select);
    }

    @Step("Deselect \"{0}\"")
    @Override
    public void deselect(Select.Option option) {
        super.deselect(option);
    }

    //Radio button
    @Step("Select \"{0}\"")
    @Override
    public void select(RadioButtonGroup.RadioButton button) {
        super.select(button);
    }

    //CheckBox
    @Step("Select \"{0}\"")
    @Override
    public void select(CheckBox checkBox) {
        super.select(checkBox);
    }

    @Step("Deselect \"{0}\"")
    @Override
    public void deselect(CheckBox checkBox) {
        super.deselect(checkBox);
    }

    //FileInput
    @Step("Upload file \"{1}\" to \"{0}\"")
    @Override
    public void setFileToUpload(FileInput fileInput, String filePath) {
        super.setFileToUpload(fileInput, filePath);
    }

    //Screenshots
    @Step
    @Override
    public Photographer inScreenshotIgnoring(By... locators) {
        return super.inScreenshotIgnoring(locators);
    }

    @Step
    @Override
    public Photographer inScreenshotIgnoring(UIElement... elements) {
        return super.inScreenshotIgnoring(elements);
    }

    @Step
    @Override
    public Photographer inScreenshotIgnoring(Coords... areas) {
        return super.inScreenshotIgnoring(areas);
    }

    @Step
    @Override
    public Screenshot takeScreenshot() {
        return super.takeScreenshot();
    }

    @Step
    @Override
    public Screenshot takeScreenshot(UIElement... elements) {
        return super.takeScreenshot(elements);
    }

    @Step
    @Override
    public Screenshot takeScreenshot(Ignored... elements) {
        return super.takeScreenshot(elements);
    }

    @Override
    protected void setDriver(WebDriver driver) {
        super.setDriver(driver);
    }
}
