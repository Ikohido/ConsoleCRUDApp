package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Label;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LabelRepository implements GenericInterface<Label, Integer> {
    private final String dataFilePath;
    private final Gson gson;
    private List<Label> labels;

    public LabelRepository(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.gson = new Gson();
        this.labels = new ArrayList<>(); // Инициализация пустой коллекции в конструкторе
    }

    @Override
    public Label getById(Integer id) {
        try {
            List<Label> labels = getAllLabels();
            for (Label label : labels) {
                if (label.getId() == id) {
                    return label;
                }
            }
        } catch (NullPointerException n) {
            System.out.println("Labels отсутствуют");
        }

        return null;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabels();
    }

    @Override
    public Label save(Label label) {
        List<Label> labels = getAllLabels();
        if (labels == null) {
            labels = new ArrayList<>();
        }
        int maxId = labels.stream().mapToInt(Label::getId).max().orElse(0);
        label.setId(maxId + 1);
        labels.add(label);
        saveAllLabels(labels);
        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> labels = getAllLabels();
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getId() == label.getId()) {
                labels.set(i, label);
                saveAllLabels(labels);
                return label;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        List<Label> labels = getAllLabels();
        labels.removeIf(label -> label.getId() == id);
        saveAllLabels(labels);
    }

    private List<Label> getAllLabels() {
        try (Reader reader = new FileReader(dataFilePath)) {
            Type listType = new TypeToken<List<Label>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAllLabels(List<Label> labels) {
        try (Writer writer = new FileWriter(dataFilePath)) {
            gson.toJson(labels, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
