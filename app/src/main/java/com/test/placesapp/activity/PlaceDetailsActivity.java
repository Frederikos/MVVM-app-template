package com.test.placesapp.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.placesapp.R;
import com.test.placesapp.databinding.ActivityPlaceDetailsBinding;
import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.widget.ObservableScrollView;

public class PlaceDetailsActivity extends BaseActivity {

    public static final String SHARED_ELEMENT_TRANSITION = "place_image_transition";
    public static final String KEY_PLACE = "place";

    private ActivityPlaceDetailsBinding binding;
    private int minHeight;
    private int maxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportPostponeEnterTransition();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_details);
        binding.setViewModel(new PlaceDetailsViewModel((PlacesResponseModel.PlaceModel) getIntent().getParcelableExtra(KEY_PLACE)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            binding.ivPlaceImage.setTransitionName(SHARED_ELEMENT_TRANSITION);
        }

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class PlaceDetailsViewModel {
        private PlacesResponseModel.PlaceModel placeModel;

        public PlaceDetailsViewModel(PlacesResponseModel.PlaceModel placeModel) {
            this.placeModel = placeModel;
            Glide.with(PlaceDetailsActivity.this)
                    .load(placeModel.getImage())
                    .asBitmap()
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    supportStartPostponedEnterTransition();
                                    int toolbarColor = palette.getDarkVibrantColor(Color.DKGRAY);
                                    binding.toolbar.setBackgroundColor(toolbarColor);
                                    minHeight = getToolbarHeight() + getResources().getDimensionPixelSize(R.dimen.statusbar_height);
                                    maxHeight = getResources().getDimensionPixelSize(R.dimen.detail_image_height);
                                    initScrollableHeader();
                                }
                            });
                            return false;
                        }
                    })
                    .into(binding.ivPlaceImage);
        }

        public String getTitle() {
            return placeModel.getTitle();
        }

        public String getDescription() {
            //TODO stub description
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 50; i++) {
                stringBuilder.append(placeModel.getTitle() + "\n");
            }
            return stringBuilder.toString().trim();
        }

        private void initScrollableHeader() {
            binding.tvTitle.setPivotY(binding.tvTitle.getHeight());
            updateTitle(binding.mainScrollView.getScrollY());
            updateToolbar(binding.mainScrollView.getScrollY());
            binding.mainScrollView.setScrollListener(new ObservableScrollView.ScrollListener() {
                @Override
                public void onScrollChanged(int x, int y) {
                    updateTitle(y);
                    updateToolbar(y);
                    updateImageTranslation(y);
                }
            });
        }

        private void updateTitle(int scrollY) {
            int current = maxHeight - minHeight - scrollY;
            float scale = 1f + (current * 1.0f / maxHeight);
            binding.tvTitle.setScaleX(Math.max(1f, scale));
            binding.tvTitle.setScaleY(Math.max(1f, scale));
        }

        private void updateToolbar(int y) {
            int alpha = (int) ((y * 1.0f / (maxHeight - minHeight)) * 255);
            int finalAlpha = Math.max(0, Math.min(alpha, 255));
            binding.toolbar.getLayoutParams().height = Math.max(maxHeight - y, minHeight);
            binding.toolbar.requestLayout();
            binding.toolbar.getBackground().setAlpha(finalAlpha);
        }

        private void updateImageTranslation(int scrollY) {
            binding.ivPlaceImage.setTranslationY(-(scrollY / 2.0f));
        }
    }

}
