import java.math.BigInteger;
class RSA
{
	public static void main(String args[])
	{
		if(args.length<1)
		{
			System.out.println("Please enter command line  arguments");
			return;
		}
		String str=args[0];
		BigInteger encKey[]=new BigInteger[2], decKey[]=new BigInteger[2];
		genKey(encKey,decKey);
		System.out.println("Encryption Key: "+encKey[0]+", " +encKey[1]);
		System.out.println("Decryption Key: "+decKey[0]+", " +decKey[1]);
		BigInteger arr[]=new BigInteger[str.length()];
		BigInteger encryptedText[], decryptedText[];
		for(int i=0;i<arr.length;i++)
		{
			arr[i]=BigInteger.valueOf((int)str.charAt(i));
		}
		System.out.println("\nOriginal Text: "+str);
		System.out.print("\nASCII representation of Original Text: ");
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+" ");
		}
		System.out.print("\n\nCipherText: ");
		/* Encryption takes place here */
		encryptedText = encryptDecrypt(arr,encKey);
		for(BigInteger bint: encryptedText)
		{
			System.out.print(bint+" ");
		}
		System.out.print("\n\nDecrypted Text: ");
		/* Decryption takes place here */
		decryptedText = encryptDecrypt(encryptedText,decKey);
	
		for(BigInteger bint: decryptedText)
		{
			System.out.print((char)bint.intValue());
		}
		System.out.println();
		/*genKey(new BigInteger[]{},new BigInteger[]{});*/
	}
	static void genKey(BigInteger enc[], BigInteger dec[])
	{
		BigInteger p=new BigInteger("48112959837082048697"),
		 q=new BigInteger("54673257461630679457");
		BigInteger n=p.multiply(q);
		/*System.out.println(n);*/
		BigInteger m=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		/*System.out.println(m);*/
		BigInteger e=new BigInteger("3187");
		/*System.out.println(e);*/
		BigInteger d=Eucledian.fun(m,e);
		/*System.out.println(d);*/
		enc[0]=e;
		dec[0]=d;
		enc[1]=dec[1]=n;
	}
	{
	}

	static BigInteger[]  encryptDecrypt(BigInteger arr[],BigInteger key[])
	{
		BigInteger ctext[]=new BigInteger[arr.length];
		for(int i=0;i<arr.length;i++)
		{
			ctext[i]=arr[i].modPow(key[0],key[1]); /*powMod(arr[i],key[0],key[1]);*/
		}
		return ctext;
	}
}

