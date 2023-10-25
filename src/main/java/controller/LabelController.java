package controller;

import model.Label;
import model.PostStatus;
import repository.LabelRepository;
import view.LabelView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LabelController {
    private LabelRepository labelRepository = new LabelRepository("src/main/resources/labels.json");
    private LabelView labelView = new LabelView();

    public void createLabel(int id, String name) {
        Label label = new Label(id, name, PostStatus.ACTIVE);
        labelRepository.save(label);
        System.out.println("Label создан: " + label);
    }

    public void editLabel(int id, String name) throws NullPointerException {
        Label label = labelRepository.getById(id);
        label.setName(name);
        labelRepository.update(label);
        System.out.println("Label отредактирован: " + label);
    }

    public void deleteLabel(int id) throws NullPointerException {
        Label label = labelRepository.getById(id);
        label.setStatus(PostStatus.DELETED);
        labelRepository.update(label);
        System.out.println("Label удален: " + label);
    }

    public void getLabel(int id) throws NullPointerException {
        Label label = labelRepository.getById(id);
        labelView.showLabel(label);
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
                        createLabel(id, name);
                        break;
                    case 2:
                        System.out.print("Введите ID Label, который вы хотите отредактировать: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newName = scanner.nextLine();
                        try {
                            labelController.editLabel(editId, newName);
                        } catch (NullPointerException npe) {
                            System.out.println("Метка с этим ID отсутствует");
                        }
                        break;
                    case 3:
                        System.out.print("Введите ID Label, который вы хотите удалить: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            labelController.deleteLabel(deleteId);
                        } catch (NullPointerException npe) {
                            System.out.println("Метка с таким ID отсутствует");// мы тут пишем об этом в связи с тем,
                            // что по условию задачи метки на самом деле не удаляются, а просто переводятся в статус "DELETED".
                            // Поэтому я и вставил sout в каждый контроллер.
                        }
                        break;
                    case 4:
                        System.out.print("Введите ID Label для просмотра: ");
                        int viewId = scanner.nextInt();
                        try {
                            labelController.getLabel(viewId);
                        } catch (NullPointerException nullPointerException) {
                            System.out.println("Метка отсутствует");
                        }
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

