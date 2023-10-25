package view;

import model.Label;

import java.util.List;

public class LabelView {
    public void showAllLabels(List<Label> labels) {
        if (labels.isEmpty()) {
            System.out.println("Список меток пуст");
        } else {
            System.out.println("Метки");
            for (Label label : labels) {
                showLabel(label);
            }
        }
    }

    public void showLabel(Label label) {
        if (label != null) {
            System.out.println("Label Details:");
            System.out.println("ID: " + label.getId());
            System.out.println("Name: " + label.getName());
        } else {
            System.out.println("Label not found.");
        }
    }
}
