import java.math.BigInteger;

public class RSA
{

	private BigInteger p, q, n, m, e, d;

	RSA(String s)
	{
		this.p = new BigInteger("48112959837082048697");
		this.q = new BigInteger("54673257461630679457");
		this.n = new BigInteger("0");
		this.m = new BigInteger("0");
		this.e = new BigInteger("0");
		this.d = new BigInteger("0");
	}

	private BigInteger getE() 
	{
		BigInteger bi = new BigInteger("2");


		while(bi.compareTo(this.n) <= 0) {
			if(this.n.mod(bi).compareTo(BigInteger.ZERO) != 0)
				break;
			bi = bi.add(BigInteger.ONE);
		}



		return bi;
	}

	public void calculate()
	{	
		this.n = this.p.multiply(this.q);
		this.m = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
		this.e = this.getE();
		this.d = 
		System.out.println("e : " + this.e);
		
	}

	public static void main(String args[])
	{
		if(args.length != 1) {
			System.out.println("Command Line Arguments Needed!!");
			return;
		}
		
		RSA rsa = new RSA(args[0]);
		rsa.calculate(); 
	}
} 
