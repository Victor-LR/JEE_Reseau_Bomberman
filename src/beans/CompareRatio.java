package beans;

import java.util.Comparator;

public class CompareRatio implements Comparator<JoueurClassement> {

	public CompareRatio() {

	}

	@Override
	public int compare(JoueurClassement arg0, JoueurClassement arg1) {
		// TODO Auto-generated method stub
		if (arg0.getRatio() > arg1.getRatio()) {
			return -1;
		} else
			return 1;
	}

}
