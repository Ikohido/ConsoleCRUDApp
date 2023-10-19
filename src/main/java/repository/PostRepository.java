package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Post;
import model.PostStatus;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PostRepository implements GenericInterface<Post, Integer> {
    private final String fileName;
    private final Gson gson = new Gson();
    protected List<Post> posts;

    public PostRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Post getById(Integer id) {
        try {
            return posts.stream()
                    .filter(post -> post.getId() == id)
                    .findFirst()
                    .orElse(null);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Такого поста не существует");
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        try {
            return new ArrayList<>(posts);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Посты отсутствуют");
        }
        return null;
    }

    @Override
    public Post save(Post post) {
        List<Post> posts = getAllPosts();
        if (posts == null) {
            posts = new ArrayList<>();
        }
        int maxId = posts.stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0);
        post.setId(maxId + 1);
        post.setStatus(PostStatus.ACTIVE);
        posts.add(post);
        saveAllPosts(posts);
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = getAllPosts();
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == post.getId()) {
                posts.set(i, post);
                saveAllPosts(posts);
                return post;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        List<Post> posts = getAllPosts();
        posts.removeIf(post -> post.getId() == (id));
        saveAllPosts(posts);
    }

    private List<Post> getAllPosts() {
        try (Reader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<Post>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveAllPosts(List<Post> posts) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
