package repository;

import org.junit.Test;

public class LabelRepositoryTest {
    @Test
    void save(){
        LabelRepository labelRepository = new LabelRepository("src/test/resources/labelsTest.json");
    }

}
