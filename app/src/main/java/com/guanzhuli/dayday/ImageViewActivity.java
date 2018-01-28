package com.guanzhuli.dayday;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.guanzhuli.dayday.customized.ImageGalleryAdapter;
import com.guanzhuli.dayday.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ImageViewActivity extends AppCompatActivity implements ImageGalleryAdapter.OnImageClickListener {
    RecyclerView recyclerView;
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        scanImage();
        bindView();
    }

    private void bindView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ViewImage");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.image_explore_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constant.COLUMN_NUMBER));
        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, images);
        adapter.setOnImageClickListener(this);
    }

    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(ImageViewActivity.this, NewDayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_IMAGES, images.get(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PICK_IMAGE);
    }

    private List<String> scanImage() {
        images = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage;
        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = this.getContentResolver().query(uri, projection, null,null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            images.add(absolutePathOfImage);
        }
        return  images;
    }
}
