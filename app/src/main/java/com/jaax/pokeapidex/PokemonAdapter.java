package com.jaax.pokeapidex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaax.pokeapidex.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private final List<Pokemon> listaPokemon;
    public PokemonAdapter(){
        listaPokemon = new ArrayList<Pokemon>(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon poke = listaPokemon.get(position);
        holder.nombreTxtView.setText(poke.getName());
        holder.numberTextView.setText("#"+poke.getNumber());

        Glide.with(holder.itemView.getContext())
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+poke.getNumber()+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImgView);
    }

    @Override
    public int getItemCount() {
        return listaPokemon.size();
    }

    public void addListaPokemon(List<Pokemon> list){
        listaPokemon.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView fotoImgView;
        private final TextView nombreTxtView;
        private final TextView numberTextView;

        public ViewHolder(View itemview) {
            super(itemview);
            fotoImgView = itemview.findViewById(R.id.imgPokemon);
            nombreTxtView = itemview.findViewById(R.id.nombrePokemon);
            numberTextView = itemview.findViewById(R.id.numPokemon);
        }
    }

    /*@Override
    public Filter getFilter() {
        return pokemonFiltrado;
    }

    private Filter pokemonFiltrado = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pokemon> filter = new ArrayList<>(0);

            if( constraint == null || constraint.length() == 0){
                filter.addAll(dataset);
            } else {
                String txt_filtrado = constraint.toString().toLowerCase().trim();
                for( Pokemon poke: dataset ){
                    if( poke.getName().toLowerCase().contains(txt_filtrado) ){
                        filter.add(poke);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filter;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datasetSimple.clear();
            datasetSimple.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };*/
}
