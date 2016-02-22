package com.zhaoxiaodan.miband.model;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class UserInfo {

    private int uid;
    private byte gender;
    private byte age;
    private byte height;        // cm
    private byte weight;        // kg
    private String alias = "";
    private byte type;

    private UserInfo() {

    }

    public UserInfo(int uid, int gender, int age, int height, int weight, String alias, int type) {
        this.uid = uid;
        this.gender = (byte) gender;
        this.age = (byte) age;
        this.height = (byte) (height & 0xFF);
        this.weight = (byte) weight;
        this.alias = alias;
        this.type = (byte) type;
    }

    public static UserInfo fromByteData(byte[] data) {
        if (data.length < 20) {
            return null;
        }
        UserInfo info = new UserInfo();

        info.uid = data[3] << 24 | (data[2] & 0xFF) << 16 | (data[1] & 0xFF) << 8 | (data[0] & 0xFF);
        info.gender = data[4];
        info.age = data[5];
        info.height = data[6];
        info.weight = data[7];
        info.type = data[8];
        try {
            info.alias = new String(data, 9, 8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            info.alias = "";
        }

        return info;
    }

    public byte[] getBytes(String mBTAddress) {
        byte[] aliasBytes;
        try {
            aliasBytes = this.alias.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            aliasBytes = new byte[0];
        }
        ByteBuffer bf = ByteBuffer.allocate(20);
        bf.put((byte) (uid & 0xff));
        bf.put((byte) (uid >> 8 & 0xff));
        bf.put((byte) (uid >> 16 & 0xff));
        bf.put((byte) (uid >> 24 & 0xff));
        bf.put(this.gender);
        bf.put(this.age);
        bf.put(this.height);
        bf.put(this.weight);
        bf.put(this.type);
        bf.put((byte) 4);
        bf.put((byte) 0);

        if (aliasBytes.length <= 8) {
            bf.put(aliasBytes);
            bf.put(new byte[8 - aliasBytes.length]);
        } else {
            bf.put(aliasBytes, 0, 8);
        }

        byte[] crcSequence = new byte[19];
        for (int u = 0; u < crcSequence.length; u++)
            crcSequence[u] = bf.array()[u];

        byte crcb = (byte) ((getCRC8(crcSequence) ^ Integer.parseInt(mBTAddress.substring(mBTAddress.length() - 2), 16)) & 0xff);
        bf.put(crcb);
        return bf.array();
    }

    private int getCRC8(byte[] seq) {
        int len = seq.length;
        int i = 0;
        byte crc = 0x00;

        while (len-- > 0) {
            byte extract = seq[i++];
            for (byte tempI = 8; tempI != 0; tempI--) {
                byte sum = (byte) ((crc & 0xff) ^ (extract & 0xff));
                sum = (byte) ((sum & 0xff) & 0x01);
                crc = (byte) ((crc & 0xff) >>> 1);
                if (sum != 0) {
                    crc = (byte) ((crc & 0xff) ^ 0x8c);
                }
                extract = (byte) ((extract & 0xff) >>> 1);
            }
        }
        return (crc & 0xff);
    }

    public String toString() {
        return "uid:" + this.uid
                + ",gender:" + this.gender
                + ",age:" + this.age
                + ",height:" + this.getHeight()
                + ",weight:" + this.getWeight()
                + ",alias:" + this.alias
                + ",type:" + this.type;
    }

    /**
     * @return the uid
     */
    public int getUid() {
        return uid;
    }

    /**
     * @return the gender
     */
    public byte getGender() {
        return gender;
    }

    /**
     * @return the age
     */
    public byte getAge() {
        return age;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return (height & 0xFF);
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight & 0xFF;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }
}
