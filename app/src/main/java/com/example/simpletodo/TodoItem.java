package com.example.simpletodo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodoItem {

    Boolean check;
    String item;
    String date;

    public TodoItem(){}

    public TodoItem(String item, Boolean check){
        this.check = check;
        this.item = item;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        DateFormat df = new SimpleDateFormat("EEE d, MMM yyyy");
        return df.format(Calendar.getInstance().getTime());
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static ArrayList<TodoItem> createTodoItem(TodoItem item) {
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();

        for (int i = 1; i <= 5; i++) {
            todoItems.add(new TodoItem(item.getItem(), item.getCheck()));
        }

        return todoItems;
    }
}
