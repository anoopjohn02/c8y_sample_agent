package com.logicline.sample.device.service.xbee;

import com.digi.xbee.api.WiFiDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.models.XBeeProtocol;
import com.logicline.sample.device.service.SwitchService;
import com.logicline.sample.device.service.raspberry.RaspberryPiLightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XbeeSwitchService implements SwitchService {

    private static final Logger logger = LoggerFactory.getLogger(XbeeSwitchService.class);

    private static final String PORT = "/dev/ttyAMA0/";
    private static final int BAUD_RATE = 9600;
    private XBeeDevice xbeeDevice;

    @Override
    public void init() {
        xbeeDevice = new XBeeDevice(PORT, BAUD_RATE);
    }

    @Override
    public void switchOn(String switchId) {
        logger.info("XBee Operation - On");
        String dataToSend = switchId+"_ON";
        sendData(dataToSend);
    }

    @Override
    public void switchOff(String switchId) {
        logger.info("XBee Operation - Off ");
        String dataToSend = switchId+"_OFF";
        sendData(dataToSend);
    }

    private void sendData(String data){
        try {
            byte[] dataToSend = data.getBytes();
            xbeeDevice.open();

            if (xbeeDevice.getXBeeProtocol() == XBeeProtocol.XBEE_WIFI) {
                xbeeDevice.close();
                xbeeDevice = new WiFiDevice(PORT, BAUD_RATE);
                xbeeDevice.open();
                ((WiFiDevice)xbeeDevice).sendBroadcastIPData(0x2616, dataToSend);
            } else{
                xbeeDevice.sendBroadcastData(dataToSend);
            }
        } catch (Exception e) {
            logger.error("Error occured during sending to XBee device ", e);
        }
        finally {
            xbeeDevice.close();
        }
    }
}
