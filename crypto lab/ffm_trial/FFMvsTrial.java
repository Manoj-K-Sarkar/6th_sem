// import java.math.BigInteger;

// public class FermatsFactorizationMethod
// {
//     private BigInteger arr[];

//     FermatsFactorizationMethod(String args[])
//     {
//         this.arr = new BigInteger[args.length];

//         for(int i = 0; i < args.length; i++) 
//             this.arr[i] = new BigInteger(args[i]);
//     }

//     public static void main (String args[]) 
//     {
//         if(args.length < 1) {
//             System.out.println("No Input Found!!");
//             return;
//         }

//         FermatsFactorizationMethod ffm = new FermatsFactorizationMethod(args);
        
//     }
// }
import java.math.BigInteger;

public class FFMvsTrial
{

    public static void fermatsFactorizationMethod(String s) {
        BigInteger num = new BigInteger(s);
        
        System.out.println(num.sqrt());

    }

    public static void main (String args[]) 
    {
        if(args.length < 1) {
            System.out.println("No Input Found!!");
            return;
        }
        
        for(int i = 0; i < args.length; i++) {

            fermatsFactorizationMethod(args[i]);

        }
    }
}