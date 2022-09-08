package com.example.pixplanttrackerv1.UI;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * PlantAdapter
 * Used to set up the RecyclerViews for showing plants.
 * Utilized on the HomeScreen and PlantList screens
 * When clicking a plant in the RecyclerView, it takes the data for the plant and moves to PlantDetail with it
 * @see HomeScreen
 * @see PlantList
 * @see PlantDetail
 */
public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    class PlantViewHolder extends RecyclerView.ViewHolder{
        private final TextView plantItemView;
        private PlantViewHolder(View itemView){
            super(itemView);
            plantItemView = itemView.findViewById(R.id.recyclerTextView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Plant current = mPlants.get(position);
                    Intent intent = new Intent(context, PlantDetail.class);
                    intent.putExtra("id", current.getPlantID());
                    intent.putExtra("name", current.getPlantName());
                    intent.putExtra("detail", current.getPlantDetail());
                    intent.putExtra("create", current.getPlantCreateLong());
                    intent.putExtra("days", current.getPlantDaysToWater());
                    intent.putExtra("shelfID", current.getShelfID());

                    context.startActivity(intent);
                }
            });



        }
    }
    private List<Plant> mPlants;
    private final Context context;
    private final LayoutInflater mInflater;

    public PlantAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PlantAdapter.PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new PlantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.PlantViewHolder holder, int position){
        if(mPlants != null){
            Plant current = mPlants.get(position);
            String name = current.getPlantName();
            holder.plantItemView.setText(name);
        } else {
            holder.plantItemView.setText("No Plant name");
        }
    }

    public void setPlants(List<Plant> plants){
        mPlants = plants;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mPlants != null){
            return mPlants.size();
        } else return 0;
    }

}
