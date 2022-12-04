package com.unitapplications.postmakerwithunsplashapi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LAST_LINK = "last_link";
    public static final String LAST_TEXT = "last_text";
    public static final String LAST_WATERMARK = "last_watermark";
    public static final String LAST_FONT = "last_font";
    public static final String LAST_TEXT_SIZE = "last_text_size";
    public static final String LAST_TEXT_COLOR = "last_text_color";
    public static final String LAST_TEXT_BG_COLOR = "last_text_bg_color";
    public static final String LAST_CHECK = "last_check";
    public static final String LAST_WATERMARK_CHECK = "last_watermark_check";
    public static final String LAST_BG_CHECK = "last_bg_check";
    TextView t1, t2, t3, t4, t5, t6, t7;
    ContentResolver resolver;
    SharedPreferences sharedPreferences;
    String getLastLink, getLastText, getLastTextFont,getLastWatermark;
    ImageView imageView, text_color;
    Boolean getLastCheck,getLastWatermarkCheck,getLastBgCheck;
    ImageView browse, save, change_text, change_name,
            online, rotate, tMinus, bold, cancel, saved_iv,
            text_centre,text_bg,color_text_bg;
    EditText et_watermark, et;
    TextView poetry_tv, watermark, t_edit, i_edit,watermark_iv;
    int k, getTextSize,getLastTextColor,getLastBgColor;
    RelativeLayout layout;
    LinearLayout doneLayout, t_editL, i_editL,lW;
    String poetry_txt, name_txt;
    Uri imageUri;
    Button share;
    ColorDrawable cd;
    Uri selectedImg;
    CheckBox checkBox;
    float textSize;
    ActivityResultLauncher<String[]> resultLauncher;
    ActivityResultLauncher<Intent> GetImage;
    SeekBar seekBar, opacitySeekbar;
    String pic_url, name;
    private ViewGroup mainLayout;
    private boolean isReadPermissionGranted = false;
    private boolean isWritePermissionGranted = false;
    private int xDelta;
    private int yDelta;
    private int default_color;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t4 = findViewById(R.id.textView4);
        t5 = findViewById(R.id.textView5);
        t6 = findViewById(R.id.textView6);
        t7 = findViewById(R.id.textView7);

        t1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/glimer.otf"));
        t2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/involo.ttf"));
        t3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Inter_reg.ttf"));
        t4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/inter.ttf"));
        t5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/mukta.ttf"));
        t6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aref.ttf"));
        t7.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"));

        //making onclick for seconds buttons

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);

        text_bg = findViewById(R.id.text_bg);
        text_color = findViewById(R.id.text_color);
        text_centre = findViewById(R.id.text_centre);
        layout = findViewById(R.id.layout);
        lW = findViewById(R.id.layout_watermark);
        t_editL = findViewById(R.id.text_edit_layout);
        i_editL = findViewById(R.id.image_edit_layout);
        t_edit = findViewById(R.id.text_edit);
        i_edit = findViewById(R.id.image_edit);
        color_text_bg = findViewById(R.id.colo_text_bg);


        doneLayout = findViewById(R.id.doneLayout);
        imageView = findViewById(R.id.imageView);
        browse = findViewById(R.id.gallery_open);
        save = findViewById(R.id.save_img);
        poetry_tv = findViewById(R.id.poetry_tv);
        online = findViewById(R.id.all_template);
        change_text = findViewById(R.id.change_text);
        change_name = findViewById(R.id.change_name);
        et = findViewById(R.id.et);
        et_watermark = findViewById(R.id.et_watermark);
        bold = findViewById(R.id.bold);
        rotate = findViewById(R.id.rotate_iv);
        seekBar = findViewById(R.id.seekBar);
        opacitySeekbar = findViewById(R.id.opacitySeekBar);
        imageView = findViewById(R.id.imageView);
        watermark = findViewById(R.id.watermark_tv);
        watermark_iv = findViewById(R.id.watermark_iv);

        mainLayout = layout;
        cd = new ColorDrawable(0xFFFF6666);
        cancel = findViewById(R.id.cancel);
        share = findViewById(R.id.share_bt);
        saved_iv = findViewById(R.id.saved_iv);
        checkBox = findViewById(R.id.setDefault);
        resolver = getContentResolver();
        default_color = 0;
        k = 1;
        textSize = 12.0f;
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


        loadAll();

        Boolean getCheck = loadCheck();
        Boolean lastWCheck = loadWCheck();
        Boolean lastBgCheck = loadBgCheck();
        checkBox.setChecked(getCheck);
        String getLastFont = getLastTextFont;
        String uri_string = getLastLink;
        int LastTextColor = getLastTextColor;
        int LastBgColor = getLastBgColor;

        changeFontSaved(getLastFont);

        if (checkBox.isChecked()) {
            if (uri_string != null) {
                Bitmap b = decodeBase64(uri_string);
                imageView.setImageBitmap(b);
            }
        }
        if (lastWCheck){
            showWatermark();
        }
        if (lastBgCheck){
            poetry_tv.setBackgroundColor(LastBgColor);
        }

        if (LastTextColor!=0){
            poetry_tv.setTextColor(LastTextColor);
        }

        watermark.setText(getLastWatermark);
        poetry_tv.setTextSize(getTextSize);
        poetry_tv.setText(getLastText);

        poetry_tv.setOnTouchListener(onTouchListener());

        try {
            // in this try method i get strings of note and title
            // from write main activty to show on read activity
            Intent intent1 = getIntent();
            pic_url = intent1.getStringExtra("url_key");
            name = intent1.getStringExtra("name_key");
            if (pic_url != null) {
                Picasso.get()
                        .load(pic_url)
                        .placeholder(R.drawable.sample_image_ic)
                        .into(imageView);
            }
        } catch (Exception e) {
        }

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        try {
                            if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE != null)) {
                                isReadPermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                            }
                            if (result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE != null)) {
                                isWritePermissionGranted = result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        } catch (Exception ignored) {
                            //Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // this code pasess the name and bitmap to saveImage function that save image
        GetImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();


                            Bitmap bitmap = (Bitmap) bundle.get("data");

                            if (isWritePermissionGranted) {
                                if (SaveImage("MY_IMAge" + UUID.randomUUID(), bitmap)) ;
                                Toast.makeText(MainActivity.this, "Saved Image Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No Saved Image Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        save.setOnClickListener(view -> {
            //here we are calling this method that makes bitmap from any layout
            save.startAnimation(animation);
            doneLayout.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            getBitmap();

        });

        t_edit.setOnClickListener(view -> {
            t_edit.startAnimation(animation);
            t_edit.setBackgroundColor(getResources().getColor(R.color.grey_900));
            i_edit.setBackgroundColor(getResources().getColor(R.color.cream));
            t_editL.setVisibility(View.VISIBLE);
            i_editL.setVisibility(View.GONE);
            t_edit.setTextColor(getResources().getColor(R.color.white));
            i_edit.setTextColor(getResources().getColor(R.color.teal_700));

        });
        i_edit.setOnClickListener(view -> {
            i_edit.startAnimation(animation);
            i_edit.setBackgroundColor(getResources().getColor(R.color.grey_900));
            t_edit.setBackgroundColor(getResources().getColor(R.color.cream));
            i_editL.setVisibility(View.VISIBLE);
            t_editL.setVisibility(View.GONE);
            i_edit.setTextColor(getResources().getColor(R.color.white));
            t_edit.setTextColor(getResources().getColor(R.color.teal_700));


        });
        browse.setOnClickListener(view -> {
            browse.startAnimation(animation);
            Intent intent = new Intent(
                    Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });
        online.setOnClickListener(view -> {
            online.startAnimation(animation);
            startActivity(new Intent(MainActivity.this,unsplash.class));
            //startActivity(new Intent(MainActivity.this, SignIn.class));
        });

        change_text.setOnClickListener(view -> {
            poetry_txt = et.getText().toString();
            poetry_tv.setText(poetry_txt);
            saveText(poetry_txt);
        });
        change_name.setOnClickListener(view -> {
            change_name.startAnimation(animation);
            name_txt = et_watermark.getText().toString();
            watermark.setVisibility(View.VISIBLE);
            watermark.setText("-" + name_txt);
            saveWatermark(name_txt);
        });

        cancel.setOnClickListener(view -> {
            cancel.startAnimation(animation);
            doneLayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        });
        share.setOnClickListener(view -> {
            share.startAnimation(animation);
            doneLayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            shareImageUri(imageUri);
        });

        bold.setOnClickListener(view -> {
            bold.startAnimation(animation);
            if ("off".equals(bold.getTag())) {
                //changing tag to on to make else true when clicked
                //second time
                bold.setTag("on");
                bold.setImageResource(R.drawable.bold_ic);
                bold.setColorFilter(getResources().getColor(R.color.brown));
                poetry_tv.setTypeface(Typeface.DEFAULT_BOLD);


            } else {
                bold.setImageResource(R.drawable.bold_ic);
                bold.setColorFilter(getResources().getColor(R.color.brown));
                bold.setTag("off");
                poetry_tv.setTypeface(Typeface.DEFAULT);

            }

        });

        requestPermission();


        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        poetry_tv.setTextSize(i / 4);
                        watermark.setTextSize(i / 8);
                        saveTextSize(i / 4);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        opacitySeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        float alpha_value = i / 100f;
                        imageView.setAlpha(alpha_value);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
        rotate.setOnClickListener(view -> {
            rotate.startAnimation(animation);
            imageView.setRotation(imageView.getRotation() + 90);

        });

        text_color.setOnClickListener(view -> {
            text_color.startAnimation(animation);
            openColorPickerDialogue();
        });
        text_centre.setOnClickListener(view -> {
            text_centre.startAnimation(animation);
            poetry_tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            poetry_tv.setGravity(Gravity.CENTER);
        });
        watermark_iv.setOnClickListener(view -> {
            watermark_iv.startAnimation(animation);
            if ("off".equals(watermark_iv.getTag())) {
                watermark_iv.setTag("on");
                showWatermark();
                saveWaterCheck(true);
            } else {
                et_watermark.setVisibility(View.GONE);
                //changing tag to on to make else true when clicked
                //second time
                watermark.setVisibility(View.GONE);
                watermark_iv.setTag("off");
                lW.setVisibility(View.GONE);
                watermark_iv.setTextColor(getResources().getColor(R.color.grey));
                saveWaterCheck(false);
            }
        });
        color_text_bg.setOnClickListener(view -> {
            colorPicBg();
        });
        text_bg.setOnClickListener(view -> {
            if ("off".equals(text_bg.getTag())){
                color_text_bg.setVisibility(View.VISIBLE);
                poetry_tv.setBackgroundColor(getResources().getColor(R.color.black));
                text_bg.setTag("on");
                saveBgCheck(true);
            }
            else{
                color_text_bg.setVisibility(View.VISIBLE);
                poetry_tv.setBackgroundResource(android.R.color.transparent);
                text_bg.setTag("off");
                saveBgCheck(false);
            }
        });

    }//OnCreate
    public void showWatermark(){
        watermark.setVisibility(View.VISIBLE);
        et_watermark.setVisibility(View.VISIBLE);
        lW.setVisibility(View.VISIBLE);
        watermark_iv.setTextColor(getResources().getColor(R.color.brown));
    }

    public void bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        String encodedImg = Base64.encodeToString(arr, Base64.DEFAULT);
        saveUri(encodedImg);
    }

    public void check(View v) {
        if (checkBox.isChecked()) {
            makeBitmap();
            saveCheck(true); }
    }

    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void saveUri(String pic_link) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_LINK, pic_link);
        editor.apply();
    }

    private void saveCheck(Boolean check) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LAST_CHECK, check);
        editor.apply();
    }
    private void saveWaterCheck(Boolean water_check) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LAST_WATERMARK_CHECK, water_check);
        editor.apply();
    }
    private void saveBgCheck(Boolean bg_check) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LAST_BG_CHECK, bg_check);
        editor.apply();
    }

    private void saveText(String text_last) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_TEXT, text_last);
        editor.apply();
    }
    private void saveWatermark(String watermark_last) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_WATERMARK, watermark_last);
        editor.apply();
    }

    private void saveTextSize(int last_text_size) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_TEXT_SIZE, last_text_size);
        editor.apply();
    }
    private void saveTextColor(int last_text_color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_TEXT_COLOR, last_text_color);
        editor.apply();
    }


    private void saveFont(String last_text_font) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_FONT, last_text_font);
        editor.apply();
    }

    private void loadAll() {
        getLastLink = sharedPreferences.getString(LAST_LINK, "");
        getLastText = sharedPreferences.getString(LAST_TEXT, "Seekh kar Gayi hai vo Mohabbat Mujhse");
        getLastWatermark = sharedPreferences.getString(LAST_WATERMARK, "Shayar");
        getTextSize = sharedPreferences.getInt(LAST_TEXT_SIZE, 8);
        getLastTextColor = sharedPreferences.getInt(LAST_TEXT_COLOR, getResources().getColor(R.color.white));
        getLastTextFont = sharedPreferences.getString(LAST_FONT, "t1");

    }

    private Boolean loadCheck() {
        getLastCheck = sharedPreferences.getBoolean(LAST_CHECK, false);
        return getLastCheck;
    }
    private Boolean loadWCheck() {
        getLastWatermarkCheck = sharedPreferences.getBoolean(LAST_WATERMARK_CHECK, false);
        return getLastWatermarkCheck;
    }
    private Boolean loadBgCheck() {
        getLastBgCheck = sharedPreferences.getBoolean(LAST_CHECK, false);
        return getLastBgCheck;
    }



    private void changeFontSaved(String last_font) {

        switch (last_font) {
            case "t1": {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/glimer.otf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/glimer.otf"));
            }
            break;
            case "t2": {

                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/involo.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/involo.ttf"));

            }
            break;
            case "t3": {

                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Inter_reg.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Inter_reg.ttf"));
            }
            break;
            case "t4": {

                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/inter.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/inter.ttf"));
            }
            break;
            case "t5": {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/mukta.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/mukta.ttf"));
            }
            break;
            case "t6": {

                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aref.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aref.ttf"));
            }
            break;
            case "t7": {

                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"));
            }
            break;
        }

    }

    private void log(String l) {
        Log.d("taggi", l);
    }

    private void msg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            selectedImg = data.getData();
            String filePath;
            log("URI = " + selectedImg);
            if (selectedImg != null && "content".equals(selectedImg.getScheme())) {
                Cursor cursor = this.getContentResolver().query(selectedImg, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
                cursor.moveToFirst();
                filePath = cursor.getString(0);
                cursor.close();
            } else {
                filePath = selectedImg.getPath();
            }
            log("Chosen path = " + filePath);

            Picasso.get()
                    .load(selectedImg)
                    .resize(layout.getWidth(), layout.getHeight())
                    .placeholder(R.drawable.sample_image_ic)
                    .into(imageView);
            saveUri(selectedImg.toString());
        }
    }


    private boolean SaveImage(String title, Bitmap bitmap) {
        Uri ImageCollection;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ImageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        } else {
            ImageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues values = new ContentValues();
        // here we can change the name type of image
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = resolver.insert(ImageCollection, values);
        saveUri(imageUri.toString());


        try {
            OutputStream outputStream = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);
            return true;
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Image note Saved", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return false;

    }

    private void shareImageUri(Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void requestPermission() { // this is all about permissions

        boolean minSdk = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

        isReadPermissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;


        isWritePermissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        isWritePermissionGranted = isWritePermissionGranted || minSdk;

        List<String> permissionRequest = new ArrayList<String>();

        if (!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!isWritePermissionGranted) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionRequest.isEmpty()) {
            resultLauncher.launch(permissionRequest.toArray(new String[0]));
        }

    }

    private void getBitmap() {
        Bitmap bmp = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        layout.draw(canvas);

        if (isWritePermissionGranted) {
            if (SaveImage(UUID.randomUUID().toString(), bmp)) ;
            // Toast.makeText(MainActivity.this, "Saved Image Successfully", Toast.LENGTH_SHORT).show();
            //    bitMapToString(bmp);
            saved_iv.setImageURI(imageUri);
        } else {
            if (SaveImage(UUID.randomUUID().toString(), bmp)) ;
            Toast.makeText(MainActivity.this, "Compartmentally Saved Image Successfully", Toast.LENGTH_SHORT).show();
            //  Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeBitmap() {
        poetry_tv.setVisibility(View.GONE);
        watermark.setVisibility(View.GONE);
        Bitmap bmp2 = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bmp2);
        layout.draw(canvas2);
        bitMapToString(bmp2);
        poetry_tv.setVisibility(View.VISIBLE);
        watermark.setVisibility(View.VISIBLE);
    }


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();

                return true;
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //changin color of seconds button
            case R.id.textView1: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/glimer.otf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/glimer.otf"));
                t1.setTextColor(getResources().getColor(R.color.brown));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t1");
            }
            break;
            case R.id.textView2: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/involo.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/involo.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.brown));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t2");
            }
            break;
            case R.id.textView3: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Inter_reg.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Inter_reg.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.brown));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t3");
            }
            break;

            case R.id.textView4: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/inter.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/inter.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.brown));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t4");
            }
            break;
            case R.id.textView5: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/mukta.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/mukta.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.brown));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t5");
            }
            break;
            case R.id.textView6: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aref.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aref.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.brown));
                t7.setTextColor(getResources().getColor(R.color.grey));
                saveFont("t6");
            }
            break;
            case R.id.textView7: {
                poetry_tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"));
                watermark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"));
                t1.setTextColor(getResources().getColor(R.color.grey));
                t2.setTextColor(getResources().getColor(R.color.grey));
                t3.setTextColor(getResources().getColor(R.color.grey));
                t4.setTextColor(getResources().getColor(R.color.grey));
                t5.setTextColor(getResources().getColor(R.color.grey));
                t6.setTextColor(getResources().getColor(R.color.grey));
                t7.setTextColor(getResources().getColor(R.color.brown));
                saveFont("t7");
            }
            break;

        }
    }

    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, default_color,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        default_color = color;


                        poetry_tv.setTextColor(default_color);
                        watermark.setTextColor(default_color);
                        saveTextColor(default_color);
                    }
                });
        colorPickerDialogue.show();

    }
    public void colorPicBg() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, default_color,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        default_color = color;

                        poetry_tv.setBackgroundColor(default_color);

                    }
                });
        colorPickerDialogue.show();

    }
}