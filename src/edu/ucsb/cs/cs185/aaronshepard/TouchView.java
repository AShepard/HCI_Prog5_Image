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
	public TouchView(Context context, String image_path) {
		super(context);
		
		m_matrix = new Matrix();
		setImage(image_path);
	}
	
	public void setImage(String image_path) {
    	m_bit_map = BitmapFactory.decodeFile(image_path);
    	setImageBitmap(m_bit_map);
    	m_matrix = getImageMatrix();
    }
	
	public void scaleImage(float factor, float focus_x, float focus_y) {
		setScaleType(ScaleType.MATRIX);
		m_matrix.postScale(factor, factor, focus_x, focus_y);
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		setImageMatrix(m_matrix);
		super.onDraw(canvas);
	}

}
