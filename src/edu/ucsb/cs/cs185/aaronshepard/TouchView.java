package edu.ucsb.cs.cs185.aaronshepard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.ImageView;

public class TouchView extends ImageView{
	
	public TouchView(Context context, String image_path) {
		super(context);
		
		setImage(image_path);
	}
	
	public void setImage(String image_path) {
    	Bitmap bm = BitmapFactory.decodeFile(image_path);
    	setImageBitmap(bm);
    }
	

}
