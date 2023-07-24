package com.sensorsus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsus.R;
import com.sensorsus.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder>{

    private List<Product> products = new ArrayList<>();
    private Context context;
    private final OnProductClickListener mListener;

    public ProductAdapter(Context context){
        this.context = context;

        try {
            this.mListener = ((OnProductClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnPlaceClickListener.");
        }

        String[] favoriteFoodNames = {"H. Barão de Lucena", "H. Correia Picanço",
        "H. Restauração", "H. Geral da Mirueira", "H. Getúlio Vargas", "H. Geral de Areias", "H. Otávio de Freitas", "H. Camaragibe", "H. Jaboatão Prazeres", "H. Pelópidas Silveira"};

        String[] favoriteFoodPlaces = {"H. Barão de Lucena", "H. Correia Picanço",
        "H. Restauração", "H. Geral da Mirueira", "H. Getúlio Vargas", "H. Geral de Areias", "H. Otávio de Freitas", "H. Camaragibe", "H. Jaboatão Prazeres", "H. Pelópidas Silveira"};

        String[] favoriteFoodPrices = {"...", "...", "...",
                "...", "...", "...", "...", "...", "...", "..."};

        for (int i = 0; i < 10; i++){
            Product prod = new Product(favoriteFoodNames[i], "Description " + (i + 1),
                    favoriteFoodPlaces[i], favoriteFoodPrices[i]);
            products.add(prod);
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final Product prod =  products.get(position);

        holder.mProductName.setText(prod.getmProductName());
        holder.mProductDescription.setText(prod.getmProductRestaurant());
        holder.mProductValue.setText(prod.getmProductValue());

        holder.mNoFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener  != null){
                    mListener.OnProductFavoriteClick(prod);
                    holder.mNoFavorite.setVisibility(View.GONE);
                    holder.mFavorite.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener  != null){
                    mListener.OnProductNoFavoriteClick(prod);
                    holder.mNoFavorite.setVisibility(View.VISIBLE);
                    holder.mFavorite.setVisibility(View.GONE);
                }
            }
        });
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mProductName, mProductDescription, mProductValue, mProductRestaurant;
        public ImageView mNoFavorite, mFavorite;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductDescription = itemView.findViewById(R.id.product_description);
            mProductValue = itemView.findViewById(R.id.product_price);
            mFavorite = itemView.findViewById(R.id.favorite);
            mNoFavorite = itemView.findViewById(R.id.no_favorite);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked Item", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnProductClickListener {
        void OnProductNoFavoriteClick(Product products);
        void OnProductFavoriteClick(Product products);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
