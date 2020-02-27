package coursework;
import java.math.BigInteger;
import java.util.*;
import java.io.*;
public class menu {
	int classcounter = 0; //used to help us implement the protocol step-by-step
	BigInteger p;BigInteger q;	BigInteger r;BigInteger d; // private key for Alice
	BigInteger n; int e; //public key for Alice
	
	BigInteger p2;BigInteger q2;BigInteger r2;BigInteger d2; //private key for Bob
	BigInteger n2; int e2; //public key for bob;

	BigInteger p3;BigInteger q3;     BigInteger r3;BigInteger d3;  //private key for Server
	int e3;BigInteger n3;    //public key for Server

	String identityA = "";String identityB = "";     //identity i.e. names of A, B

	BigInteger[] Server_Sends_Encrypted_PublicKeyB_To_A;BigInteger[] Server_Sends_Encrypted_PublicKeyA_To_B; //server sends public key to A and B

	BigInteger[] decryptNonceA;  BigInteger[] decryptNonceB;  //B decrypting Nonce A. A decrypting Nonce B;
	BigInteger[] encryptNonceB; BigInteger[] encryptNonceB2;BigInteger[] encryptNonceA;  //encryptNonceB is Nonce B that's encrypted and sent to A. encryptNonceB2 is Nonce B being encrypted by A and being sent back to B at the last stage of the protocol. Finally, encryptNonceA is NonceA being encrypted and sent to B
	
	BigInteger[] B_send_Encrypted_NonceA_To_A;    //B encrypting the decrypted nonce A to send back to A for proof
	
	int nonceA; int nonceB;  //storing Nonce B and Nonce A
	BigInteger B_n2; int B_e2; //once public key has been decrypted by A, these variables will extract the public key (n and e) of B sent from the server
		
	BigInteger[] A_sends_Encrypted_message_to_server;   //first step of protocol of part 2
	BigInteger[] B_sends_Encrypted_message_to_server;   //fourth step of protocol of part 2
	
private static final String TITLE =
"\n2910326 Computer Security coursework\n"+
" by Shah Ishtiyaq Ali_33455846\n\n"+
"\t*****************************\n"+
"\t1. Part I \n" +
"\t2. Part II \n"+
"\t3. Exit \n"+
"\t*****************************\n"+
"Please input a single digit (1-3): ";
public menu() {
int selected= 0;
while (selected!=3) {
	System.out.println(TITLE);
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
try {
selected = Integer.parseInt(in.readLine());
switch(selected) {
case 1: RSA();
break;
case 2: Implement_Protocol();
break;

} }
catch(Exception ex) {} } // end while
System.out.println();
System.out.println("Program Exited");
}



public void RSA() { //in this method, we generate a public key and a private key for Alice and Bob (or whoever you want to call them.) We also create one for Server S for the second part of the coursework 
	                //this method will also print out the UI and an example of a problematic state 
	Scanner sc = new Scanner(System.in);
	System.out.println("Please enter a name: ");
	identityA = sc.next();
	identityA+= sc.nextLine();
	p = firstprimeNumber(new BigInteger("1"));
	q = secondprimeNumber(new BigInteger("1"));
	

	n = p.multiply(q);
	 BigInteger subtract_by_1 = new BigInteger("1");
	 BigInteger subtract_p = p.subtract(subtract_by_1);
	 BigInteger subtract_q = q.subtract(subtract_by_1);
	 	
    r = subtract_p.multiply(subtract_q);
	
    
    ArrayList<Integer>all_e_vals = new ArrayList<Integer>();

 	for(int i = 2;i < 60;i++) {
 		
 		if(gcd(i,r.intValue()) == 1) {
 			
 			all_e_vals.add(i);
           
 		}
 	}
 	Random generator = new Random();
 	int randomIndex = generator.nextInt(all_e_vals.size());
	e = all_e_vals.get(randomIndex);
    
	
 	d = null;

 	int k = 0;
	
 	for(k = 0;k < 150;k++) {
		BigInteger k2 = null;
	 	BigInteger add_one = new BigInteger(Integer.toString(1));
	 	BigInteger r_val_string = new BigInteger(r.toString());
	 	BigInteger e_val_string = new BigInteger(Integer.toString(e));
	 	k2 = new BigInteger(Integer.toString(k));	
		 d = k2.multiply(r_val_string);
		 d = d.add(add_one);
		 
		 if(k >= 149 && randomIndex != all_e_vals.size()) {
			 e = all_e_vals.get(randomIndex++);
			 k = 0;
		 }
		 
		 if( d.mod(e_val_string).equals(BigInteger.ZERO)) {
			 d = d.divide(e_val_string);
			break;
		 }
		
	}
	 System.out.println();

 	System.out.println("p: " + p);
 	System.out.println("q: " + q);
 	System.out.println("n: " + n);
 	System.out.println("r: " + r);
 	System.out.println("e: " + e);
 	System.out.println("d: " + d);
	
 	System.out.println();
	System.out.println("Generating keys for " + identityA+"......");
	System.out.println();

	System.out.println("Public Key: " + "(" + n +", " + e + ")");
	System.out.println("Private Key: " + "(" + p +", " + q +", "+ r+", " +d+")");
	System.out.println();
	
	System.out.println("Please enter another name: ");
	identityB = sc.next();
	identityB+= sc.nextLine();
	
	p2 = firstprimeNumber(new BigInteger("1"));
	q2 = secondprimeNumber(new BigInteger("1"));
	

	n2 = p2.multiply(q2);
	 BigInteger subtract_by_1_2 = new BigInteger("1");
	 BigInteger subtract_p2 = p2.subtract(subtract_by_1_2);
	 BigInteger subtract_q2 = q2.subtract(subtract_by_1_2);
	 	
    r2 = subtract_p2.multiply(subtract_q2);
	
    
    ArrayList<Integer>all_e_vals2 = new ArrayList<Integer>();

 	for(int i = 2;i < 20;i++) {
 		
 		if(gcd(i,r2.intValue()) == 1) {
 			
 			all_e_vals2.add(i);
           
 		}
 	}
 	Random generator2 = new Random();
 	int randomIndex2 = generator2.nextInt(all_e_vals2.size());
	e2 = all_e_vals2.get(randomIndex2);
    
	
 	d2 = null;

 	int k3 = 0;
	
 	for(k3 = 0;k3 < 150;k3++) {
		BigInteger k2 = null;
	 	BigInteger add_one = new BigInteger(Integer.toString(1));
	 	BigInteger r_val_string = new BigInteger(r2.toString());
	 	BigInteger e_val_string = new BigInteger(Integer.toString(e2));
	 	k2 = new BigInteger(Integer.toString(k3));	
		 d2 = k2.multiply(r_val_string);
		 d2 = d2.add(add_one);
		 
		 if(k3 >= 149 && randomIndex2 != all_e_vals2.size()) {
			 e2 = all_e_vals2.get(randomIndex2++);
			 k3 = 0;
		 }
		 
		 if( d2.mod(e_val_string).equals(BigInteger.ZERO)) {
			 d2 = d2.divide(e_val_string);
			break;
		 }
		
	}
	 System.out.println();

 	System.out.println("p: " + p2);
 	System.out.println("q: " + q2);
 	System.out.println("n: " + n2);
 	System.out.println("r: " + r2);
 	System.out.println("e: " + e2);
 	System.out.println("d: " + d2);
	
 	System.out.println();
	System.out.println("Generating keys for " + identityB+"......");
	System.out.println();

	System.out.println("Public Key: " + "(" + n2 +", " + e2 + ")");
	System.out.println("Private Key: " + "(" + p2 +", " + q2 +", "+ r2+", " +d2+")");
	System.out.println();
	
	
	
	
	p3 = firstprimeNumber(new BigInteger("1"));
	q3 = secondprimeNumber(new BigInteger("1"));
	

	n3 = p3.multiply(q3);
	 BigInteger subtract_by_1_3 = new BigInteger("1");
	 BigInteger subtract_p3 = p3.subtract(subtract_by_1_3);
	 BigInteger subtract_q3 = q3.subtract(subtract_by_1_3);
	 	
    r3 = subtract_p3.multiply(subtract_q3);
	
    
    ArrayList<Integer>all_e_vals3 = new ArrayList<Integer>();

 	for(int i = 2;i < 20;i++) {
 		
 		if(gcd(i,r3.intValue()) == 1) {
 			
 			all_e_vals3.add(i);
           
 		}
 	}
 	Random generator3 = new Random();
 	int randomIndex3 = generator3.nextInt(all_e_vals3.size());
	e3 = all_e_vals3.get(randomIndex3);
    
	
 	d3 = null;

 	int k4 = 0;
	
 	for(k4 = 0;k4 < 150;k4++) {
		BigInteger k2 = null;
	 	BigInteger add_one = new BigInteger(Integer.toString(1));
	 	BigInteger r_val_string = new BigInteger(r3.toString());
	 	BigInteger e_val_string = new BigInteger(Integer.toString(e3));
	 	k2 = new BigInteger(Integer.toString(k4));	
		 d3 = k2.multiply(r_val_string);
		 d3 = d3.add(add_one);
		 
		 if(k4 >= 149 && randomIndex3 != all_e_vals3.size()) {
			 e3 = all_e_vals3.get(randomIndex3++);
			 k4 = 0;
		 }
		 
		 if( d3.mod(e_val_string).equals(BigInteger.ZERO)) {
			 d3 = d3.divide(e_val_string);
			break;
		 }
		
	}
		
	System.out.println("Please enter a message (m): ");
	String m = sc.next();
	m += sc.nextLine();
	BigInteger[] lettertonumber = new BigInteger[m.length()];	
	char[] numbertoletter = new char[m.length()];
	
   char characters = 0;
	
	for(int i = 0; i < m.length();i++) {
		
		characters = m.charAt(i);
		numbertoletter[i] = characters;
		lettertonumber[i] = BigInteger.valueOf(numbertoletter[i]);	
	}
	
	System.out.print("Message (m): ");
	for(int i = 0; i < numbertoletter.length;i++) {
	System.out.print(numbertoletter[i]);
	}
	System.out.println();

	System.out.print("Letter to number: ");
	for(int i = 0; i < lettertonumber.length;i++) {
	System.out.print(lettertonumber[i] + ", ");
	if(i == lettertonumber.length - 1) {
		
		System.out.print(lettertonumber[i]);
	}
	}
	System.out.println();
	System.out.println();
	System.out.println("Encrypting......");
	System.out.println();

	BigInteger encrypt = null;
	BigInteger[] c = new BigInteger[m.length()];
	
	BigInteger e_to_bigInt = BigInteger.valueOf(e2);
	
	for(int j = 0; j < m.length();j++) {
		
		encrypt =  modularExponentiation(lettertonumber[j],e_to_bigInt,n2);
		c[j] = encrypt;
	}
	System.out.println();
	System.out.print("Encrypted Message to number (c): ");

	for(int i = 0; i < c.length;i++) {
	System.out.print(c[i] + ", ");
	if(i == c.length - 1) {
		
		System.out.print(c[i]);
	}
	}

	System.out.println();
	System.out.println();

	System.out.println("Decrypting......");


	char[] decryptedtext = new char[m.length()];
	int[] decryptedtextInt = new int[m.length()];

	for(int j = 0; j < m.length();j++) {
	BigInteger temp4 = modularExponentiation((c[j]),d2,n2);
	decryptedtextInt[j] = temp4.intValue();
	decryptedtext[j] = (char) temp4.intValue();

	}

	System.out.println();
	String l = " ";
	System.out.print("Decrypted Message (m): ");
	for(int i = 0; i < decryptedtext.length;i++) {
		System.out.print(decryptedtext[i]);	
		l = l + decryptedtext[i];
	}

	System.out.println();
	System.out.print("Decrypted Message to number: ");
	for(int i = 0; i < decryptedtextInt.length;i++) {
	System.out.print(decryptedtextInt[i] + ", ");
	if(i == decryptedtextInt.length - 1) {
		
		System.out.print(decryptedtextInt[i]);
	}
	}

	Random r3 = new Random();
	BigInteger random = new BigInteger(7,r3);
	
	BigInteger[] c_attack = new BigInteger[m.length()];
	for(int i = 0; i < c_attack.length;i++) {
		BigInteger temp = random.pow(e2);
		c_attack[i] = c[i].multiply(temp).mod(n2);      //here we swap ciphertext C with C' shown in the user interface
	}
	String b = " ";
	String f = " ";

	char[] c_attack_to_text = new char[m.length()];

	for(int i = 0; i < c_attack.length;i++) {
		BigInteger temp = c_attack[i].modPow(d2, n2);      //Bob decrypts the tampered ciphertext C'. it will print out a message most likely filled with symbols. Bob will not understand this and will relay this back to Alice. Charlie will intercept it midway
		c_attack[i] = temp;
		c_attack_to_text[i] = (char) c_attack[i].intValue();
		b+= c_attack[i] + " ";
		f+= c_attack_to_text[i];
	}	

	String y = " ";
	String z = " ";
	for(int i = 0; i < c_attack.length;i++) {
		c_attack[i] = c_attack[i].divide(random);       //once charlie intercepted ciphertect C', he just has to simply divide the result with r - the random value stored in random variable above
		c_attack_to_text[i] = (char) c_attack[i].intValue();
		y+= c_attack[i] + " ";
		z+= c_attack_to_text[i];
	}
	System.out.println();
	System.out.println();

	System.out.println("Alice                      Charlie                    Bob\n"
			+"--------------------------------------------------------------------------------\n"
			+"m = "+m+"\nv\nm^e mod n      >      c = m^e mod n      >      c' = c * random^e mod n \n  "
	          + "                      swap with...\n"
			  +"                    c' = c * random^e mod n                 v   \n\n"
	          + "c'^d mod n     <        c'^d mod n       <         c'^d mod n ="+f+"\n\n"
	          + "v                           v    \n\n"
	          + "m^e mod n              c'^d mod n ="+b+"\n\n"
	          + "v\n\n"
	          + "m^e mod n      >         m^e mod n       >        m^e mod n\n\n"
	          + "                            v                        v\n\n"
	          + "                       c'^d mod n/random           "+l+"\n\n"
	          +"                            v                                                 \n\n"
	          +"                       c'^d mod n/random ="+y+"                                               \n\n  "
	          +"                          v             \n\n "
	          +"                      c'^d mod n/random ="+z+"          ");

	System.out.println();
	System.out.println();
	problematicState();

		}
		

public BigInteger firstprimeNumber(BigInteger p) {	 //this generates a 256 bit random prime number for p in the RSA algorithm
	
	 Random a = new Random();
	 p = BigInteger.probablePrime(256, a);
   return p;


}
public BigInteger secondprimeNumber(BigInteger q) {	 //this generates a 256 bit random prime number for q in the RSA algorithm
	
	 Random a = new Random();
	 q = BigInteger.probablePrime(256, a);
  return q;

}

public  void problematicState() { //this method emulates a problematic state the RSA algorithm can have when user input small p and q values
	Scanner sc=new Scanner(System.in);
	System.out.println("********************************************Problematic State********************************************");
	System.out.println("enter a message for the problematic state: ");
	String m = sc.nextLine();
	System.out.println("enter prime number p thats less than 11: ");
	int p = sc.nextInt();
	
	System.out.println("enter prime number q thats less than 11: ");
	int q = sc.nextInt();
	
	boolean a = true;
	boolean b = true;
	for(int i = 2; i <p;i++) {
		if(p % i == 0) {
			a = false;
		}
	}
	for(int i = 2; i <q;i++) {
		if(q % i == 0) {
			b = false;
		}
	}
	
if(p < 11 && q < 11 && a == true && b == true) {

	int n = p*q;
	int r = (p-1) * (q-1);
	System.out.println();
	System.out.println("n: " + n);
	System.out.println("r: " + r);

	int e;
	 for(e=2;e<r;e++)
	 {
	 if(gcd(e,r)==1)            // e is for public key exponent
	 { 
	 break;
	 }
	 }
	 System.out.println("e: "+e); 
	 int d = 0;
	 for(int i=0;i<=9;i++)
	 {
	 int x=1+(i*r);
	 if(x%e==0)      //d is for private key exponent
	 {
	 d=x/e;
	 break;
	 }
	 }
	 System.out.println("d: "+d); 

	 BigInteger[] mtoint = new BigInteger[m.length()];
	 char[] mtotext = new char[m.length()];

	 for(int i =0;i<mtoint.length;i++) {
		 mtoint[i] = BigInteger.valueOf(m.charAt(i));
	 }
	 BigInteger n2 = BigInteger.valueOf(n);

	 for(int i =0;i<mtoint.length;i++) {
		 mtoint[i] = mtoint[i].pow(e).mod(n2);
		 mtotext[i] = (char) mtoint[i].intValue();
	 }
	 System.out.println("text encrypted: " + Arrays.toString(mtotext));
	 
	 BigInteger[]decodedint = new BigInteger[m.length()];
	 char[]decodedtext = new char[m.length()];


	 for(int i =0;i<decodedint.length;i++) {
		 decodedint[i] =   mtoint[i].pow(d).mod(n2);
		 decodedtext[i] = (char) decodedint[i].intValue();
	 }
	 System.out.println("text decrypted: " + Arrays.toString(decodedtext));
}
else {
	System.out.println("Please enter a PRIME NUMBER value less than 10 for p and q to see the problematic state!");
	System.out.println();
	problematicState();
}
}

public static void main(String[] args) {

menu a = new menu();

}

public BigInteger modularExponentiation(BigInteger Base, BigInteger Exponent, BigInteger Modulus)  //helps us to encrypt plaintext or decrypt cipher efficiently even if exponent is a big number
{ 
    BigInteger result = new BigInteger("1");      
    Base = Base.mod(Modulus);
    BigInteger comp = Exponent.and(new BigInteger("1"));

    while (Exponent.compareTo(new BigInteger("0")) > 0) 
    { 
    	comp = Exponent.and(new BigInteger("1"));
        if(comp.equals(BigInteger.ONE)) 
        	result = result.multiply(Base);
        result = result.mod(Modulus);
        Exponent = Exponent.shiftRight(1);
        Base = Base.multiply(Base);
        Base = Base.mod(Modulus);

    } 
    return result; 
} 

static int gcd(int val1, int val2)    //helps us to generate a random list of 'e' values in which we choose randomly
{
if(val1==0)
return val2; 
else
return gcd(val2%val1,val1);
}

public BigInteger[] A() {
	if(classcounter == 0 ) {     //within this if statement, we perform the first step of the protocol
		
		System.out.println("Protocol\n---------------------");
	    
		String requestfromA = "requesting public key B";
		System.out.println("1. A sends following message to server - " + requestfromA+"\n");
		BigInteger[] lettertonumber = new BigInteger[requestfromA.length()];	
		char[] numbertoletter = new char[requestfromA.length()];	
	   char characters = 0;
		for(int i = 0; i < requestfromA.length();i++) {
			characters = requestfromA.charAt(i);   //encrypted message sent to server
			numbertoletter[i] = characters;
			lettertonumber[i] = BigInteger.valueOf(numbertoletter[i]);	
		}

		BigInteger encrypt = null;
		A_sends_Encrypted_message_to_server = new BigInteger[requestfromA.length()];
		BigInteger e_to_bigInt = BigInteger.valueOf(e3);
		
		for(int j = 0; j < requestfromA.length();j++) {
			
			encrypt =  modularExponentiation(lettertonumber[j],e_to_bigInt,n3);
			A_sends_Encrypted_message_to_server[j] = encrypt;
		}
		classcounter++;
		return A_sends_Encrypted_message_to_server;
	}

if(classcounter == 1) {   //within this if statement, we decrypt the message i.e. the public key B sent from server to A
	char[] decryptedtext = new char[Server_Sends_Encrypted_PublicKeyB_To_A.length];
	for(int j = 0; j < Server_Sends_Encrypted_PublicKeyB_To_A.length;j++) {
	BigInteger temp4 = modularExponentiation((Server_Sends_Encrypted_PublicKeyB_To_A[j]),d,n);
	decryptedtext[j] = (char) temp4.intValue();
	}

	String nLoc = "";
	String eLoc = "";

	int count = 0;
	for(int i = 0;i <decryptedtext.length;i++) {
		if(decryptedtext[i] == ' ') {
			count++;
			i++;
		}
		if(decryptedtext[i] != ' ' && count == 0) {
			nLoc+= decryptedtext[i];
		}
		if(count == 1) {
			eLoc+= decryptedtext[i];
		}
	}
	B_n2 = new BigInteger(nLoc);   //decrypted public key n of B
	B_e2 = Integer.parseInt(eLoc); //decrypted public key e of B
	BigInteger[] b_e2_b_n2 = new BigInteger[2];
	b_e2_b_n2[0] = B_n2;
	b_e2_b_n2[1] = BigInteger.valueOf(B_e2);
	classcounter++;
	return b_e2_b_n2;

	}
	
   if(classcounter == 2) { //within this if statement, we perform the third step of the protocol
    BigInteger encrypt = null;
	Random r = new Random();
    nonceA = r.nextInt((100000000 - 0) + 1) + 0;

	BigInteger[] c = new BigInteger[1];
	BigInteger e_to_bigInt = BigInteger.valueOf(B_e2);
	
	c[0] = BigInteger.valueOf(nonceA);
	
	for(int j = 0; j < c.length;j++) {
		
		encrypt =  modularExponentiation(c[j],e_to_bigInt,B_n2);
		c[j] = encrypt;
	}
	
    System.out.println("3. A sends encrypted nonceA to B\nActual NonceA: " + nonceA + "\nEncrypted nonceA: " + Arrays.toString(c)+"\n");

    encryptNonceA = c;
	classcounter++;
	return c;

	}
   
   //final step of the protocol
	else {             //here, A decrypts nonceA for proof that B has successfully decrypted it previously. A also decrypts nonceB and sends this back to B
		BigInteger[] OurEncryptedNonceB = encryptNonceB;
		decryptNonceB = new BigInteger[OurEncryptedNonceB.length];    //decrypting nonce B
		for(int j = 0; j < OurEncryptedNonceB.length;j++) {
		BigInteger temp4 = modularExponentiation((OurEncryptedNonceB[j]),d,n);
		decryptNonceB[j] = temp4;
		}
		
		decryptNonceA = new BigInteger[B_send_Encrypted_NonceA_To_A.length];    //decrypting nonce A
		for(int j = 0; j < B_send_Encrypted_NonceA_To_A.length;j++) {
		BigInteger temp4 = modularExponentiation((B_send_Encrypted_NonceA_To_A[j]),d,n);
		decryptNonceA[j] = temp4;
		}
		
		if(decryptNonceA[0].intValue() == nonceA) {
		    BigInteger encrypt = null;
			BigInteger[] c = new BigInteger[1];
			BigInteger e_to_bigInt = BigInteger.valueOf(B_e2);
			
			c = decryptNonceB;
			
			for(int j = 0; j < c.length;j++) {
				
				encrypt =  modularExponentiation(c[j],e_to_bigInt,B_n2);
				c[j] = encrypt;
			}
            System.out.println("7. A encrypts and sends nonceB to B to prove that A has decrypted it!\nDecrypted NonceB: " + decryptNonceB[0] +"\nEncrypted NonceB: " + c[0]+"\n");
            encryptNonceB2 = c;
			B();
			return c;
			
		}
		else {
			System.out.println("Decrypted Nonce A sent from B does not match ACTUAL Nonce A!");
			System.out.println("END PROTOCOL!");
			return null;
		}
		}	}

public BigInteger[] B() {

	if(classcounter == 3) { //within this if statement, we perform the fourth step of the protocol
		
		String requestfromB = "requesting public key A";
		System.out.println("4. B sends following message to server - " + requestfromB+"\n");

		BigInteger[] lettertonumber = new BigInteger[requestfromB.length()];	
		char[] numbertoletter = new char[requestfromB.length()];	
	   char characters = 0;
		for(int i = 0; i < requestfromB.length();i++) {
			characters = requestfromB.charAt(i);
			numbertoletter[i] = characters;
			lettertonumber[i] = BigInteger.valueOf(numbertoletter[i]);	
		}
		BigInteger encrypt = null;
	    B_sends_Encrypted_message_to_server = new BigInteger[requestfromB.length()];
		BigInteger e_to_bigInt = BigInteger.valueOf(e3);
		
		for(int j = 0; j < requestfromB.length();j++) {
			
			encrypt =  modularExponentiation(lettertonumber[j],e_to_bigInt,n3);
			B_sends_Encrypted_message_to_server[j] = encrypt;
		}
		classcounter++;
		return B_sends_Encrypted_message_to_server;
	}
			
	 if(classcounter == 4) {  //within this if statement, we perform the sixth step of the protocol i.e. decrypting NonceA,creating NonceB and sending NonceB and NonceA (encrypted) to A

			String nLoc = "";
			String eLoc = "";
			int count = 0;
			char[] decryptedtext = new char[Server_Sends_Encrypted_PublicKeyA_To_B.length];

	for(int j = 0; j < Server_Sends_Encrypted_PublicKeyA_To_B.length;j++) {
	BigInteger temp4 = modularExponentiation((Server_Sends_Encrypted_PublicKeyA_To_B[j]),d2,n2);
	decryptedtext[j] = (char) temp4.intValue();
	}	

	for(int i = 0;i <decryptedtext.length;i++) {
		if(decryptedtext[i] == ' ') {
			count++;
			i++;
		}
		if(decryptedtext[i] != ' ' && count == 0) {
			nLoc+= decryptedtext[i];
		}
		if(count == 1) {
			eLoc+= decryptedtext[i];
		}
	}
	BigInteger B_n = new BigInteger(nLoc);   
	int B_e = Integer.parseInt(eLoc);

	BigInteger[] EncryptedNonceA = encryptNonceA;

	Random r = new Random();
    nonceB = r.nextInt((100000000 - 0) + 1) + 0;

	
	decryptNonceA = new BigInteger[EncryptedNonceA.length];    //decrypting nonce A
	for(int j = 0; j < EncryptedNonceA.length;j++) {
	BigInteger temp4 = modularExponentiation((EncryptedNonceA[j]),d2,n2);
	decryptNonceA[j] = temp4;
	}	

	BigInteger encrypt = null;                         //encrypting nonce B
	BigInteger[] c = new BigInteger[1];
	BigInteger e_to_bigInt = BigInteger.valueOf(B_e);
	c[0] = BigInteger.valueOf(nonceB);
	
	for(int j = 0; j < c.length;j++) {
		
		encrypt =  modularExponentiation(c[j],e_to_bigInt,B_n);
		c[j] = encrypt;
	}

	B_send_Encrypted_NonceA_To_A = new BigInteger[1];
	B_send_Encrypted_NonceA_To_A[0] = decryptNonceA[0];
	
	for(int j = 0; j < B_send_Encrypted_NonceA_To_A.length;j++) {                        //encrypting the decrypted nonce A to send back to A for proof
		encrypt =  modularExponentiation(B_send_Encrypted_NonceA_To_A[j],e_to_bigInt,B_n);
		B_send_Encrypted_NonceA_To_A[j] = encrypt;
	}
	System.out.println("6. B encrypts and sends nonceB to A. B also encrypts and sends nonceA to prove he has decrypted it!\nActual NonceB: " + nonceB +"\nEncrypted NonceB: " + Arrays.toString(c) + "\nDecrypted NonceA: " + decryptNonceA[0] + "\nEncrypted NonceA: " + Arrays.toString(B_send_Encrypted_NonceA_To_A)+"\n");

	
	encryptNonceB = c;
	classcounter++;
	return c;

	}
	else {  //NonceB sent back to B from A. B decrypts it to see proof that A has successfully decrypted it
		BigInteger[] decryptNonceBSentFromA =new BigInteger[encryptNonceB2.length];
		for(int j = 0; j < decryptNonceBSentFromA.length;j++) {
		BigInteger temp4 = modularExponentiation((encryptNonceB2[j]),d2,n2);
		decryptNonceBSentFromA[j] = temp4;
		}	
		if(decryptNonceBSentFromA[0].intValue() == nonceB) {
			System.out.println("Success!");
		}
		else {
			System.out.println("Decrypted Nonce B sent from A does not match ACTUAL Nonce B!");
			System.out.println("END PROTOCOL!");
		}
		return decryptNonceBSentFromA;
	}
	
}

public void Implement_Protocol() {   
	if(n == null || e == 0) {   //run part 1 before running part 2!!!
		System.out.println();
		System.out.println("Please run part 1 before running part 2!");
		System.out.println();
	}
	else {		
		A();
		BigInteger[] c1 = A_sends_Encrypted_message_to_server;
		char[] decryptedtext = new char[c1.length];

		for(int j = 0; j < c1.length;j++) {
		BigInteger temp4 = modularExponentiation((c1[j]),d3,n3);
		decryptedtext[j] = (char) temp4.intValue();    //server decrypts text sent from A

		} 
	
		//server responds to A's message
        //server encrypts public key b and send to A
		String ServermessagetoA = n2.toString() + " " + e2;
		BigInteger[] lettertonumber = new BigInteger[ServermessagetoA.length()];	
		char[] numbertoletter = new char[ServermessagetoA.length()];	
	   char characters = 0;
		for(int i = 0; i < ServermessagetoA.length();i++) {
			characters = ServermessagetoA.charAt(i);
			numbertoletter[i] = characters;
			lettertonumber[i] = BigInteger.valueOf(numbertoletter[i]);	
		}
		BigInteger encrypt = null;
		Server_Sends_Encrypted_PublicKeyB_To_A = new BigInteger[ServermessagetoA.length()];
		BigInteger e_to_bigInt = BigInteger.valueOf(e);
		
		for(int j = 0; j < ServermessagetoA.length();j++) {
			
			encrypt =  modularExponentiation(lettertonumber[j],e_to_bigInt,n);
			Server_Sends_Encrypted_PublicKeyB_To_A[j] = encrypt; //server encrypts public key b and send to A
		}
		A();
		System.out.println("2. server sends encrypted signed public key B to A\n" + Arrays.toString(Server_Sends_Encrypted_PublicKeyB_To_A)+"\n");
		A();
		B();

	BigInteger[] c2 = B_sends_Encrypted_message_to_server;

		char[] decryptedtext2 = new char[c2.length];

		for(int j = 0; j < c2.length;j++) {
		BigInteger temp4 = modularExponentiation((c2[j]),d3,n3);
		decryptedtext2[j] = (char) temp4.intValue();
		} 	
		//server decrypts text sent from B

		
		//server responds to B's message
        //server encrypts public key A and send to B
String ServermessagetoB = n.toString() + " " + e;
 lettertonumber = new BigInteger[ServermessagetoB.length()];	
 numbertoletter = new char[ServermessagetoB.length()];	
 characters = 0;
for(int i = 0; i < ServermessagetoB.length();i++) {
	characters = ServermessagetoB.charAt(i);
	numbertoletter[i] = characters;
	lettertonumber[i] = BigInteger.valueOf(numbertoletter[i]);	
}
 encrypt = null;
 
 Server_Sends_Encrypted_PublicKeyA_To_B = new BigInteger[ServermessagetoB.length()];
 e_to_bigInt = BigInteger.valueOf(e2);

for(int j = 0; j < ServermessagetoB.length();j++) {
	
	encrypt =  modularExponentiation(lettertonumber[j],e_to_bigInt,n2);
	Server_Sends_Encrypted_PublicKeyA_To_B[j] = encrypt; //server encrypts public key A and send to B
}


System.out.println("5. server sends encrypted signed public key A to B\n" + Arrays.toString(Server_Sends_Encrypted_PublicKeyA_To_B)+"\n");
B();

A();

System.out.println("END OF PROTOCOL!\n");
partII_UI();
protocol_vulnerability() ;

	}

}
public void partII_UI() {   //User Interface for part 2
	System.out.println("Meaning: PA = public key A  PB = public key B  nA = nonce A  nB = nonce B  s = signed \n");
	System.out.println("A                              Server                             B\n"
			+"--------------------------------------------------------------------------------\n"
			+"A, B             >               PB\n\n"
			+"                                 v\n\n"
			+"(PB,B)s          <             (PB,B)s\n\n"
			+"v\n\n"
			+"(nA,A)PB                         >                             (nA,A)PB\n\n" 
			+ "                                                                  v\n\n"
			+"                                 PA               <              B, A\n\n"
			+"                                 v\n\n"
			+"                               (PA,A)s            >             (PA,A)s\n\n"
			+"                                                                  v\n\n"
			+"(nA,nB)PA                        <                             (nA,nB)PA\n\n"
			+"v\n\n"
			+"(nB)PB                           >                              (nB)PB");
	System.out.println();
	System.out.println();


}
public void protocol_vulnerability() {
	System.out.println("********************************************Protocol Vulnerability********************************************");
	System.out.println("Meaning: Z = impersonator  PZ = public key Z  PA = public key A  PB = public key B  nA = nonce A  nB = nonce B \n");
	System.out.println("A > Z (nA,A)PZ");
	System.out.println("Z > B (nA,A)PB");
	System.out.println("B > Z (nA,nB)PA");
	System.out.println("Z > A (nA,nB)PA");
	System.out.println("A > Z(nA)PZ");
	System.out.println("Z > B(nB)PB");

}
}