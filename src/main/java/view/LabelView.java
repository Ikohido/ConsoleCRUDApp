package view;

import model.Label;

import java.util.List;

public class LabelView {
    public void showAllLabels(List<Label> labels) {
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.println(label.getId() + ": " + label.getName());
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

    public void showSuccessMessage(String action) {
        System.out.println(action + " label successfully.");
    }

}
