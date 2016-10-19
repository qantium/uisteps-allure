package com.qantium.uisteps.allure.browser;

import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.core.browser.pages.Page;
import com.qantium.uisteps.core.browser.pages.UIElement;
import com.qantium.uisteps.core.browser.pages.elements.*;
import com.qantium.uisteps.core.browser.pages.elements.Select.Option;
import com.qantium.uisteps.core.browser.pages.elements.alert.Alert;
import com.qantium.uisteps.core.browser.pages.elements.alert.AuthenticationAlert;
import com.qantium.uisteps.core.browser.pages.elements.alert.ConfirmAlert;
import com.qantium.uisteps.core.browser.pages.elements.alert.PromtAlert;
import com.qantium.uisteps.core.screenshots.Ignored;
import com.qantium.uisteps.core.screenshots.Screenshot;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by Anton Solyankin
 */
public class Browser extends com.qantium.uisteps.core.browser.Browser {

    private Storage storage;

    public Browser() {
        setStorage(new Storage());
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Step("Open {0}")
    @Override
    public <T extends Page> T open(T page) {
        return super.open(page);
    }

    @Step
    @Override
    public void deleteAllCookies() {
        super.deleteAllCookies();
    }

    @Step
    @Override
    public void deleteCookie(String name) {
        super.deleteCookie(name);
    }

    @Step
    @Override
    public void refreshPage() {
        super.refreshPage();
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
    public void clear(TextField input) {
        super.clear(input);
    }

    @Override
    @Step("Type into \"{0}\" value \"{1}\"")
    public void typeInto(TextField input, Object text) {
        super.typeInto(input, text);
    }

    @Override
    @Step("Enter into \"{0}\" value \"{1}\"")
    public void enterInto(TextField input, Object text) {
        super.enterInto(input, text);
    }

    @Override
    @Step("Click \"{0}\" on point ({1};{2})")
    public void clickOnPoint(UIElement element, int x, int y) {
        super.clickOnPoint(element, x, y);
    }

    @Step("Click \"{0}\"")
    @Override
    public void click(UIElement element) {
        super.click(element);
    }

    //Alert
    @Step
    @Override
    public void accept(Alert alert) {
        super.accept(alert);
    }

    @Step
    @Override
    public void dismiss(ConfirmAlert confirm) {
        super.dismiss(confirm);
    }

    @Step("Enter into \"{0}\" text \"{1}\"")
    @Override
    public PromtAlert enterInto(PromtAlert promt, String text) {
        return super.enterInto(promt, text);
    }

    @Step("Authenticate in \"{0}\" using login = \"{1}\" and password = \"{2}\"")
    @Override
    public void authenticateUsing(AuthenticationAlert authenticationAlert, String login, String password) {
        super.authenticateUsing(authenticationAlert, login, password);
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
    public void contextClick(UIElement element) {
        super.contextClick(element);
    }

    @Step("Depress mouse left button")
    @Override
    public void releaseMouse() {
        super.releaseMouse();
    }

    @Step("Depress mouse left button over {0}")
    @Override
    public void releaseMouse(UIElement element) {
        super.releaseMouse(element);
    }

    @Override
    @Step("Click and hold on \"{0}\"")
    public void clickAndHold(UIElement element) {
        super.clickAndHold(element);
    }

    @Step
    @Override
    public void doubleClick() {
        super.doubleClick();
    }

    @Step("Double click on \"{0}\"")
    @Override
    public void doubleClick(UIElement element) {
        super.doubleClick(element);
    }

    @Step("Double and drop \"{0}\" to \"{1}\"")
    @Override
    public void dragAndDrop(UIElement source, UIElement target) {
        super.dragAndDrop(source, target);
    }

    @Step("Double and drop \"{0}\" with offset ({1}; {2})")
    @Override
    public void dragAndDrop(UIElement element, int xOffset, int yOffset) {
        super.dragAndDrop(element, xOffset, yOffset);
    }

    @Step("Press the key \"{0}\"")
    @Override
    public void keyDown(Keys theKey) {
        super.keyDown(theKey);
    }

    @Step("Click \"{0}\" and press the key \"{1}\"")
    @Override
    public void keyDown(UIElement element, Keys theKey) {
        super.keyDown(element, theKey);
    }

    @Step("Lift the key \"{0}\"")
    @Override
    public void keyUp(Keys theKey) {
        super.keyUp(theKey);
    }

    @Step("Click \"{0}\" and lift the key \"{1}\"")
    @Override
    public void keyUp(UIElement element, Keys theKey) {
        super.keyUp(element, theKey);
    }

    @Step("Move mouse with offset ({0}; {1})")
    @Override
    public void moveMouseByOffset(int xOffset, int yOffset) {
        super.moveMouseByOffset(xOffset, yOffset);
    }

    @Step("Move mouse to \"{0}\" with offset ({1}; {2})")
    @Override
    public void moveToElement(UIElement element, int xOffset, int yOffset) {
        super.moveToElement(element, xOffset, yOffset);
    }

    @Step("Move mouse over \"{0}\"")
    @Override
    public void moveMouseOver(UIElement element) {
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
    public void scrollWindowToTarget(UIElement element) {
        super.scrollWindowToTarget(element);
    }

    @Step("Scroll window to {0} by offset ({1}; {2})")
    @Override
    public void scrollWindowToTargetByOffset(UIElement element, int x, int y) {
        super.scrollWindowToTargetByOffset(element, x, y);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void scrollToTarget(UIElement scroll, UIElement target) {
        super.scrollToTarget(scroll, target);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void horizontalScroll(UIElement scroll, int pixels) {
        super.horizontalScroll(scroll, pixels);
    }

    @Step("Move \"{0}\" to \"{1}\"")
    @Override
    public void verticalScroll(UIElement scroll, int pixels) {
        super.verticalScroll(scroll, pixels);
    }

    @Step("Move \"{0}\" by offset ({1}; {2})")
    @Override
    public void scroll(UIElement scroll, int x, int y) {
        super.scroll(scroll, x, y);
    }

    //Select
    @Step("Select \"{0}\"")
    @Override
    public void select(Option option) {
        super.select(option);
    }

    @Step("Deselect ol values from \"{0}\"")
    @Override
    public void deselectAllValuesFrom(Select select) {
        super.deselectAllValuesFrom(select);
    }

    @Step("Deselect \"{0}\"")
    @Override
    public void deselect(Option option) {
        super.deselect(option);
    }

    //Radio button
    @Step("Select \"{0}\"")
    @Override
    public boolean select(RadioButton button) {
        return super.select(button);
    }

    //CheckBox
    @Step("Select \"{0}\"")
    @Override
    public boolean select(CheckBox checkBox) {
        return super.select(checkBox);
    }

    @Step("Deselect \"{0}\"")
    @Override
    public boolean deselect(CheckBox checkBox) {
        return super.deselect(checkBox);
    }

    //FileInput
    @Step("Upload file \"{1}\" to \"{0}\"")
    @Override
    public void setFileToUpload(FileInput fileInput, String filePath) {
        super.setFileToUpload(fileInput, filePath);
    }

    //Screenshots
    @Override
    public Screenshot takeScreenshot() {
        Screenshot screenshot = super.takeScreenshot();
        storage.attach("screenshot", screenshot.asByteArray());
        return screenshot;
    }

    @Override
    public Screenshot takeScreenshot(UIElement... elements) {
        Screenshot screenshot = super.takeScreenshot(elements);
        storage.attach("screenshot", screenshot.asByteArray());
        return screenshot;
    }

    @Override
    public Screenshot takeScreenshot(Ignored... elements) {
        Screenshot screenshot = super.takeScreenshot(elements);
        storage.attach("screenshot", screenshot.asByteArray());
        return screenshot;
    }

    @Override
    public String getPageSource() {
        String pageSource = super.getPageSource();
        storage.attach("page source", pageSource);
        return pageSource;
    }
}
