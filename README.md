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
  
  
  


----------

BottomSheetDialog 的使用
=====================

  BottomSheetDialog的使用很简单，直接获得控件后去setContentView就行，如果是列表的话 还会有个很好的展示效果，直接上代码：

列表展示：
-----
###(1)java代码
```java
public class BottomSheetDialogActivity extends AppCompatActivity{
    private List<String> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for(int i=0; i<20; i++){
            mList.add("item "+i);
        }
    }

    public void click1(View view){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        //创建recyclerView
        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mList,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                Toast.makeText(BottomSheetDialogActivity.this, "item "+position, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }
 
}
```
###(2)recyclerView的adapter

```java
public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<String> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_layou, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(list.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClickListener(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClickListener(View item, int position);
    }
}

```
###(3)item的布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:orientation="vertical">

    <TextView
        android:id="@+id/item_tv"
        android:layout_width="match_parent"
        android:layout_height="50dp" />
</LinearLayout>
```
###(4)效果图：
<img src="https://github.com/yingshirun/BottomSheet/blob/master/gif/bottomSheetList.gif?raw=true">

显示普通view
--------

###(1)java代码
```java
   public void click2(View view){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View inflate = View.inflate(this, R.layout.bottomsheetdalig_layout, null);
        View qq = inflate.findViewById(R.id.share_qq);
        View wx = inflate.findViewById(R.id.share_wx);
        View sina = inflate.findViewById(R.id.share_sina);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到qq", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到wx", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到sina", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(inflate);
        bottomSheetDialog.show();
    }
```
###(2)xml布局
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <LinearLayout
        android:id="@+id/share_qq"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:orientation="horizontal">
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@mipmap/ic_launcher"/>
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="分享到QQ"/>
    </LinearLayout>
    <LinearLayout
        android:id="@+id/share_wx"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:orientation="horizontal">
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@mipmap/ic_launcher"/>
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="分享到微信"/>
    </LinearLayout>
    <LinearLayout
        android:id="@+id/share_sina"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:orientation="horizontal">
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@mipmap/ic_launcher"/>
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="分享到微博"/>
    </LinearLayout>

</LinearLayout>

```
###(3)效果图
<img src="https://github.com/yingshirun/BottomSheet/blob/master/gif/bottomSheetView.gif?raw=true">

## activity的布局 ##
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:onClick="click1"
        android:text="很多列表数据"/>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="click2"
        android:text="少量数据"/>

</LinearLayout>

```