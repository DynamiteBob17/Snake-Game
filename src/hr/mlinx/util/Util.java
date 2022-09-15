package hr.mlinx.util;

import java.awt.Toolkit;
import java.util.Random;

public class Util {
	
	public static final double SCALE = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1920.0;
	public static final Random R = new Random();
	
	public static double map(double val, double valLow, double valHigh, double returnValLow, double returnValHigh) {
		double ratio = (val - valLow) / (valHigh - valLow);
		
		return ratio * (returnValHigh - returnValLow) + returnValLow;
	}
	
}
