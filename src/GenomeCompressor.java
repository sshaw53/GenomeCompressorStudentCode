/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {
    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Created a map that directly translates to the different integer values
        int[] genomeKeys = new int[256];
        genomeKeys['A'] = 0;
        genomeKeys['C'] = 1;
        genomeKeys['T'] = 2;
        genomeKeys['G'] = 3;

        // Read in the sequence
        String toCompress = BinaryStdIn.readString();
        int len = toCompress.length();

        // Write out the length of the string to the beginning of the file to avoid extra padding, using 32-bit because
        // we can assume that it's less than 2 billion
        BinaryStdOut.write(len);

        // Writing in the compressed characters in  2-bit because the values can only be from 0-3
        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(genomeKeys[toCompress.charAt(i)],2);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Other map to go back from numbers to characters
        char[] genomeDecode = new char[4];
        genomeDecode[0] = 'A';
        genomeDecode[1] = 'C';
        genomeDecode[2] = 'T';
        genomeDecode[3] = 'G';

        // Get the length
        int len = BinaryStdIn.readInt(32);
        int count = 0;

        // While we aren't at the padded 0's yet, keep reading in the 2-bit info and convert from int to char using
        // the map
        while (count < len) {
            int c = BinaryStdIn.readInt(2);
            BinaryStdOut.write(genomeDecode[c]);
            count += 1;
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}