import java.math.BigInteger;
import java.util.*;

public class Main
{
    private static class FermatsFactorizationMethod
    {
        private BigInteger num;
    
        FermatsFactorizationMethod(String s)
        {
            this.num = new BigInteger(s);
        }

        public BigInteger[] factorize() 
        {
            BigInteger a, b;
            
            BigInteger root = this.num.sqrt();
            if(root.multiply(root).compareTo(this.num) != 0)
                a = root.add(BigInteger.ONE);
            else
                a = root;
            
            b = new BigInteger("0");

            while(true) {
                BigInteger bSquare = a.multiply(a).subtract(this.num);
                b = bSquare.sqrt();
                
                if(bSquare.compareTo(b.multiply(b)) == 0) 
                    break;
                
                a = a.add(BigInteger.ONE);
            }
            
            return new BigInteger[] {a.add(b), a.subtract(b)};
        }
    }

    private static class TrialDivision
    {
        private BigInteger num;
    
        TrialDivision(String s)
        {
            this.num = new BigInteger(s);
        }

        public BigInteger[] factorize() 
        {
            BigInteger bi = new BigInteger("2");
            while(bi.compareTo(this.num) <= 0) {
                if(this.num.mod(bi).compareTo(BigInteger.ZERO) == 0)
                    break;
                bi = bi.add(BigInteger.ONE);
            }
            return new BigInteger[] {bi, this.num.divide(bi)};
        }
    }    

    public static void main (String args[]) 
    {
        if(args.length < 1) {
            System.out.println("No Input Found!!");
            return;
        }

        System.out.println("Fermat's Factorization : Time" + "\n\t\t\t\t|\tTrial Division : Time");
        
        for(int i = 0; i < args.length; i++) {
            if(new BigInteger(args[i]).mod(new BigInteger("2")).compareTo(BigInteger.ZERO) == 0) {
                System.out.println(args[i] + " is An Even Number!");
                continue;
            }

            Date start, end;
            BigInteger factors[];

            FermatsFactorizationMethod ffm = new FermatsFactorizationMethod(args[i]);
            start = new Date();
            factors = ffm.factorize();
            end = new Date();
            System.out.println((i+1) + ".\t[ " +factors[0] + " , " + factors[1] + " ] : " + (end.getTime() - start.getTime()) + " ms");

            TrialDivision td = new TrialDivision(args[i]);
            start = new Date();
            factors = td.factorize();
            end = new Date();
            System.out.println("\t\t\t\t|"+ (i+1)+".\t [ " + factors[0] + " , " + factors[1] + " ] : " + (end.getTime() - start.getTime()) + " ms\n");

        }
    }
}