public class HillCipher
{
	final int SIZE = 4;
	static final int KEYLENGTH = 16;

	private String plainText;
	private String key;
	private String cipherText;
	private String decryptedText;
	private int plainTextMatrix[][];
	private int keyMatrix[][];
	private int cipherTextMatrix[][];

	private int determinantKey;

        public static void main (String args[])
        {
                HillCipher hillCipher = new HillCipher(args[0],args[1]);
                System.out.println("PlainText : " + hillCipher.plainText);
                hillCipher.checkKey();
                System.out.println("Key : " + hillCipher.key);
                hillCipher.encrypt();
                System.out.println("CipherText : " + hillCipher.cipherText);
                hillCipher.decrypt();
        }

        HillCipher(String pt, String k)
        {
                this.plainText = pt;
                this.key = k;
                this.cipherText = "";
		this.decryptedText = "";

                this.plainTextMatrix = new int[SIZE][((pt.length() - 1) / 4) + 1];
                this.keyMatrix = new int[SIZE][SIZE];
                this.cipherTextMatrix = new int[SIZE][this.plainTextMatrix[0].length];
        }

        public void checkKey()
        {
                if ( this.key.length() != KEYLENGTH )
                {
                        System.out.println("Key Length Should be 16 \n !!Automatically Exit!!");
                        System.exit(0);
                }
        }
	
	private void setPlainTextMatrix ()
	{
		int index = 0;
		for (int i = 0; i < this.plainTextMatrix[0].length; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				if (index < this.plainText.length())
				{
					this.plainTextMatrix[j][i] = (int)(this.plainText.charAt(index) - 'A');
					index++;
				}else this.plainTextMatrix[j][i] = 25;
			}
		}
		
		System.out.println("=====PlainTextMatrix======");
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < this.plainTextMatrix[0].length; j++)
			{
				System.out.print(" " + this.plainTextMatrix[i][j]);
			}
			System.out.println();
		}
	
	}
	
	private void setKeyMatrix()
	{
		int index = 0;
		System.out.println("======KeyMatrix=======");
		for (int i = 0; i < SIZE; i++)
		{
			for ( int j = 0; j < SIZE; j++)
			{
				this.keyMatrix[i][j] = (int)(this.key.charAt(index++) - 'A');
				System.out.print( " " + keyMatrix[i][j] ); 
			}
			System.out.println();
		}
	}

	private void setCipherTextMatrix()
	{
		for(int i = 0 ; i < SIZE; i++)
		{
			for(int j = 0; j < this.cipherTextMatrix[0].length; j++)
			{
				this.cipherTextMatrix[i][j] = 0;
				for(int k = 0; k < SIZE; k++)
				{
					this.cipherTextMatrix[i][j] += 
						this.keyMatrix[i][k] * this.plainTextMatrix[k][j];
				}
				this.cipherTextMatrix[i][j] %= 26;
			}
		}

		System.out.println("=====CipherTextMatrix======");
                for (int i = 0; i < SIZE; i++)
                {
                        for (int j = 0; j < this.cipherTextMatrix[0].length; j++)
                        {
                                System.out.print(" " + this.cipherTextMatrix[i][j]);
                        }
                        System.out.println();
                }

	
	}

	private void setCipherText()
	{
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < this.cipherTextMatrix[0].length; j++)
			{
				this.cipherText += (char)(this.cipherTextMatrix[i][j] + 'A');
			}
		}
	}

        public void encrypt()
        {
                this.setPlainTextMatrix();
                this.setKeyMatrix();
                this.setCipherTextMatrix();

                this.setCipherText();
        }


        private int getDeterminantValue(int[][] matrix)
	{
		int result = 0;
		if(matrix[0].length == 2)
		{
			result = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
			return result;
		}

		boolean sign = true;
		int size = matrix[0].length;
		for(int i = 0; i < size; i++)
		{
			int newMatrix[][] = new int[size-1][size-1];
			int c , r;
			c = r = 0;
			for(int j = 1; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					if(k==i) continue;
					newMatrix[r%(size-1)][c%(size-1)] = matrix[j][k];
					c++;
				}
				r++;
			}
			if(sign)	
				result += matrix[0][i]*this.getDeterminantValue(newMatrix);
			else
				result -= matrix[0][i]*this.getDeterminantValue(newMatrix);
			sign = !sign;
		}
		return result;
	}

        private int getInverseValue(int dk)
        {
                int ik = 0;
                while(true){
                        ++ik;
                        if( dk*ik % 26 == 1)
                                return ik;
                }
        }

	
	private void setAdjointMatrix(int[][] matrix)
	{
		
		boolean sign = true;
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				int cofac[][] = new int[SIZE-1][SIZE-1];
				int c, r;
				c = r = 0;
				for(int m = 0; m < SIZE; m++)
				{
					if(m==i) continue;
					for(int n = 0; n < SIZE; n++)
					{
						if(n==j) continue;
						cofac[r%(SIZE-1)][c%(SIZE-1)] = this.keyMatrix[m][n];
						c++;
					}
					r++;
				}
				
				if(sign)
					matrix[j][i] = this.getDeterminantValue(cofac);
				else
					matrix[j][i] -= this.getDeterminantValue(cofac);

				sign = !sign;
			}
		 	sign = !sign;
		}
			
		System.out.println("====AdjointKeyMatrix=====");
                for (int i = 0; i < SIZE; i++)
                {
                        for ( int j = 0; j <  SIZE; j++)
                        {
                                System.out.print( " " + matrix[i][j] );
                        }
                        System.out.println();
                }
	
	}  
	

	public void decrypt()
	{
		int detKey = this.getDeterminantValue(this.keyMatrix);
		System.out.println("DeterminantValue : " + detKey);
		int inverseDetKey = this.getInverseValue(detKey);		

		int adjKeyMatrix[][] = new int[SIZE][SIZE];
		this.setAdjointMatrix(adjKeyMatrix);
		

		float inverseMatrix[][] = new float[SIZE][SIZE];
		System.out.println("=========InverseMatrix============");
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				inverseMatrix[i][j] = (float)((adjKeyMatrix[i][j]) * inverseDetKey);
				//System.out.print( (char)( ((int)( inverseMatrix[i][j]%26 ) ) + 'A') );
				System.out.print(" " + inverseMatrix[i][j]);
			}
			System.out.println();
		}
	
		int newMatrix[][] = new int[SIZE][cipherTextMatrix[0].length];
                for(int i = 0 ; i < SIZE; i++)
                {
                        for(int j = 0; j < cipherTextMatrix[0].length; j++)
                        {
                                for(int k = 0; k < SIZE; k++)
                                {
                                        newMatrix[i][j] += 
                                                inverseMatrix[i][k] * this.cipherTextMatrix[k][j];
                                }
                                newMatrix[i][j] %= 26;
                        }
                }

		System.out.println("=====DecyrptedMatrix======");
                for (int i = 0; i < cipherTextMatrix[0].length; i++)
                {
                        for (int j = 0; j < SIZE; j++)
                        {
                                if(newMatrix[j][i] < 0)
					newMatrix[j][i] += 26;
				this.decryptedText += (char)(newMatrix[j][i] + 'A');
				
                        }
                        
                }

		for (int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < cipherTextMatrix[0].length; j++){

				System.out.print(" " + newMatrix[i][j]);
			}
			System.out.println();
		}
	
		System.out.println(" Decrypted Text : " + this.decryptedText);
	}	
}
