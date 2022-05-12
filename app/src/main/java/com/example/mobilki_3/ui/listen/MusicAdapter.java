package com.example.mobilki_3.ui.listen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilki_3.R;
import java.util.ArrayList;
import java.util.List;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<Music> musicList;
    private int selectedPosition = -1;


    public MusicAdapter(Context context, List<Music> list) {
        this.musicList = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMusicList(ArrayList<Music> list) {
        this.musicList = new ArrayList<>();
        this.musicList = list;
        notifyDataSetChanged();
    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.music, parent, false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.ViewHolder holder, int position) {
        holder.bind(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView author, composition;
        final ConstraintLayout musicLayout;

        ViewHolder(View view){
            super(view);
            author = view.findViewById(R.id.textViewMusicAuthor);
            composition = view.findViewById(R.id.textViewMusicComposition);
            musicLayout = view.findViewById(R.id.musicLayout);
        }

        void bind(final Music music){
            if (selectedPosition == -1){
                musicLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorMenuBackground));
            } else if (selectedPosition == getAdapterPosition()){
                musicLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
            } else {
                musicLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorMenuBackground));
            }

            author.setText(music.getAuthor());
            composition.setText(music.getComposition());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    musicLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                    if (selectedPosition != getAdapterPosition()){
                        notifyItemChanged(selectedPosition);
                        selectedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public Music getSelected(){
        if (selectedPosition != -1){
            return musicList.get(selectedPosition);
        }
        return null;
    }
}
