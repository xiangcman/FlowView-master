在写该文章之前自己也写了一个用`RecyclerView`实现的流式布局[RecyclerView实现的流式布局](https://github.com/1002326270xc/LayoutManager-FlowLayout),也得到一些关注，但是也有不少的朋友提出了相关的问题，比如想规定行数的流式布局，还有item高度不统一时动态显示item的位置问题。于是近两天就简单写了个`ViewGroup`实现的流式布局：

这里在时给出了四种情况的用例:

**(一)文字统一高度，也就是默认的情况**

![默认情况.gif](https://github.com/1002326270xc/FlowView-master/blob/master/photos/基本.gif)

这里详见:[MainActivity](https://github.com/1002326270xc/FlowView-master/blob/master/app/src/main/java/com/single/flowlayout/MainActivity.java)

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
<declare-styleable name="LineFlowLayout">
        <!--规定行数-->
        <attr name="flow_line_count" format="integer" />
        <!--文本是否居中-->
        <attr name="is_line_center" format="boolean" />
</declare-styleable>
```
**后期添加:**
最近在学习自定义滑动控件，自己也给该流式布局加上滑动的处理，上面的demo里面在时用的是`ScrollView`处理滑动

### 关于我:

**email:** a1002326270@163.com

**csdn:**[enter](http://blog.csdn.net/u010429219/article/details/72897017)

**简书:**[enter](http://www.jianshu.com/p/67c4bd0e2091)
