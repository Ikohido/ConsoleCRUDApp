package controller;

import model.Label;
import model.Post;
import model.PostStatus;
import repository.LabelRepository;
import repository.PostRepository;
import view.PostView;

import java.time.LocalDateTime;
import java.util.*;

public class PostController {
    private final PostRepository postRepository = new PostRepository("src/main/resources/posts.json");
    private final PostView postView = new PostView();


    public void createPost(int id, String content, List<Label> labels) {
        Date now = new Date();
        Post post = new Post(id, content, now, now, labels, PostStatus.ACTIVE);
        postRepository.save(post);
        postView.displaySuccessMessage("Пост успешно создан.");
    }

    public void editPost(int id, String newContent, List<Label> newLabels, Date updateTime) throws NullPointerException {
        Post post = postRepository.getById(id);
        post.setContent(newContent);
        post.setLabels(newLabels);
        post.setUpdated(updateTime);
        postRepository.update(post);
        postView.displaySuccessMessage("Пост успешно отредактирован.");
    }

    public void deletePost(int id) throws NullPointerException {
        Post post = postRepository.getById(id);
        post.setStatus(PostStatus.DELETED);
        postRepository.update(post);
        postView.displaySuccessMessage("Пост успешно удален.");
    }

    public void getPost(int id) throws NullPointerException {
        Post post = postRepository.getById(id);
        postView.displayPost(post);
    }

    public void activatePostController() {
        Scanner scanner = new Scanner(System.in);
        LabelRepository labelRepos = new LabelRepository("src/main/resources/labels.json");
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать новый пост");
                System.out.println("2. Редактировать существующий пост");
                System.out.println("3. Удалить пост");
                System.out.println("4. Просмотреть пост по ID");
                System.out.println("5. Просмотреть все посты");
                System.out.println("0. Выйти");
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
                        break;
                    case 2:
                        System.out.print("Введите ID поста, который вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое содержание: ");
                        String editContent = scanner.nextLine();
                        Date updateTime = new Date();
                        System.out.println("Нужны ли новые метки? Да/Нет");
                        String apply = scanner.nextLine();
                        List<Label> newLabels = new ArrayList<>();
                        if (apply.equalsIgnoreCase("Да") || apply.equalsIgnoreCase("Нужны")) {
                            System.out.print("Введите ID меток, разделяя их запятой: ");
                            String labelIdsString = scanner.nextLine();
                            String[] labelIdArray = labelIdsString.split(",");
                            for (String labelIdStr : labelIdArray) {
                                try {
                                    int labelId = Integer.parseInt(labelIdStr.trim());
                                    Label label = labelRepos.getById(labelId);
                                    newLabels.add(label);
                                } catch (NullPointerException npe) {
                                    throw new NullPointerException();
                                }
                            }
                            try {
                                editPost(editId, editContent, newLabels, updateTime);
                            } catch (NullPointerException npe) {
                                System.out.println("Не удалось отредактировать пост");
                            }
                        } else {
                            try {
                                editPost(editId, editContent, newLabels, updateTime);
                            } catch (NullPointerException npe) {
                                System.out.println("Не удалось отредактировать пост");
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Введите ID поста, который вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        try {
                            deletePost(deleteId);
                        } catch (NullPointerException npe) {
                            System.out.println("Пост с данным ID не найден"); // мы тут пишем об этом в связи с тем,
                            // что по условию задачи посты на самом деле не удаляются, а просто переводятся в статус "DELETED".
                            // Поэтому я и вставил sout в каждый контроллер.
                        }

                        break;
                    case 4:
                        System.out.print("Введите ID поста для просмотра: ");
                        int viewId = scanner.nextInt();
                        PostController postController = new PostController();
                        try {
                            postController.getPost(viewId);
                        } catch (NullPointerException nullPointerException) {
                            System.out.println("Пост с указанным ID отсутствует");
                        }

                        break;
                    case 5:
                        try {
                            postView.displayAllPosts(postRepository.getAll());
                        } catch (NullPointerException npe) {
                            System.out.println("Список постов пуст");
                        }

                        break;
                    case 0:
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