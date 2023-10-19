package controller;

import java.util.Scanner;

public class GeneralController {
    static PostController postController = new PostController();
    static LabelController labelController = new LabelController();
    static WriterController writerController = new WriterController();

    public static void activateGeneralController() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Управление Label");
                System.out.println("2. Управление Post");
                System.out.println("3. Управление Writer");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        labelController.activateLabelController();
                        break;
                    case 2:
                        postController.activatePostController();
                        break;
                    case 3:
                        writerController.activateWriterController();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
