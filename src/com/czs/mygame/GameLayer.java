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
    private CCSprite bird;
    private CCSprite background;
    private CCSprite floorOne;
    private CCSprite floorTwo;
    private Context context;
    private CCSprite upBarOne;
    private CCSprite upBarTwo;
    private CCSprite downBarOne;
    private CCSprite downBarTwo;
    
    private float height = 0.0f;
    private float weight = 0.0f;
    private float scaleX = 0.0f;
    private float scaleY = 0.0f;
    private float barHeight = 0.0f;
    private float barChangeHeight = 0.0f;
    private float barWeight = 0.0f;   // 障碍的宽


//    private CCAnimation ccAnimation = CCAnimation.animation("", 0.2f);

    public GameLayer(Context context) {
        this.context = context;
        CGSize cgSize    = CCDirector.sharedDirector().winSize();
        height = cgSize.height;
        weight = cgSize.width;
        barHeight = height /10;
        barChangeHeight = height / 50;
        LogUtil.d("height:" + height);
        LogUtil.d("weight:" + weight);

        // 背景
        background = CCSprite.sprite("bg.png");     
        this.addChild(background);
        background.setAnchorPoint(0, 0);   //默认情况下 anchorPoint 为（ 0.5,0.5 ），即贴图对象 的中心位置对应着节点背景对象 的左下角；而当 anchorPoint 为（0,0 ），即贴图对象 的左下角对应着节点背景对象 的左下角
        background.setPosition(0, 0);
        scaleX=  weight/background.getContentSize().getWidth();
        background.setScaleX(scaleX);
        scaleY = height/background.getContentSize().getHeight();
        background.setScaleY(scaleY);
        
        // 地板
        floorOne = CCSprite.sprite("ground.png");
        this.addChild(floorOne);
        floorTwo = CCSprite.sprite("ground.png");
        this.addChild(floorTwo);
        floorOne.setAnchorPoint(0, 0);
        floorOne.setPosition(0, 0);
        floorTwo.setAnchorPoint(0, 0);
        floorTwo.setPosition(weight, 0);
        floorOne.setScaleY(scaleY);
        floorOne.setScaleX(scaleX);
        floorTwo.setScaleY(scaleY);
        floorTwo.setScaleX(scaleX);
        
        
        upBarOne = CCSprite.sprite("up_bar.png");
        upBarOne.setScaleX(scaleX);
        upBarOne.setScaleY(scaleY);
        barWeight = upBarOne.getContentSize().getWidth() * scaleX;
        this.addChild(upBarOne);
        upBarOne.setAnchorPoint(0,0);
        upBarOne.setPosition(weight, height/2+barHeight);

        downBarOne = CCSprite.sprite("down_bar.png");
        downBarOne.setScaleX(scaleX);
        downBarOne.setScaleY(scaleY);
        this.addChild(downBarOne);
        downBarOne.setAnchorPoint(0,0);
        downBarOne.setPosition(weight, 0-barHeight);
//        
//        
        upBarTwo = CCSprite.sprite("up_bar.png");
        upBarTwo.setScaleX(scaleX);
        upBarTwo.setScaleY(scaleY);
        this.addChild(upBarTwo);
        upBarTwo.setAnchorPoint(0,0);  
        upBarTwo.setPosition(1150, 1280-400);
        
        downBarTwo = CCSprite.sprite("down_bar.png");
        downBarTwo.setScaleX(scaleX);
        downBarTwo.setScaleY(scaleY);
        this.addChild(downBarTwo);
        downBarTwo.setAnchorPoint(0,0);
        downBarTwo.setPosition(1150, 0);
        
      
        
//
//
       
//        
//      
//        bird = CCSprite.sprite("loading_01.png");
//        CCAnimation animation = CCAnimation.animation("dance", 0.05f);
//        for (int i = 1; i < 12; i++) {
//            animation.addFrame(new CCFormatter().format("loading_%02d.png", i));
//            LogUtil.d(new CCFormatter().format("loading_%02d.png", i));
//        }
//
//        CCIntervalAction action = CCAnimate.action(animation);
//
//        this.addChild(bird);
//
//        CGSize s = CCDirector.sharedDirector().winSize();
//
//        bird.setPosition(CGPoint.make(s.width / 3, s.height / 2));
//        CCRepeatForever ccRepeatForever = CCRepeatForever.action(action);
//        bird.runAction(ccRepeatForever);
//        this.setIsTouchEnabled(true);
//
//
//      
        this.schedule("refreshBar", 0.01f);
        this.schedule("refreshFloor", 0.01f);
    }
    
    // 刷新障碍
    public void refreshBar(float dt) {
    	upBarOne.setPosition(upBarOne.getPosition().x - 2, upBarOne.getPosition().y);
    	downBarOne.setPosition(downBarOne.getPosition().x - 2, downBarOne.getPosition().y);
        if (upBarOne.getPosition().x < -barWeight) {
        	Random random = new Random();
        	int i = random.nextInt(10);
        	upBarOne.setPosition(weight, height/2 + barHeight + i);
        	LogUtil.d("czs:"+ (height/2 + barHeight + i));
        	downBarOne.setPosition(weight, 0 - barHeight - i);
        }
    	
        
//        upBarTwo.setPosition(upBarTwo.getPosition().x - 2, upBarTwo.getPosition().y);
//        downBarTwo.setPosition(downBarTwo.getPosition().x - 2, downBarTwo.getPosition().y);
//        if (upBarTwo.getPosition().x == -156) {
//        	Random random = new Random();
//        	int i = random.nextInt(50)-25;
//        	upBarTwo.setPosition(720, 1280-400 -i);
//        	downBarTwo.setPosition(720, 0-i);
//        }
    }

    // 刷新地板
    public void refreshFloor(float dt) {
        floorOne.setPosition(floorOne.getPosition().x - 2, floorOne.getPosition().y);
        if (floorOne.getPosition().x == -weight) {
            floorOne.setPosition(0.0f, floorOne.getPosition().y);
        }
        floorTwo.setPosition(floorTwo.getPosition().x - 2, floorTwo.getPosition().y);
        if (floorTwo.getPosition().x == 0) {
            floorTwo.setPosition(weight, floorOne.getPosition().y);
        }
    }

    // 点击处理
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        SoundEngine.sharedEngine().playEffect(context, R.raw.sfx_wing);
        return CCTouchDispatcher.kEventHandled;
    }
    
    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        CGPoint point = CGPoint.ccp(100, 320);
        CCJumpTo jumpTo = CCJumpTo.action(1, point, 200, 1);
        bird.runAction(jumpTo);
        return super.ccTouchesMoved(event);
    }
    
    // 进入处理
    @Override
    public void onEnter() {
        super.onEnter();
        SoundEngine.sharedEngine().playSound(context, R.raw.backsound, true);
    }

    // 退出处理
    @Override
    public void onExit() {
        SoundEngine.sharedEngine().pauseSound();
        super.onExit();
    }

}
