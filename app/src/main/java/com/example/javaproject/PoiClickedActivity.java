package com.example.javaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PoiClickedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_clicked);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Integer id = getIntent().getIntExtra("Id",1);


        TextView titleText = findViewById(R.id.title);
        TextView routeText = findViewById(R.id.routeView);
        TextView tagText = findViewById(R.id.tagView);
        TextView descriptionText = findViewById(R.id.descriptionView);
        ImageView image = findViewById(R.id.image);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setPadding(0, 25, 0, 25);
        image.setImageResource(R.drawable.caminologo);

        GetPOI getter =new GetPOI(this);
        class Lpoi extends ListPOI<POI>{
            @Override
            public void add(POI o){
                Picasso.get().load(o.getPhotoLink()).placeholder(R.drawable.caminologo).error(R.drawable.caminologo).into(image);
                titleText.setText(o.getTitle());
                descriptionText.setText((o.getSnippet()));
            }
        }
        Lpoi lpoi = new Lpoi();
        getter.getPOIbyId(id,lpoi);


        //descriptionText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris maximus turpis velit, ullamcorper bibendum magna lobortis non. Ut commodo ante sit amet risus venenatis tincidunt. Vestibulum in justo varius, mattis arcu ac, pretium lacus. Morbi tincidunt in odio eu tempor. Maecenas eget nisi non nisi viverra molestie vitae ut velit. Duis dui turpis, posuere id libero non, commodo commodo elit. In ullamcorper in nisi ut malesuada. Etiam nec fringilla nisi. Proin vel commodo turpis, quis euismod erat. Aliquam lobortis nibh nec commodo rhoncus. Nunc feugiat ipsum vitae diam maximus, at fringilla lorem luctus. Nulla sed sem laoreet, euismod elit ac, elementum urna. Aenean ac est at lorem vestibulum volutpat ac non ex. Donec id diam nisl. Phasellus risus diam, cursus sit amet pretium quis, dictum ac lectus. Proin elit ante, rhoncus lacinia nulla ut, gravida fermentum orci. Mauris malesuada malesuada ipsum et commodo. Nam leo tortor, tincidunt imperdiet faucibus id, pretium ut magna. Mauris mollis sem neque, et iaculis tellus fermentum at. Pellentesque eu nibh vel arcu tincidunt consectetur. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae Integer accumsan eu justo a eleifend. Ut euismod libero at pellentesque gravida. Ut tempor ultrices est id sodales. Praesent eu purus massa. In nec ex lacus. Fusce ac pretium risus. Etiam sagittis erat vel elit commodo, at interdum lorem tincidunt. In consectetur fermentum est, id semper neque pharetra ac. Duis pulvinar enim dolor, ut congue nisl ornare non. Cras ac neque quis velit accumsan mattis. Maecenas viverra mauris ac ex ultrices malesuada. Aliquam mattis ac nunc vitae vestibulum. Curabitur vehicula eros ut urna elementum, vel mattis leo consectetur. Integer egestas velit ut neque imperdiet dictum. Donec ac ante ligula. Nullam lacinia risus eu lacus suscipit porttitor eget vitae nulla. In vel lorem consectetur magna condimentum tempus. Etiam vitae purus tortor. Sed erat felis, egestas nec tristique non, ultricies nec nisl. Sed malesuada, purus et suscipit vestibulum, quam justo tincidunt leo, ac ultrices tellus arcu at ligula. Donec ac massa quis nunc aliquet viverra. Vivamus facilisis arcu sed mauris viverra, eget sollicitudin orci molestie. Integer sit amet justo id ante dapibus euismod. Proin in purus commodo, mattis diam non, maximus nisi. Phasellus in purus non massa dignissim pellentesque.");
        //image.setImageResource(Integer.parseInt(imageResource));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}




