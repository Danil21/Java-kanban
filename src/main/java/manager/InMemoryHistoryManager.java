package manager;

import tasks.Task;
import utility.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList history = new CustomLinkedList();
    Map<Integer, Node> nodes = new HashMap<>();


    @Override
    public void add(Task task) {
        int taskId = task.getId();

        if (nodes.containsKey(taskId)) {
            history.removeNode(nodes.get(taskId));
        }
        nodes.put(taskId, history.linkLast(task));
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void remove(int id) {
        history.removeNode(nodes.get(id));
        nodes.remove(id);
    }


    private static class CustomLinkedList {
        Node head;
        Node tail;

        private int size = 0;

        public Node linkLast(Task task) {
            final Node oldTail = tail;
            final Node newNode = new Node(oldTail, task, null);

            tail = newNode;

            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.setNext(newNode);
            }

            size++;
            return newNode;
        }

        public List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            Node node = head;

            for (int i = 0; i < size; i++) {
                tasks.add(node.getData());
                node = node.getNext();
            }

            return tasks;
        }

        public void removeNode(Node node) {
            if (node == null) return;

            Node prevNode = node.getPrev();
            Node nextNode = node.getNext();

            if (node.getPrev() != null) {
                prevNode.setNext(nextNode);
            } else {
                head = nextNode;
            }

            if (nextNode != null) {
                nextNode.setPrev(prevNode);
            } else {
                tail = prevNode;
            }

            size--;
        }

    }

}
