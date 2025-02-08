package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class UpdatedCardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRescheduleMeeting() {

        var user = DataGenerator.Registration.generateUser("ru");
        int daysToAddBeforeFirstPlannedMeeting = 3;
        String dateForFirstPlannedMeeting = DataGenerator.generateDate(daysToAddBeforeFirstPlannedMeeting);
        int daysToAddBeforeSecondPlannedMeeting = 10;
        String dateForSecondPlannedMeeting = DataGenerator.generateDate(daysToAddBeforeFirstPlannedMeeting);


        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateForFirstPlannedMeeting);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button").findBy(Condition.exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldHave(Condition.text(dateForFirstPlannedMeeting));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateForSecondPlannedMeeting);
        $$(".button").findBy(Condition.exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification']").shouldHave(Condition.partialText("Перепланировать"));
        $$(".button").findBy(Condition.exactText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldHave(Condition.text(dateForSecondPlannedMeeting));
    }
}