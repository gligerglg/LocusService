# LocusService (gliger.glg)
Android library to access location data in easy way.


<h3>What is LocusService</h3>

<pre>This android library can be used in any location based android application in easy way.
<ul>
  <li>GetGPSLocation() - Only one GPS location fix will be returned</li>
  <li>GetNetLocation() - Only one Network location fix will be returned</li>
  <li>RealTimeLocationListener - Used for tracking services. Real-time location will be returned</li>
  <li>SetProximityAlert() - Proximity alerts can be setup for a given location and radius</li>
</ul></pre>

<h3>How Works?</h3>

<h4>Step : 1.1 -  Add the JitPack repository to your build file </h4>
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

<h4>Step : 1.2 -  Add the dependency</h4>

	dependencies {
	        compile 'com.github.gligerglg:LocationService:v1.0'
	}

<h4>Step : 2</h4>
Insert android manifest permission 

<pre>
        &ltuses-permission android:name="android.permisssion.ACCESS_FINE_LOCATION"&gt&lt/uses-permission&gt
        &ltuses-permission android:name="android.permisssion.ACCESS_COARSE_LOCATION"&gt&lt/uses-permission&gt
        &ltuses-permission android:name="android.permisssion.INTERNET"&gt&lt/uses-permission&gt
</pre> 
<h4>Step : 3</h4>
Create a LocusService instance and initialize it.
<pre><code>
        LocusService locusService = new LocusService(this);
</code></pre>

If you prefer to get your real GPS position information with a single fix you can use following mrthod.
<pre><code>
        Location location_gps = locusService.GetGPSLocation();
        *Returning location can be null. Therefore check nullability before using it.
</code></pre>

If you prefer to get your real Neteork position information with a single fix you can use following mrthod.
<pre><code>
        Location location_net = locusService.GetNetLocation();
        *Returning location can be null. Therefore check nullability before using it.
</code></pre>

If you are developing a tracking application, you need to have the real-time location change updated. Here it is very simple.<br>
First create a listener<br>
<pre><code>
        locusService.setRealTimeLocationListener(new LocusService.RealtimeListenerService() {
            @Override
            public void OnRealLocationChanged(Location location) {
                    txt_real.setText("RealTime Location\nLatitude\t" + location.getLatitude() + "\nLongitude\t" + location.getLongitude());
            }
        });
</code></pre>
Then start Listening by invoking below method. You need to specify the interval between each location update in miliseconds.
<pre><code>
        locusService.StartRealtimeListening(0); //interval is '0' here.
</code></pre>
You can stop listening whenever you want.
<pre><code>
        locusService.StopRealtimeListening();
</code></pre>

The other service of this library is giving chance to setup proximity alerts.<br>
Simply Add this code segment!
<pre><code>
        locusService.SetProximityAlert(double latitude,double longitude, long radius);
        locusService.SetProximityAlert(double latitude,double longitude, long radius, long expireTime);
</code></pre>
Here we do have 2 methods, You can use any of them. First method doesen't have any expire time for the proximity alert.<br>
Now you need to create a Reciever class.
<pre><code>
       public class YourReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {

                final String key = LocationManager.KEY_PROXIMITY_ENTERING;
                final Boolean entering = intent.getBooleanExtra(key, false);

                if (entering) {
                    Toast.makeText(context, "entering", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "exiting", Toast.LENGTH_SHORT).show();
                }
            }
        }
</code></pre>
Now register the receiver in your manifest. Use following code segment
<pre>
        &ltreceiver android:name="yourpackage.YourReceiver " &gt
            &ltintent-filter&gt
                &ltaction android:name="GLIGER_PROXIMITY_ALERT" /&gt
            &lt/intent-filter&gt
        &lt/receiver&gt
</pre>
That's All. Happy Coding ;)
