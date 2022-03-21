package com.example.yawarkhan.mobileselfencryption;

/**
 * Created by Yawar Khan on 1/14/2018.
 */
public interface ShiftRegister {

    /**
     * Will return the size, number of "bits", of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.
     *
     * @return The size, number of "bits", of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.
     */
    public int size();

    /**
     * Get the output bit(s) of this shift register.
     * <br>
     * All implementing classes must indicate many bits will be returned.
     *
     * @ensure The returned output will be a byte representing one bit,<br>
     *         or a byte representing up to 8 bits. This will be determined<br>
     *         by the implementing class.
     */
    public byte getOuput();

    /**
     * Get a bit form the register from the given position.
     *
     * @require position >= 0 && position < this.size()
     * @ensure Will return the bit at the given position as a byte.<br>
     *         result == 0x00 || result = 0x01
     * @param position The position of the bit in this com.example.yawarkhan.mobileselfencryption.ShiftRegister
     * @return The bit at the given position.
     */
    public byte getBitAt(int position);

    /**
     * Load a value into the register. The register loads values from the left.<br>
     * <br>
     * All implementing classes must indicate how many bits can be loaded at one time.
     *
     * @param value The value to be loaded into the register.
     */
    public void loadValue(byte value);

    /**
     * Get the bits from this com.example.yawarkhan.mobileselfencryption.ShiftRegister at the given positions.<br>
     * The returned array will contain the the bits in the same order that<br>
     * the positions were give.<br>
     * <br>
     *
     * @param positions The positions of the bits to be returned
     */
    public byte[] getBits(int... positions);
}
