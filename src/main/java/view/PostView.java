package view;

import model.Post;

import java.util.List;

public class PostView {
    public void displayAllPosts(List<Post> posts) throws NullPointerException {
        if (posts.isEmpty()) {
            System.out.println("Список постов пуст.");
        } else {
            System.out.println("Список постов:");
            for (Post post : posts) {
                displayPost(post);
            }
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


}
