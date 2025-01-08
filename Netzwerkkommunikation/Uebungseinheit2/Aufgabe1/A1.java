package Uebungseinheit2.Aufgabe1;

import java.util.Arrays;

public class A1 {
    public static void main(String[] args) {
        // Load items
        TodoService.loadItems(items -> {
            System.out.println("Loaded items:");
            Arrays.stream(items).limit(10).forEach(item -> 
                System.out.println(item.id + ": " + item.title + " (Completed: " + item.completed + ")"));
        });

        // Create item
        TodoItem newTodo = new TodoItem(null, "New Todo", false);
        TodoService.createItem(newTodo, (code, item) -> 
            System.out.println("Created item: " + (item != null ? item.title : "Error")));

        // Load item by ID
        TodoService.loadItem("1", (code, item) -> 
            System.out.println("Loaded item: " + (item != null ? item.title : "Error")));

        // Update item
        TodoItem updateTodo = new TodoItem(null, "Updated Todo", true);
        TodoService.updateItem("1", updateTodo, (code, item) -> 
            System.out.println("Updated item: " + (item != null ? item.title : "Error")));

        // Delete item
        TodoService.deleteItem("1", (code, item) -> 
            System.out.println("Deleted item with code: " + code));
    }
}
