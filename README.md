# LocusService (gliger.glg)
Android library to access location data in easy way.


<h3>What is LocusService</h3>

<pre>This android library can be used in any location based android application in easy way.
<ul>
  <li>GetGPSLocation() - Only one GPS location fix will be returned</li>
  <li>GetNetLocation() - Only one Network location fix will be returned</li>
  <li>RealTimeLocationListener - Used for tracking services. Real-time location will be returned</li>
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
	        compile 'com.github.gligerglg:LocusService:v3.0.0'
	}


<h4>Step : 2</h4>
Insert android manifest permission 


<pre>
        &ltuses-permission android:name="android.permisssion.ACCESS_FINE_LOCATION"/&gt
        &ltuses-permission android:name="android.permisssion.ACCESS_COARSE_LOCATION"/&gt
        &ltuses-permission android:name="android.permisssion.INTERNET"/&gt
	
</pre>

<h4>Step : 3</h4>
Create a LocusService instance and initialize it.

```java
        LocusService locusService = new LocusService(this);
```


If you prefer to get your real GPS position information with a single fix you can use following mrthod.


```java
        Location location_gps = locusService.GetGPSLocation();
        if(location_gps!=null)
		//Use this location for your work
```


If you prefer to get your real Neteork position information with a single fix you can use following mrthod.

```java
        Location location_net = locusService.GetNetLocation();
        if(location_net!=null)
		//Use this location for your work
```


If you are developing a tracking application, you need to have the real-time location change updated. Here it is very simple.<br>
First create a listener<br>

```java
        locusService.setRealTimeLocationListener(new LocusService.RealtimeListenerService() {
            @Override
            public void OnRealLocationChanged(Location location) {
                    txt_real.setText("RealTime Location\nLatitude\t" + location.getLatitude() + "\nLongitude\t" + 				    	location.getLongitude());
            }
        });
```

Then start Listening by invoking below method. You need to specify the interval between each location update in miliseconds.

```java
        locusService.StartRealtimeListening(0); //interval is '0' here.
```

You can stop listening whenever you want.

```java
        locusService.StopRealtimeListening();
```


That's All. Happy Coding ;)
