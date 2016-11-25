LikeAnimatorUtil
===

可以通过简单的调用，为你的控件添加点赞动画效果


优势
---


> *不改变原有布局，不受布局影响

> *使用灵活，支持多种自定义属性


用法
---

``` java
likeAnimatorManager = new LikeAnimatorManager(this,targetView,imgResourceIdArray);
//动画视图的大小和位置默认为target的大小和位置，可以通过setLocation()和setSize()自由配置
//支持自定义的轨迹动画生成器setAnimGenerator(customAnimGenerator)
likeAnimatorManager.setAnimDelay(50);//单个动画的间隔，默认200ms
likeAnimatorManager.setAnimCount(50);//点赞动画的数量，默认20
likeAnimatorManager.setAnimDuration(1000);//单个动画的Duration，默认2000ms
likeAnimatorManager.setAnimPathHeight(1000);//动画轨迹的高度，默认600
likeAnimatorManager.setAnimHorizontalScope(600);//动画轨迹的横向最大范围，默认200
likeAnimatorManager.setAlphaAble(true);//是否开启透明度变化，默认false
likeAnimatorManager.setStartAlpha(0.2f);//起始透明度
likeAnimatorManager.setEndAlpha(1f);//结束透明度
likeAnimatorManager.setScaleAble(true);//是否开启缩放，默认false
likeAnimatorManager.setScaleScope(1.2f);//缩放动画的缩放范围：1-（1+scope），默认0.5,最小值 -1
likeAnimatorManager.play();

```

License
---

```
Copyright 2016 yzr007

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

```