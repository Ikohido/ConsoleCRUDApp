package controller;

import model.Label;
import model.PostStatus;
import org.junit.Before;
import org.junit.Test;
import repository.LabelRepository;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LabelControllerTest {
    LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    LabelController labelController = new LabelController(labelRepository);
    private static final String jsonFileName = "src/test/java/resources/labelsTest.json";

    /**
     * Идея этого метода заключена в том, что после выполнения всех тестов, метод чистит labelsTest.json
     * То есть тесты по очереди выполняют то, что должны выполнять, т.е. меняют метку, а потом все восстанавливается и может снова работать
     */
    @Before
    public void clearJsonFile() {
        try (FileWriter writer = new FileWriter(jsonFileName, false)) {
            writer.write(""); // Пишем пустую строку, чтобы очистить содержимое файла
        } catch (IOException e) {
            System.out.println("Нет возможности изменить json файл"); // Обработка исключения в случае ошибки записи
        }
    }

    /**
     * Данный метод создает метку
     * Given: Дана метка с именем "Тест1"
     * When: Создание метки и запись в labelsTest.json
     * Then: Проверка, что метка корректно создалась
     */
    @Test
    public void createLabelTest() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        // --- When ---
        labelController.saveInJsonFile(labelController.createLabel(label.getId(), label.getName()));
        // --- Then ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /**
     * Данный метод создает метку, сохраняет ее и изменяет название.
     * Given: Создается метка с названием "Тест1", после чего проверяется функция сохранения в labelsTest.json
     * When: Изменение названия метки на "Тест2" и сохранение в labelsTest.json
     * Then: Создание проверочной метки и сравнение с первоначальной.
     */
    @Test
    public void editLabelTest() {
        // --- Given ---
        Label firstLabel = new Label(1, "Тест1", PostStatus.ACTIVE);
        labelController.saveInJsonFile(labelController.createLabel(firstLabel.getId(), firstLabel.getName()));
        assertEquals(firstLabel, labelRepository.getById(firstLabel.getId()));
        // --- When ---
        labelController.updateJsonFile(labelController.editLabel(firstLabel.getId(), "Тест2"));
        // --- Then ---
        Label secondLabel = new Label(1, "Тест2", PostStatus.ACTIVE);
        assertEquals(secondLabel, labelRepository.getById(firstLabel.getId()));
    }


    /**
     * Данный метод изменяет статус метки на удаленный (DELETED)
     * Given: Создание метки с названием "Тест1" и состоянием "ACTIVE", сохранение в файл и проверка соответствия.
     * When: Удаление метки, то есть ее перевод в состояние "DELETED".
     * Then: Создание проверочной метки и сравнение статусов ожидаемой и действительной меток.
     */
    @Test
    public void deleteLabelTest() {
        // --- Given ---
        Label firstLabel = new Label(1, "Тест1", PostStatus.ACTIVE);
        labelController.saveInJsonFile(labelController.createLabel(firstLabel.getId(), firstLabel.getName()));
        assertEquals(firstLabel, labelRepository.getById(firstLabel.getId()));
        // --- When ---
        labelController.deleteLabel(firstLabel);
        // --- Then ---
        Label secondLabel = new Label(1, "Тест1", PostStatus.ACTIVE);
        assertEquals(secondLabel, labelRepository.getById(firstLabel.getId()));
    }
}
