package org.winkhouse.mwinkhouse.activity.listeners.anim;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SelAnagraficheEndAnimationListener implements AnimationListener {

	private ImageView image = null;
	private int endImageResource;
	private int endImageResourceBack;
	private int state = 0;			
	
	public SelAnagraficheEndAnimationListener(ImageView image,int endImageResource,int endImageResourceBack){
		this.image = image;
		this.endImageResource = endImageResource;
		this.endImageResourceBack = endImageResourceBack;
	}
	
	public SelAnagraficheEndAnimationListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		if (state == 0){
			image.setImageResource(endImageResource);
			state = 1;
		}else{
			image.setImageResource(endImageResourceBack);
			state = 0;
		}

	}

}
