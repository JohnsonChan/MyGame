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
    private float barHeight = 0.0f;    // 中心点，往上高多少，往下低多少
    private float barWeight = 0.0f;   // 障碍的宽
    
    private float Y = 0;

    private float speep = 7.0f;   // 速度

    public GameLayer(Context context) {
        this.context = context;
        CGSize cgSize = CCDirector.sharedDirector().winSize();
        height = cgSize.height;
        weight = cgSize.width;
        barHeight = height / 10;
        LogUtil.d("height:" + height);
        LogUtil.d("weight:" + weight);

        // 背景
        background = CCSprite.sprite("bg.png");
        this.addChild(background);
        background.setAnchorPoint(0, 0);   // 默认情况下 anchorPoint 为（ 0.5,0.5 ），即贴图对象 的中心位置对应着节点背景对象 的左下角；而当 anchorPoint 为（0,0 ），即贴图对象 的左下角对应着节点背景对象 的左下角
        background.setPosition(0, 0);
        scaleX = weight / background.getContentSize().getWidth();
        background.setScaleX(scaleX);
        scaleY = height / background.getContentSize().getHeight();
        background.setScaleY(scaleY);

        // 第一条障碍物上
        upBarOne = CCSprite.sprite("up_bar.png");
        upBarOne.setScaleX(scaleX);
        upBarOne.setScaleY(scaleY);
        barWeight = upBarOne.getContentSize().getWidth() * scaleX;
        this.addChild(upBarOne);
        upBarOne.setAnchorPoint(0, 0);
        upBarOne.setPosition(weight, height / 2 + barHeight);
        // 第一条障碍物下
        downBarOne = CCSprite.sprite("down_bar.png");
        downBarOne.setScaleX(scaleX);
        downBarOne.setScaleY(scaleY);
        this.addChild(downBarOne);
        downBarOne.setAnchorPoint(0, 0);
        downBarOne.setPosition(weight, 0 - barHeight);
        // 第二条障碍物上
        upBarTwo = CCSprite.sprite("up_bar.png");
        upBarTwo.setScaleX(scaleX);
        upBarTwo.setScaleY(scaleY);
        this.addChild(upBarTwo);
        upBarTwo.setAnchorPoint(0, 0);
        upBarTwo.setPosition(weight * 3 / 2 + barWeight / 2, height / 2 + barHeight);
        // 第二条障碍物下
        downBarTwo = CCSprite.sprite("down_bar.png");
        downBarTwo.setScaleX(scaleX);
        downBarTwo.setScaleY(scaleY);
        this.addChild(downBarTwo);
        downBarTwo.setAnchorPoint(0, 0);
        downBarTwo.setPosition(weight * 3 / 2 + barWeight / 2, 0 - barHeight);

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


        bird = CCSprite.sprite("loading_01.png");
        this.addChild(bird);
        bird.setScale(1.5f);
        Y = bird.getPosition().y;



        bird.runAction(birdAction(1));

        bird.setPosition(CGPoint.make(weight / 3, height / 2));
        this.setIsTouchEnabled(true);

        // 循环刷新处理
        this.schedule("refreshBar", 0.01f);
        this.schedule("refreshFloor", 0.01f);
        this.schedule("birdState", 0.1f);
    }

    public void birdState(float dt) {
      if (Y > bird.getPosition().y)
      {
         bird.runAction(birdAction(2));
      }
      if (Y < bird.getPosition().y)
      {
          bird.runAction(birdAction(0));
      }
      if (Y == bird.getPosition().y)
      {
          bird.runAction(birdAction(1));
      }
      Y = bird.getPosition().y;
    }

    // 小鸟动作
    private CCRepeatForever birdAction(int index) {
        String picName = null;
        switch (index) {
            case 0:
                picName = "birdUp";
                break;
            case 1:
                picName = "birdNor";
                break;
            case 2:
                picName = "birdDown";
                break;
            case 3:
                picName = "birdDie";
                break;
        }
        CCAnimation animation = CCAnimation.animation("dance", 0.01f);
        for (int i = 1; i < 4; i++) {
            animation.addFrame(new CCFormatter().format(picName + "%d.png", i));

        }
        CCIntervalAction action = CCAnimate.action(animation);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(action);
        return ccRepeatForever;
    }

    // 刷新障碍
    public void refreshBar(float dt) {
        upBarOne.setPosition(upBarOne.getPosition().x - speep, upBarOne.getPosition().y);
        downBarOne.setPosition(downBarOne.getPosition().x - speep, downBarOne.getPosition().y);
        if (upBarOne.getPosition().x < -barWeight) {
            Random random = new Random();
            int i = random.nextInt((int) barHeight);
            upBarOne.setPosition(weight, height / 2 + barHeight - i);
            LogUtil.d("czs:" + (height / 2 + barHeight + i));
            downBarOne.setPosition(weight, 0 - barHeight - i);
        }


        upBarTwo.setPosition(upBarTwo.getPosition().x - speep, upBarTwo.getPosition().y);
        downBarTwo.setPosition(downBarTwo.getPosition().x - speep, downBarTwo.getPosition().y);
        if (upBarTwo.getPosition().x < -barWeight) {
            Random random = new Random();
            int i = random.nextInt((int) barHeight);
            upBarTwo.setPosition(weight, height / 2 + barHeight + i);
            downBarTwo.setPosition(weight, 0 - barHeight + i);
        }
    }

    // 刷新地板
    public void refreshFloor(float dt) {
        floorOne.setPosition(floorOne.getPosition().x - speep, floorOne.getPosition().y);
        if (floorOne.getPosition().x < -weight) {
            floorOne.setPosition(0.0f, floorOne.getPosition().y);
        }
        floorTwo.setPosition(floorTwo.getPosition().x - speep, floorTwo.getPosition().y);
        if (floorTwo.getPosition().x < 0) {
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
