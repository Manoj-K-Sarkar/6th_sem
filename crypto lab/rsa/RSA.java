import java.math.BigInteger;
import java.util.*;
import java.lang.Math;

public class RSA
{
        private BigInteger ascii[], eText[], dText[];
        private BigInteger p, q, n, m, e, d;

        private BigInteger producePrime(int length)
        {
                Random random = new Random();
                BigInteger res = BigInteger.probablePrime((int)Math.floor(length*(10.0/3.0)),random);

                BigInteger bi[] = new FermatsFactorizationMethod(res.toString()).factorize();
                if(bi[0].compareTo(BigInteger.ONE) == 0 || bi[1].compareTo(BigInteger.ONE) == 0)
                        return res;
                return producePrime(length);
        }

        private BigInteger[] produceAscii( String s)
        {
                BigInteger arr[] = new BigInteger[s.length()];

                for(int i = 0; i < s.length(); i++) {
                        arr[i] = BigInteger.valueOf((int)s.charAt(i));
                }
                return arr;
        }


        RSA(String s, String bit)
        {
                this.ascii = this.produceAscii(s);
                this.eText = new BigInteger[s.length()];
                this.dText = new BigInteger[s.length()];

                this.p = this.producePrime(Integer.parseInt(bit));
                // System.out.println(" p : " + this.p);

                this.q = p;
                while(q.compareTo(p) == 0) {
                        q = this.producePrime(Integer.parseInt(bit));
                }
                // System.out.println(" q : " + this.q);

                this.n = new BigInteger("0");
                this.m = new BigInteger("0");
                this.e = new BigInteger("0");
                this.d = new BigInteger("0");
        }

 
        public void calculate()
        {
                this.n = this.p.multiply(this.q);
                // System.out.println(" n : " + this.n);

                this.m = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
                // System.out.println(" m : " + this.m);

                FermatsFactorizationMethod ffm = new FermatsFactorizationMethod(this.m.add(BigInteger.ONE).toString());
                BigInteger bi[] = ffm.factorize();

                this.e = bi[0];
                // System.out.println(" e : " + this.e);

                this.d = bi[1];
                // System.out.println(" d : " + this.d);
        }

        public void disp()
        {
                System.out.println(" p = " + this.p);
                System.out.println(" q = " + this.q);
                System.out.println(" n = (p*q) = (" + this.p + "*" + this.q + ") = " + this.n);
                System.out.println(" m = (p-1)*(q-1) = (" + this.p.subtract(BigInteger.ONE) + "*" + this.q.subtract(BigInteger.ONE) + ") = " + this.m);
                System.out.println(" public Key = { e,n } = { " + this.e + " , " + this.n + "}");
                System.out.println(" private Key = { d,n } = { " + this.d + " , " + this.n + "}");
        }


        public void encodeDecode() 
        {

                System.out.print("Ascii Value Of PlainText : ");
                for(int i = 0; i < ascii.length; i++)
                        System.out.print(ascii[i] + " ");


                System.out.print("\n\nEncrypted CipherText : ");
                for(int i = 0; i < ascii.length; i++) {
                        eText[i] = ascii[i].modPow(e,n);
                        System.out.print(eText[i] + " ");
                }

                System.out.print("\n\nDecrypted Text : ");
                for(int i = 0; i < ascii.length; i++) {
                        dText[i] = eText[i].modPow(d,n);
                        System.out.print((char)dText[i].intValue());
                }

                System.out.println();
        }

        public static void main(String args[])
        {
                if(args.length != 2) {
                        System.out.println("Command Line Arguments Needed!!");
                        return;
                }

                RSA rsa = new RSA(args[0], args[1]);
                rsa.calculate();
                rsa.disp();
                System.out.println("PlainText : " + args[0]);
                rsa.encodeDecode();
        }


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


} 
