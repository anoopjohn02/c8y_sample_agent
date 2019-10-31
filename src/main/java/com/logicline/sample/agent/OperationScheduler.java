package com.logicline.sample.agent;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.model.operation.OperationStatus;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.devicecontrol.OperationCollection;
import com.cumulocity.sdk.client.devicecontrol.OperationFilter;
import com.logicline.sample.agent.drivers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class OperationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OperationScheduler.class);

    @Autowired
    private List<Driver> drivers;

    @Autowired
    private List<OperationExecutor> operationExecutors;

    private Map<ExecuterType, OperationExecutor> executers;

    @Autowired
    private Platform platform;

    @Autowired
    private GId gid;

    @PostConstruct
    public void init(){
        this.executers = initializeExecuters();
        initializeDriverPlatforms();
        startDrivers();
    }

    private Map<ExecuterType, OperationExecutor> initializeExecuters() {
        Map<ExecuterType, OperationExecutor> executers = new HashMap<>();
        logger.info("Initializing executers...");

        for (OperationExecutor operationExecutor : operationExecutors) {
            try {
                logger.info("Initializing " + operationExecutor.getClass());
                executers.put(operationExecutor.getType(), operationExecutor);
            } catch (Exception e) {
                logger.warn("Skipping executer " + operationExecutor.getClass());
                logger.debug("Executer error message: ", e);
            } catch (UnsatisfiedLinkError error) {
                logger.warn("Skipping executer " + operationExecutor.getClass());
                logger.debug("Executer error message: " + operationExecutor.getClass(), error);
            }
        }
        return executers;
    }

    private void initializeDriverPlatforms() {
        for (Driver driver : drivers) {
            try {
                driver.initialize(platform);
            } catch (Exception e) {
                logger.error("Can't initialize driver platform " + driver.getClass(), e);
            }
        }
    }

    private void startDrivers() {
        logger.info("Starting drivers");
        for (Driver driver : drivers) {
            driver.start();
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void execute(){
        for (OperationRepresentation operation : getOperations()) {
            if (!this.gid.equals(operation.getDeviceId())) {
                continue;
            }
            processOperation(operation);
        }
    }

    private Iterable<OperationRepresentation> getOperations(){
        logger.info("Checking for new operations ");
        OperationFilter opsFilter = new OperationFilter().byAgent(gid.getValue()).byStatus(OperationStatus.EXECUTING);
        Optional<OperationCollection> operationCollection = Optional.of(platform.getDeviceControlApi().getOperationsByFilter(opsFilter));

        if (!operationCollection.isPresent()){
            logger.info("No new operations found");
            return Collections.emptyList();
        }

        return operationCollection.get().get().allPages();
    }

    private void processOperation(OperationRepresentation operation){
        for (final String key : operation.getAttrs().keySet()) {
            ExecuterType type = ExecuterType.getType(key);
            if(validateType(type)){
                logger.info("Executing operation for key {}", key);
                try{
                    executers.get(type).execute(operation);
                    operation.setStatus(OperationStatus.SUCCESSFUL.toString());
                }catch (Exception e){
                    operation.setStatus(OperationStatus.FAILED.toString());
                    operation.setFailureReason(e.toString());
                    logger.error("Error occured during execute the operation {} ", key, e);
                }
                updateOperation(operation);
            }
        }
    }

    private boolean validateType(ExecuterType type){
        return type != null &&
                executers.containsKey(type);
    }

    private void updateOperation(OperationRepresentation operationRepresentation) {
        platform.getDeviceControlApi().update(operationRepresentation);
    }

}
