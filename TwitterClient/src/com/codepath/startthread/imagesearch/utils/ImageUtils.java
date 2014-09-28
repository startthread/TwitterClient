package com.codepath.startthread.imagesearch.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.ImageView;

public class ImageUtils {

	private static final String TAG = "ImageUtils";
	
	// should not be called on UI thread
	public static Uri getLocalBitmapUri(ImageView imageView) {
		final Drawable drawable = imageView.getDrawable();
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
		} else {
			return null;
		}

		Uri uri = null;
		try {
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"share_image_" + System.currentTimeMillis() + ".png");
			file.getParentFile().mkdirs();
			
			FileOutputStream out;
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			
			uri = Uri.fromFile(file);
		} catch (IOException e) {
			Log.e(TAG, "Error while writing bitmap to file", e);
		}
		
		return uri;
	}
	
	public static Uri addImageToMedia(ImageView imageView, String description) {
		Drawable mDrawable = imageView.getDrawable();
		Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

		String path = Images.Media.insertImage(imageView.getContext().getContentResolver(), 
		    mBitmap, description, null);

		Uri uri = Uri.parse(path);
		return uri;
	}
}
