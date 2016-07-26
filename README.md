# BottomSheet的用法

标签（空格分隔）：bottomSheet 

---

Android Support Library 23.2里的 Design Support Library新加了一个Bottom Sheets控件
我们来看下使用怎么使用

###(1)在xml中的布局：
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/cl"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:onClick="click"
        android:text="点我啊" />

    <android.support.v4.widget.NestedScrollView
        android:id="@+id/bottom_sheet"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_behavior="@string/bottom_sheet_behavior">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="300dp"
                android:background="#00ffff"
                android:text="Hello World!" />
        </LinearLayout>

    </android.support.v4.widget.NestedScrollView>
</android.support.design.widget.CoordinatorLayout>

```
###(2)在java中的代码
```java
public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private BottomSheetBehavior<View> from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        from.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变回调
                //newState 有四个状态 ：
                //展开 BottomSheetBehavior.STATE_EXPANDED
                //收起 BottomSheetBehavior.STATE_COLLAPSED
                //拖动 BottomSheetBehavior.STATE_DRAGGING
                //下滑 BottomSheetBehavior.STATE_SETTLING
                Log.d(TAG, "newState:" + newState);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，slideOffset为0-1 完全收起为0 完全展开为1
                Log.d(TAG, "slideOffset:" + slideOffset);
            }
        });

    }

    public void click(View view) {
        if (from.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            from.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            from.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
```
####效果图
  <img src="https://github.com/yingshirun/BottomSheet/blob/master/gif/bottomSheet.gif?raw=true"/>
  
  