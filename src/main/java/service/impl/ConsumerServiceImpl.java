package service.impl;

import dao.DeviceStatusWithNearbyDao;
import model.DeviceStatusWithNearby;
import model.partial.Device;
import service.ConsumerService;
import utils.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of service for getting data from application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger LOGGER = Logger.getLogger(ConsumerServiceImpl.class.getName());
    private static final int THREAD_POOL_SIZE = 10;
    private ExecutorService executor;


    /**
     * Adds new record with device status and nearby devices into database
     */
    @Inject
    DeviceStatusWithNearbyDao deviceStatusWithNearbyDao;

    public void addNewDeviceNearbyStatusRecord(DeviceStatusWithNearby record) {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        }
        executor.submit(new SaveRecordRunnable(record));
    }

    private void fillNearbyDevicesWithMacVendorInfo(DeviceStatusWithNearby record) {
        String macVendor;
        for (Device device : record.getNearbyDevices()) {
            macVendor = getMacVendorFromApi(device.getMacAddress());
            if (macVendor != null) {
                device.setMacVendor(macVendor);
            }
        }
    }

    private String getMacVendorFromApi(String macAddress) {

        try {
            String urlString = Constants.MAC_VENDORS_API_URL + macAddress;
            URL urlObj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            LOGGER.log(Level.INFO, "\nSending 'GET' request to URL : " + urlString);
            LOGGER.log(Level.INFO, "Response Code : " + responseCode);

            //readResponse only if api returned something
            if (responseCode < HttpURLConnection.HTTP_INTERNAL_ERROR) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //log response
                //if vendor is found api returns 200, if mac address was malformed - 400 and if vendor was not found 404
                LOGGER.log(Level.INFO, response.toString());
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return response.toString();
                }
            }

        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "The URL is incorrect\n" + e.toString());
            return null;
        } catch (ProtocolException e) {
            LOGGER.log(Level.SEVERE, "The protocol is incorrect\n" + e.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "There was a problem during reading response stream\n" + e.toString());
        }
        return null;
    }

    private class SaveRecordRunnable implements Runnable {

        private DeviceStatusWithNearby record;

        SaveRecordRunnable(DeviceStatusWithNearby record) {
            this.record = record;
        }

        @Override
        public void run() {
            //Mac vendors api is very limited in FREE version so we don't use it until paid
            //fillNearbyDevicesWithMacVendorInfo(record);
            deviceStatusWithNearbyDao.create(record);
        }
    }
}
