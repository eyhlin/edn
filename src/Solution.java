import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    static int solve1(int[] x, int d) {
        int cost = x[0]-(x[x.length-1]-d);
        
//        System.out.print("--");
//        for(int i=0;i<x.length;i++){
//        	System.out.print(x[i]+" ");
//        }
//        System.out.println("--");
        
        x[0]=x[x.length-1]-d;
        return cost+solve(x, d);
    }
    
    static int solve2(int[] x, int d) {
        int cost = (x[0]+d)-x[x.length-1];
        x[x.length-1]=x[0]+d;
        return cost+solve(x, d);
    }

    static int solve(int[] x, int d) {
        int min = x[0];
        int max = x[x.length-1];
        int range = max - min;
        
        int bottom = max-d;
        int top = min+d;
        
        int cost = 0;
        for(int i=1;i<x.length-1;i++){
            if(x[i]>bottom&&x[i]<top){
                if(x[i]+d-max < min-(x[i]-d)){
                    cost+= (x[i]+d-max);
                    x[x.length-1] = x[i]+d;
                    max = x[x.length-1];
                    bottom = max-d;
                }else{
                    cost+= min-(x[i]-d);
                    x[0] = x[i]-d;
                    min = x[0];
                    top = min+d;
                }
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            int d = in.nextInt();
            int[] x = new int[n];
            
            Map<Set<Integer>, Integer> cache = new HashMap<>();
            
            
            Set<Integer> current = new HashSet<>();
            for(int x_i = 0; x_i < n; x_i++){
                

                x[x_i] = in.nextInt();
                
                if(x_i==0) System.out.print(0);
                else if(d==0) System.out.print(" 0");
                else {
                    
                    System.out.print(" ");

                    
                    
                    int cost = 0;
                    
                    int[] now = Arrays.copyOf(x, x_i+1);
                    Arrays.sort(now);
                    int min = x[0];
                    int max = x[x_i];
                    
//                    int range = max - min;
////                    if(range<d){
////                        int so1 = solve1(Arrays.copyOf(now, x_i+1), d);
////                        int so2 = solve2(Arrays.copyOf(now, x_i+1), d);
////                        
////                        cost = Math.min(so1, so2);
////                    }else 
                    	cost = solve(Arrays.copyOf(now, x_i+1), d);
                    System.out.print(cost);
                }
            }
            System.out.println("");
        }
        in.close();
    }
}
