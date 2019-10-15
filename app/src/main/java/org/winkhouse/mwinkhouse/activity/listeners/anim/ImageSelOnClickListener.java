package org.winkhouse.mwinkhouse.activity.listeners.anim;

import java.util.HashMap;

import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;

import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;

public class ImageSelOnClickListener implements OnClickListener {
	
	private Animation animation = null;
	private HashMap<Integer,Object> selected = null;
	
	public ImageSelOnClickListener(Animation animation,HashMap<Integer,Object> selected) {
		this.animation 	= animation;
		this.selected	= selected;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag() instanceof ImmobiliVO){
			if (selected.get(((ImmobiliVO)v.getTag()).getCodImmobile()) != null){
				selected.remove(((ImmobiliVO)v.getTag()).getCodImmobile());
			}else{
				selected.put(((ImmobiliVO)v.getTag()).getCodImmobile(),(ImmobiliVO)v.getTag());
			}
			
		}
		if (v.getTag() instanceof AnagraficheVO){
			if (selected.get(((AnagraficheVO)v.getTag()).getCodAnagrafica()) != null){
				selected.remove(((AnagraficheVO)v.getTag()).getCodAnagrafica());
			}else{
				selected.put(((AnagraficheVO)v.getTag()).getCodAnagrafica(),(AnagraficheVO)v.getTag());
			}
			
		} 
		if (v.getTag() instanceof ColloquiVO){
			if (selected.get(((ColloquiVO)v.getTag()).getCodColloquio()) != null){
				selected.remove(((ColloquiVO)v.getTag()).getCodColloquio());
			}else{
				selected.put(((ColloquiVO)v.getTag()).getCodColloquio(),(ColloquiVO)v.getTag());
			}
			
		} 
		
		v.setAnimation(animation);
		v.startAnimation(animation);

	}

	public HashMap<Integer,Object> getSelected() {
		return selected;
	}

}
