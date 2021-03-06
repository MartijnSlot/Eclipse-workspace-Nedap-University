package week5;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NaiveStrategy implements Strategy {

	private String name;

	public NaiveStrategy(){
		this.name = "Naive";
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int determineMove(Board b, Mark m) {
		Set<Integer> emptyFields = new HashSet<>();
		int randomEmptyField = (int) (Math.random() * emptyFields.size());
	    int counter = 0;
		
		for (int i = 0; i < Math.pow(b.DIM, 2); i++){
			if (b.isEmptyField(i)) {
				emptyFields.add(i);
			}
		}
		
	    Iterator<Integer> iterator = emptyFields.iterator();
	    Integer selectField = iterator.next();
	    while(counter < randomEmptyField) {
	        selectField = iterator.next();
	        counter ++;
	    }
		return selectField.intValue();
	}
}
