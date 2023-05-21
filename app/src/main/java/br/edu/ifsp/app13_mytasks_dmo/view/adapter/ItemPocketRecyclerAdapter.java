package br.edu.ifsp.app13_mytasks_dmo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.ifsp.app13_mytasks_dmo.R;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;
import br.edu.ifsp.app13_mytasks_dmo.utils.mvp.MainMVP;
import br.edu.ifsp.app13_mytasks_dmo.view.RecyclerViewItemClickListener;

public class ItemPocketRecyclerAdapter extends RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder>{
    private Context context;
    private MainMVP.Presenter presenter;
    private List<Task> data;
    private static RecyclerViewItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Task> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = data.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.creationdateTextView.setText(task.getCreationDate());
        if(task.isPriority()){
            holder.priorityImageView.setColorFilter(context.getColor(R.color.purple));
        }else{
            holder.priorityImageView.setColorFilter(context.getColor(R.color.white));
        }
        holder.priorityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starClick(task);
            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteClick(task);
            }
        });
        holder.editImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editClick(task);
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(RecyclerViewItemClickListener listener){
        clickListener = listener;
    }
    private void starClick(Task task){
        presenter.priorityTask(task);
        notifyDataSetChanged();
    }
    private void deleteClick(Task task){
        presenter.deleteTask(task);
        notifyDataSetChanged();
    }
    private void editClick(Task task){
        presenter.editTask(task);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView creationdateTextView;
        public ImageView priorityImageView;
        public ImageView deleteImageView;
        public ImageView editImageView;

        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title_listitem);
            descriptionTextView = itemView.findViewById(R.id.text_description_listitem);
            creationdateTextView = itemView.findViewById(R.id.text_creationdate_listitem);
            priorityImageView = itemView.findViewById(R.id.image_priority_listitem);
            deleteImageView = itemView.findViewById(R.id.image_delete_listitem);
            editImageView = itemView.findViewById(R.id.image_edit_listitem);
        }
        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}