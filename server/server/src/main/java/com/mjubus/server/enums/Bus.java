package com.mjubus.server.enums;

public enum Bus {
    명지대역(10),
    시내(20),
    기흥역(30),
    합정_영등포 (100),
    노원_구리 (110),
    인천 (120),
    송내 (130),
    안산 (140),
    금정_범계 (150),
    분당 (160),
    시외버스_5001 (200),
    시외버스_5001_1 (201),
    시외버스_5003B (202),
    시외버스_5000B(210),
    시외버스_5002A (211),
    시외버스_5002B (212),
    시외버스_5005 (213),
    시외버스_5600 (214),
    시외버스_5700A (215),
    시외버스_5700B (216);


    private final int value;
    Bus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
