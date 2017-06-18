BoxLoaderView[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BoxLoaderView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5907)

===

![Screenshot](https://github.com/nipun-birla/BoxLoaderView/blob/master/preview.gif)

<h2>Overview</h2>
A clean and easy to use Animated Progress View in a Square
.
<h2>Usage</h2>

Add dependency in your build.gradle(app)

    dependencies {
        compile 'com.github.nipun-birla:BoxLoaderView:0.0.1'
    }

Put BoxLoaderView in your layout as required :

    <com.nipunbirla.boxloader.BoxLoaderView
                android:id="@+id/progress"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:strokeColor="#995876"
                app:loaderColor="#C67890"
                app:strokeWidth="20"
                app:speed="10"/>

Find view in your activity as :

    BoxLoaderView boxLoader = findViewById(R.id.progress);

To set stroke/background color :

    boxLoader.setStrokeColor(Color.WHITE);

To set Loader color :

        mLoader.setLoaderColor(Color.BLUE);

To set stroke width/outer margin :

        mLoader.setStrokeWidth(20);

To set Loader speed :

        mLoader.setSpeed(20);

<h2>Defaults</h2>

<b>Loader Color : </b> BLUE<br>
<b>Stroke/Background Color : </b> WHITE<br>
<b>Speed : </b> 10<br>
<b>Width : </b> 20



