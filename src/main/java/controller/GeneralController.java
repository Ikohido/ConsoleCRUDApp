package controller;

import repository.LabelRepository;

import java.util.Scanner;

public class GeneralController {
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController(new LabelRepository("src/main/resources/labels.json"));
    private final WriterController writerController = new WriterController();

    public void activateGeneralController() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Управление Label");
                System.out.println("2. Управление Post");
                System.out.println("3. Управление Writer");
                System.out.println("0. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> labelController.activateLabelController();
                    case 2 -> postController.activatePostController();
                    case 3 -> writerController.activateWriterController();
                    case 0 -> {
                        System.out.println("Выход из программы.");
                        return;
                    }
                    default -> System.out.println("Некорректный выбор. Пожалуйста, выберите существующую опцию.");
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
