/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hssychargingpole.xpg.com.baidumapdemo.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.zxing.camera.CameraManager;



/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {
	public static final int TYPE_1D_CODE = 1;
	public static final int TYPE_2D_CODE = 2;

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	private static final long ANIMATION_DELAY = 100L;
	private static final int OPAQUE = 0xFF;

	private int type;
	private String tips;

	private final Paint paint;
	private final Paint bluePaint;
	private Bitmap resultBitmap;
	private final int titleColor;
	private final int maskColor;
	private final int resultColor;
	private final int frameColor;
	private final int laserColor;
	private final int cornerColor;
	private final int resultPointColor;
	private int scannerAlpha;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint();
		bluePaint = new Paint();
		Resources resources = getResources();
		titleColor = resources.getColor(R.color.title_text);
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		frameColor = resources.getColor(R.color.viewfinder_frame);
		laserColor = resources.getColor(R.color.viewfinder_laser);
//		cornerColor = resources.getColor(R.color.viewfinder_corner);
		cornerColor = resources.getColor(R.color.text_cyan);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		scannerAlpha = 0;
		possibleResultPoints = new HashSet<ResultPoint>(5);

		bluePaint.setColor(resources.getColor(R.color.water_blue));
	}

	@Override
	public void onDraw(Canvas canvas) {
		if(isInEditMode()) return;
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();
//		left：是矩形距离左边的X轴
//		top：是矩形距离上边的Y轴
//		right：是矩形距离右边的X轴
//		bottom：是矩形距离下边的Y轴
		// Draw the exterior (i.e. outside the framing rect) darkened
		int lens = frame.width() / 6;
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left + lens/2, frame.bottom + 1- lens, paint);
		canvas.drawRect(frame.right + 1 - lens/2, frame.top, width, frame.bottom + 1- lens,
				paint);
		canvas.drawRect(0, frame.bottom + 1 - lens, width, height, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			// Draw a two pixel solid black border inside the framing rect
//			paint.setColor(frameColor);
//			canvas.drawRect(frame.left, frame.top, frame.right + 1,
//					frame.top + 2, paint);
//			canvas.drawRect(frame.left, frame.top + 2, frame.left + 2,
//					frame.bottom - 1, paint);
//			canvas.drawRect(frame.right - 1, frame.top, frame.right + 1,
//					frame.bottom - 1, paint);
//			canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1,
//					frame.bottom + 1, paint);

			// Draw a red "laser scanner" line through the middle to show
			// decoding is active
			if (type == TYPE_1D_CODE) {
				paint.setColor(laserColor);
				paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
				scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
				int middle = frame.height() / 2 + frame.top;
				canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
						middle + 2, paint);
			} else if (type == TYPE_2D_CODE) {
				paint.setColor(cornerColor);
				paint.setStrokeWidth(width * 0.007f);
				int len = frame.width() / 6;
				float[] pts = new float[] { frame.left + len/2, frame.top + len/2, frame.left+ len/2, frame.top,
						frame.left+ len/2, frame.top, frame.left + len, frame.top,
						frame.right - len, frame.top, frame.right-len/2, frame.top,
						frame.right-len/2, frame.top, frame.right-len/2, frame.top + len/2,
						frame.right-len/2, frame.bottom - len-len/2, frame.right-len/2, frame.bottom - len,
						frame.right-len/2, frame.bottom- len, frame.right - len, frame.bottom- len,
						frame.left + len, frame.bottom- len, frame.left+ len/2, frame.bottom- len,
						frame.left + len/2, frame.bottom- len, frame.left + len/2, frame.bottom - len - len /2, };
				canvas.drawLines(pts, paint);
			}

			// Draw title
			if (tips != null) {
				paint.setColor(titleColor);
				paint.setTextSize(width * 0.04f);
				String[] tipsArray = tips.split("&");
				int x0 = (int) (width - tipsArray[0].length() * width * 0.04f) / 2;
//				int x1 = (int) (width - tipsArray[1].length() * width * 0.035f) / 2;
				canvas.drawText(tipsArray[0], x0, frame.bottom
						+ height * 0.1f, paint);
//				canvas.drawText(tipsArray[1], x1, frame.bottom
//						+ height * 0.1f + width * 0.06f, paint);
				int x1;
				if(tipsArray.length == 4){
					bluePaint.setTextSize(width * 0.04f);
					x1 = (int) (width - (tipsArray[1].length()+tipsArray[2].length() + tipsArray[3].length()) * width * 0.04f) / 2;
					canvas.drawText(tipsArray[1], x1, frame.bottom + height * 0.1f + width * 0.06f, paint);
					int x2 =(int)(x1+width * 0.04f*tipsArray[1].length()+width * 0.02f);
					int x3 = (int) (x2+width * 0.035f*tipsArray[2].length()-width * 0.015f);
					canvas.drawText(tipsArray[0], x0, frame.bottom + height * 0.1f, paint);
					canvas.drawText(tipsArray[2], x2, frame.bottom
							+ height * 0.1f + width * 0.06f, bluePaint);
					canvas.drawText(tipsArray[3], x3, frame.bottom
							+ height * 0.1f + width * 0.06f, paint);
				}else{
					x1 = (int) (width - (tipsArray[1].length()) * width * 0.035f) / 2;
					canvas.drawText(tipsArray[1], x1, frame.bottom + height * 0.1f + width * 0.06f, paint);
				}
			}

			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(OPAQUE);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 6.0f, paint);
				}
			}
			if (currentLast != null) {
				paint.setAlpha(OPAQUE / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 3.0f, paint);
				}
			}

			// Request another update at the animation interval, but only
			// repaint the laser line,
			// not the entire viewfinder mask.
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 *
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setTips(int tipsId) {
		this.tips = getResources().getString(tipsId);
	}
}
