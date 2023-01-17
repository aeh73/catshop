package clients;

public class CaesarCipher {
	public static final int ALPHASIZE=26;//English alphabet(uppercaseonly)
	public static final char[] alpha = {
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
			'Q','R','S','T','U','V','W','X','Y','Z'
	};
	protected static char[] encrypt = new char[ALPHASIZE];//Encryption Array
	protected char[] decrypt = new char[ALPHASIZE];//DECRYPTION ARRAY
	
	/*Constructor to initialise the arrays*/
	public CaesarCipher(){
		for(int i = 0;i<ALPHASIZE;i++)
			encrypt[i]=alpha[(i+3)%ALPHASIZE];//rotate alphabet by 3 places
		
		for(int i = 0;i<ALPHASIZE;i++)
			decrypt[encrypt[i]-'A']=alpha[i];//decrypt is reverse of encrypt
	}
	
	/*Encryption Method*/
	public static String encrypt(String secret) {
		char[] mess = secret.toCharArray();//the message array
		for(int i = 0;i<mess.length;i++)//encryption loop
			if(Character.isUpperCase(mess[i]))//we have a letter to change
				mess[i]=encrypt[mess[i]-'A'];//use letter as an index
		return new String(mess);
	}
	
	/*Decryption Method*/
	public String decrypt(String secret) {
		char[] mess = secret.toCharArray();//the message array
		for(int i = 0;i<mess.length;i++)//decryption loop
			if(Character.isUpperCase(mess[i]))//we have a letter to change
				mess[i]=decrypt[mess[i]-'A'];//use letter as an index
		return new String(mess);
	}
	
	public static void main(String[] args) {
		CaesarCipher c = new CaesarCipher();
		System.out.println("Encryption order = "+ new String(c.encrypt));
		System.out.println("Decryption order = "+ new String(c.decrypt));
		String secret="LIVERPOOL           ARE THE BEST TEAM IN THE WORLD!";
		secret=c.encrypt(secret);
		System.out.println(secret);
		secret=c.decrypt(secret);
		System.out.println(secret);
		

	}

}
