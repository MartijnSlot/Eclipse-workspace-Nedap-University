package week6.dictionaryattack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

public class BruteForceAttack {
	private Map<String, String> passwordMap = new HashMap<>();
	private Map<String, String> hashDictionary = new HashMap<>();
	private static final String PATH = "/Users/martijn.slot/Documents/eclipse.workspace/ss/week6/"; //Your path to the test folder


	public void readPasswords(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String[] splitLine;

		while ((br.readLine()) != null) {
			splitLine = br.readLine().split(": ");
			passwordMap.put(splitLine[0], splitLine[1]);
		}

		br.close();


	}

	/**
	 * Given a password, return the MD5 hash of a password. The resulting
	 * hash (or sometimes called digest) should be hex-encoded in a String.
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getPasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return Hex.encodeHexString(md.digest(password.getBytes("UTF-8")));
	}

	/**
	 * Checks the password for the user the password list. If the user
	 * does not exist, returns false.
	 * @param user
	 * @param password
	 * @return whether the password for that user was correct.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean checkPassword(String user, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (!passwordMap.containsKey(user)){
			return false;
		}

		String value = getPasswordHash(password);
		return passwordMap.get(user).equals(value);
	}

	/**
	 * Reads a dictionary from file (one line per word) and use it to add
	 * entries to a dictionary that maps password hashes (hex-encoded) to
	 * the original password.
	 * @param filename filename of the dictionary.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void createDictionary(char[] apekop) throws IOException{
		File fout = new File(PATH + "/dictionaryattack/out.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (char i : apekop) {
			for (char j : apekop) {
				for (char k : apekop) {
					for (char l : apekop) {
						for (char m : apekop) {
							String line = new StringBuilder().append(i).append(j).append(k).append(l).append(m).toString();
							bw.write(line);
							bw.newLine();
						}
					}
				}
			}
		}
	}

	/**
	 * Reads a dictionary from file (one line per word) and use it to add
	 * entries to a dictionary that maps password hashes (hex-encoded) to
	 * the original password.
	 * @param filename filename of the dictionary.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void addToHashDictionary(String filename) throws IOException, NoSuchAlgorithmException {
		BufferedReader br = new BufferedReader(new FileReader(filename));

		while (br.readLine() != null) {
			String MD5Hashies = getPasswordHash(br.readLine());
			hashDictionary.put(MD5Hashies, br.readLine());
		}

		br.close();
	}

	/**
	 * Do the dictionary attack.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void doDictionaryAttack() throws IOException, NoSuchAlgorithmException {
		for (String user : passwordMap.keySet()) {
			if (hashDictionary.containsKey(passwordMap.get(user))) {
				System.out.println(user + ": " + hashDictionary.get(passwordMap.get(user)));
			}
		}		
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		BruteForceAttack bfa = new BruteForceAttack();
		char[] apekop = "abcdefghijklmnopqrstuvwxyz".toCharArray(); //1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ
		bfa.createDictionary(apekop);
		bfa.readPasswords(PATH + "test/LeakedPasswords.txt");
		bfa.addToHashDictionary(PATH + "dictionaryattack/out.txt");
		bfa.doDictionaryAttack();
	}


}
