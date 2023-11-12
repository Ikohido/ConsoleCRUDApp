package controller;

import model.Label;
import model.PostStatus;
import repository.LabelRepository;
import view.LabelView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LabelController {
    private final LabelRepository labelRepository;
    private final LabelView labelView = new LabelView();

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public void updateJsonFile(Label label) throws NullPointerException {
        labelRepository.update(label);
    }

    public void saveInJsonFile(Label label) {
        labelRepository.save(label);
    }

    public Label createLabel(int id, String name) {
        return new Label(id, name, PostStatus.ACTIVE);
    }

    public Label editLabel(int id, String name) throws NullPointerException {
        Label label = labelRepository.getById(id);
        label.setName(name);
        System.out.println("Label отредактирован: " + label);
        return label;
    }

    public Label deleteLabel(Label label) {
        try {
            label.setStatus(PostStatus.DELETED);
            System.out.println("Label удален: " + label);
        } catch (NullPointerException npe) {
            System.out.println("Метка с таким ID отсутствует");
        }
        return label;
    }

    public void getLabel(int id) {
        try {
            Label label = labelRepository.getById(id);
            labelView.showLabel(label);
        } catch (NullPointerException npe) {
            System.out.println("Метка отсутствует");
        }
    }

    public void activateLabelController() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Меню:");
                System.out.println("1. Создать новый Label");
                System.out.println("2. Редактировать существующий Label");
                System.out.println("3. Удалить существующий Label");
                System.out.println("4. Просмотреть Label по ID");
                System.out.println("5. Просмотреть список всех Label");
                System.out.println("0. Выйти");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        int id = 0;
                        System.out.print("Введите имя для нового Label: ");
                        String name = scanner.nextLine();
                        saveInJsonFile(createLabel(id, name));
                        break;
                    case 2:
                        System.out.print("Введите ID Label, который вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newName = scanner.nextLine();
                        try {
                            updateJsonFile(editLabel(editId, newName));
                        } catch (NullPointerException npe) {
                            System.out.println("Метка с указанным ID отсутствует. Будет создана новая метка с указанным именем");
                            saveInJsonFile(createLabel(editId, newName));
                        }
                        break;
                    case 3:
                        System.out.print("Введите ID Label, который вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        Label label = labelRepository.getById(deleteId);
                        updateJsonFile(deleteLabel(label));
                        break;
                    case 4:
                        System.out.print("Введите ID Label для просмотра: ");
                        int viewId = scanner.nextInt();
                        getLabel(viewId);
                    case 5:
                        try {
                            labelView.showAllLabels(labelRepository.getAll());
                        } catch (NullPointerException nullPointerException) {
                            System.out.println("Метки отсутствуют");
                        }
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

