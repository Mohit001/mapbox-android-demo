package com.mapbox.mapboxandroiddemo.examples.dds;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import static com.mapbox.mapboxsdk.style.expressions.Expression.color;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;

/**
 * Use data-driven styling to set circles' colors based on imported vector data.
 */
public class StyleCirclesCategoricallyActivity extends AppCompatActivity {

  private MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Mapbox access token is configured here. This needs to be called either in your application
    // object or in the same activity which contains the mapview.
    Mapbox.getInstance(this, getString(R.string.access_token));

    // This contains the MapView in XML and needs to be called after the access token is configured.
    setContentView(R.layout.activity_dds_style_circles_categorically);

    mapView = (MapView) findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(MapboxMap mapboxMap) {

        VectorSource vectorSource = new VectorSource(
          "ethnicity-source",
          "http://api.mapbox.com/v4/examples.8fgz4egr.json?access_token=" + Mapbox.getAccessToken()
        );
        mapboxMap.addSource(vectorSource);

        CircleLayer circleLayer = new CircleLayer("population", "ethnicity-source");
        circleLayer.setSourceLayer("sf2010");
        circleLayer.withProperties(
          circleRadius(
            interpolate(
              exponential(1.75f),
              zoom(),
              stop(12, 2f),
              stop(22, 180f)
            )),
          circleColor(
            match(get("ethnicity"), color(Color.parseColor("#000000")),
              stop("white", color(Color.parseColor("#fbb03b"))),
              stop("Black", color(Color.parseColor("#223b53"))),
              stop("Hispanic", color(Color.parseColor("#e55e5e"))),
              stop("Asian", color(Color.parseColor("#3bb2d0"))),
              stop("Other", color(Color.parseColor("#cccccc"))))));

        mapboxMap.addLayer(circleLayer);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
