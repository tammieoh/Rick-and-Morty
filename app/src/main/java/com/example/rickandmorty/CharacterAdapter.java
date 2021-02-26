package com.example.rickandmorty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View characterView = inflater.inflate(R.layout.item_characters, parent, false);
        CharacterAdapter.ViewHolder viewHolder = new CharacterAdapter.ViewHolder(characterView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.textView_character.setText(character.getName());
        Picasso.get().load(character.getImage_url()).into(holder.imageView_character);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_character;
        ImageView imageView_character;



        public ViewHolder(View itemView) {
            super(itemView);
            textView_character = itemView.findViewById(R.id.textView_characterName);
            imageView_character = itemView.findViewById(R.id.imageView_character);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
