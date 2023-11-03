package controller;

import model.Label;
import model.PostStatus;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.LabelRepository;
import view.LabelView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class LabelControllerTest {
    @Mock
    LabelView labelView = new LabelView();
    @InjectMocks
    LabelController labelController = Mockito.mock(LabelController.class);
    @Mock
    LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    @Mock
    Label label = new Label(1, "Тест1", PostStatus.ACTIVE);

    /*      Данный метод создает метку и сохраняет ее в labels.json
            Create: Создание метки
            Save: Сохранение метки в json файл
            Equals: Сравнение ожидаемой и действительной меток.
     */
    @Test
    public void createLabelTest() {
        //        --- Create ---
        labelController.createLabel(label.getId(), label.getName());
        //        --- Save ---
        labelRepository.save(label);
        //        --- Equals ---
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    /*      Данный метод изменяет название метки
            Edit: Изменение
            Update: Сохранение изменения
            Equals: Сравнение ожидаемой и действительной меток.
    */
    @Test
    public void editLabelTest() {
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
