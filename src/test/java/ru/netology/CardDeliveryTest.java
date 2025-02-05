package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    @Test
    public void testSendOrderIfValidData () {
        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71230001234");
        $("[data-test-id='agreement']").click();
        $(".button span.button__content").click();

    }
}
