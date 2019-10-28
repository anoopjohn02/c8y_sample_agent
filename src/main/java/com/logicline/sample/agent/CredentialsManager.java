package com.logicline.sample.agent;

import com.cumulocity.model.authentication.CumulocityCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {

  private static final Logger logger = LoggerFactory.getLogger(CredentialsManager.class);

  public static final String DEVICE_PROPS_LOCATION = "./cfg/device.properties";

  private static final String DEFAULT_BOOTSTRAP_TENANT = "management";
  private static final String DEFAULT_BOOTSTRAP_USER = "devicebootstrap";
  private static final String DEFAULT_BOOTSTRAP_PASSWORD = "Fhdt1bb1f";

  private final String host;
  private final String devicePropsFile;

  private final CumulocityCredentials deviceCredentials;
  private final CumulocityCredentials bootstrapCredentials;

  public static CredentialsManager defaultCredentialsManager(String host) {
    return new CredentialsManager(DEVICE_PROPS_LOCATION, host);
  }

  public CredentialsManager(String devicePropsFile, String host) {
    this.devicePropsFile = devicePropsFile;
    Properties deviceProps = fromFile(devicePropsFile);
    this.deviceCredentials = initDeviceCredentials(deviceProps);
    this.bootstrapCredentials = initBootstrapCredentials();
    this.host = host;
  }

  private static CumulocityCredentials initBootstrapCredentials() {
    return new CumulocityCredentials(DEFAULT_BOOTSTRAP_TENANT, DEFAULT_BOOTSTRAP_USER,
        DEFAULT_BOOTSTRAP_PASSWORD, null);
  }

  private static CumulocityCredentials initDeviceCredentials(Properties deviceProps) {
    String user = deviceProps.getProperty("user");
    String password = deviceProps.getProperty("password");
    if (user == null || password == null) {
      return null;
    } else {
      return new CumulocityCredentials(deviceProps.getProperty("tenant", "demo"), user, password,
          null);
    }
  }

  public String getHost() {
    return host;
  }

  public CumulocityCredentials getDeviceCredentials() {
    return deviceCredentials;
  }

  public CumulocityCredentials getBootstrapCredentials() {
    return bootstrapCredentials;
  }

  public void saveDeviceCredentials(CumulocityCredentials cumulocityCredentials) {
    File file = new File(devicePropsFile);
    try {
      file.createNewFile();
    } catch (IOException ex) {
      throw new RuntimeException("Cant create file " + devicePropsFile, ex);
    }
    Properties props = new Properties();
    props.setProperty("user", cumulocityCredentials.getUsername());
    props.setProperty("password", cumulocityCredentials.getPassword());
    props.setProperty("tenant", cumulocityCredentials.getTenantId());
    toFile(props, devicePropsFile);
  }

  private Properties fromFile(String file) {
    Properties props = new Properties();
    fromFile(file, props);
    return props;
  }

  private void fromFile(String file, Properties props) {
    try (FileReader reader = new FileReader(file)) {
      props.load(reader);
      logger.debug("Read configuration file, current configuration: " + props);
    } catch (IOException iox) {
      logger.warn("Configuration file {} cannot be read, assuming empty configuration", file);
    }
  }

  private String toFile(Properties props, String file) {
    try (FileWriter writer = new FileWriter(file)) {
      props.store(writer, null);
    } catch (IOException iox) {
      logger.warn("Configuration file {} cannot be written", file);
      return iox.getMessage();
    }
    return null;
  }
}
