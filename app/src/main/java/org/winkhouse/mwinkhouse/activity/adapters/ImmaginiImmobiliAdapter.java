package org.winkhouse.mwinkhouse.activity.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.winkhouse.mwinkhouse.models.ImmagineVO;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ImmaginiImmobiliAdapter extends ArrayAdapter<ImmagineVO> {

	public ImmaginiImmobiliAdapter(Context context, int textViewResourceId, List<ImmagineVO> immagini) {
		super(context, textViewResourceId, immagini);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_gallery_immagini, null);
            viewHolder = new ViewHolder();
            viewHolder.selezione  = convertView.findViewById(R.id.chk_item);
            viewHolder.immagine = convertView.findViewById(R.id.image_detail);
            viewHolder.nome_immagine = convertView.findViewById(R.id.image_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        ImmagineVO immagine = getItem(position);
        
        File imgFile = new  File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/immagini" +
        						 File.separator + immagine.getCodImmobile().toString() + File.separator + immagine.getPathImmagine());
        
        if(imgFile.exists()){
        	Bitmap b = decodeFile(new File(imgFile.getAbsolutePath()));
        	
        	viewHolder.immagine.setImageBitmap(b);
        	viewHolder.immagine.setTag(immagine.getCodImmagine());
        	viewHolder.nome_immagine.setText(immagine.getPathImmagine());
        }
        viewHolder.selezione.setTag(immagine.getCodImmagine());
      	
        return convertView;
    }

    private class ViewHolder {
    	public CheckBox selezione;
        public ImageView immagine;
        public TextView nome_immagine;
    }

    private Bitmap decodeFile(File f){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //The new size we want to scale to
            final int REQUIRED_SIZE=70;

            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }}
