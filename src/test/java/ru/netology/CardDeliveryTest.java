package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.HOME;

public class CardDeliveryTest {
    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void testSendOrderIfValidData() {
        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71230001234");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    public void testSendOrderIfValidDataAndSityList() {
        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").click();
        $("[data-test-id='city'] input").setValue("го");
        $$(".menu-item__control").findBy(Condition.text("Волгоград")).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71230001234");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    public void testSendOrderIfValidDataAndFormCalendar() {
        Selenide.open("http://localhost:9999");
        int plusDay = 32; //установить период до встречи, дней

        int defaultDateMonth = LocalDate.now().plusDays(3).getMonthValue();
        int planningDateMonth = LocalDate.now().plusDays(plusDay).getMonthValue();
        String planningDateDay = generateDate(plusDay, "d");
        String planningDate = generateDate(plusDay, "dd.MM.yyyy");
        int diffDateMonth = planningDateMonth - defaultDateMonth;

        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71230001234");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, HOME), Keys.BACK_SPACE);

        $(".icon_name_calendar").click();
        while (diffDateMonth > 0) {
            $$(".calendar__arrow_direction_right").get(1).click();
            diffDateMonth = diffDateMonth - 1;
        }
        $$(".calendar__day").findBy(Condition.text(planningDateDay)).click();
        $(".button span.button__content").click();
        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
