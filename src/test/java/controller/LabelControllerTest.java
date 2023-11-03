package controller;

import model.Label;
import model.PostStatus;
import org.junit.Test;
import repository.LabelRepository;
import view.LabelView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class LabelControllerTest {
    LabelView labelView = new LabelView();
    LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    LabelController labelController = new LabelController(labelRepository);
    // sssssssssssssssss
    /**
     * Данный метод создает метку и сохраняет ее в labels.json
     * Given: Дана метка с именем "Тест1"
     * When: Сохранение метки в json файл
     * Then: Проверка, что метка корректно создалась
     */
    @Test
    public void createLabelTest() {
        // --- Given ---
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        // --- When ---
        labelController.createLabel(label.getId(), label.getName());
        // --- Then ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /**
     * Данный метод изменяет название метки
     * Given: Изменение
     * Update: Сохранение изменения
     * Then: Сравнение ожидаемой и действительной меток.
     */
    @Test
    public void editLabelTest() {
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        //        --- Edit ---
        label.setName("Тест2");
        //        --- Update ---
        labelRepository.update(label);
        //        --- Equals ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /*      Данный метод изменяет статус метки на удаленный (DELETED)
            NewStatus: Изменение статуса на удаленный
            UpdateStatus: Сохранение нового статуса
            Equals: Сравнение ожидаемой и действительной меток.
    */
    @Test
    public void deleteLabelTest() {
        Label label = new Label(1, "Тест1", PostStatus.ACTIVE);
        //        --- NewStatus ---
        label.setStatus(PostStatus.DELETED);
        //        --- UpdateStatus ---
        labelRepository.update(label);
        //        --- Equals ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /*  Данный метод выводит данные о указанной по ID метке.
        Output: Захват вывода в консоль
        SetOutput: Использование захвата и вывод данных в output
        ShowLabel: Использование метода showLabel() для выдачи данных о метке.
        SetOut: Захват вывода прерывается.
        Expect: Что мы ожидаем увидеть от showLabel()
        Equals: Сравнение ожидаемой и действительной меток.*/
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
