package controller;

import model.Label;
import model.Post;
import model.PostStatus;
import model.Writer;
import repository.LabelRepository;
import repository.PostRepository;
import view.PostView;


import java.util.*;

public class PostController {
    protected PostRepository postRepository = new PostRepository("posts.json");
    protected PostView postView = new PostView();
    protected  PostController postController;

    public void createPost(int id, String content, List<Label> labels) {
        Date now = new Date();
        Post post = new Post(id, content, now, now, labels, PostStatus.ACTIVE);
        postRepository.save(post);
        postView.displaySuccessMessage("Пост успешно создан.");
    }

    public void editPost(int id, String newContent, List<Label> newLabels, Date updateTime) {
        try{
            Post post = postRepository.getById(id);
            if (post != null) {
                post.setContent(newContent);
                post.setLabels(newLabels);
                post.setUpdated(updateTime);
                postRepository.update(post);
                postView.displaySuccessMessage("Пост успешно отредактирован.");
            } else {
                postView.displayErrorMessage("Пост с указанным ID не найден.");
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Список постов пуст");
        }
    }

    public void deletePost(int id) {
        try {
            Post post = postRepository.getById(id);
            if (post != null) {
                post.setStatus(PostStatus.DELETED);
                postRepository.update(post);
                postView.displaySuccessMessage("Пост успешно удален.");
            } else {
                postView.displayErrorMessage("Пост с указанным ID не найден.");
            }
        } catch (NullPointerException n) {
            System.out.println("Posts отсутствуют");
        }
    }
    public void getPost(int id) {
            Post post = postRepository.getById(id);
            if (post != null) {
                postView.displayPost(post);
            } else {
                System.out.println("Post с указанным ID не найден.");
            }
        }
    public void activatePostController() {
        Scanner scanner = new Scanner(System.in);
        LabelRepository labelRepos = new LabelRepository("labels.json");
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать новый пост");
                System.out.println("2. Редактировать существующий пост");
                System.out.println("3. Удалить пост");
                System.out.println("4. Просмотреть пост по ID");
                System.out.println("5. Просмотреть все посты");
                System.out.println("6. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        int id = 0;
                        System.out.print("Введите содержание поста: ");
                        String content = scanner.nextLine();
                        System.out.print("Введите ID меток, разделяя их запятой: ");
                        String labelIdsStr = scanner.nextLine();
                        List<Label> labels = new ArrayList<>();
                        String[] labelIdsArray = labelIdsStr.split(",");
                        for (String labelIdStr : labelIdsArray) {
                            try {
                                int labelId = Integer.parseInt(labelIdStr.trim());
                                Label label = labelRepos.getById(labelId);
                                if (label != null) {
                                    labels.add(label);
                                } else {
                                    System.out.println("Метка с ID " + labelId + " не найдена.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Некорректный ID метки: " + labelIdStr);
                                break;
                            }
                        }
                        createPost(id, content, labels);
                        System.out.println(id);
                        break;
                    case 2:
                        System.out.print("Введите ID поста, который вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое содержание: ");
                        String newContent = scanner.nextLine();
                        Date updateTime = new Date();
                        // Здесь можно запросить ввод новых меток и создать список меток
                        List<Label> newLabels = new ArrayList<>();
                        editPost(editId, newContent, newLabels, updateTime);
                        break;
                    case 3:
                        System.out.print("Введите ID поста, который вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        deletePost(deleteId);
                        break;
                    case 4:
                        System.out.print("Введите ID поста для просмотра: ");
                        int viewId = scanner.nextInt();
                        try{
                            postController.getPost(viewId);
                        }catch (NullPointerException nullPointerException){
                            System.out.println("Посты отсутствуют");
                    }

                        break;
                    case 5:
                        postView.displayAllPosts(postRepository.getAll());
                        break;
                    case 6:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Некорректный выбор. Пожалуйста, выберите существующую опцию.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Сделайте корректный выбор");
                scanner.nextLine();
            }
        }
    }
}