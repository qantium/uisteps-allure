package com.qantium.uisteps.allure.user;

import com.qantium.uisteps.allure.browser.BrowserManager;
import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.core.browser.pages.UIElement;
import com.qantium.uisteps.core.browser.pages.elements.Link;
import com.qantium.uisteps.core.browser.pages.elements.TextField;
import com.qantium.uisteps.core.browser.pages.elements.lists.Links;
import com.qantium.uisteps.core.browser.pages.elements.lists.TextFields;
import com.qantium.uisteps.core.name.Name;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Anton Solyankin
 */
public class User extends com.qantium.uisteps.core.user.User {

    public User() {
        super(new BrowserManager(), new Storage());
    }

    @Override
    public Storage getStorage() {
        return (Storage) super.getStorage();
    }

    public static void main(String[] args) {

        UIStepsProperty.WEBDRIVER_DRIVER.setValue("chrome");
        UIStepsProperty.ELEMENT_DECORATOR_USE.setValue("true");

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("Второй", "Антон");
        map.put("Первый", "Солянки");
        map.put("Элем", Arrays.asList("Газмеева", "Светлана", null,"Псевдоним", "Г"));

        User user = new User();
        user.openUrl("file:///C:/Users/SolAN/Desktop/page.html");
//        user.get(Link.class, By.tagName("a")).click();
//
//        user.closeWindow();
//
        AllForm form = user.onDisplayed(AllForm.class);

//        form.fill("Первый", "Саша");
//        form.fill(map);

        sleep(2000);


//        user.closeWindow();

        ((List<Object>) form.getValue("form")).stream().forEach((value) -> System.out.println("=====: " + ((List) value).get(0).getClass()));
        ((List<Object>) form.getValue("form")).stream().forEach((value) -> System.out.println("=====: " + ((List) value).get(0)));
        System.out.println("=====: " + form.getValue("form"));

    }

    private static void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {


        }
    }

    @FindBy(id = "all")
    public static class AllForm extends UIElement {
        @Name("form")
        Form form;


    }

    @FindBy(id = "my_id")
    public static class Form extends UIElement {

        @Name("Первый")
        @FindBy(id = "f0")
        TextFields input_1;

        @Name("Второй")
        @FindBy(id = "f00")
        TextField input_2;

        @Name("Элем")
        TextFields getInputs() {
            return get(TextFields.class, By.xpath("//input"));
        }

        @Override
        public Object getValue() {
            return getValue("Элем");
        }
    }
}
