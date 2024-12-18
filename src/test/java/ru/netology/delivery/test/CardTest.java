package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.bouncycastle.util.encoders.HexTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Date Planer")
    void datePlaner() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var dateForFirstPlaner = DataGenerator.generateDate(4);
        var dateForAddSecondPlaner = 7;
        var dateForSecondPlaner = DataGenerator.generateDate(dateForAddSecondPlaner);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateForFirstPlaner);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id ='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dateForFirstPlaner))
                .shouldBe(visible);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateForSecondPlaner);
        $(byText("Запланировать")).click();
        $("[data-test-id = 'replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id = 'replan-notification'] button").click();
        $("[data-test-id = 'success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dateForSecondPlaner))
                .shouldBe(visible);


    }


}
