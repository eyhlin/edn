import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution3 {
	
	static int MOD = 1000000009;
	static int MODSQR = 100000;
	static int SQRT = 31623;
	
	
	static int modulus(int x, int mod){
	    return ((x%mod)+mod)%mod;
	}
	
	
	Map<Integer, Long> cache = new HashMap<>();
	

    int highwayConstruction(long n, int k) {
    	cache.put(1, 1L);
    	int cost = 0;
    	
    	if(false){
    	}
    	else{
	    	for(int i=1;i<n;i++){
	    		if(cache.containsKey(i)){
	    			cost+=cache.get(i);
	    			cost%=MOD;
	    		}else{
	    			int power = 1;
	    			long num = i;
	    			while(power*2<=k){
	    				power*=2;
	    				num*=num;
	    				num%=MOD;
	    			}
	    			while(power<k){
	    				num*=i;
	    				num%=MOD;
	    				power++;
	    			}
	    		
	    			cost+=num;
	    			cost%=MOD;
	    			cache.put(i, num);
	    			
	    			
	    			int temp = i;
	    			while(temp*temp<n){
	    				num*=num;
	    				num%=MOD;
	    				cache.put(temp*temp, num);
	    				temp*=temp;
	    			}
	    		}
	    	}
    	}
    	return cost;
    }

    public static void main(String[] args) {
    	Map<Integer, Solution3> solutionCache = new HashMap<>();
    	
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long n = in.nextLong();
            int k = in.nextInt();
            
            Solution3 s;
            if(solutionCache.containsKey(k))s = solutionCache.get(k);
            else s = new Solution3();
            int result = s.highwayConstruction(n, k);
            System.out.println(result-1);
            solutionCache.put(k, s);
        }
        in.close();
    }
}
