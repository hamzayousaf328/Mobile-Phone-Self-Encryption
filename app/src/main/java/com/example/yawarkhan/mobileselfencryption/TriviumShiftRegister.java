package com.example.yawarkhan.mobileselfencryption;

/**
 * Created by Yawar Khan on 1/14/2018.
 */
public class TriviumShiftRegister implements ShiftRegister {

    //the array of FlipFlops used for this shift regster
    private FlipFlop[] flipFlops;
    private byte tap;//the tap of this com.example.yawarkhan.mobileselfencryption.ShiftRegister

    /**
     * Creates a com.example.yawarkhan.mobileselfencryption.ShiftRegister with the number of "bits" given.
     *
     * @require sizeOfRegister > 0
     * @ensure this.size() = sizeOfRegister && this.getOutput() == 0x00
     * @param sizeOfRegister The number of "bits" of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.
     */
    public TriviumShiftRegister(int sizeOfRegister) {
        flipFlops = new FlipFlop[sizeOfRegister];

        //create the rightmost com.example.yawarkhan.mobileselfencryption.FlipFlop as a com.example.yawarkhan.mobileselfencryption.FlipFlop without a neighbor
        flipFlops[sizeOfRegister - 1] = new FlipFlop();

        //create the FlipFlops for this com.example.yawarkhan.mobileselfencryption.ShiftRegister
        for (int i = flipFlops.length - 2; i >= 0; i--) {
            flipFlops[i] = new FlipFlop(flipFlops[i + 1]);
        }

        //initialize the tap
        tap = 0x00;
    }

    /**
     * Will return the size, number of "bits", of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.
     *
     * @return The size, number of "bits", of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.
     */
    @Override
    public int size() {
        return this.flipFlops.length;
    }

    /**
     * Get the output bit(s) of this shift register.
     * <br>
     * This will return the value in the tap of this com.example.yawarkhan.mobileselfencryption.ShiftRegister.<br>
     * The output byte will be 0x00 || 0x01.<br>
     * <br>
     * A call to this.getBits() may need to be called with the appropriate<br>
     * parameters before getOuput() is called.
     *
     * @ensure result == 0x00 || result = 0x01
     */
    @Override
    public byte getOuput() {
        return this.tap;
    }

    /**
     * Get a bit form the register from the given position.
     *
     * @require position >= 0 && position < this.size()
     * @ensure Will return the bit at the given position as a byte.<br>
     *         result == 0x00 || result = 0x01
     * @param position The position of the bit in this com.example.yawarkhan.mobileselfencryption.ShiftRegister
     * @return The bit at the given position.
     */
    @Override
    public byte getBitAt(int position) {
        return flipFlops[position].getValue();
    }

    /**
     * Load a value into the register. The register loads values from the left.<br>
     * <br>
     * The loaded value must be either 0x00 || 0x01
     *
     * @require value == 0x00 || value == 0x01
     * @ensure the value loaded into the leftmost com.example.yawarkhan.mobileselfencryption.FlipFlop will be the the given value
     * @param value The value to be loaded into the register.
     */
    @Override
    public void loadValue(byte value) {
        flipFlops[0].tick(value);
    }

    /**
     * Get the bits from this com.example.yawarkhan.mobileselfencryption.ShiftRegister at the given positions.<br>
     * The returned array will contain the the bits in the same order that<br>
     * the positions were give.<br>
     * <br>
     *
     * @require positions != null && positions[i] >= 0 && positions[n] < this.size()
     * @ensure will return the bits in the same order as the given positions
     * @param positions The positions of the bits to be returned
     */
    @Override
    public byte[] getBits(int... positions) {
        byte[] result = new byte[positions.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = flipFlops[positions[i]].getValue();
        }

        return result;
    }
}
