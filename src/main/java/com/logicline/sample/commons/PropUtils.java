package com.logicline.sample.commons;

import com.logicline.sample.App;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class PropUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtils.class);

    private Properties mainProperties;

    private String mainPropsLocation;

    private static final String PROPS_PATH = "agent.properties";
    private static final String LOG_LEVEL_PREFIX = "logging.level.";

    @PostConstruct
    private void initMainPropertyFile() throws AgentRuntimeException {
        final String configEnvVar = App.getConfigDir();
        final Path configPath = Paths.get(configEnvVar, PROPS_PATH);
        if (configPath.toFile().exists()) {
            LOGGER.info("Changing config-file path {} to {}", mainPropsLocation, configPath);
            mainPropsLocation = configPath.toString();
            mainProperties = new Properties();
            fromFile(mainPropsLocation, mainProperties);
        } else {
            throw new AgentRuntimeException("File " + configPath.toString() + " does not exist.");
        }
    }

    public String getProperty(final C8yProperties c8yProperty) {
        if (!mainProperties.containsKey(c8yProperty.toString())) {
            return null;
        }
        return mainProperties.getProperty(c8yProperty.toString()).trim();
    }

    public void savePropertyToFile(C8yProperties c8yProperty, String value) {
        LOGGER.debug("Saving property {} with value {} to properties file.", c8yProperty, value);
        final Properties props = getMainProperties();
        props.setProperty(c8yProperty.toString(), value);
        toFile(props, getMainPropsLocation());
    }

    public Properties getMainProperties() {
        return mainProperties;
    }

    public Map<String, String> getLogLevelProperties() {
        return mainProperties.entrySet().stream()
                .filter(entry -> entry.getKey().toString().startsWith(LOG_LEVEL_PREFIX)).collect(
                        Collectors.toMap(e -> StringUtils.removeStart(e.getKey().toString(), LOG_LEVEL_PREFIX),
                                e -> e.getValue().toString()));
    }

    private void fromFile(final String file, final Properties props) {
        try (FileReader reader = new FileReader(file)) {
            props.load(reader);
        } catch (final IOException iox) {
            LOGGER.warn("Configuration file {} cannot be read, assuming empty configuration", file);
        }
    }

    public String toFile(final Properties props, final String file) {
        try (FileWriter writer = new FileWriter(file)) {
            props.store(writer, null);
        } catch (final IOException iox) {
            LOGGER.warn("Configuration file {} cannot be written", file);
            return "Error";
        }
        return null;
    }

    public String getMainPropsLocation() {
        return mainPropsLocation;
    }
}
