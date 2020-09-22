package com.theta.input;

public class BitArray {

  private final byte[] bytes;
  private final int length;
  private final int size;

  public BitArray(int size) {
    length = length(size);
    bytes = new byte[length];
    this.size = size;
  }

  private int length(int bits) {
    if (bits < 1) {
      return 0;
    }
    return (bits >> 3) + (bits % 8 == 0 ? 0 : 1);// n/8 : n >> 3
  }

  private int index(int bits) {
    if (bits < 1) {
      return 0;
    }
    return bits >> 3;
  }

  private String binaryString(int bits) {
    char[] buf = new char[8];
    for (int offset = 0; offset < 8; offset++) {
      buf[offset] = (char) ('0' + ((bits >> (7 - offset)) & 1));
    }
    return new String(buf, 0, 8);
  }

  public int length() {
    return length;
  }

  public int size() {
    return size;
  }

  public int remainder() {
    return (length(size) << 3) - size;
  }

  public boolean get(int bit) {
    if (bit >= size + remainder() || bit < 0) {
      return false;
    }
    int index = index(bit);
    int offset = bit - (index << 3);
    return (bytes[index] & (byte) (128 >>> offset)) != 0;
  }

  public void set(int bit, boolean val) {
    if (bit >= size + remainder() || bit < 0) {
      return;
    }
    int index = index(bit);
    int offset = (bit - (index << 3));
    if (val) {
      bytes[index] |= (byte) (0x80 >>> offset);
    } else {
      bytes[index] &= (byte) (~(0x80 >>> offset));
    }

  }

  public void printByte(int bit) {
    bit = index(bit);
    System.out.println("\n[" + bit + "]:\t\t[" + binaryString(0xff & bytes[bit]) + "]\n");
  }

  public void printBit(int bit) {
    boolean value = get(bit);
    int bucket = index(bit);
    int position = bit - (bucket << 3);
    if (bit % 8 == 0) {
      printByte(bit);
    }
    System.out.println(" " + position + "\t\t\t\t" + value + "\t" + bit);
  }

  public void print() {
    double valueb;
    char prefb;
    double unitb;
    double valueB;
    char prefB;
    double unitB;
    switch (Integer.toString(size).length()) {
    case 1:
    case 2:
    case 3:
      unitb = 1;
      prefb = (char) 0;
      break;
    case 4:
    case 5:
    case 6:
      unitb = 1000;
      prefb = 'K';
      break;
    case 7:
    case 8:
    case 9:
      unitb = 1000000;
      prefb = 'M';
      break;
    default:
      unitb = 1000000000;
      prefb = 'G';
    }
    valueb = size / unitb;
    switch (Integer.toString(length).length()) {
    case 1:
    case 2:
    case 3:
      unitB = 1;
      prefB = (char) 0;
      break;
    case 4:
    case 5:
    case 6:
      unitB = 1000;
      prefB = 'K';
      break;
    case 7:
    case 8:
    case 9:
      unitB = 1000000;
      prefB = 'M';
      break;
    default:
      unitB = 1000000000;
      prefB = 'G';
    }
    valueB = length / unitB;// size / unitB / 8;
    System.out
        .println("\n\n\nSize: " + valueb + prefb + 'b' + " (" + valueB + prefB + 'B' + " +" + remainder() + 'b' + ")");
    System.out.println("Length: " + (int) Math.ceil(valueB) + prefB + 'B');
    System.out.println("\n\nPosition\tBucket\t\tValue\tIndex\n");
    for (int i = 0; i < size; i++) {
      printBit(i);
    }
  }

  public static void test() {
    BitArray bits0 = new BitArray(256);
    BitArray bits1 = new BitArray(50);
    for (int i = 0; i < bits0.size() + bits0.remainder(); i++) {
      if (i % 4 != 0) {
        bits0.set(i, true);
      }
    }
    bits0.print();
    for (int i = 0; i < bits0.size() + bits0.remainder(); i++) {
      if (i % 2 == 0) {
        bits0.set(i, false);
      }
    }
    bits0.print();
    for (int i = 0; i < bits1.size() + bits1.remainder(); i++) {
      if (i % 4 != 0) {
        bits1.set(i, true);
      }
    }
    bits1.print();
    for (int i = 0; i < bits1.size() + bits1.remainder(); i++) {
      if (i % 2 == 0) {
        bits1.set(i, false);
      }
    }
    bits1.print();
  }
}
