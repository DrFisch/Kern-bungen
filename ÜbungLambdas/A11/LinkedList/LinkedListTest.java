package A11.LinkedList;

import java.util.Iterator;

class LinkedList<T> implements Iterable<T> {
    private Node<T> head; // Anfang der Liste
    private int size;     // Anzahl der Elemente in der Liste

    // Node-Klasse (innere Klasse)
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // Fügt ein Element am Ende der Liste hinzu
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) { // Wenn die Liste leer ist
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) { // Traverse bis zum Ende
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Fügt ein Element am Anfang der Liste hinzu
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Entfernt das erste Element der Liste
    public T removeFirst() {
        if (head == null) throw new IllegalStateException("Liste ist leer");
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    // Entfernt das letzte Element der Liste
    public T removeLast() {
        if (head == null) throw new IllegalStateException("Liste ist leer");
        if (head.next == null) { // Nur ein Element in der Liste
            T data = head.data;
            head = null;
            size--;
            return data;
        }
        Node<T> current = head;
        while (current.next.next != null) { // Traverse bis zum vorletzten Element
            current = current.next;
        }
        T data = current.next.data;
        current.next = null;
        size--;
        return data;
    }

    // Gibt die Größe der Liste zurück
    public int size() {
        return size;
    }

    // Gibt alle Elemente der Liste aus
    public void printList() {
        for (T element : this) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // Implementierung des `Iterable`-Interfaces
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}

public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        System.out.println("Liste vorwärts:");
        for (Integer value : list) {
            System.out.print(value + " "); // Nutzung des Iterable-Interfaces
        }
        System.out.println();

        list.addFirst(5);
        System.out.println("Nach addFirst(5):");
        list.printList(); // Nutzung der printList-Methode mit Iterable

        list.removeLast();
        System.out.println("Nach removeLast():");
        list.printList();

        list.removeFirst();
        System.out.println("Nach removeFirst():");
        list.printList();
    }
}
