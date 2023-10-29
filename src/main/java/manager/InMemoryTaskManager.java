package manager;

import enums.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager;
    private int idTask = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public int generateId() {
        return ++idTask;
    }

    public Task createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        this.tasks.put(newTaskId, task);
        return tasks.get(newTaskId);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            historyManager.remove(taskId);
            System.out.println("task with id:" + taskId + " is DELETED");
        } else {
            printError("Task");
        }

    }

    @Override
    public Epic createEpic(Epic epic) {
        int newEpicId = generateId();
        epic.setId(newEpicId);
        this.epics.put(newEpicId, epic);
        return epics.get(newEpicId);
    }


    @Override
    public Subtask createSubtask(Subtask subtask) {
        Epic epic = this.epics.get(subtask.getEpicId());
        if (epic != null) {
            int newSubtaskId = generateId();
            subtask.setId(newSubtaskId);
            subTasks.put(newSubtaskId, subtask);
            epic.setSubtaskIds(newSubtaskId);
            updateStatusEpic(epic);
        } else {
            printError("Epic");
        }
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subTasks.containsKey(subtask.getId())) {
            subTasks.put(subtask.getId(), subtask);
            Epic epic = this.epics.get(subtask.getEpicId());
            updateStatusEpic(epic);
        } else {
            printError("Subtask");
        }
    }

    @Override
    public void deleteSubTaskById(int subtaskId) {
        Subtask subtask = subTasks.get(subtaskId);
        if (subtask != null) {
            Epic epic = this.epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove(subtask.getId());
            updateStatusEpic(epic);
            subTasks.remove(subtaskId);
            historyManager.remove(subtaskId);

            System.out.println(subtaskId + " subTask is DELETED");
        } else {
            printError("Subtask");
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subTasks.remove(subtaskId);
            }
            epics.remove(epicId); // без него epic не удаляеться, так как эпик может быть без Subtask (в момент создания юзером)
            System.out.println(epicId + " epic is DELETED");
        } else {
            printError("Epic");
        }
    }

    @Override
    public void deleteAllTasks() {

        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }

        tasks.clear();
        System.out.println("All tasks DELETED");
    }

    @Override
    public void deleteAllEpics() {

        for (Subtask subtask : subTasks.values()) {
            historyManager.remove(subtask.getId());
        }
        subTasks.clear();

        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        epics.clear();

        System.out.println("All epics DELETED");
    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask subtask : subTasks.values()) {
            historyManager.remove(subtask.getId());
        }
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateStatusEpic(epic);
        }

    }

    @Override
    public List<Subtask> getAllSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return Collections.emptyList();
        }
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subTasks.get(id));
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task not found");
        }
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    public void updateStatusEpic(Epic epic) {
        if (this.epics.containsKey(epic.getId())) {
            if (epic.getSubtaskIds().size() == 0) {
                epic.setStatus(Status.NEW);
            } else {
                List<Subtask> subtasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;

                for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                    subtasksNew.add(subTasks.get(epic.getSubtaskIds().get(i)));
                }

                for (Subtask subtask : subtasksNew) {
                    if (subtask.getStatus() == Status.DONE) {
                        countDone++;
                    }
                    if (subtask.getStatus() == Status.NEW) {
                        countNew++;
                    }
                    if (subtask.getStatus() == Status.IN_PROGRESS) {
                        epic.setStatus(Status.IN_PROGRESS);
                        return;
                    }
                }

                if (countDone == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.DONE);
                } else if (countNew == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        } else {
            printError("Epic");
        }
    }

    private void printError(String typeTask) {
        System.out.println(typeTask + " not found");
    }

}
