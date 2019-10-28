package com.logicline.sample.agent;

import c8y.IsDevice;
import c8y.RequiredAvailability;
import com.cumulocity.model.ID;
import com.cumulocity.model.authentication.CumulocityCredentials;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformImpl;
import com.cumulocity.sdk.client.SDKException;
import com.sun.jersey.api.client.ClientHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Agent {

    private static final Logger logger = LoggerFactory.getLogger(Agent.class);

    public static final String TYPE = "c8y_Linux";
    public static final String XTIDTYPE = "c8y_Serial";
    public static final String ALARMTYPE = "c8y_AgentStartupError";
    public static final long RETRY_WAIT_MS = 5000L;
    public static final int RESPONSE_INTERVAL_MIN = 3;

    private final Platform platform;
    private final ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
    private final DeviceBootstrapProcessor deviceBootstrapProcessor;
    private final String serial;
    private final String host;

    public Agent(String serial, String host) {
        this(CredentialsManager.defaultCredentialsManager(host), serial, host);
    }
    public Agent(CredentialsManager credentialsManager, String serial, String host) {
        this.serial = serial;
        this.host = host;
        logger.info("Starting agent");
        this.deviceBootstrapProcessor = new DeviceBootstrapProcessor(credentialsManager);
        // See {@link Driver} for an explanation of the driver life cycle.
        this.platform = initializePlatform(credentialsManager);
        initializeInventory();
    }

    private Platform initializePlatform(CredentialsManager credentialsManager) {
        CumulocityCredentials credentials = credentialsManager.getDeviceCredentials();
        if (credentials == null) {
            credentials = deviceBootstrapProcessor.process(specifySerialNumber());
        }
        if (credentials == null) {
            throw new RuntimeException("Can't bootstrap device!");
        }
        return new PlatformImpl(credentialsManager.getHost(), credentials);
    }

    private String specifySerialNumber() {
        return serial;
    }

    private void initializeInventory() throws SDKException {
        logger.info("Initializing inventory");
        String serial = specifySerialNumber();
        ID extId = asExternalId(serial);
        mo.setType(TYPE);
        mo.setName("EWD_" + serial);
        mo.set(new com.cumulocity.model.Agent());
        mo.set(new IsDevice());
        mo.set(new RequiredAvailability(RESPONSE_INTERVAL_MIN));

        checkConnection();

        logger.debug("Agent representation is {}, updating inventory", mo);

        if (new DeviceManagedObject(platform).createOrUpdate(mo, extId, null)) {

            logger.debug("Agent was created in the inventory");
        } else {
            logger.debug("Agent was updated in the inventory");
        }

    }

    private static ID asExternalId(String serial) {
        String id = "serial-" + serial;
        ID extId = new ID(id);
        extId.setType(XTIDTYPE);
        return extId;
    }

    private void checkConnection() throws SDKException {
        logger.info("Checking platform connectivity");
        boolean connected = false;

        while (!connected) {
            try {
                platform.getInventoryApi().getManagedObjects().get(1);
                connected = true;
            } catch (ClientHandlerException x) {
                logger.debug("No connectivity, wait and retry", x);
                try {
                    Thread.sleep(RETRY_WAIT_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public ManagedObjectRepresentation getMo() {
        return this.mo;
    }
}
