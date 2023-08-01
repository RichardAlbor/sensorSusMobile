package com.sensorsus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sensorsus.R;
import com.sensorsus.Utils.NetworkUtils;
import com.sensorsus.interfaces.ApiService;
import com.sensorsus.model.Content;
import com.sensorsus.model.Place;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ItemHolder> {

    final private List<Place> mPlaces = new ArrayList<>();
    final Context context;
    private final OnPlaceClickListener mListener;
    List<Content> estabelecimentos;

    public PlaceAdapter(Context context) {
        this.context = context;
        this.estabelecimentos = new ArrayList<>();

        try {
            this.mListener = ((OnPlaceClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnPlaceClickListener.");
        }


        Retrofit retrofitClient = NetworkUtils.getRetrofitInstance("https://b68d-200-133-17-1.ngrok.io/");

        ApiService apiService = retrofitClient.create(ApiService.class);


        final PlaceAdapter minhaClassePlaceAdpter= this;

        apiService.getData().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {



                    if (response.isSuccessful() && response.body() != null) {
                        JsonObject jsonObject = response.body();

                        if (jsonObject.has("content")) {
                            JsonArray jsonArray = jsonObject.getAsJsonArray("content");

                            Gson gson = new Gson();
                            Type tipoListaContent = new TypeToken<Array>() {}.getType();

                            minhaClassePlaceAdpter.estabelecimentos = gson.fromJson(jsonArray, tipoListaContent);


                            // Agora você tem a lista de objetos Content pronta para ser utilizada
                            for (Content content : estabelecimentos) {
                                //Pegar os 10 primeiros hospitais aqui
                                Place place = new Place( content.id,content.nome, content.endereco.logradouro, String. valueOf(content.score) , content.endereco.cep );
                                minhaClassePlaceAdpter.mPlaces.add(place);
                            }
                        }
                    }

                }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ErroComunicacao", "ErroComunicacao");
            }
        });

        String[] placeNames = {"H. Barão de Lucena", "H. Correia Picanço",
                "H. Restauração", "H. Geral da Mirueira", "H. Getúlio Vargas", "H. Geral de Areias", "H. Otávio de Freitas", "H. Camaragibe", "H. Jaboatão Prazeres", "H. Pelópidas Silveira"};

        String[] placeDelivery = {"Detalhar", "Detalhar",
                "Detalhar", "Detalhar", "Detalhar", "Detalhar", "Detalhar", "Detalhar", "Detalhar", "Detalhar"};

        for (int i = 0; i < 10; i++) {
            Place place = new Place((i + 1), placeNames[i], "Av Caxanga, " + (i + 1),
                    "4." + (i + 1), placeDelivery[i]);
            mPlaces.add(place);
        }
    }

    public void setFavorite(int placeId) {
        if (mPlaces.size() > 0) {
            for (int i = 0; i < mPlaces.size(); i++) {
                if (mPlaces.get(i).getPlaceId() == placeId) {
                    if (!mPlaces.get(i).isFavorite()) {
                        mPlaces.get(i).setFavorite(true);
                        break;
                    } else {
                        mPlaces.get(i).setFavorite(false);
                        break;
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_place_card, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final Place place = mPlaces.get(position);

        holder.mItem = place;

        holder.placeName.setText(place.getPlaceName());
        holder.placeLocation.setText(place.getLocation());
        holder.placeRating.setText(place.getRating());
        holder.placeDelivery.setText(place.getDelivery());

        if (holder.mItem.isFavorite()) {
            holder.icFavorite.setImageResource(R.drawable.star);
        } else {
            holder.icFavorite.setImageResource(R.drawable.star2);
        }

        holder.lnlFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onPlaceFavoriteClick(place);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onPlaceClickListener(place);
            }
        });
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView placeName, placeLocation, placeRating, placeDelivery;
        public RelativeLayout lnlFavorite;
        public ImageView icFavorite;
        public final View mView;
        public Place mItem;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            placeName = itemView.findViewById(R.id.place_name);
            placeLocation = itemView.findViewById(R.id.place_location);
            placeRating = itemView.findViewById(R.id.place_rating);
            placeDelivery = itemView.findViewById(R.id.place_delivery);
            lnlFavorite = itemView.findViewById(R.id.lnl_favorite);
            icFavorite = itemView.findViewById(R.id.ic_favorite);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked Item", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnPlaceClickListener {
        void onPlaceClickListener(Place place);

        void onPlaceFavoriteClick(Place place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}
