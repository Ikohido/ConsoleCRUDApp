package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Label;
import model.Writer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WriterRepository implements GenericInterface<Writer, Integer>{
    protected List<Writer> writers;
    private final Gson gson;
    private final String fileName;

    public WriterRepository(String fileName) {
        this.fileName = fileName;
        this.gson = new Gson();
        this.writers = new ArrayList<>();
    }

    @Override
    public Writer getById(Integer id) {
        try {
            List<Writer> writers = getAllWriters();
            for (Writer writer : writers) {
                if (writer.getId() == id) {
                    return writer;
                }
            }
        }catch (NullPointerException n){
            System.out.println("Writers отсутствуют");
        }
        return null;
    }
    @Override
    public List<Writer> getAll() {
        return getAllWriters();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = getAllWriters();
        if (writers == null){
            writers = new ArrayList<>();
        }
        int maxId = writers.stream()
                .mapToInt(Writer::getId)
                .max()
                .orElse(0);
        writer.setId(maxId + 1);
        writers.add(writer);
        saveAllWriters(writers);
        return writer;
    }



    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = getAllWriters();
        for (int i = 0; i < writers.size(); i++) {
            if (writers.get(i).getId() == writer.getId()) {
                writers.set(i, writer);
                saveAllWriters(writers);
                return writer;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        List<Writer> writers = getAllWriters();
        writers.removeIf(writer -> writer.getId() == id);
        saveAllWriters(writers);

    }
    private List<Writer> getAllWriters() {
        try (Reader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<Writer>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveAllWriters(List<Writer> writers) {
        try (java.io.Writer writer = new FileWriter(fileName)) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
