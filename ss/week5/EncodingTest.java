package week5;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * A simple class that experiments with the Hex encoding
 * of the Apache Commons Codec library.
 *
 */
public class EncodingTest {
    public static void main(String[] args) throws DecoderException {
        String input = "Hello World";
        byte[] inputBytes = input.getBytes();

        System.out.println(Hex.encodeHexString(inputBytes));
        
        decodeBytes = Hex.decode(Hex.encodeHexString(inputBytes));
    }
}