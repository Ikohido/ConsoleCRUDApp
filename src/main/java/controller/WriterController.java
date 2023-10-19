package controller;

import model.Label;
import model.Post;
import model.PostStatus;
import model.Writer;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import view.LabelView;
import view.WriterView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterController {
    protected WriterRepository writerRepository = new WriterRepository("src/main/resources/writers.json");
    protected final WriterView writerView = new WriterView();


    public void createWriter(int id, String firstName, String lastName, List<Post> posts) {
        Writer writer = new Writer(id, firstName, lastName, posts, PostStatus.ACTIVE);
        writerRepository.save(writer);
        System.out.println("Писатель создан: " + writer);
    }

    public void editWriter(int id, String firstName, String lastName) {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            writerRepository.update(writer);
            System.out.println("Писатель отредактирован: " + writer);
        } else {
            System.out.println("Писатель с указанным ID не найден.");
        }
    }

    public void deleteWriter(int id) {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            writer.setStatus(PostStatus.DELETED);
            writerRepository.update(writer);
            System.out.println("Writer удален: " + writer);
        } else {
            System.out.println("Writer с указанным ID не найден.");
        }
    }

    public void getWriter(int id) {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            writerView.showWriter(writer);
        } else {
            System.out.println("Писатель с указанным ID не найден.");
        }
    }

    public void getAllWriters() {
        List<Writer> writers = writerRepository.getAll();
        writerView.showAllWriters(writers);
    }

    public void activateWriterController() {
        Scanner scanner = new Scanner(System.in);
        WriterController writerController = new WriterController();
        PostRepository repos = new PostRepository("posts.json");

        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать нового писателя");
                System.out.println("2. Редактировать существующего писателя");
                System.out.println("3. Удалить писателя");
                System.out.println("4. Получить информацию о писателе");
                System.out.println("5. Получить список всех писателей");
                System.out.println("6. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите ID для нового писателя: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите имя: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Введите фамилию: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Введите id поста, который должен относиться к писателю: ");
                        int postId = scanner.nextInt();
                        List<Post> neededPost = new ArrayList<>();
                        neededPost.add(repos.getById(postId));
                        writerController.createWriter(id, firstName, lastName, neededPost);
                        break;
                    case 2:
                        System.out.print("Введите ID писателя, которого вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newFirstName = scanner.nextLine();
                        System.out.print("Введите новую фамилию: ");
                        String newLastName = scanner.nextLine();
                        writerController.editWriter(editId, newFirstName, newLastName);
                        break;
                    case 3:
                        System.out.print("Введите ID писателя, которого вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        writerController.deleteWriter(deleteId);
                        break;
                    case 4:
                        System.out.print("Введите ID писателя, информацию о котором вы хотите получить: ");
                        int infoId = scanner.nextInt();
                        writerController.getWriter(infoId);
                        break;
                    case 5:
                        writerController.getAllWriters();
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
