package com.example.mobilki_3.horoscope;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilki_3.R;

import java.util.ArrayList;
import java.util.List;

public class ZodiacSignAdapter extends RecyclerView.Adapter<ZodiacSignAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private List<ZodiacSign> zodiacSignList;

    public ZodiacSignAdapter(Context context, List<ZodiacSign> list) {
        this.zodiacSignList = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setZodiacSignList(ArrayList<ZodiacSign> list) {
        this.zodiacSignList = new ArrayList<>();
        this.zodiacSignList = list;
        notifyDataSetChanged();
    }

    @Override
    public ZodiacSignAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.zodiac_sign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZodiacSignAdapter.ViewHolder holder, int position) {
        holder.bind(zodiacSignList.get(position));
    }

    @Override
    public int getItemCount() {
        return zodiacSignList.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageViewSign;
        final TextView zodiacSignName, horoscopeText;

        ViewHolder(View view){
            super(view);
            imageViewSign = view.findViewById(R.id.imageViewZodiacSign);
            zodiacSignName = view.findViewById(R.id.textViewZodiacSign);
            horoscopeText = view.findViewById(R.id.textViewHoroscope);
        }

        void bind(final ZodiacSign zodiacSign){
            imageViewSign.setImageBitmap(zodiacSign.getImage());
            zodiacSignName.setText(zodiacSign.getName());
            horoscopeText.setText(zodiacSign.getHoroscope());
        }
    }

}
