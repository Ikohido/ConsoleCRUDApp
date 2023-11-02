package controller;

import model.Label;
import model.PostStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.LabelRepository;
import view.LabelView;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LabelControllerTest {
    @Mock
    LabelView labelView = new LabelView();
    @InjectMocks
    LabelController labelController = Mockito.mock(LabelController.class);
    @Mock
    LabelRepository labelRepository = new LabelRepository("src/test/java/resources/labelsTest.json");
    @Mock
    Label label = new Label(1, "Тест1", PostStatus.ACTIVE);

    @Test
    public void createLabelTest() {
        labelController.createLabel(label.getId(), label.getName());
        labelRepository.save(label);
        assertEquals(label, labelRepository.getById(label.getId()));
    }
    @Test
    public void editLabelTest() {
        label.setName("Тест2");
        labelRepository.update(label);
        assertEquals(label, labelRepository.getById(label.getId()));
    }

    @Test
    public void deleteLabelTest() {
        label.setStatus(PostStatus.DELETED);
        labelRepository.update(label);
        assertEquals(label, labelRepository.getById(label.getId()));
    }
    @Test
    public void getLabelTest(){
        Label label2 = labelRepository.getById(label.getId());
        assertEquals(label, label2);
    }

}
