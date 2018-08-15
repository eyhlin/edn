import java.util.Random;

public class Test {

	public static void main(String[] args){
		Random r = new Random();
		
		
		int n = r.nextInt(4_000_000-1)+1;
		int m = (int)(4_000_000.0/n);
		System.out.println(n+" "+m);
		
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				System.out.print(r.nextInt(501)-250);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
