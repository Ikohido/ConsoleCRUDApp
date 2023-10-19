package controller;

import model.Label;
import model.PostStatus;
import repository.LabelRepository;
import view.LabelView;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelController {
    protected LabelRepository labelRepository = new LabelRepository("src/main/resources/labels.json");

    public void createLabel(int id, String name) {
        Label label = new Label(id, name, PostStatus.ACTIVE);
        labelRepository.save(label);
        System.out.println("Label создан: " + label);
    }

    public void editLabel(int id, String name) {
        try{
            Label label = labelRepository.getById(id);
            if (label != null) {
                label.setName(name);
                labelRepository.update(label);
                System.out.println("Label отредактирован: " + label);
            } else {
                System.out.println("Label с указанным ID не найден.");
            }
        }catch (NullPointerException n){
            System.out.println("Label отсутствуют");
        }

    }

    public void deleteLabel(int id) {
        Label label = labelRepository.getById(id);
        if (label != null) {
            label.setStatus(PostStatus.DELETED);
            labelRepository.update(label);
            System.out.println("Label удален: " + label);
        } else {
            System.out.println("Label с указанным ID не найден.");
        }
    }

    public void activateLabelController() {
        Scanner scanner = new Scanner(System.in);
        LabelController labelController = new LabelController();
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать новый Label");
                System.out.println("2. Редактировать существующий Label");
                System.out.println("3. Удалить существующий Label");
                System.out.println("4. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();


                switch (choice) {
                    case 1:
                        int id = 0;
                        System.out.print("Введите имя для нового Label: ");
                        String name = scanner.nextLine();
                        createLabel(id, name);
                        break;
                    case 2:
                        System.out.print("Введите ID Label, который вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newName = scanner.nextLine();
                        labelController.editLabel(editId, newName);
                        break;
                    case 3:
                        System.out.print("Введите ID Label, который вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        labelController.deleteLabel(deleteId);
                        break;
                    case 4:
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

