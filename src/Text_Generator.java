import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;


public class Text_Generator {
    public static void main(String[] args) {

    	// Create the initial vector of (apple, orange)
    	  ICombinatoricsVector<String> originalVector = Factory.createVector(
    	      new String[] { "0","1", "2","3","4","5","6","7","8","9","a","b","c","d","e","f" } );


    	  Generator<String> gen = Factory.createPermutationWithRepetitionGenerator(originalVector, 4);
    	   // Print the result
    	  for (ICombinatoricsVector<String> perm : gen){
        	  String key_check = "";
    	      //System.out.println( perm.getVector());
    		  for (String string : perm) {
    			    key_check = key_check + string;
    			}
    		  System.out.println(key_check + "\n");
    	  }
    }
}
