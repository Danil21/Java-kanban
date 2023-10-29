import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import static enums.Status.NEW;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefault());

        System.out.println("!-------------------------- Epic 1---------------------------!\n");
        System.out.println("--- Create EPIC with 4 subTask ---\n");
        Epic epicOne = taskManager.createEpic(new Epic("Описание-1", "Epic-1", NEW));
        System.out.println(epicOne);

        System.out.println("--- Create subtask ---");
        Subtask subtaskOne = taskManager.createSubtask(new Subtask("Описание-1",
                "Subtask-1", NEW, epicOne.getId()));

        taskManager.createSubtask(new Subtask("Описание-2", "Subtask-2", NEW, epicOne.getId()));
        taskManager.createSubtask(new Subtask("Описание-3", "Subtask-3", NEW, epicOne.getId()));
        taskManager.createSubtask(new Subtask("Описание-4", "Subtask-4", NEW, epicOne.getId()));

        System.out.println("\n!----------------------- History -------------------------!\n");
        System.out.println(taskManager.getHistory());
        System.out.println("\n!----------------------- END History -------------------------!\n");

        System.out.println("--- Get all subtasks by epic id ---");
        System.out.println(taskManager.getAllSubtasksByEpicId(epicOne.getId()));

        System.out.println("--- Get subtask by id ---");
        Subtask subtask = taskManager.getSubtaskById(subtaskOne.getId());
        System.out.println(taskManager.getSubtaskById(subtask.getId()));


        System.out.println("--- Update subtask ---");
        taskManager.updateSubtask(subtask);

        System.out.println("--- Delete subtask by id ---");
        taskManager.deleteSubTaskById(subtask.getId());

        System.out.println("--- Get EPIC by id ---");
        System.out.println(taskManager.getEpicById(epicOne.getId()));

        System.out.println("--- Delete all subtasks ---");
        taskManager.deleteAllSubtasks();

        System.out.println("--- Get all subtasks by epic id ---");
        System.out.println(taskManager.getAllSubtasksByEpicId(epicOne.getId()));

        System.out.println("--- Get all Epic---");
        System.out.println(taskManager.getAllEpics());


        // - удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        System.out.println("\n!----------------------- History -------------------------!\n");
        System.out.println(taskManager.getHistory());
        System.out.println("\n!----------------------- END History -------------------------!\n");


        System.out.println("!---------------------------- Epic 2 ------------------------------!\n");
        System.out.println("--- Create EPIC without subTask ---");

        Epic epicTwo = taskManager.createEpic(new Epic("Описание-2", "Epic-2", NEW));
        System.out.println(epicTwo);

        System.out.println("--- Get EPIC by id ---");
        System.out.println(taskManager.getEpicById(epicTwo.getId()));


        System.out.println("\n!----------------------- History -------------------------!\n");
        System.out.println(taskManager.getHistory());
        System.out.println("\n!----------------------- END History -------------------------!\n");

        System.out.println("--- Delete epic by id ---");
        taskManager.deleteEpicById(epicTwo.getId());

        System.out.println("--- Get all Epic---");
        System.out.println(taskManager.getAllEpics());

        System.out.println("--- Delete all Epic---");
        taskManager.deleteAllEpics();

        System.out.println("\n!----------------------- Task test -------------------------!\n");
        System.out.println("--- Create task ---");

        taskManager.createTask(new Task("Описание-1", "Task-1", NEW));
        Task taskTwo = taskManager.createTask(new Task("Описание-2", "Task-2", NEW)); // используеться
        System.out.println(taskTwo);

        taskManager.createTask(new Task("Описание-3", "Task-3", NEW));

        System.out.println("--- Get all tasks ---");
        System.out.println(taskManager.getAllTasks());

        System.out.println("--- Get task by id ---");
        System.out.println(taskManager.getTaskById(taskTwo.getId()));

        System.out.println("\n!----------------------- History -------------------------!\n");
        System.out.println(taskManager.getHistory());
        System.out.println("\n!----------------------- END History -------------------------!\n");

        System.out.println("--- Update task ---");
        taskManager.updateTask(taskTwo);

        System.out.println("--- Delete task by id ---");
        taskManager.deleteTaskById(taskTwo.getId());

        // - удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        System.out.println("\n!----------------------- History -------------------------!\n");
        System.out.println(taskManager.getHistory());
        System.out.println("\n!----------------------- END History -------------------------!\n");


        System.out.println("--- Get all tasks ---");
        System.out.println(taskManager.getAllTasks());

        System.out.println("--- Delete all tasks ---");
        taskManager.deleteAllTasks();

        System.out.println("--- Get all tasks ---");
        System.out.println(taskManager.getAllTasks());


    }
}

