package manager;

public class Managers {

    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefault() {
        return new InMemoryHistoryManager();
    }
}
