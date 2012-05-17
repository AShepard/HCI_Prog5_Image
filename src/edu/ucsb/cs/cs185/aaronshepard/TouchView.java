package edu.ucsb.cs.cs185.aaronshepard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.style.UpdateAppearance;
import android.widget.ImageView;

public class TouchView extends ImageView{
	
	private Matrix m_matrix;
	public TouchView(Context context, String image_path) {
		super(context);
		
		m_matrix = new Matrix();
		setImage(image_path);
	}
	
	public void setImage(String image_path) {
    	Bitmap bm = BitmapFactory.decodeFile(image_path);
    	setImageBitmap(bm);
    	
    }
	
	public void scaleImage(float factor) {
		m_matrix = getImageMatrix();
		m_matrix.postScale(factor, factor);
		
		setImageMatrix(m_matrix);
	}
	

}
