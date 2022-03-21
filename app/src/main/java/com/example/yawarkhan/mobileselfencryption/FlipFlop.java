package com.example.yawarkhan.mobileselfencryption;

/**
 * Created by Yawar Khan on 1/14/2018.
 */
public class FlipFlop {

    private boolean hasRightNeighbor;//used to see if this FlopFlop has a neighbor
    private FlipFlop rightNeighbor;//used to reference the right neighbor
    private byte value;//the value of this com.example.yawarkhan.mobileselfencryption.FlipFlop

    /**
     * Creates a com.example.yawarkhan.mobileselfencryption.FlipFlop without neighbor on the right.
     *
     * @ensure this.getValue() == 0x00 && this.getRightNeighbor() == null
     */
    public FlipFlop() {
        value = 0;
        hasRightNeighbor = false;

    }

    /**
     * Creates a new com.example.yawarkhan.mobileselfencryption.FlipFlop with the neighbor on the right.
     *
     * @require neighbor != null
     * @ensure this.getValue() == 0x00 && this.getRightNeighbor() == neighbor
     * @param neighbor The com.example.yawarkhan.mobileselfencryption.FlipFlop that will be to the right of this com.example.yawarkhan.mobileselfencryption.FlipFlop.
     */
    public FlipFlop(FlipFlop neighbor) {
        value = 0;
        hasRightNeighbor = true;
        this.rightNeighbor = neighbor;
    }

    /**
     * Causes this com.example.yawarkhan.mobileselfencryption.FlipFlop to give its value to its right neighbor (if it has
     * a neighbor). It will then update its value with the given value.
     *
     * @require value != null && (value == 0x01 || value == 0x00)
     * @ensure this.getValue() == value && <br>
     *         this.getRightNeighbor().getValue() = old this.getValue()
     * @param value The value that this com.example.yawarkhan.mobileselfencryption.FlipFlop will now contain
     */
    public void tick(byte value) {
        //if this com.example.yawarkhan.mobileselfencryption.FlipFlop has a neighbor, then cause it to tick
        if (hasRightNeighbor) {
            rightNeighbor.tick(this.value);
        }

        this.value = value;
    }

    /**
     * Return a reference to this com.example.yawarkhan.mobileselfencryption.FlipFlop's right neighbor.
     *
     * @ensure iff this has a right neighbor, then this.getRightNeighbor() != null
     * @return Will return a reference to this com.example.yawarkhan.mobileselfencryption.FlipFlop's right neighbor. <br>
     *         If this com.example.yawarkhan.mobileselfencryption.FlipFlop has right neighbor, then the returned <br>
     *         reference will be != null, else it will be null.
     */
    public FlipFlop getRightNeighbor() {
        return rightNeighbor;
    }

    /**
     * Return the value contained in this com.example.yawarkhan.mobileselfencryption.FlipFlop.
     *
     * @ensure The return will be 0x01 || 0x00
     * @return Will return the value contained in this com.example.yawarkhan.mobileselfencryption.FlipFlop
     */
    public byte getValue() {
        return value;
    }
}
