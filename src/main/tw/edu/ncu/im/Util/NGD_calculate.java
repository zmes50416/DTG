package tw.edu.ncu.im.Util;

public class NGD_calculate {

	public static double NGD_cal(long x, long y, long m) {
		double logX = Math.log10(x);
		double logY = Math.log10(y);
		double logM = Math.log10(m);
		double logN = 6.4966;
		double NGD = 0;

		NGD = (Math.max(logX, logY) - logM) / (logN - Math.min(logX, logY));
		
		if (logM == 0) {
			NGD = 1;
		}
		if (NGD > 1) {
			NGD = 1;
		}
		if (NGD < 0) {
			NGD = 0;
		}
		return NGD;
	}
}
