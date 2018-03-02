package plugins.gligerglg.locusservice;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/*
 * Created by Gayan Lakshitha on 2/20/2018.
 */

public class LocusService {
    private LocationManager locationManager;
    private Context context;
    private Location final_net, final_gps;
    private RealtimeListenerService listenerService;
    private boolean isAutoDetectionEnabled;

    private LocationListener realtime_listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                listenerService.OnRealLocationChanged(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            if(isAutoDetectionEnabled)
                openSettingsWindow("Do You Want to Enable GPS?");
        }
    };

    public interface RealtimeListenerService {
        public void OnRealLocationChanged(Location location);
    }

    public LocusService(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isAutoDetectionEnabled = true;
    }

    public LocusService(Context context,boolean autoDetectProviderAvailability) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isAutoDetectionEnabled = autoDetectProviderAvailability;
    }

    public void setRealTimeLocationListener(RealtimeListenerService listener) {
        this.listenerService = listener;
    }

    public Location getGPSLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMessage("Check Manifest Permission!");
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    final_gps = location;
                    locationManager.removeUpdates(this);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                if(isAutoDetectionEnabled)
                    openSettingsWindow("Do You Want to Enable GPS?");
            }
        });

        return final_gps;
    }

    public Location getNetLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMessage("Check Manifest Permission!");
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    final_net = location;
                    locationManager.removeUpdates(this);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                if(isAutoDetectionEnabled)
                    openSettingsWindow("Do You Want to Enable GPS?");
            }
        });

        return final_net;
    }

    public void openSettingsWindow(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void getRealtimeGPSLocation(long interval) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMessage("Check Manifest Permission!");
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval, 0, realtime_listener);
    }

    private void getRealtimeNetLocation(long interval) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastMessage("Check Manifest Permission!");
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval, 0, realtime_listener);
    }

    public void startRealtimeGPSListening(long interval) {
        getRealtimeGPSLocation(interval);
    }

    public void stopRealTimeGPSListening() {
        locationManager.removeUpdates(realtime_listener);
    }

    public void startRealtimeNetListening(long interval) {
        getRealtimeNetLocation(interval);
    }

    public void stopRealTimeNetListening() {
        locationManager.removeUpdates(realtime_listener);
    }

    public boolean isGPSProviderEnabled(){return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}

    public boolean isNetProviderEnabled(){return  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}

    private void ToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
