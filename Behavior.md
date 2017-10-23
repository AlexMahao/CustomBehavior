## 自定义Behavior以及相关源码分析

### Behavior方法

```
public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params)

```

调用时机： 当这个`behavior`被加入到`LayoutParams`中时，实际是指`LayoutParams`被创建时回调。




** public void onDetachedFromLayoutParams()**

当`behavior`从`LayoutParams`中移除时回调，当`View`从`CoordinatorLayout`中移除时不会被回调。


**public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev)**


其回调时机比较复杂，具体逻辑如下：

- 遍历所有的`behavior`, 按照顺序执行`onInterceptTouchEvent()`
- 如果其中一个`behhavior`的`onInterceptTouche`返回true
	- 如果当前事件是`ACTION_DOWN`:则后面的`behavior`的`onIntercep`就不会被回调
	- 如果当前事件是其他，则后面会被收到一个`ACTION_CANCEL` 事件

在`CoordinatiorLayout`的`onInterceptTouchEvent()`中，如果有某一个`Behavior`返回`true`，则会保存该对象，以便`onTouchEvent()`时使用。

> 如果该方法返回true，则`CoordinatiorLayout`的`onInterceptTouchEvent()`方法也会返回true;

**public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev)**

- 如果在`onInterceptTouchEvent()`时，某一个`behavior`返回了true,则会直接调用它的`onTouchEvent()`方法。
- 如果不符合上面的条件，他会走和`onInterceptTouchEvent()`时的类似逻辑
	- `Down`事件时，在某一个`behavior`的`onTouchEvent()`返回`true`之前的`behavior`都会被调用，之后的不会调用。
	- 其他事件，在某一个`behavior`的`onTouchEvent()`返回`true`之前的`behavior`都会被调用，之后的会受到一个`CANCEL`事件。

> 注意，如果再`onInterceptTouchEvent()`时没有找到一个处理的`Behavior`对象，会在`onTouchEvent`时遍历所有`behavior`，一旦找到一个`behavior`处理，则不会再一次遍历所有，而是使用保存的`behavior`对象。


