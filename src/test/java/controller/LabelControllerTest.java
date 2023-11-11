package controller;

import model.Label;
import model.PostStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import repository.LabelRepository;
import view.LabelView;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class LabelControllerTest {
    LabelView labelView = new LabelView();
    LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    LabelController labelController = new LabelController(labelRepository);
    private static final String jsonFileName = "src/test/java/resources/labelsTest.json";
    private static final String jsonCopyName = "resources/labelsTestCopy.json";

    /**
     * Идея этого метода заключена в том, что после выполнения всех тестов, метод чистит labelsTest.json
     * То есть тесты по очереди выполняют то, что должны выполнять, т.е. меняют метку, а потом все восстанавливается и может снова работать
     *
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
     * Данный метод достает метку из labels.json и изменяет ее название.
     * Given: Дана метка с названием "Тест1"
     * When: Изменение названия метки на "Тест2" и сохранение в labelsTest.json
     * Then: Проверка изменения имени метки.
     */
    @Test
    public void editLabelTest() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        // --- When ---
        labelController.updateJsonFile(labelController.editLabel(label.getId(), "Тест2"));
        label.setName("Тест2");
        // --- Then ---
        assertEquals(label, labelRepository.getById(label.getId()));
    } // а вот начиная с этого идут неприятности


    /**
     * Данный метод изменяет статус метки на удаленный (DELETED)
     * Given: Создание метки с названием "Тест1" и удаленным состоянием.
     * Then: Сравнение статусов ожидаемой и действительной меток.
     */
    @Test
    public void deleteLabelTest() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.DELETED);
        // --- When ---
        // --- Then ---
        assertEquals(label, labelController.deleteLabel(label));
    }

    /**
     * Данный метод выводит данные о указанной по ID метке.
     * Output: Захват вывода в консоль
     * SetOutput: Использование захвата и вывод данных в output
     * ShowLabel: Использование метода showLabel() для выдачи данных о метке.
     * SetOut: Захват вывода прерывается.
     * Expect: Что мы ожидаем увидеть от showLabel()
     * Equals: Сравнение ожидаемой и действительной меток.
     */
    @Test
    public void getLabelTest() {
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        //        --- Output ---
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //        --- SetOutput ---
        System.setOut(new PrintStream(outputStream));
        //        --- ShowLabel ---
        labelView.showLabel(label);
        //        --- SetOut ---
        System.setOut(System.out);
        //        --- Expect ---
        String expectedOutput = "Label Details:\r\nID: " + label.getId() + "\r\nName: " + label.getName() + "\r\n";
        //        --- Equals ---
        assertEquals(expectedOutput, outputStream.toString());
    }

}
