package com.logicline.sample.agent.drivers;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.agent.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggingDriver implements Driver, OperationExecutor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingDriver.class);

    private Platform platform;

    @Autowired
    private GId gid;

    @Override
    public void initialize(Platform platform) throws Exception {
        this.platform = platform;
    }

    @Override
    public void start() {

    }

    @Override
    public void execute(OperationRepresentation operation) throws Exception {
        logger.info("LOGGING Operation successfully called...");
    }

    @Override
    public ExecuterType getType() {
        return ExecuterType.LOGGING;
    }
}
