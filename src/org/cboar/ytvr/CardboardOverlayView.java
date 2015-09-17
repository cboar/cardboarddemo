package org.cboar.ytvr;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;

public class CardboardOverlayView extends LinearLayout {
	private final CardboardOverlayEyeView left;
	private final CardboardOverlayEyeView right;

	public CardboardOverlayView(Context ctx, AttributeSet attrs){
		super(ctx, attrs);
		setOrientation(HORIZONTAL);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        left = new CardboardOverlayEyeView(ctx, attrs);
        left.setLayoutParams(params);
        addView(left);

        right = new CardboardOverlayEyeView(ctx, attrs);
        right.setLayoutParams(params);
        addView(right);

        setDepthOffset(0.01f);
		setVisibility(View.VISIBLE);
	}

	private void setDepthOffset(float offset){
		left.setOffset(offset);
		right.setOffset(-offset);
	}

	private class CardboardOverlayEyeView extends ViewGroup {
		private final ImageView imageView;
		private float offset;

		public CardboardOverlayEyeView(Context ctx, AttributeSet attrs){
			super(ctx, attrs);

			imageView = new ImageView(ctx, attrs);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setAdjustViewBounds(true);
            addView(imageView);
		}

		public void setOffset(float offset){
			this.offset = offset;
		}

		@Override
		protected void onLayout(boolean changed, int left, int top, int right, int bottom){
			final int width = right - left;
            final int height = bottom - top;
            final float imageSize = 0.1f;
            final float verticalImageOffset = -0.07f;
            final float verticalTextPos = 0.52f;
            float adjustedOffset = offset;
            if (width > 1000) {
                adjustedOffset = 3.8f * offset;
            }
            float imageMargin = (1.0f - imageSize) / 2.0f;
            float leftMargin = (int) (width * (imageMargin + adjustedOffset));
            float topMargin = (int) (height * (imageMargin + verticalImageOffset));

            imageView.layout(
                    (int) leftMargin, (int) topMargin,
                    (int) (leftMargin + width * imageSize),
                    (int) (topMargin + height * imageSize)
				);
		}
	}
}
