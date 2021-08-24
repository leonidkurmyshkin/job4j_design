package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        byte oneByte = 125;
        short oneShort = 31000;
        char oneChar = 'H';
        int oneInt = 133000001;
        long oneLong = -2147483640L;
        float oneFloat = 234.5F;
        double oneDouble = 156785.989843988D;
        boolean isBoolean = true;
        LOG.debug("oneByte : {}, oneShort : {}, oneInt : {}, oneLong : {}", oneByte, oneShort, oneInt, oneLong);
        LOG.debug("oneFloat : {}, oneDouble : {}", oneFloat, oneDouble);
        LOG.debug("oneChar : {}", oneChar);
        LOG.debug("isBoolean : {}", isBoolean);
    }
}