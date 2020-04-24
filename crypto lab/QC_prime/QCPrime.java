import java.math.BigInteger;

public class QCPrime
{
    private BigInteger x, a, p;
    
    QCPrime(String args1, String args2)
    {
        this.x = BigInteger.ZERO;
        this.a = new BigInteger(args1);
        this.p = new BigInteger(args2);
    }

    private BigInteger pow(BigInteger num, BigInteger pow)
    {
        if(pow.compareTo(BigInteger.ZERO) < 0) return BigInteger.ZERO;
        BigInteger res = BigInteger.ONE;
        for(BigInteger i = BigInteger.ZERO; i.compareTo(pow) < 0; i=i.add(BigInteger.ONE))
            res = res.multiply(this.a);
        return res;
    }

    public boolean calculate()
    {
        BigInteger ap1 = pow(this.a, this.p.subtract(BigInteger.ONE).divide(new BigInteger("2")));
    //    System.out.println(ap1);
        if(ap1.mod(this.p).compareTo(BigInteger.ONE) != 0)
            return false;            

        while(this.x.multiply(this.x).mod(this.p).compareTo(a) != 0) {
            this.x = this.x.add(BigInteger.ONE);
        }
        return true;
    }

    public void display()
    {
        System.out.println("a = " + this.a);
        System.out.println("p = " + this.p);
    //    System.out.println("(p-1)/2 = " );
        System.out.println("x = " + this.x);
    }

    public static void main( String args[]) 
    {
        if(args.length != 2)
        {
            System.out.println("Arguments needed for a and p.");
            System.exit(0);
        }

        QCPrime qc = new QCPrime(args[0],args[1]);
        if(qc.calculate())
            qc.display();
        else
            System.out.println("Hobena");
    }
}