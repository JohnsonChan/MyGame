package com.czs.mygame;

import java.util.Random;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.utils.CCFormatter;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import com.czs.mygame.util.LogUtil;

public class GameLayer extends CCLayer {
    CCSprite bird;
    CCSprite background;
    CCSprite floorOne;
    CCSprite floorTwo;
    Context context;
    CCSprite upBarOne;
    CCSprite upBarTwo;
    CCSprite downBarOne;
    CCSprite downBarTwo;


    CCAnimation ccAnimation = CCAnimation.animation("", 0.2f);

    public GameLayer(Context context) {
        this.context = context;
        background = CCSprite.sprite("bg.png");
        this.addChild(background);
        background.setAnchorPoint(0, 0);
        background.setPosition(0, 0);
        
        
        upBarOne = CCSprite.sprite("up_bar.png");
        upBarOne.setScaleX(2.5f);
        upBarOne.setScaleY(3);
        this.addChild(upBarOne);
        upBarOne.setAnchorPoint(0,0);
        upBarOne.setPosition(720, 1280-640);
        
        
        downBarOne = CCSprite.sprite("down_bar.png");
        downBarOne.setScaleX(2.5f);
        downBarOne.setScaleY(3);
        this.addChild(downBarOne);
        downBarOne.setAnchorPoint(0,0);
        downBarOne.setPosition(720, -240);
        
        
        downBarTwo = CCSprite.sprite("down_bar.png");
        downBarTwo.setScaleX(2.5f);
        downBarTwo.setScaleY(3);
        this.addChild(downBarTwo);
        downBarTwo.setAnchorPoint(0,0);
        downBarTwo.setPosition(1150, 0);
        
      
        upBarTwo = CCSprite.sprite("up_bar.png");
        
        Log.d("upBarTwo", upBarTwo.getContentSize().getWidth()+ ":" +upBarTwo.getContentSize().getHeight());
        upBarTwo.setScaleX(2.5f);
        upBarTwo.setScaleY(2);
        Log.d("upBarTwo", upBarTwo.getContentSize().getWidth()+ ":" +upBarTwo.getContentSize().getHeight());
        this.addChild(upBarTwo);
        upBarTwo.setAnchorPoint(0,0);
        upBarTwo.setPosition(1150, 1280-400);


        floorOne = CCSprite.sprite("ground.png");
        this.addChild(floorOne);
        floorTwo = CCSprite.sprite("ground.png");
        this.addChild(floorTwo);
        floorOne.setAnchorPoint(0, 0);
        floorOne.setPosition(0, 0);
        floorTwo.setAnchorPoint(0, 0);
        floorTwo.setPosition(720, 0);
        // ground2.setPosition(0,960);
        
      
        bird = CCSprite.sprite("loading_01.png");
        CCAnimation animation = CCAnimation.animation("dance", 0.05f);
        for (int i = 1; i < 12; i++) {
            animation.addFrame(new CCFormatter().format("loading_%02d.png", i));
            LogUtil.d(new CCFormatter().format("loading_%02d.png", i));
        }

        CCIntervalAction action = CCAnimate.action(animation);

        this.addChild(bird);

        CGSize s = CCDirector.sharedDirector().winSize();

        bird.setPosition(CGPoint.make(s.width / 3, s.height / 2));
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(action);
        bird.runAction(ccRepeatForever);
        this.setIsTouchEnabled(true);


      
        this.schedule("xunhuanUp", 0.01f);
        this.schedule("xunhuan", 0.01f);
    }
    
    public void xunhuanUp(float dt) {
    	upBarOne.setPosition(upBarOne.getPosition().x - 2, upBarOne.getPosition().y);
    	downBarOne.setPosition(downBarOne.getPosition().x - 2, downBarOne.getPosition().y);
        if (upBarOne.getPosition().x == -156) {
        	Random random = new Random();
        	int i = random.nextInt(50)-25;
        	upBarOne.setPosition(720, 1280-640 + i);
        	downBarOne.setPosition(720, -240 + i);
        }
    	
//        if (downBarOne.getPosition().x == -156) {
//        	
//        }
        
        
        upBarTwo.setPosition(upBarTwo.getPosition().x - 2, upBarTwo.getPosition().y);
        downBarTwo.setPosition(downBarTwo.getPosition().x - 2, downBarTwo.getPosition().y);
        if (upBarTwo.getPosition().x == -156) {
        	Random random = new Random();
        	int i = random.nextInt(50)-25;
        	upBarTwo.setPosition(720, 1280-400 -i);
        	downBarTwo.setPosition(720, 0-i);
        }
       
//        if (downBarTwo.getPosition().x == -156) {
//        	
//        }
//        LogUtil.d(upBarTwo.getPosition().x);
//        
//        LogUtil.d(upBarTwo.getPosition().x);
    }

    /**
     * 重写方法：ccTouchesMoved, @see org.cocos2d.layers.CCLayer#ccTouchesMoved(android.view.MotionEvent) 参数：
     * 
     * @param event
     * @return 实现功能：
     */
    @Override
    public boolean ccTouchesMoved(MotionEvent event) {

//        Log.d("ccTouchesMoved", getScaleY() + "");
        CGPoint point = CGPoint.ccp(100, 320);
        CCJumpTo jumpTo = CCJumpTo.action(1, point, 200, 1);
        bird.runAction(jumpTo);
        return super.ccTouchesMoved(event);
    }

    public void xunhuan(float dt) {
        floorOne.setPosition(floorOne.getPosition().x - 2, floorOne.getPosition().y);
        if (floorOne.getPosition().x == -720) {
            floorOne.setPosition(0.0f, floorOne.getPosition().y);
        }
        floorTwo.setPosition(floorTwo.getPosition().x - 2, floorTwo.getPosition().y);
        if (floorTwo.getPosition().x == 0) {
            floorTwo.setPosition(720, floorOne.getPosition().y);
        }
//        LogUtil.d(floorOne.getPosition().x);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        SoundEngine.sharedEngine().playEffect(context, R.raw.sfx_wing);

        return CCTouchDispatcher.kEventHandled;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        SoundEngine.sharedEngine().playSound(context, R.raw.backsound, true);
    }

    @Override
    public void onExit() {
        SoundEngine.sharedEngine().pauseSound();

        super.onExit();
    }

}
