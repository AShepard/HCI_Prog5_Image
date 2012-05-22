package edu.ucsb.cs.cs185.aaronshepard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.style.UpdateAppearance;
import android.widget.ImageView;

public class TouchView extends ImageView{
	private Bitmap m_bit_map;
	private Matrix m_matrix;
	private float point_x;
	private float point_y;
	public TouchView(Context context, String image_path) {
		super(context);
		point_x = 0;
		point_y = 0;
		m_matrix = new Matrix();
		setImage(image_path);
	}
	
	public void setImage(String image_path) {
    	m_bit_map = BitmapFactory.decodeFile(image_path);
    	setImageBitmap(m_bit_map);
    	m_matrix = getImageMatrix();
    }
	
	public void scaleImage(float factor, float focus_x, float focus_y) {
		m_matrix.postScale(factor, factor, focus_x, focus_y);
		
		invalidate();
	}
	
	public void translateImage(float trans_x, float trans_y) {

		int width = getWidth();
		int height = getHeight();
		//http://stackoverflow.com/questions/6075363/android-image-view-matrix-scale-translate
		m_matrix.postTranslate(trans_x- point_x, trans_y-point_y);
		point_x = trans_x;
		point_y = trans_y;
		
		invalidate();
	}
	
	public void setPoint(float x, float y) {
		point_x = x;
		point_y = y;
	}
	
	public void rotateImage(float focus_x, float focus_y) {
		//http://stackoverflow.com/questions/4538896/how-to-rotate-image-with-fix-point-along-with-touch-in-android
		//float radians = (float)Math.atan2(focus_x-getWidth()/2, getHeight()/2-focus_y);
		float radians = (float)Math.atan2(focus_x-point_x, point_y-focus_y);
		
		m_matrix.postRotate(radians, focus_x, focus_y);
		
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		setImageMatrix(m_matrix);
		super.onDraw(canvas);
	}

}
