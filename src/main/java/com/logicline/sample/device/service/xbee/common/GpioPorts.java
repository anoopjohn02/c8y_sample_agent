package com.logicline.sample.device.service.xbee.common;

import com.digi.xbee.api.io.IOLine;

public enum GpioPorts {
    SWITCH_01("SWITCH01",IOLine.DIO6),
    SWITCH_02("SWITCH02",IOLine.DIO7),
    SWITCH_03("SWITCH02",IOLine.DIO8),
    SWITCH_04("SWITCH02",IOLine.DIO9),
    SWITCH_05("SWITCH02",IOLine.DIO12),
    SWITCH_06("SWITCH02",IOLine.DIO13),
    SWITCH_07("SWITCH02",IOLine.DIO14),
    SWITCH_08("SWITCH02",IOLine.DIO15),
    SWITCH_09("SWITCH02",IOLine.DIO16),
    SWITCH_10("SWITCH02",IOLine.DIO17),
    SWITCH_11("SWITCH02",IOLine.DIO18),
    SWITCH_12("SWITCH02",IOLine.DIO19);

    private String name = null;
    private IOLine line = null;

    private GpioPorts(String name, IOLine line){
        this.name = name;
        this.line = line;
    }

    public static GpioPorts getByName(String name){
        for (GpioPorts port : GpioPorts.values()) {
            if(port.name.equals(name)){
                return port;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IOLine getLine() {
        return line;
    }

    public void setLine(IOLine line) {
        this.line = line;
    }
}
