
public class Utility 
{
	
	public static long HashFromString( String strString)
	{
		long lHashValue = 0;
		
		for( int i=0; i<strString.length(); i++ )
		{
			lHashValue = (long)strString.charAt(i) + (lHashValue << 6) + (lHashValue << 16) - lHashValue;
		}
		
		return( lHashValue & 0x7fffffff );
	}
	
	public static long NextPrime(long lPrime) {
		while (!(isPrime(++lPrime)));
		return lPrime;
	}
	
	private static boolean isPrime(long lPrime) {
		int x = 1;
		while (lPrime % ++x != 0 && lPrime > x * x);
		return ((lPrime < x * x || lPrime == 2) && lPrime > 1);
	}
	
}
