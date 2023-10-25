package view;

import model.Post;
import model.Writer;

import java.util.List;

public class WriterView {


    public void showAllWriters(List<Writer> writers) throws NullPointerException {
        System.out.println("Список всех писателей:");
        for (Writer writer : writers) {
            System.out.println("ID: " + writer.getId());
            System.out.println("Имя: " + writer.getFirstName());
            System.out.println("Фамилия: " + writer.getLastName());
            System.out.println("Статус: " + writer.getStatus());
            System.out.println();
        }
    }

    public void showWriter(Writer writer) throws NullPointerException {
        System.out.println("Информация о писателе:");
        System.out.println("ID: " + writer.getId());
        System.out.println("Имя: " + writer.getFirstName());
        System.out.println("Фамилия: " + writer.getLastName());
        System.out.println("Статус: " + writer.getStatus());
        System.out.println("Список постов:");
        for (Post post : writer.getPosts()) {
            if (post != null)
                System.out.println("  - " + post.getContent());
            else System.out.println("Список постов пуст");
        }
        System.out.println();

    }
}