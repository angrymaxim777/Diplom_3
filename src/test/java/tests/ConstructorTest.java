package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pages.MainPage;
import static org.junit.Assert.assertTrue;

@DisplayName("Тестирование раздела 'Конструктор'")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Проверка перехода к разделу 'Соусы'")
    @Description("Тест проверяет, что при клике на раздел 'Соусы' он становится активным")
    public void testSwitchToSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Страница должна загрузиться", mainPage.isPageLoaded());

        mainPage.clickSaucesSection();
        assertTrue("Раздел 'Соусы' должен быть активен",
                mainPage.isSaucesSectionActive());
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Начинки'")
    @Description("Тест проверяет, что при клике на раздел 'Начинки' он становится активным")
    public void testSwitchToFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Страница должна загрузиться", mainPage.isPageLoaded());

        mainPage.clickFillingsSection();
        assertTrue("Раздел 'Начинки' должен быть активен",
                mainPage.isFillingsSectionActive());
    }

    @Test
    @DisplayName("Проверка возврата к разделу 'Булки'")
    @Description("Тест проверяет возврат к разделу 'Булки' после перехода к другому разделу")
    public void testSwitchBackToBunsSection() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Страница должна загрузиться", mainPage.isPageLoaded());

        // Переходим к соусам
        mainPage.clickSaucesSection();
        assertTrue("Раздел 'Соусы' должен быть активен", mainPage.isSaucesSectionActive());

        // Возвращаемся к булкам
        mainPage.clickBunsSection();
        assertTrue("Раздел 'Булки' должен быть активен после возврата",
                mainPage.isBunsSectionActive());
    }
}