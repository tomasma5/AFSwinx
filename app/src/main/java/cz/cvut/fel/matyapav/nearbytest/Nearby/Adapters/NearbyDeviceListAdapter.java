package cz.cvut.fel.matyapav.nearbytest.Nearby.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.Nearby.Device;
import cz.cvut.fel.matyapav.nearbytest.R;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyDeviceListAdapter extends ArrayAdapter<Device> {

    public NearbyDeviceListAdapter(@NonNull Context context, @NonNull List<Device> devices) {
        super(context, 0, devices);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        Device device = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.nearby_device_list_item, viewGroup, false);
        }
        ImageView deviceIcon = (ImageView) view.findViewById(R.id.device_icon);
        TextView deviceName = (TextView) view.findViewById(R.id.device_name);
        TextView deviceAddress = (TextView) view.findViewById(R.id.device_address);
        TextView deviceType = (TextView) view.findViewById(R.id.device_type);

        if(device != null) {
            deviceName.setText(device.getName() != null? device.getName() : "Cannot get name");
            deviceAddress.setText(device.getMacAddress() != null ? device.getMacAddress() : "Cannot get address");
            deviceType.setText(device.getDeviceType().toString());
            switch (device.getDeviceType()){
                case BLUETOOTH:
                    deviceIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bluetooth));
                    break;
                case WIFI_DEVICE:
                    deviceIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_device));
                    break;
                case WIFI_NETWORK:
                    deviceIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_wifi));
                    break;
            }
        }
        return view;
    }
}
