package edu.ucsb.cs.cs185.aaronshepard;

import com.example.android.actionbarcompat.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView;
import android.widget.Toast;

public class HCI_Prog5_ImageActivity extends ActionBarActivity {
	private final static int SELECT_PICTURE = 1;
	private final static int TRANSLATE = 0;
	private final static int ROTATE = 1;
	private String m_picture_path;
	private ImageView iv_display;
	
	private int last_action;
	
	private ScaleGestureDetector m_scale_detector;
	private TouchView m_touch_view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_picture_path = "";
        iv_display = (ImageView)findViewById(R.id.iv_display);
        m_touch_view = null;
        
        m_scale_detector = new ScaleGestureDetector(this, new MySimpleOnScaleGestureListener());
        
        //set blank title
        setTitle("");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        // Calling super after populating the menu is necessary here to ensure that the
        // action bar helpers have a chance to handle this event.
        return super.onCreateOptionsMenu(menu);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                m_picture_path = getPath(selectedImageUri);
                setImage(m_picture_path);
            }
        }
    }
    
    //http://marakana.com/forums/android/examples/98.html
    public void setImage(String image_path) {
    	m_touch_view = new TouchView(this, image_path);
    	setContentView(m_touch_view);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_picture:
                //Toast.makeText(this, "Picture...", Toast.LENGTH_SHORT).show();
                // in onCreate or any event where your want the user to
                // select a file
                //http://stackoverflow.com/questions/2169649/open-an-image-in-androids-built-in-gallery-app-programmatically
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
                break;

            case R.id.menu_settings:
            	
            	AlertDialog settings_dialog = new AlertDialog.Builder(HCI_Prog5_ImageActivity.this).create();
            	settings_dialog.setMessage("For settings/help please forward $25 to developer. Cash only!");
            	settings_dialog.setButton("OK", new DialogInterface.OnClickListener() {
            	      public void onClick(DialogInterface dialog, int which) {
            	 
            	       //nothing to do
            	 
            	    } });
              	
            	settings_dialog.show();
                break;

            case R.id.menu_help:
            	AlertDialog help_dialog = new AlertDialog.Builder(HCI_Prog5_ImageActivity.this).create();
            	help_dialog.setMessage("Name: Aaron Shepard. \nSoftware Version: Unknown. \nExtra Info: This project deserves an A+!");
            	help_dialog.setButton("OK", new DialogInterface.OnClickListener() {
            	      public void onClick(DialogInterface dialog, int which) {
            	 
            	       //nothing to do
            	 
            	    } });
              	
              	help_dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	
    	if (m_touch_view==null) {
    		return false;
    	}
    	
    	m_scale_detector.onTouchEvent(event);
    	
    	switch(action) {
    		case MotionEvent.ACTION_DOWN:
    			last_action = TRANSLATE;
    			m_touch_view.setPoint(event.getX(), event.getY());
    			break;
    		case MotionEvent.ACTION_POINTER_2_DOWN:
    			last_action = ROTATE;
    			break;
    		case MotionEvent.ACTION_POINTER_2_UP:
    			//if lifting second finger, go back to translate
    			last_action = TRANSLATE;
    			break;
    		case MotionEvent.ACTION_MOVE:
    			if(last_action==TRANSLATE) {
    				m_touch_view.translateImage(event.getX(), event.getY());
    			}
    			else if(last_action==ROTATE) {
    				m_touch_view.rotateImage(event.getX(), event.getY());
    			}
    			break;
    	}
    	
	    return true;
    }
    
    //http://android-coding.blogspot.com/2011/09/scalegesturedetector.html
    public class MySimpleOnScaleGestureListener extends SimpleOnScaleGestureListener {
     
	     @Override
	     public boolean onScale(ScaleGestureDetector detector) {
	    	 float scaleFactor = detector.getScaleFactor();
	    	 float spanX = detector.getFocusX();
	    	 float spanY = detector.getFocusY();
	    	 if(m_touch_view != null) {
         		m_touch_view.scaleImage(scaleFactor, spanX, spanY);
         		return true;
         	} else {
         		//Toast.makeText(getApplicationContext(), "No Image to scale!", Toast.LENGTH_SHORT).show();
         	}
	    	 return false;
	     }
     
     }
}