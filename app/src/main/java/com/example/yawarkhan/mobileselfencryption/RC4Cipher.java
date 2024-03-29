package com.example.yawarkhan.mobileselfencryption;
import java.util.Arrays;
public class RC4Cipher {

    private byte state[] = new byte[256];
    private int x;
    private int y;

    public RC4Cipher(String key) throws NullPointerException {
        this(key.getBytes());
    }

    /**
     * Initializes the class with a byte array key.  The length
     * of a normal key should be between 1 and 2048 bits.  But
     * this method doens't check the length at all.
     *
     * @param key   the encryption/decryption key
     */
    public RC4Cipher(byte[] key) throws NullPointerException {

        for (int i=0; i < 256; i++) {
            state[i] = (byte)i;
        }

        x = 0;
        y = 0;

        int index1 = 0;
        int index2 = 0;

        byte tmp;

        if (key == null || key.length == 0) {
            throw new NullPointerException();
        }

        for (int i=0; i < 256; i++) {

            index2 = ((key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;

            tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;

            index1 = (index1 + 1) % key.length;
        }



    }

    /**
     * RC4Cipher encryption/decryption.
     *
     * @param data  the data to be encrypted/decrypted
     * @return the result of the encryption/decryption
     */
    public byte[] rc4(String data) {

        if (data == null) {
            return null;
        }

        byte[] tmp = data.getBytes();

        this.rc4(tmp);

        return tmp;
    }

    /**
     * RC4Cipher encryption/decryption.
     *
     * @param buf  the data to be encrypted/decrypted
     * @return the result of the encryption/decryption
     */
    public byte[] rc4(byte[] buf) {

        //int lx = this.x;
        //int ly = this.y;

        int xorIndex;
        byte tmp;

        if (buf == null) {
            return null;
        }

        byte[] result = new byte[buf.length];

        for (int i=0; i < buf.length; i++) {

            x = (x + 1) & 0xff;
            y = ((state[x] & 0xff) + y) & 0xff;

            tmp = state[x];
            state[x] = state[y];
            state[y] = tmp;

            xorIndex = ((state[x] &0xff) + (state[y] & 0xff)) & 0xff;
            result[i] = (byte)(buf[i] ^ state[xorIndex]);
        }

        //this.x = lx;
        //this.y = ly;

        return result;
    }

    public static void main(String[] args) {
        byte[] plainData = "abc123".getBytes();
        byte[] key = "Key".getBytes();
        RC4Cipher rc4 = new RC4Cipher(key);
        byte[] cipherData = rc4.rc4(plainData);
        System.out.println("加密后: " + new String(cipherData));
        byte[] _plainData = rc4.rc4(cipherData);
        System.out.println("解密后: " + new String(_plainData));
        System.out.println(Arrays.equals(plainData, _plainData));
    }

}