package com.jpmorgan.simplestockmarket;

import java.util.Comparator;

public class CustomComparator implements Comparator<Trade> {
    @Override
    public int compare(Trade o1, Trade o2) {
        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
    }
}