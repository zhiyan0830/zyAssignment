package com.example.myapplication.Remote;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Opening_hours;
import com.example.myapplication.Model.PlaceDetail;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;
//import com.google.android.gms.common.api.Response;
//import com.google.android.gms.common.internal.service.Common;

public class ViewPlace extends AppCompatActivity {
    ImageView photo;
    RatingBar ratingBar;
    TextView place_open_hour,place_address,place_name;
    Button btn_show_map;

    iGoogleAPIService mService;

    PlaceDetail mPlace;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Commons.getGoogleAPIService();

        photo = (ImageView) findViewById(R.id.photo);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        place_address = (TextView) findViewById(R.id.place_address);
        place_name = (TextView) findViewById(R.id.place_name);
        place_open_hour = (TextView) findViewById(R.id.place_open_hour);
        btn_show_map  = (Button) findViewById(R.id.btn_show_map);

        //empty view
        /*place_name.setText("");
        place_address.setText("");
        place_open_hour.setText("");*/

      btn_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
                startActivity(mapIntent);

            }
        });

        //photo
        if(Commons.currentResult.getPhotos() != null && Commons.currentResult.getPhotos().length >0)
        {
            Picasso.with(this)
                    .load(getPhotoOfPlace(Commons.currentResult.getPhotos()[0].getPhoto_reference(),1000))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(photo);
        }

        //rating
        if(Commons.currentResult.getRating()!=null && !TextUtils.isEmpty(Commons.currentResult.getRating())){
            ratingBar.setRating(Float.parseFloat(Commons.currentResult.getRating()));
        }
        else{
            ratingBar.setVisibility(View.GONE);
        }

        //openning hour
       if(Commons.currentResult.getOpening_hours()!= null){
           place_open_hour.setText(Commons.currentResult.getOpening_hours().getOpen_now());
        }
        else{
           place_open_hour.setVisibility(View.GONE);
        }

        //place
       mService.getDetailPlace(getPlaceDetailUrl(Commons.currentResult.getPlace_id()))
               .enqueue(new Callback<PlaceDetail>() {
                   @Override
                   public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
                       mPlace = response.body();

                       place_address.setText(mPlace.getResult().getFormatted_address());
                       place_name.setText(mPlace.getResult().getName());
                   }

                   @Override
                   public void onFailure(Call<PlaceDetail> call, Throwable t) {

                   }
               });

    }

    private String getPlaceDetailUrl(String place_id){
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?place_id="+"ChIJ9YjugWxGzDERD3MerlAbuxQ");
        url.append("&key="+getResources().getString(R.string.browser_key));
        return url.toString();
    }

    private String getPhotoOfPlace(String photo_reference,int maxWidth){
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth=" +maxWidth);
        url.append("&photoreferences="+photo_reference);
        url.append("&key=" +getResources().getString(R.string.browser_key));
        return url.toString();
    }
}
