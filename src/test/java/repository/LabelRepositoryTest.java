package repository;

import model.Label;
import model.PostStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LabelRepositoryTest {
    private final LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    private static final String jsonFileName = "src/test/java/resources/labelsTest.json";

    @Before
    public void clearJsonFile() {
        try (FileWriter writer = new FileWriter(jsonFileName, false)) {
            writer.write(""); // Пишем пустую строку, чтобы очистить содержимое файла
        } catch (IOException e) {
            System.out.println("Нет возможности изменить json файл"); // Обработка исключения в случае ошибки записи
        }
    }

    /**
     * Данный метод тестирует сохранение метки в labelsTest.json
     * Given: Создание метки с именем "Тест1"
     * When: Сохранение метки в labelsTest.json
     * Then: Проверка, что метка корректно создалась
     */
    @Test
    public void save() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        // --- When ---
        labelRepository.save(label);
        // --- Then ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /**
     * Данный метод тестирует выдачу метки из labelsTest.json
     * Given: Создание метки с именем "Тест1" и сохранение в labelsTest.json
     * When: Получение метки из labelsTest.json
     * Then: Проверка, что метка корректно досталась
     */
    @Test
    public void getById() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        labelRepository.save(label);
        // --- When ---
        Label expectedLabel = labelRepository.getById(label.getId());
        // --- Then ---
        assertEquals(label, expectedLabel);
    }

    /**
     * Данный метод тестирует изменение имени метки из labelsTest.json
     * Given: Создание метки с именем "Тест1" и сохранение в labelsTest.json
     * When: Создание новой метки и замена старой.
     * Then: Проверка, что метка корректно изменилась.
     */
    @Test
    public void update() {
        // --- Given ---
        Label oldLabel = new Label(1, "Тест1", PostStatus.ACTIVE);
        labelRepository.save(oldLabel);
        // --- When ---
        Label newLabel = new Label(1, "Тест2", PostStatus.ACTIVE);
        labelRepository.update(newLabel);
        // --- Then ---
        assertEquals(newLabel, labelRepository.getById(newLabel.getId()));
    }

}
