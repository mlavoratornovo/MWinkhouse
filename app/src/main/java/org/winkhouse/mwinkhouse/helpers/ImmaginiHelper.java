package org.winkhouse.mwinkhouse.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.os.Environment;

public class ImmaginiHelper {

	public ImmaginiHelper() {
		
	}
	
	public File createImageFile(Integer codimmobile) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    
	    File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/immagini/" + codimmobile.toString());
	    
	    if (!storageDir.exists()){
	    	storageDir.mkdirs();
	    }
	    File image = File.createTempFile(imageFileName,".jpg",storageDir);

	    
	    return image;
	}
	
	public boolean cancellaImmagini(Context context, DataBaseHelper dbh, Integer cod_immobile, Integer[] codiciImmagine){
		
		boolean returnValue = true;				
		
	    HashMap columns = new HashMap();
	    columns.put(ImmagineColumnNames.PATHIMMAGINE, null);
	    ArrayList<ImmagineVO> lista_immagini = new ArrayList<ImmagineVO>();
	    
		for (int i = 0; i < codiciImmagine.length; i++) {
			ImmagineVO ivo = dbh.getImmagineById(codiciImmagine[i], columns);
			lista_immagini.add(ivo);
		}
			
		dbh.deleteImmagini(codiciImmagine);
		
		for (ImmagineVO immagineVO : lista_immagini) {
			deleteImmagineFile(context, cod_immobile, immagineVO.getPathImmagine());
		}
		
		return returnValue;
		
	}
	
	public boolean deleteImmagineFile(Context context,Integer cod_immobile, String nomeImmagine){
		
		File img = new File(Environment.getExternalStorageDirectory() + File.separator + 
						    "winkhouse/immagini/" + cod_immobile.toString() + File.separator +
						    nomeImmagine);
		
		return img.delete();
		
	}
	
	public void resizeImage(String image_path,int PHOTO_WIDTH, int PHOTO_HEIGHT,int quality) {
		
	    Bitmap original = BitmapFactory.decodeFile(image_path);
	    Bitmap resized = Bitmap.createScaledBitmap(original, PHOTO_WIDTH, PHOTO_HEIGHT, true);
	         
	    ByteArrayOutputStream blob = new ByteArrayOutputStream();
	    quality = (quality > 100)?100:(quality < 0)?0:quality;
	    resized.compress(Bitmap.CompressFormat.JPEG, quality, blob);
	    
	    try {
			FileOutputStream fos = new FileOutputStream(new File(image_path));
			fos.write(blob.toByteArray());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {		
			e.printStackTrace();		
		} catch (IOException e) {		
			e.printStackTrace();
		}

	}
	
	public Point getImageDimension(String image_path){
		
		Point p = new Point();
		Options opt = new BitmapFactory.Options();
		
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(image_path,opt);
		
		p.set(opt.outWidth, opt.outHeight);
		
		return p;
	}

}
