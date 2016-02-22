package com.zhaoxiaodan.miband.model;

public class LeParams {
    public int connIntMin;
    public int connIntMax;
    public int connInt;

    public int latency;
    public int timeout;
    public int advInt;

    public static LeParams fromByte(byte[] b) {
        LeParams params = new LeParams();

        params.connIntMax = 0xffff & (0xff & b[0] | (0xff & b[1]) << 8);
        params.connIntMax = 0xffff & (0xff & b[2] | (0xff & b[3]) << 8);
        params.latency = 0xffff & (0xff & b[4] | (0xff & b[5]) << 8);
        params.timeout = 0xffff & (0xff & b[6] | (0xff & b[7]) << 8);
        params.connInt = 0xffff & (0xff & b[8] | (0xff & b[9]) << 8);
        params.advInt = 0xffff & (0xff & b[10] | (0xff & b[11]) << 8);

        params.connIntMin *= 1.25;
        params.connIntMax *= 1.25;
        params.advInt *= 0.625;
        params.timeout *= 10;

        return params;
    }
}
