package com.example.simpletodo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

public class ItemsAdapter  extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener{
        void onItemClicked(int position);
    }
    public  interface OnLongClickListener {
        void onItemLongClicked(int position);
    }
    public interface OnCheckBoxClickListener{
        void onCheckBoxCLicked(int position);
    }

    List<TodoItem> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    OnCheckBoxClickListener checkBoxClickListener;

    public void setFilteredList(List<TodoItem> filteredList){
        this.items = filteredList;
        notifyDataSetChanged();
    }


    public ItemsAdapter(List<TodoItem> items, OnLongClickListener longClickListener, OnClickListener onClickListener,
                        OnCheckBoxClickListener checkBoxClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = onClickListener;
        this.checkBoxClickListener = checkBoxClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //User layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem item = items.get(position);
        holder.bind(item);

        //holder.date.setText(items.get(position).getDate());
        //holder.items.setText(items.get(position).getItems());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;
        CheckBox checkBox;
        TextView tvDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvTodo);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvDate = itemView.findViewById(R.id.tvDate);

        }

        public void bind(TodoItem item) {
            tvItem.setText(item.getItem());
            tvItem.setTextColor(Color.parseColor("#FFFFFF"));
            tvDate.setText(item.getDate());
            checkBox.setChecked(item.getCheck());

            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxClickListener.onCheckBoxCLicked(getAdapterPosition());
                }
            });
        }
    }
}
