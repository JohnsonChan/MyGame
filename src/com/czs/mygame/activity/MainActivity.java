package com.czs.mygame.activity;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import com.czs.mygame.GameLayer;
import com.czs.mygame.data.Constants;

public class MainActivity extends Activity {
	private CCGLSurfaceView ccGLSurfaceView;
	private GameLayer gameLayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 获得屏幕大小
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Constants.WIDTH = dm.widthPixels;
		Constants.HEIGHT = dm.heightPixels;

		super.onCreate(savedInstanceState);

		ccGLSurfaceView = new CCGLSurfaceView(this);
		setContentView(ccGLSurfaceView);

		CCDirector ccDirector = CCDirector.sharedDirector();
		ccDirector.attachInView(ccGLSurfaceView);
		ccDirector.setDisplayFPS(false);  // 左下角的帧
		ccDirector.setAnimationInterval(1 / 30.0);

		CCScene ccScene = CCScene.node(); 
		gameLayer = new GameLayer(this);
		ccScene.addChild(gameLayer);
		ccDirector.runWithScene(ccScene);
	}
	
	
//	/**
//		* 实现功能：
//		* @see android.app.Activity#onResume()
//		*/
//	@Override
//	protected void onResume() {
//	    gameLayer.onEnter();
//	    super.onResume();
//	}
//	
//	/**
//		* 实现功能：
//		* @see android.app.Activity#onPause()
//		*/
//	@Override
//	protected void onPause() {
//	    gameLayer.onExit();
//	    super.onPause();
//	}

}
