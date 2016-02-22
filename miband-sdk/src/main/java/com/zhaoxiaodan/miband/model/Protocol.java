package com.zhaoxiaodan.miband.model;

public class Protocol {
    public static final byte[] PAIR = {2};
    public static final byte[] VIBRATION_WITH_LED = {1};
    public static final byte[] VIBRATION_10_TIMES_WITH_LED = {2};
    public static final byte[] VIBRATION_WITHOUT_LED = {4};
    public static final byte[] STOP_VIBRATION = {0};
    public static final byte[] ENABLE_REALTIME_STEPS_NOTIFY = {3, 1};
    public static final byte[] DISABLE_REALTIME_STEPS_NOTIFY = {3, 0};
    public static final byte[] ENABLE_SENSOR_DATA_NOTIFY = {18, 1};
    public static final byte[] DISABLE_SENSOR_DATA_NOTIFY = {18, 0};
    public static final byte[] SET_COLOR_RED = {14, 6, 1, 2, 1};
    public static final byte[] SET_COLOR_BLUE = {14, 0, 6, 6, 1};
    public static final byte[] SET_COLOR_ORANGE = {14, 6, 2, 0, 1};
    public static final byte[] SET_COLOR_GREEN = {14, 4, 5, 0, 1};
    public static final byte[] START_HEART_RATE_SCAN = {21, 2, 1};

    public static final byte[] REBOOT = {12};
    public static final byte[] REMOTE_DISCONNECT = {1};
    public static final byte[] FACTORY_RESET = {9};
    public static final byte[] SELF_TEST = {2};
}
