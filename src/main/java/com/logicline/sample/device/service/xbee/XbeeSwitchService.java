package com.logicline.sample.device.service.xbee;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.WiFiDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.io.IOMode;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeProtocol;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import com.logicline.sample.device.service.SwitchService;
import com.logicline.sample.device.service.xbee.common.GpioPorts;
import com.logicline.sample.device.service.xbee.common.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XbeeSwitchService implements SwitchService {

    private static final Logger logger = LoggerFactory.getLogger(XbeeSwitchService.class);

    private static final String PORT = "/dev/ttyAMA0/";
    private static final int BAUD_RATE = 9600;
    private static final String REMOTE_DEVICE_MAC_ADDRESS = "000000409D5EXXXX";
    private XBeeDevice xbeeDevice;

    @Override
    public void init() {
        xbeeDevice = new XBeeDevice(PORT, BAUD_RATE);
    }

    @Override
    public void switchOn(String switchId) throws AgentRuntimeException {
        logger.info("XBee Operation - On");
        send(switchId, Operations.ON);
    }

    @Override
    public void switchOff(String switchId) throws AgentRuntimeException {
        logger.info("XBee Operation - Off ");
        send(switchId, Operations.OFF);
    }

    private void send(String switchId, Operations operation) throws AgentRuntimeException{
        try{
            xbeeDevice.open();
            GpioPorts port = GpioPorts.getByName(switchId);

            // Instantiate a remote XBee device object.
            RemoteXBeeDevice remoteXBeeDevice = new RemoteXBeeDevice(xbeeDevice,  new XBee64BitAddress(REMOTE_DEVICE_MAC_ADDRESS));

            switch (operation){
                case ON:
                    remoteXBeeDevice.setIOConfiguration(port.getLine(), IOMode.DIGITAL_OUT_HIGH);
                    break;
                case OFF:
                    remoteXBeeDevice.setIOConfiguration(port.getLine(), IOMode.DIGITAL_OUT_LOW);
                    break;
            }

        }catch (XBeeException e) {
            logger.error("Error occured while connecting remote device", e);
            throw new AgentRuntimeException("Error occured while connecting remote device", e);
        } finally {
            xbeeDevice.close();
        }
    }

    private void sendTextData(String data){
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
