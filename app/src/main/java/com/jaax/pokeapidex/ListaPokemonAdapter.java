package com.jaax.pokeapidex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaax.pokeapidex.models.Pokemon;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;

    public ListaPokemonAdapter(){
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nombreTxtView.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addListaPokemon(ArrayList<Pokemon> list){
        dataset.addAll(list);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoImgView;
        private TextView nombreTxtView;

        public ViewHolder(View itemview){
            super(itemview);

            fotoImgView = itemview.findViewById(R.id.fotoImagenView);
            nombreTxtView = itemview.findViewById(R.id.nombreTextView);
        }
    }
}
