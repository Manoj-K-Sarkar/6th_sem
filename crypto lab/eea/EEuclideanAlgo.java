import java.math.BigInteger;

public class EEuclideanAlgo{
	private  BigInteger x, n;
	
	EEuclideanAlgo(String r1, String r2) {
		this.x = new BigInteger(r2);
		this.n = new BigInteger(r1);
	}

	public BigInteger evaluate() {
		BigInteger q, r, r1, r2, t1, t2, t;
		r1 = this.n;
		r2 = this.x;
		t1 = new BigInteger("0");
		t2 = new BigInteger("1");
		
		while(r2.compareTo(new BigInteger("0")) != 0) {
			q = r1.divide(r2);
			r = r1.mod(r2);
			t = (t1.subtract(t2.multiply(q)));
			r1 = r2;
			r2 = r;
			t1 = t2;
			t2 = t;
		}
		return (t1.compareTo(new BigInteger("0")) > 0)? t1:t1.add(this.n);
	}

	public static void main ( String args[])
	{
		if(args.length < 2) {
			System.out.println("Missing Command Line Argument!!");
			return;
		}
		
		EEuclideanAlgo eea = new EEuclideanAlgo(args[0],args[1]);
		BigInteger res = eea.evaluate();
		System.out.println(res);
	}

}
