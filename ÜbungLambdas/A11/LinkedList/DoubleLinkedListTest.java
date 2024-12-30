package A11.LinkedList;

class DoubleLinkedList<T> {
    private Node<T> head; // Anfang der Liste
    private Node<T> tail; // Ende der Liste
    private int size;     // Anzahl der Elemente in der Liste

    // Node-Klasse (innere Klasse)
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // Fügt ein Element am Ende der Liste hinzu
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = tail = newNode; // Wenn die Liste leer ist
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Fügt ein Element am Anfang der Liste hinzu
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode; // Wenn die Liste leer ist
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Entfernt ein Element am Ende der Liste
    public T removeLast() {
        if (tail == null) throw new IllegalStateException("Liste ist leer");
        T data = tail.data;
        if (tail.prev != null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            head = tail = null; // Liste wird leer
        }
        size--;
        return data;
    }

    // Entfernt ein Element am Anfang der Liste
    public T removeFirst() {
        if (head == null) throw new IllegalStateException("Liste ist leer");
        T data = head.data;
        if (head.next != null) {
            head = head.next;
            head.prev = null;
        } else {
            head = tail = null; // Liste wird leer
        }
        size--;
        return data;
    }

    // Gibt die Größe der Liste zurück
    public int size() {
        return size;
    }

    // Gibt alle Elemente der Liste aus
    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Gibt alle Elemente in umgekehrter Reihenfolge aus
    public void printReverse() {
        Node<T> current = tail;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
        System.out.println();
    }
}

public class DoubleLinkedListTest {
    public static void main(String[] args) {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        System.out.println("Liste vorwärts:");
        list.printList(); // Ausgabe: 10 20 30

        System.out.println("Liste rückwärts:");
        list.printReverse(); // Ausgabe: 30 20 10

        list.addFirst(5);
        System.out.println("Nach addFirst(5):");
        list.printList(); // Ausgabe: 5 10 20 30

        list.removeLast();
        System.out.println("Nach removeLast():");
        list.printList(); // Ausgabe: 5 10 20

        list.removeFirst();
        System.out.println("Nach removeFirst():");
        list.printList(); // Ausgabe: 10 20
    }
}
