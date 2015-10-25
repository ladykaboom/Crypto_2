import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class RC4 {
	private int[] S = new int[256];
	private final int keylen;

	public RC4(final byte[] key) {
		if (key.length < 1 || key.length > 256) {
			throw new IllegalArgumentException("key must be between 1 and 256 bytes");
		} else {
			keylen = key.length;
			for (int i = 0; i < 256; i++) {
				S[i] = i;
			}

			int j = 0;

			for (int i = 0; i < 256; i++) {
				j = (j + S[i] + key[i % keylen]) % 256;
				int temp = S[i];
				S[i] = S[j];
				S[j] = temp;
			}
		}
	}

	public String encrypt(final String message) {
		byte[] plaintext = new byte[0];
		try {
			plaintext = message.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
		}

		byte[] ciphertext = toBytes(transform(toInts(plaintext)));
		return Hex.encodeHexString(ciphertext);
	}

	private int[] transform(final int[] input) {
		final int[] output = new int[input.length];

		final int[] S = new int[this.S.length];
		System.arraycopy(this.S, 0, S, 0, S.length);

		int i = 0, j = 0, k, t;
		for (int counter = 0; counter < input.length; counter++) {
			i = (i + 1) & 0xFF;
			j = (j + S[i]) & 0xFF;
			S[i] ^= S[j];
			S[j] ^= S[i];
			S[i] ^= S[j];
			t = (S[i] + S[j]) & 0xFF;
			k = S[t];
			output[counter] = input[counter] ^ k;
		}

		return output;
	}

	private int[] toInts(byte[] bytes) {
		int[] output = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			output[i] = bytes[i];
		}
		return output;
	}

	private byte[] toBytes(int[] ints) {
		byte[] output = new byte[ints.length];
		for (int i = 0; i < ints.length; i++) {
			output[i] = (byte) ints[i];
		}
		return output;
	}

	public String decrypt(final String ciphertext) {
		try {
			int[] cipherints = toInts(Hex.decodeHex(ciphertext.toCharArray()));
			for (int i = 0; i < cipherints.length; i++) {
				cipherints[i] = cipherints[i] & 0xff;
			}
			byte[] plaintext = toBytes(transform(cipherints));
			return new String(plaintext, Charset.forName("ASCII"));
		} catch (DecoderException e) {
		}
		return null;
	}

	public static void main(String[] args) throws DecoderException, NumberFormatException {
		//String hex_key = "449bdd83af52c65e";
		String hex_key = "449bdd83af52c65e";
		byte[] key = hex_key.getBytes();
		
		String hexStr = "";
		String bin_temp = "01001010 11000010 00110101 00110100 11100001 10010000 00110000 10000000 01000011 00010010 11110101 01010011 10110000 10010111 01011001 01110110 10100001 11111110 01001000 10100101 01011111 10100111 11100011 11010111 01010100 10010100 01011110 00101111 11110001 00111100 01011001 01000011 01101110 01111000 11000000 10101000 11010101 00011010 01101101 10000101 10100101 11000100 01011111 00000000 10011011 00101011 11010000 00100101 00111100 11001011 10001001 11110000 00101110 01001001 10001101 10011000 10111011 11110000 11110001 00010001 11000010 11111101 10001000 01110111 00001001 10000110 01001101 00011111 10010110 10101110 10110101 10100010 00110000 00100010 00010000 11110110 11111111 ";
		String bin = "";
		
		/*
		 * changing plaintext(bin) to hex string
		 */
		for( int i =0 ; i< bin_temp.length() - 1 ; i++){
			if(bin_temp.charAt(i)!=' '){
				bin = bin + bin_temp.charAt(i);
			}
		}

		try{
			BigInteger decimal = new BigInteger(bin, 2);
			hexStr = decimal.toString(16);
		}
		catch(NumberFormatException e){}
			
			
			
		RC4 rc4 = new RC4(key);
			//String message = "pedia";
			//System.out.println("Message: " + message);
			//String cipherText =  rc4.encrypt(message);
		
		String cipherText =  hexStr;
		System.out.println("Encrypted: " + cipherText);
		String decrypted = rc4.decrypt(cipherText);
		System.out.println("Decrypted: " + decrypted);
		
	//	String hex = "449bdd83af52c65e";
	//	System.out.println(hexToASCII(hex));

		List<String> lista_mozliwych_slow = new ArrayList<String>();
		boolean czy_alfanumeryczne = false;
		int raz_moze_byc_znak = 1;
		
		for( int i = 0 ; i < 10 ; i++){
			int j = (int)decrypted.charAt(i);
			System.out.print(j + " ");
			if( ((  j >= 65 && j <= 122 ) || (j >= 32 &&  j <= 57))){
				//lista_mozliwych_slow.add(decrypted);
				czy_alfanumeryczne = true;
			}
			else {
				if(raz_moze_byc_znak == 1){raz_moze_byc_znak--;}
				else{
					czy_alfanumeryczne = false;
					break;
				}
				
			}
		}
		if(czy_alfanumeryczne == true )
			lista_mozliwych_slow.add(decrypted);
	      System.out.println("Rozmiar listy to: "+lista_mozliwych_slow.size() );
	      
	      for(int i = 0; i<lista_mozliwych_slow.size(); i++ )
	      {
	    	  System.out.println(lista_mozliwych_slow.get(i));
	      }

	}	
}