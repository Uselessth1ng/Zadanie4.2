import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

public class Tests

{
    @BeforeEach
    void setup(){
        Configuration.browser = "firefox";
        open("https://the-internet.herokuapp.com/");
    }

    SelenideElement input1 = $x("//input[1]");
    SelenideElement input2 = $x("//input[2]");

    void checked(){
        System.out.println("checkbox1: " + input1.getAttribute("checked") + " checkbox2: " + input2.getAttribute("checked"));
    }

    @Test
    public void testCheckBoxes(){
        $x("//a[@href='/checkboxes']").click();
        checked();
        input1.click();
        checked();
        input2.click();
        checked();
    }

    @Test
    public void testDropdown(){
        $x("//a[@href='/dropdown']").click();
        $x("//select").click();
        $x("//option[@value='1']").click();
        System.out.println($x("//select").getText());
        $x("//option[@value='2']").click();
        System.out.println($x("//select").getText());
    }

    @Test
    public void testDisapperaingEls(){
        int counter = 0;
        while (counter < 10) {
            $x("//a[@href='/disappearing_elements']").click();
            ElementsCollection el = $$x("//li");
            if (el.size() == 5) break;
            counter++;
            open("https://the-internet.herokuapp.com/");
        }
        if (counter == 10) assert false;
        System.out.println("Количество попыток: " + (counter + 1));
    }

    @Test
    public void testInputs(){
        $x("//a[@href='/inputs']").click();
        Integer a = new Random().nextInt(10000) + 1;
        $x("//input").sendKeys(a.toString());
        System.out.println($x("//input").getAttribute("value"));
    }

    @Test
    public void testHovers() {
        $x("//a[@href='/hovers']").click();
        for (int i = 1; i < 4; i++) {
            System.out.println($x("//div[@class=\"figure\"][" + i + "]\n").hover().getText() + "\n");
        }
    }

    @Test
    public void testNotificationMsg(){
        $x("//a[@href='/notification_message']").click();
        $x("//p/a");
        int counter = 0;
        while ($x("//div[@id=\"flash\"]").getText().indexOf("unsuccesful") != -1){
            $x("//*[@id=\"flash\"]/a").click();
            $x("//p/a").click();
            counter++;
        }
        System.out.println("Количество попыток: " + (counter + 1));
    }

    public void buttonsInfo(){
        ElementsCollection el = $$x("//div[@id='elements']/button");
        System.out.println("Количество кнопок: " + el.size());
        for (SelenideElement button:el){
            System.out.println(button.getText());
        }
        System.out.println();
    }

    @Test
    public void testAREls(){
        $x("//a[@href='/add_remove_elements/']").click();
        for (int i = 0; i < 5; i++) {
            $x("//div[@class='example']/button").click();
            ElementsCollection el = $$x("//div[@id='elements']/button");
            System.out.println(el.get(el.size() - 1).getText() + "\n");
        }
        int delNum;
        buttonsInfo();
        for (int i = 0; i < 3; i++){
            delNum = new Random().nextInt(5 - i) + 1;
            System.out.println("Нажата кнопка: " + delNum);
            $x("//div[@id='elements']/button[" + delNum + "]").click();
            buttonsInfo();
        }
    }

    @Test
    public void testStatusCodes200(){
        $x("//a[@href='/status_codes']").click();
        $x("//li/a[text()=200]").click();
        String result = $x("//p").getText();
        System.out.println(result.substring(0, result.indexOf("\n") - 1));
    }

    @Test
    public void testStatusCodes301(){
        $x("//a[@href='/status_codes']").click();
        $x("//li/a[text()=301]").click();
        String result = $x("//p").getText();
        System.out.println(result.substring(0, result.indexOf("\n") - 1));
    }

    @Test
    public void testStatusCodes404(){
        $x("//a[@href='/status_codes']").click();
        $x("//li/a[text()=404]").click();
        String result = $x("//p").getText();
        System.out.println(result.substring(0, result.indexOf("\n") - 1));
    }

    @Test
    public void testStatusCodes500(){
        $x("//a[@href='/status_codes']").click();
        $x("//li/a[text()=500]").click();
        String result = $x("//p").getText();
        System.out.println(result.substring(0, result.indexOf("\n") - 1));
    }

    @AfterEach
    void quit(){
        closeWebDriver();
    }
}
