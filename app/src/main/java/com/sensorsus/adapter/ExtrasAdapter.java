package com.sensorsus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sensorsus.R;
import com.sensorsus.model.Extra;

import java.util.ArrayList;
import java.util.List;

public class ExtrasAdapter extends RecyclerView.Adapter<ExtrasAdapter.ItemHolder>{

    private static String TAG = "ExtrasAdapter";

    private List<Extra> mExtras = new ArrayList<>();
    private Context context;
    private final OnExtraClickListener mListener;

    private String[] extrasNames = {"Recepção, Médico, Laboratório, Instalações, Higiene e Limpeza, Exame de Imagem, Enfermagem, Classificação de Risco, Auxiliar de remoção"};

    public ExtrasAdapter(Context context){
        this.context = context;

        try {
            this.mListener = ((OnExtraClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnPlaceClickListener.");
        }

        String[] extrasNames = {"Recepção", "Médico", "Laboratório", "Instalações",
                "Higiene e Limpeza", "Exame de Imagem", "Enfermagem", "Classificação de Risco", "Auxiliar de remoção"};
        String[] extrasValues = {"2", "4", "2", "9", "4", "3", "7", "8", "9"};

        for (int i = 0; i < 8; i++){
            Extra extra = new Extra((i + 1), extrasNames[i], extrasValues[i] + ",00");
            mExtras.add(extra);
        }
    }

    public void addExtra(int extraId) {
        if(mExtras.size() > 0) {
            for (int i = 0; i < mExtras.size(); i++) {
                if(mExtras.get(i).getExtraId() == extraId) {
                    if (!mExtras.get(i).isAdded()) {
                        mExtras.get(i).addExtra(true);
                        break;
                    } else {
                        mExtras.get(i).addExtra(false);
                        break;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.extra_item, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final Extra extra =  mExtras.get(position);

        holder.mItem = extra;

        holder.mExtraName.setText(extra.getExtraName());
        holder.mExtraValue.setText(extra.getExtraValue());

        if (holder.mItem.isAdded()) {
            holder.icAdd.setImageResource(R.drawable.positivo_polegar);
        } else {
            holder.icAdd.setImageResource(R.drawable.negativo_polegar);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onExtraClickListener(extra);
            }
        });
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mExtraName, mExtraValue;
        public ImageView icAdd;
        public final View mView;
        public Extra mItem;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mExtraName = itemView.findViewById(R.id.extra_name);
            mExtraValue = itemView.findViewById(R.id.extra_value);
            icAdd = itemView.findViewById(R.id.ic_add);
        }

        @Override
        public void onClick(View v) {}
    }

    @Override
    public int getItemCount() {
        return mExtras.size();
    }

    public interface OnExtraClickListener {
        void onExtraClickListener(Extra extra);
    }
}
