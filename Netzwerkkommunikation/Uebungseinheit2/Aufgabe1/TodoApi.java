package Uebungseinheit2.Aufgabe1;

import retrofit2.Call;
import retrofit2.http.*;

public interface TodoApi {
    @GET("todos")
    Call<TodoItem[]> getTodos();

    @GET("todos/{id}")
    Call<TodoItem> getTodoById(@Path("id") String id);

    @POST("todos")
    Call<TodoItem> createTodo(@Body TodoItem todoItem);

    @PUT("todos/{id}")
    Call<TodoItem> updateTodo(@Path("id") String id, @Body TodoItem todoItem);

    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") String id);
}