# SmoothLoadingButton
[![](https://jitpack.io/v/foxyoverdrive/SmoothLoadingButton.svg)](https://jitpack.io/#foxyoverdrive/SmoothLoadingButton)

With this library you can easily create and animate button within 3 states - normal, loading and finished. Nothing but Google Support Library inside.

# Basic Usage
1. Initiate your button in XML

```xml
<com.foxysoft.smoothloadingbutton.main.LoadingButton
        android:id="@+id/btn_test"
        android:layout_gravity="center"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:text="@string/do_something"
        app:textSizeSP="20"
        app:normalBackground="@drawable/shape_rounded_white_stroke_orange"
        app:finishedBackground="@drawable/shape_rounded_orange"
        app:textColor="@color/foxy"
        app:finishedIcon="@drawable/ic_vector_finished"/>
```

- `app:text="@string/do_something"` - String resourse, your desired text that will be displayed on your button.
- `app:textSizeSP="20"` - Int value, desired text size in SP.
- `app:normalBackground="@drawable/shape_rounded_white_stroke_orange"` - Drawabale resourse for Normal (default) and Loading button state.
- `app:finishedBackground="@drawable/shape_rounded_orange"` - Drawabale resourse for Finished button state.
- `app:textColor="@color/foxy"` - Color resourse for text color on your button.
- `app:finishedIcon="@drawable/ic_vector_finished"` - Drawable resourse for Finished button state, vector or not - doesn't matter.
- ProgressBar color depends on your `colorAccent`.

2. Initiate your button in Activity/Fragment/RV ViewHolder etc.

```java
private LoadingButton mBtnDoSomething;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDoSomething = findViewById(R.id.btn_test);
        mBtnDoSomething.setOnClickListener(this);
    }
```

3. Do something with your button :)

Use:
- `changeState(LoadingButtonState state)` to change it's state with animation
or
- `setState(LoadingButtonState state)` to change state immediately without animation (if you want to init button in Finished state e.g.)

4. Have fun! :)

# Import dependency 

Add jitpack repositorie in your __build.gradle__ root level
```gradle
allprojects {
  repositories {
	...
	maven { url "https://jitpack.io" }
  }
}
```

```gradle
dependencies {
	compile 'com.github.foxyoverdrive:SmoothLoadingButton:$latest_version'
}
```
