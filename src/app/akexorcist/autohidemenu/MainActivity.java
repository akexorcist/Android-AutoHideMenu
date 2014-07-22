package app.akexorcist.autohidemenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {
	RelativeLayout layoutMenu, layoutActionBar, layoutHeader;
	ScrollView scrollView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().hide();

        layoutMenu = (RelativeLayout)findViewById(R.id.layoutMenu);
        layoutActionBar = (RelativeLayout)findViewById(R.id.layoutActionBar);
        layoutHeader = (RelativeLayout)findViewById(R.id.layoutHeader);
        
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new OnTouchListener() {
        	final int DISTANCE = 3; 

        	float startY = 0;
        	float dist = 0;
        	boolean isMenuHide = false;        	
        	
        	public boolean onTouch(View v, MotionEvent event) {
        		int action = event.getAction();
        		
        		if(action == MotionEvent.ACTION_DOWN) {
        			startY = event.getY();
        		} else if(action == MotionEvent.ACTION_MOVE) {
        			dist = event.getY() - startY;
        			
        			if((pxToDp((int)dist) <= -DISTANCE) && !isMenuHide) {
        				isMenuHide = true;
            			hideMenuBar();    
        			} else if((pxToDp((int)dist) > DISTANCE) && isMenuHide) {
        				isMenuHide = false;
            			showMenuBar();
        			}
        			
        			if((isMenuHide && (pxToDp((int)dist) <= -DISTANCE))
        					|| (!isMenuHide && (pxToDp((int)dist) > 0))) {
        				startY = event.getY();
        			}
        		} else if(action == MotionEvent.ACTION_UP) {
        			startY = 0;
        		}
        			
        		return false;
        	}
        });
    }
    
    public int pxToDp(int px) {
	    DisplayMetrics dm = this.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
    
    public void showMenuBar() {
    	AnimatorSet animSet = new AnimatorSet();
    	
    	ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu, View.TRANSLATION_Y, 0);
		
    	ObjectAnimator anim2 = ObjectAnimator.ofFloat(layoutActionBar, View.TRANSLATION_Y, 0);

    	ObjectAnimator anim3 = ObjectAnimator.ofFloat(layoutHeader, View.TRANSLATION_Y, 0);
    	
    	animSet.playTogether(anim1, anim2, anim3);
    	animSet.setDuration(300);
    	animSet.start();
    }
    
    public void hideMenuBar() {
    	AnimatorSet animSet = new AnimatorSet();
	
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu, View.TRANSLATION_Y, layoutMenu.getHeight());
		
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(layoutActionBar, View.TRANSLATION_Y, -layoutActionBar.getHeight());
	
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(layoutHeader, View.TRANSLATION_Y, -layoutHeader.getHeight() * 2);
		
		animSet.playTogether(anim1, anim2, anim3);
    	animSet.setDuration(300);
		animSet.start();
    }
    
}
