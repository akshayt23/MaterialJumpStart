package com.rubberduck.materialjumpstart.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.rubberduck.materialjumpstart.R;
import com.rubberduck.materialjumpstart.adapters.DetailsRVAdapter;
import com.rubberduck.materialjumpstart.model.Dummy;

public class DetailsActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private static final String TAG = "OfferDetailsActivity";
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = true;

    private View toolbar;
    private ImageView imageViewCover;
    private View overlayView;
    private View recyclerViewBackground;
    private ObservableRecyclerView recyclerView;
    private TextView textViewTitle;
    private int actionBarSize;
    private int flexibleSpaceImageHeight;
    private int toolbarColor;

    private Dummy currentDummy;

    Palette palette;
    Palette.Swatch vibrantSwatch, darkVibrantSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        currentDummy = (Dummy) intent.getParcelableExtra("dummyObj");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        actionBarSize = getActionBarSize();

        Log.d(TAG, "Uri = " + currentDummy.getImageUri());
        imageViewCover = (ImageView) findViewById(R.id.iv_cover);
        imageViewCover.setImageResource((getImageResource(currentDummy.getImageUri())));
        overlayView = findViewById(R.id.overlay);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        recyclerView = (ObservableRecyclerView) findViewById(R.id.rv_details);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        final View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, null);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                headerView.getLayoutParams().height = flexibleSpaceImageHeight;
            }
        });
        DetailsRVAdapter adapter = new DetailsRVAdapter(this, currentDummy, headerView);
        recyclerView.setAdapter(adapter);

        Bitmap bitmap = ((BitmapDrawable)((ImageView)imageViewCover).getDrawable()).getBitmap();
        if (bitmap != null) {
            palette = Palette.generate(bitmap);
            vibrantSwatch = palette.getVibrantSwatch();
            darkVibrantSwatch = palette.getDarkVibrantSwatch();
        }

        //Set overlay color (for transition) and statusbar color
        if (vibrantSwatch != null && darkVibrantSwatch != null) {
            toolbarColor = getLighterShade(darkVibrantSwatch.getRgb());//vibrantSwatch.getRgb();
            overlayView.setBackgroundColor(getLighterShade(darkVibrantSwatch.getRgb()));//(vibrantSwatch.getRgb());
            setStatusBarColor(darkVibrantSwatch.getRgb());
        }

        textViewTitle = (TextView) findViewById(R.id.title);
        textViewTitle.setText(currentDummy.getHeaderText());
        setTitle(null);

        // recyclerViewBackground makes RecyclerView's background except header view.
        recyclerViewBackground = findViewById(R.id.list_background);
        final View contentView = getWindow().getDecorView().findViewById(android.R.id.content);
        contentView.post(new Runnable() {
            @Override
            public void run() {
                // recylcerViewBackground's should fill its parent vertically
                // but the height of the content view is 0 on 'onCreate'.
                // So we should get it with post().
                recyclerViewBackground.getLayoutParams().height = contentView.getHeight();
            }
        });

        //since you cannot programatically add a headerview to a recyclerview we added an empty view as the header
        // in the adapter and then are shifting the views OnCreateView to compensate
        final float scale = 1 + MAX_TEXT_SCALE_DELTA;
        recyclerViewBackground.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(recyclerViewBackground, flexibleSpaceImageHeight);
            }
        });
        ViewHelper.setTranslationY(overlayView, flexibleSpaceImageHeight);
        textViewTitle.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(textViewTitle, (int) (flexibleSpaceImageHeight - textViewTitle.getHeight() * scale));
                ViewHelper.setPivotX(textViewTitle, 0);
                ViewHelper.setPivotY(textViewTitle, 0);
                ViewHelper.setScaleX(textViewTitle, scale);
                ViewHelper.setScaleY(textViewTitle, scale);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offer_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = flexibleSpaceImageHeight - actionBarSize;
        int minOverlayTransitionY = actionBarSize - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageViewCover, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(recyclerViewBackground, Math.max(0, -scrollY + flexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle();
        ViewHelper.setPivotY(textViewTitle, 0);
        ViewHelper.setScaleX(textViewTitle, scale);
        ViewHelper.setScaleY(textViewTitle, scale);

        //Add left padding as user scrolls up
        int leftPadding = convertPxToDp(
                ( (1 + MAX_TEXT_SCALE_DELTA - scale) * (100*(1 + MAX_TEXT_SCALE_DELTA)) ));
        textViewTitle.setPadding(leftPadding, 0, textViewTitle.getPaddingRight(), 0);


        // Translate title text
        int maxTitleTranslationY = (int) (flexibleSpaceImageHeight - textViewTitle.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(textViewTitle, titleTranslationY);


        if (TOOLBAR_IS_STICKY) {
            // Change alpha of toolbar background
            if (-scrollY + flexibleSpaceImageHeight <= actionBarSize) {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1, toolbarColor));
            } else {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, toolbarColor));
            }
        } else {
            // Translate Toolbar
            if (scrollY < flexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(toolbar, 0);
            } else {
                ViewHelper.setTranslationY(toolbar, -scrollY);
            }
        }

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(textViewTitle, findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(textViewTitle, 0);
        }
    }

    private int convertPxToDp (float pixels) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    private int getLighterShade(int rgb) {
        float correctionFactor = 0.1f;
        float red = (255 - Color.red(rgb)) * correctionFactor + Color.red(rgb);
        float green = (255 - Color.green(rgb)) * correctionFactor + Color.green(rgb);
        float blue = (255 - Color.blue(rgb)) * correctionFactor + Color.blue(rgb);

        return Color.argb(Color.alpha(rgb), (int)red, (int)green, (int)blue);
    }

    private int getImageResource(String uri) {
        int imageResource = getResources().
                getIdentifier(uri, null, getPackageName());

        return imageResource;
    }

}
