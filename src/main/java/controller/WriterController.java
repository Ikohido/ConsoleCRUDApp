package controller;

import model.Post;
import model.PostStatus;
import model.Writer;
import repository.PostRepository;
import repository.WriterRepository;
import view.WriterView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterController {
    private final WriterRepository writerRepository;
    private final WriterView writerView = new WriterView();

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public void updateJsonFile(Writer writer) throws NullPointerException {
        writerRepository.update(writer);
    }

    public void saveInJsonFile(Writer writer) {
        writerRepository.save(writer);
    }

    public Writer createWriter(int id, String firstName, String lastName, List<Post> posts) {
        return new Writer(id, firstName, lastName, posts, PostStatus.ACTIVE);
    }

    public Writer editWriter(int id, String firstName, String lastName, List<Post> posts) throws NullPointerException {
        Writer writer = writerRepository.getById(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setPosts(posts);
        System.out.println("Писатель отредактирован: " + writer);
        return writer;
    }

    public Writer deleteWriter(Writer writer) {
        try {
            writer.setStatus(PostStatus.DELETED);
            System.out.println("Writer удален: " + writer);
        } catch (NullPointerException npe) {
            System.out.println("Писатель отсутствует");
        }
        return writer;
    }

    public void getWriter(int id) throws NullPointerException {
        Writer writer = writerRepository.getById(id);
        writerView.showWriter(writer);
    }

    public void getAllWriters() throws NullPointerException {
        List<Writer> writers = writerRepository.getAll();
        writerView.showAllWriters(writers);
    }

    public void activateWriterController() {
        Scanner scanner = new Scanner(System.in);
        WriterController writerController = new WriterController(writerRepository);
        PostRepository repos = new PostRepository("src/main/resources/posts.json");
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать нового писателя");
                System.out.println("2. Редактировать существующего писателя");
                System.out.println("3. Удалить писателя");
                System.out.println("4. Получить информацию о писателе");
                System.out.println("5. Получить список всех писателей");
                System.out.println("0. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        int id = 0;
                        System.out.print("Введите имя: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Введите фамилию: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Введите id поста, который должен относиться к писателю: ");
                        int postId = scanner.nextInt();
                        List<Post> neededPost = new ArrayList<>();
                        try {
                            neededPost.add(repos.getById(postId));
                        } catch (NullPointerException npe) {
                            System.out.println("Нужный пост не найден");
                        }
                        saveInJsonFile(writerController.createWriter(id, firstName, lastName, neededPost));
                        break;
                    case 2:
                        System.out.print("Введите ID писателя, которого вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newFirstName = scanner.nextLine();
                        System.out.print("Введите новую фамилию: ");
                        String newLastName = scanner.nextLine();
                        System.out.print("Введите id постов, которые должны относиться к писателю, разделяя id запятой: ");
                        List<Post> newPosts = new ArrayList<>();
                        String postIdsString = scanner.nextLine();
                        String[] postIdArray = postIdsString.split(",");
                        for (String postIdStr : postIdArray) {
                            try {
                                int postIdd = Integer.parseInt(postIdStr.trim());
                                Post post = repos.getById(postIdd);
                                newPosts.add(post);
                            } catch (NullPointerException npe) {
                                System.out.println("Посты отсутствуют");
                            }
                        }
                        try {
                            updateJsonFile(writerController.editWriter(editId, newFirstName, newLastName, newPosts));
                        } catch (NullPointerException npe) {
                            System.out.println("Не удалось отредактировать пост");
                        }
                        break;
                    case 3:
                        System.out.print("Введите ID писателя, которого вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        Writer writer = writerRepository.getById(deleteId);
                        updateJsonFile(deleteWriter(writer));
                        break;
                    case 4:
                        System.out.print("Введите ID писателя, информацию о котором вы хотите получить: ");
                        int infoId = scanner.nextInt();
                        try {
                            writerController.getWriter(infoId);
                        } catch (NullPointerException npe) {
                            System.out.println("Писатель с указанным ID не найден");
                        }

                        break;
                    case 5:
                        try {
                            writerController.getAllWriters();
                        } catch (NullPointerException npe) {
                            System.out.println("Писатели отсутствуют");
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
