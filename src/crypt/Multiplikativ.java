package crypt;

import java.util.Arrays;
import java.util.List;

public class Multiplikativ extends Chiffre{
	

	/**
	 * Multiplikative Chiffre: Symmetrische Buchstabenverschiebung um den Faktor/Schluessel k.
	 * @author caterina
	 *
	 */
	private String currentAlphabet;
	private boolean decrypt;
	private List<Integer> length = Arrays.asList(1);
	
	/**
	 * Funktion soll extern nicht aufgerufen werden. Nur ueber Funktionen de und encrypt
	 * @param text
	 * @param key
	 * @param alpha
	 * @return convertedString (veraenderter Text)
	 */
	private String algorithm(String text, int key, String alpha){
		String newtext = "";
		for (int n = 0; n<text.length(); n++){	
			char newChar = text.charAt(n);
			char lowerChar = Character.toLowerCase(newChar);
			for (int i = 0; i<alpha.length(); i++){
				if (alpha.charAt(i) == lowerChar){
					if (decrypt == false){
						newChar = alpha.charAt((i*key)%alpha.length());
					}
					else{
						int tmpi = i;
						while (tmpi%key != 0){
							
							tmpi = alpha.length()+tmpi;
							
						}
						
						newChar = alpha.charAt((tmpi/key)%alpha.length());
						
					}
					if (Character.isUpperCase(text.charAt(n))){	
						newChar = Character.toUpperCase(newChar);
					}
				}
			}
			newtext += newChar;
			
			
		}
		return newtext;
	}
	
	/**
	 * @param text (Klartext), key (Schluessel)
	 * wandelt key in int
	 * @return algorithm() (Geheimtext)
	 */
	public String encrypt(String text, String key)
	{
		key = key.toLowerCase();
		currentAlphabet = myAlphabet.getAlphabet();
		String verified = verify(key, currentAlphabet);
		
		if (verified == null){	
			int keynum = Tools.string2int(key, currentAlphabet);
			decrypt = false;
			return algorithm(text, keynum, currentAlphabet);

		}
		else{
			return verified;
		}
		
		
		
	}
	
	/**
	 * @param text (Geheimtext)
	 * @param key (Schluessel)
	 * wandelt key in int
	 * invertiert int key
	 * @return algorithm() (Klartext)
	 */
	public String decrypt(String text, String key)
	{
		key = key.toLowerCase();
		currentAlphabet = myAlphabet.getAlphabet();
		String verified = verify(key, currentAlphabet);
		
		if (verified == null){	
			int keynum = Tools.string2int(key, currentAlphabet);
			decrypt = true;
			return algorithm(text, keynum, currentAlphabet);
		}
		else{
			return verified;
		}
		
		
	}

	@Override
	/**
	 * checks key length, characters and coprime
	 */
	protected String verify(String key, String alphabet) {
		if (!checkLength(key, length)){
			return "Vorsicht! Bei der Multiplikativen Chiffre muss der Schlüssel genau ein Zeichen lang sein!";
		}
		else if(!checkCharacter(key, alphabet)){
			return "Vorsicht! Der Schlüssel darf nur Zeichen enthalten, die auch im Alphabet enthalten sind!";
		}
		else if(!checkCoprimes(key, alphabet)){
			List<Integer> coprimes = Tools.phi(currentAlphabet.length());
			String cp = Tools.listtoString(coprimes, currentAlphabet);
			return "Der angegebene Schlüssel ist nicht co-prim zur Länge des Alphabets. Eine Entschlüsselung wird daher nicht möglich sein. Verwendbare Schlüssel sind: " + cp;
		}
		else{
			return null;
		}
	}
}
	

