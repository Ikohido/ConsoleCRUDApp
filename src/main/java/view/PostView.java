package view;

import model.Post;
import model.Writer;
import repository.PostRepository;

import java.util.List;

public class PostView {
    public void displayAllPosts(List<Post> posts) {
        try{
            if (posts.isEmpty()) {
                System.out.println("Список постов пуст.");
            } else {
                System.out.println("Список постов:");
                for (Post post : posts) {
                    displayPost(post);
                }
            }
        }catch (NullPointerException nullPointerException){
            System.out.println("");
        }

    }
    public void displayPost(Post post) {
            System.out.println("ID: " + post.getId());
            System.out.println("Содержимое: " + post.getContent());
            System.out.println("Дата создания: " + post.getCreated());
            System.out.println("Дата обновления: " + post.getUpdated());
            System.out.println("Список меток: " + post.getLabels());
            System.out.println("Статус: " + post.getStatus());
            System.out.println();
        }
    public void displaySuccessMessage(String message) {
        System.out.println("Успешно: " + message);
    }

    public void displayErrorMessage(String message) {
        System.err.println("Ошибка: " + message);
    }

}
