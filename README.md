## div要水平居中
可以margin:0 auto来实现.注意此div必须有宽度.
## 文字垂直居中
可以line-height=height来实现.
但是如果一个div里面同时有img和文字,没法这样来实现垂直居中
可以使用vertical-align: middle来实现文字和图片居中对齐,只对行内和行内块元素有效
vertical-align默认值为bottom,这个默认值会导致图片如果设置边框，
会有底部的空白空隙。也可以通过设置为middle解决
## 文字水平居中 
text-align: center;

## 黑色半透明遮罩效果
可以放2个div使用定位定位到相同位置
然后mask div设置background为 rgba(0,0,0,0.4)

## a标签做成button
a标签可以通过设置display: inline-block后设置边框,width等来做成类似button的效果
a标签默认无法设置width属性.但是仍然可以设置margin和padding来撑开

## iconfont的使用
压缩包解压到项目根目录下.
引入此文件夹中的iconfont.css然后使用其中的class iconfont
定义一个span class="iconfont"
打开demo_index.html复制最顶上的Unicode码即为所要的图标

如果要在伪元素中使用字体图标,content本来就是接收16进制表示的字符
假设字体本来是
&#xe602;
这里的&#用于说明这里是字体图标,x表示16进制
所以写在伪元素中只需要是
\e602

## 文字溢出显示为省略号
可以通过text-overflow: ellipsis
属性并设置overflow: hidden来解决
单行溢出要设置 white-space: nowrap
多行溢出要display: -webkit-box
-webkit-line-clamp: 3 设置第3行显示不下的时候展示省略号
-webkit-box-orient: vertical

## img居中
父div设置text-align: center;并设置一个padding-top把图片挤下去

## div画圆形
border-radius: 50%

## 图片适配div尺寸
width: 100%
注意父div必须有width

## 关于li小圆圈水平居中
https://www.zhangxinxu.com/wordpress/2022/11/about-css-list-style-type-item/

想要显示小圆圈则元素的display必须为list-item所以如果修改了display属性

(例如希望用display: inline-block配合父ol text-align:center实现居中)

就会发现小圆圈不见了

经过若干测试,最好的方式还是flex布局

https://zhuanlan.zhihu.com/p/139223943