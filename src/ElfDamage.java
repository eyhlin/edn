import java.util.HashMap;
import java.util.Map;

public class ElfDamage {

	public static void main(String[] args) {
		ElfDamage damage = new ElfDamage();

		// (arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, 
		// pp, space, charge);
		damage.go(0, 0, 0, 0,     0, 0,      0, 0, 
				1, 5     , 0);
		
		
		damage.go(2, 1, 0, 1,     3, 0,      0, 0, 
				6, 5     , 0);
	}
	
	public void go(int arrow, int back, int fairy, int fairies, int bug, int bugOut, int zeroGob, int zeroFairy, int pp,
			int space, int charge){
		int ans;
		ans = getDamage(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp, space, charge);
		System.out.println(ans);
		printPath(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp, space, charge);
	}
	

	Map<String, Integer> cache = new HashMap<>();
	Map<String, String> paths = new HashMap<>();

	void printPath(int arrow, int back, int fairy, int fairies, int bug, int bugOut, int zeroGob, int zeroFairy, int pp,
			int space, int charge){
		String tag = getTag(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp, space, charge);
		String path = paths.get(tag);
		
		for(int i=0;i<path.length();i++){
			if(path.charAt(i)=='A'){
				System.out.println("Use Arrow;");
			}else if(path.charAt(i)=='B'){
				System.out.println("Return Bug to hand;");
			}else if(path.charAt(i)=='C'){
				System.out.println("Return Fairy(temp) to hand;");
			}else if(path.charAt(i)=='D'){
				System.out.println("Summon Fairy(1);");
			}else if(path.charAt(i)=='E'){
				System.out.println("Draw 2 Fairies;");
			}else if(path.charAt(i)=='F'){
				System.out.println("Summon Bug;");
			}else if(path.charAt(i)=='G'){
				System.out.println("Summon Goblin(0);");
			}else if(path.charAt(i)=='H'){
				System.out.println("Summon Fairy(0);");
			}
		}
	}
	
	String getTag(int arrow, int back, int fairy, int fairies, int bug, int bugOut, int zeroGob, int zeroFairy, int pp,
			int space, int charge) {
		return "" + arrow + back + fairy + fairies + bug + bugOut + zeroGob + zeroFairy + pp + space + charge;
	}

	int getDamage(int arrow, int back, int fairy, int fairies, int bug, int bugOut, int zeroGob, int zeroFairy,
			int pp, int space, int charge) {
		String tag = getTag(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp, space, charge);

		if (cache.containsKey(tag))
			return cache.get(tag);

		if (pp == 0 || (pp == 1 && (arrow == 0 && back == 0 && fairy == 0 && fairies == 0))) {
			cache.put(tag, 0);
			paths.put(tag, "");
			return 0;
		}

		int max = 0;
		String path = "";

		if (arrow > 0 && pp > 0) {
			int useArrow = 1 + getDamage(arrow - 1, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
					space, charge + 1);

			if (useArrow > max) {
				max = useArrow;
				path = "A" + paths.get(getTag(arrow - 1, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
						space, charge + 1));
			}

		}

		if (back > 0 && pp > 0 && space < 5) {
			if (bugOut > 0) {
				int useBack = getDamage(arrow, back - 1, fairy, fairies, bug + 1, bugOut - 1, zeroGob, zeroFairy,
						pp - 1, space + 1, charge + 1);

				if (useBack > max) {
					max = useBack;
					path = "B" + paths.get(getTag(arrow, back - 1, fairy, fairies, bug + 1, bugOut - 1, zeroGob,
							zeroFairy, pp - 1, space + 1, charge + 1));
				}
			}

			int useBack = getDamage(arrow, back - 1, fairy + 1, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
					space + 1, charge + 1);
			if (useBack > max) {
				max = useBack;
				path = "C" + paths.get(getTag(arrow, back - 1, fairy + 1, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
						space + 1, charge + 1));
			}
		}

		if (fairy > 0 && pp > 0 && space > 0) {
			int useFairy = getDamage(arrow, back, fairy - 1, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
					space - 1, charge + 1);
			if (useFairy > max) {
				max = useFairy;
				path = "D" + paths.get(getTag(arrow, back, fairy - 1, fairies, bug, bugOut, zeroGob, zeroFairy, pp - 1,
						space - 1, charge + 1));
			}
		}

		if (fairies > 0 && pp > 0) {
			int useFairies = getDamage(arrow, back, fairy + 2, fairies - 1, bug, bugOut, zeroGob, zeroFairy, pp - 1,
					space, charge + 1);
			if (useFairies > max) {
				max = useFairies;
				path = "E" + paths.get(getTag(arrow, back, fairy + 2, fairies - 1, bug, bugOut, zeroGob, zeroFairy,
						pp - 1, space, charge + 1));
			}
		}

		if (bug > 0 && pp > 1 && space > 0) {
			int useBug = charge + 1 + getDamage(arrow, back, fairy, fairies, bug - 1, bugOut + 1, zeroGob, zeroFairy,
					pp - 2, space - 1, charge + 1);
			if (useBug > max) {
				max = useBug;
				path = "F" + paths.get(getTag(arrow, back, fairy, fairies, bug - 1, bugOut + 1, zeroGob, zeroFairy,
						pp - 2, space - 1, charge + 1));
			}
		}

		if (zeroGob > 0 && space > 0) {
			int useZeroGob = bug + bugOut < 3
					? getDamage(arrow, back, fairy, fairies, bug + 1, bugOut, zeroGob - 1, zeroFairy, pp, space - 1,
							charge + 1)
					: getDamage(arrow, back, fairy, fairies, bug, bugOut, zeroGob - 1, zeroFairy, pp, space - 1,
							charge + 1);

			boolean getBug = bug + bugOut < 3 ? true : false;

			if (useZeroGob > max) {
				max = useZeroGob;
				if (getBug)
					path = "G" + paths.get(getTag(arrow, back, fairy, fairies, bug + 1, bugOut, zeroGob - 1, zeroFairy,
							pp, space - 1, charge + 1));
				else
					path = "G" + paths.get(getTag(arrow, back, fairy, fairies, bug, bugOut, zeroGob - 1, zeroFairy, pp,
							space - 1, charge + 1));
			}
		}

		if (zeroFairy > 0 && space > 0) {
			int useZeroFairy = getDamage(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy - 1, pp,
					space - 1, charge + 1);
			if (useZeroFairy > max) {
				max = useZeroFairy;
				path = "H" + paths.get(getTag(arrow, back, fairy, fairies, bug, bugOut, zeroGob, zeroFairy - 1, pp,
						space - 1, charge + 1));
			}
		}

		
		cache.put(tag, max);
		paths.put(tag, path);

		return max;
	}

}
