import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution2 {
	static long MOD = 1_000_000_009;
	static BigInteger ONE = BigInteger.ONE;
	
	class Rational{
		BigInteger numerator, denominator;
		
		
		
		private int gcd(int m, int n) {
	        if (0 == n) return m;
	        else return gcd(n, m % n);
	    }
		
		public Rational(BigInteger num, BigInteger den){
			int g = gcd(num.intValue(), den.intValue());
			this.numerator = num.divide(BigInteger.valueOf(g));
			this.denominator = den.divide(BigInteger.valueOf(g));
		}

		public Rational(int num, int den) {
			int g = gcd(num, den);
			this.numerator = BigInteger.valueOf(num / g);
			this.denominator = BigInteger.valueOf(den / g);
		}
		
		public Rational times(Rational r){
			return new Rational(this.numerator.multiply(r.numerator), this.denominator.multiply(r.denominator));
		}
		public Rational add(Rational r){
			return new Rational(this.numerator.multiply(r.denominator).add(this.denominator.multiply(r.numerator)), this.denominator.multiply(r.denominator));
		}
		public Rational minus(Rational r){
			return new Rational(this.numerator.multiply(r.denominator).subtract(this.denominator.multiply(r.numerator)), this.denominator.multiply(r.denominator));
		}
		public Rational divides(Rational r){
			return new Rational(this.numerator.multiply(r.denominator), this.denominator.multiply(r.numerator));
		}
		
		public void print(){
			System.out.print(this.numerator+"/"+this.denominator);
		}
	}
	
	public Rational[] bernoulli(int k){
		BigInteger[][] binomial = new BigInteger[k+1][k+1];
	    for (int n = 1; n <= k; n++) binomial[0][n] = BigInteger.ZERO;
	    for (int n = 0; n <= k; n++) binomial[n][0] = BigInteger.ONE;
	    
	    for (int i = 1; i <= k; i++)
            for (int j = 1; j <= k; j++)
                binomial[i][j] = binomial[i-1][j-1].add(binomial[i-1][j]);
	    
	    Rational[] bernoulli = new Rational[k+1];
        bernoulli[0] = new Rational(1, 1);
        bernoulli[1] = new Rational(-1, 2);
        for (int i = 2; i < k; i++) {
            bernoulli[i] = new Rational(0, 1);
            for (int j = 0; j < i; j++) {
                Rational coef = new Rational(binomial[i + 1][i + 1 - j], BigInteger.ONE);
                bernoulli[i] = bernoulli[i].minus(coef.times(bernoulli[j]));
            }
            bernoulli[i] = bernoulli[i].divides(new Rational(i+1, 1));
        }
        return bernoulli;
	}
	
	Rational term(Rational[] bernoulli, int c, int k){//kth term for power of c
		Rational coef = bernoulli[k*2];
		
		for(int i=0;i<=(k-1)*2;i++){
			coef = coef.times(new Rational(c-i,1));
		}
		
		for(int i=2;i<=k*2;i++){
			coef = coef.times(new Rational(1,i));
		}
		
		return coef;
	}
	
	long power(long base, int power){//power>0
		long solution = base;
		for(int i=0;i<power-1;i++){
			solution = solution*base;
			solution = solution%MOD;
		}
		return solution;
	}
	
	Rational mod(Rational r){
		if(r.denominator.multiply(BigInteger.valueOf(MOD)).compareTo(r.numerator)>1){
			return new Rational(r.numerator.subtract(r.denominator.multiply(BigInteger.valueOf(MOD))),r.denominator);
		}
		else return r;
	}
	
	long highwayConstruction(Rational[] bernoulli, long n, int c) {
		Solution2 s = new Solution2();
		
		Rational cost =
				new Rational(BigInteger.valueOf(power(n, c+1)), ONE).times(new Rational(ONE, BigInteger.valueOf(c+1)));//first term
		
		cost = mod(cost);
		
		
		Rational second = 
				new Rational(BigInteger.valueOf(power(n, c)), ONE).times(new Rational(ONE, BigInteger.valueOf(2)));//second term
		second = mod(second);
		
		cost = cost.add(second);
		cost = mod(cost);
		
		
		if(cache.containsKey(c)){
			List<Rational> terms = cache.get(c);
			for(int i=1;i<=c/2;i++){
				Rational coeff = terms.get(i-1);
				Rational term = coeff.times( new Rational(BigInteger.valueOf(power(n, c-(i*2-1) )), ONE) );
				term = mod(term);
				cost = cost.add(term);
				cost = mod(cost);
			}
		}else{
			List<Rational> terms = new ArrayList<>();
			for(int i=1;i<=c/2;i++){
				Rational coeff = s.term(bernoulli, c, i);
				terms.add(coeff);
				Rational term = coeff.times( new Rational(BigInteger.valueOf(power(n, c-(i*2-1) )), ONE) );
				term = mod(term);
				cost = cost.add(term);
				cost = mod(cost);
			}
		}
	
		return cost.numerator.longValue();
    }
	
	Map<Integer, List<Rational>> cache;

	
    public static void main(String[] args) {
    	
    	
    	
    	
    	Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        Solution2 s = new Solution2();
        s.cache = new HashMap<>(); 
        
        
//        Rational[] bernoulli = s.bernoulli(((1000-2)/2+1)*2+2);
        
        
        int maxK = 0;
        
        long[] nq = new long[q];
        int[] kq = new int[q];
        		
        for(int a0 = 0; a0 < q; a0++){
            long n = in.nextLong();
            int k = in.nextInt();
            maxK = Math.max(maxK, k);
            nq[a0] = n;
            kq[a0] = k;
        }
        
        Rational[] bernoulli = s.bernoulli(((maxK-2)/2+1)*2+2);
        
        for(int a0 = 0; a0 < q; a0++){
            long result = s.highwayConstruction(bernoulli, nq[a0]-1, kq[a0]);
            System.out.println(result-1);
        }
        
        in.close();
    	
    }
}
