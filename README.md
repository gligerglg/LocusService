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
Here we have 2 overload methods if you do use first method, when the provider is disabled in runtime, an alert dialog will be
pop up automatically to enable GPS service. Using 2nd method you can choose whether you are prefer to use this service or not.

```java

	//Automatic Provider Availability detection active (default)
        LocusService locusService = new LocusService(this);
	
	//Automatic provider availability detection can be defined by user
	LocusService locusService = new LocusService(this,false);	//Here we doesn't expect provider detection
	
```


If you prefer to get your real GPS position information with a single fix you can use following mrthod.


```java

        Location location_gps = locusService.getGPSLocation();
        if(location_gps!=null)
	{
		//Use this location for your work
		double latitude = location_gps.getLatitude();
		double longitude = location_gps.getLongitude();
	}
		
```


If you prefer to get your real Network position information with a single fix you can use following mrthod.

```java

        Location location_net = locusService.getNetLocation();
        if(location_net!=null)
	{
		//Use this location for your work
		double latitude = location_net.getLatitude();
		double longitude = location_net.getLongitude();
	}
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

You can get the real-time location either GPS or Net provider.
Start Listening by invoking below method. You need to specify the interval between each location update in miliseconds.

```java

	//Real-time location by GPS provider
        locusService.startRealtimeGPSListening(1000); 	//interval between 2 readings. hear it is 1 sec
	
	//Real-time location by Network provider
        locusService.startRealtimeNetListening(1000); 	//interval between 2 readings. hear it is 1 sec
	
```

You can stop listening whenever you want.

```java

        locusService.stopRealTimeGPSListening();
	locusService.stopRealTimeNetListening();
```

Sometimes you may need to know whether a provider is enabled or not. Here we have 2 methods to know that.

```java

	boolean gps_status = isGPSProviderEnabled();
	boolean net_status = isNetProviderEnabled();
```
How did you initialize the LocusService intent? Using 1st method? then if your service provider is disabled in runtime
an Alert dialog will show and tell you to enable it. What if you've chosen the 2nd mrthod? then you can display a custom 
alert dialog box with your own message. Very simple

```java

	openSettingsWindow("Message to Show!");
	
```
	

That's All. Happy Coding ;)
