[![](https://jitpack.io/v/1002326270xc/FlowView-master.svg)](https://jitpack.io/#1002326270xc/FlowView-master/v1.0)

在写该文章之前自己也写了一个用`RecyclerView`实现的流式布局[RecyclerView实现的流式布局](https://github.com/1002326270xc/LayoutManager-FlowLayout),也得到一些关注，但是也有不少的朋友提出了相关的问题，比如想规定行数的流式布局，还有item高度不统一时动态显示item的位置问题。于是近两天就简单写了个`ViewGroup`实现的流式布局：

这里在时给出了四种情况的用例:

**(一)文字统一高度，也就是默认的情况**

![默认情况.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/基本.gif)

这里详见:[NormalLoadActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/NormalLoadActivity.java)


**(二)不同高度的文本，没设置居中显示的情况**

![不同高度的文本，没设置居中显示的情况.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/不同高度的文本，没设置居中显示的情况.gif)

这里详见:[DiffHeightTextActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/DiffHeightTextActivity.java)


**(三)不同高度的文本，设置居中显示的情况**

![不同高度的文本，设置居中显示的情况.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/不同高度的文本，设置居中显示的情况.gif)

这里详见:[DiffHeightTextCenterActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/DiffHeightTextCenterActivity.java)


**(四)规定行数的流式布局**

![规定行数的文本.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/规定行数的文本.gif)

这里详见:[LineFlowActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/LineFlowActivity.java)

**属性:**
```xml
 <declare-styleable name="FlowLayout">
        <!--文本是否居中-->
        <attr name="is_line_center" format="boolean" />
 </declare-styleable>

<declare-styleable name="LineFlowLayout">
        <!--规定行数-->
        <attr name="flow_line_count" format="integer" />
</declare-styleable>

<declare-styleable name="ScrollFlowLayout">
        <!--上边界阴影颜色-->
        <attr name="effect_top_color" format="color" />
        <!--下边界阴影颜色-->
        <attr name="effect_bottom_color" format="color" />
        <!--滑动到顶部或底部是否需要阴影效果-->
        <attr name="need_effect" format="boolean" />
</declare-styleable>
```

**gradle依赖**
```java
allprojects {
        repositories {
                ...
                maven { url 'https://jitpack.io' }
        }
}

dependencies {
        compile 'com.github.1002326270xc:FlowView-master:v1.0'
}
```

**后期添加(自带滑动结构的流式布局):**

![自带滑动结构的流式布局.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/自带滑动结构的流式布局.gif)

这里详见:[ScrollFlowActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/ScrollFlowActivity.java)
**thanks:** 这里滑动的处理借鉴的[SuitLines](https://github.com/whataa/SuitLines)(一个图标控件)、[SwipeDelMenuLayout](https://github.com/mcxtzhang/SwipeDelMenuLayout)(侧拉菜单)

### 关于我:

**email:** a1002326270@163.com

**csdn:**[enter](http://blog.csdn.net/u010429219/article/details/72897017)

**简书:**[enter](http://www.jianshu.com/p/67c4bd0e2091)
