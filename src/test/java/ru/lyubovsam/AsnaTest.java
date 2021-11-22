package ru.lyubovsam;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AsnaTest {
    @DisplayName("Отображение поисковой строки на сайте https://www.asna.ru/")
    @Tag("WEB")
    @Test
    void asnaSearchTest(){
        open("https://www.asna.ru/");
        $("input[type='text']").click();
    }
    @DisplayName("Поиск наименования эфаклар на сайте https://www.asna.ru/")
    @Tag("WEB")
    @Test
    void asnaEffaclarSearchTest(){
        open("https://www.asna.ru/");
        $("input[type='text']").setValue("эфаклар").pressEnter();
        $("[class='productsPanel_productsContainer__rwv3w']")
                .shouldHave(text("эфаклар")).shouldBe(visible);
    }

    @DisplayName("Поиск наименования субстиан на сайте https://www.asna.ru/")
    @Tag("WEB")
    @Test
    void asnaSubstianSearchTest(){
        open("https://www.asna.ru/");
        $("input[type='text']").setValue("субстиан").pressEnter();
        $("[class='productsPanel_productsContainer__rwv3w']")
                .shouldHave(text("субстиан")).shouldBe(visible);
    }

    @ValueSource(strings = {"эфаклар","субстиан"})
    @Tag("WEB")
    @ParameterizedTest(name = "Поиск наименования {0} на сайте https://www.asna.ru/")
    void commonAsnaSearchTest(String searchQuery){
        open("https://www.asna.ru/");
        $("input[type='text']").setValue(searchQuery).pressEnter();
        $("[class='productsPanel_productsContainer__rwv3w']")
                .shouldHave(text(searchQuery)).shouldBe(visible);
    }

    @CsvSource({
            "эфаклар, Ля Рош Позе Эфаклар маска 100мл",
            "субстиан, Ля Рош Позе Субстиан крем для контура глаз 15мл"
    })
    @Tag("WEB")
    @ParameterizedTest(name = "Поиск наименования {0} на сайте https://www.asna.ru/ и проверка отображения{1}")
    void scvAsnaSearchTest(String searchQuery, String expectedResult) {
        open("https://www.asna.ru/");
        $("input[type='text']").setValue(searchQuery).pressEnter();
        $("[class='productsPanel_productsContainer__rwv3w']")
                .shouldHave(text(expectedResult)).shouldBe(visible);
    }


    static Stream <Arguments> methodSourceSearchTest(){
        return Stream.of(
                Arguments.of("эфаклар", List.of("Ля Рош Позе Эфаклар маска 100мл")),
                Arguments.of("субстиан", List.of("Ля Рош Позе Субстиан крем для контура глаз 15мл")));
    }
    @MethodSource
    @Tag("WEB")
    @ParameterizedTest(name = "Поиск по сайту https://www.asna.ru/")
    void methodSourceSearchTest(String searchQuery, List<String> expectedResult) {
        open("https://www.asna.ru/");
        $("input[type='text']").setValue(searchQuery).pressEnter();
        $("[class='productsPanel_productsContainer__rwv3w']")
                .shouldHave(text(expectedResult.get(0))).shouldBe(visible);
    }
}
