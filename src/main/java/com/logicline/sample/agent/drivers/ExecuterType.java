package com.logicline.sample.agent.drivers;

public enum ExecuterType {
    LOGGING,LIGHT;

    public static ExecuterType getType(String name){
        try {
            return valueOf(name);
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}
