package org.winkhouse.mwinkhouse.activity.listeners.anim;


import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SelAnagraficheStartAnimationListener implements AnimationListener {
	
	private Animation endAnimation = null;
	private ImageView startImage = null;
	private int startImageResource;
	
	public SelAnagraficheStartAnimationListener(ImageView startImage,int startImageResource, Animation endAnimation) {
		this.endAnimation = endAnimation;
		this.startImage = startImage;
		this.startImageResource = startImageResource;
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
		this.startImage.setImageResource(this.startImageResource);
		this.startImage.clearAnimation();
		this.startImage.setAnimation(this.endAnimation);
		this.startImage.startAnimation(this.endAnimation);		
	}
	
}
