package com.sensorsus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensorsus.R;
import com.sensorsus.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{

    private List<Item> items = new ArrayList<>();
    private Context context;
    private final onClickListener mListener;

    public ItemAdapter(Context context){
        this.context = context;

        try {
            this.mListener = ((onClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnPlaceClickListener.");
        }

        String[] itemNames = {"H. Barão de Lucena", "H. Correia Picanço",
        "H. Restauração", "H. Geral da Mirueira", "H. Getúlio Vargas", "H. Geral de Areias", "H. Otávio de Freitas", "H. Camaragibe", "H. Jaboatão Prazeres", "H. Pelópidas Silveira"};

        String[] itemPrices = {"...", "...", "...",
                "...", "...", "...", "...", "...", "...", "..."};                

        for (int i = 0; i < 10; i++){
            Item item = new Item(itemNames[i],  itemPrices[i]);
            items.add(item);
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final Item item =  items.get(position);

        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClickListener(item);
            }
        });
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName, itemPrice;
        public View mView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
        }

        @Override
        public void onClick(View v) {}
    }

    public interface onClickListener {
        void onClickListener(Item item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
