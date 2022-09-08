package com.example.pixplanttrackerv1.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Entity.Shelf;
import com.example.pixplanttrackerv1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ShelfAdapter
 * Used to set up the RecyclerViews for showing shelves.
 * Utilized on the ShelfList and PlantMove screens
 * When clicking a shelf in the RecyclerView, it takes the data for the shelf and moves to PlantList with it
 * @see ShelfList
 * @see PlantMove
 */
public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder> {
    class ShelfViewHolder extends RecyclerView.ViewHolder{
        private final TextView shelfItemView;
        private ShelfViewHolder(View itemView){
            super(itemView);
            shelfItemView=itemView.findViewById(R.id.recyclerTextView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position=getAdapterPosition();
                    final Shelf current = mShelves.get(position);
                    Intent intent = new Intent(context, PlantList.class);
                    intent.putExtra("id", current.getShelfID());
                    intent.putExtra("name", current.getShelfName());
                    intent.putExtra("desc", current.getShelfDesc());

                    //Go to next screen (PlantList) when clicking the shelf
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Shelf> mShelves;
    private final Context context;
    private final LayoutInflater mInflater;

    public ShelfAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ShelfAdapter.ShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new ShelfViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelfAdapter.ShelfViewHolder holder, int position){
        if(mShelves != null){
            Shelf current = mShelves.get(position);
            String name = current.getShelfName();
            holder.shelfItemView.setText(name);
        } else {
            holder.shelfItemView.setText("No shelf name");
        }
    }

    public void setShelves(List<Shelf> shelves){
        mShelves = shelves;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mShelves != null){
            return mShelves.size();
        } else return 0;
    }


}
