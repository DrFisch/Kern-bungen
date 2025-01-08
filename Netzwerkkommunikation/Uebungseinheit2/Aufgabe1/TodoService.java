package Uebungseinheit2.Aufgabe1;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoService {
    private static final TodoApi todoApi = RetrofitClient.getInstance().create(TodoApi.class);

    public static void loadItems(Consumer<TodoItem[]> callback) {
        todoApi.getTodos().enqueue(new Callback<TodoItem[]>() {
            @Override
            public void onResponse(Call<TodoItem[]> call, Response<TodoItem[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TodoItem[] items = response.body();
                    callback.accept(items);
                } else {
                    callback.accept(new TodoItem[0]);
                }
            }

            @Override
            public void onFailure(Call<TodoItem[]> call, Throwable t) {
                t.printStackTrace();
                callback.accept(new TodoItem[0]);
            }
        });
    }

    public static void loadItem(String id, BiConsumer<Integer, TodoItem> callback) {
        todoApi.getTodoById(id).enqueue(new Callback<TodoItem>() {
            @Override
            public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                callback.accept(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<TodoItem> call, Throwable t) {
                t.printStackTrace();
                callback.accept(500, null);
            }
        });
    }

    public static void createItem(TodoItem createTodoItem, BiConsumer<Integer, TodoItem> callback) {
        todoApi.createTodo(createTodoItem).enqueue(new Callback<TodoItem>() {
            @Override
            public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                callback.accept(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<TodoItem> call, Throwable t) {
                t.printStackTrace();
                callback.accept(500, null);
            }
        });
    }

    public static void updateItem(String id, TodoItem updateTodoItem, BiConsumer<Integer, TodoItem> callback) {
        todoApi.updateTodo(id, updateTodoItem).enqueue(new Callback<TodoItem>() {
            @Override
            public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                callback.accept(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<TodoItem> call, Throwable t) {
                t.printStackTrace();
                callback.accept(500, null);
            }
        });
    }

    public static void deleteItem(String id, BiConsumer<Integer, TodoItem> callback) {
        todoApi.deleteTodo(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.accept(response.code(), null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                callback.accept(500, null);
            }
        });
    }
}
