package it.csi.cuneo;



public class Coordinate 
{
	static public double MERIDIANO_CENTRALE_FUSO_9_GRADI=9;
	static public double MERIDIANO_CENTRALE_FUSO_15_GRADI=15;
	static private double d=6399593.626;
	static private double epsilonQuadro=0.006739496742;
	static private double A1=0.998324298453;
	static private double A2=0.002514607060;
	static private double A3=0.000002639047;
	static private double A4=0.000000003418;
	static private double a=6378137;
	private double utmEst;
	private double utmNord;
	private double phi; //radianti
	private double landa; //radianti
	private double meridianoCentraleFuso;

	
	public static void main(String arg[])
	{
		Coordinate coord=new Coordinate(44.389544,7.547910);
		coord.convertiRadiantiToUtm();
		
		coord=new Coordinate(44,23.37264,7,32.8746);
		coord.convertiRadiantiToUtm();
		
		coord=new Coordinate(44,23,22.3584,7,32,52.476);
		coord.convertiRadiantiToUtm();
	}
	
	/**
	 * Riceve in input i gradi decimali
	 * @param phiGradi gradi decimali latitudine Nord
	 * @param landaGradi gradi decimali longitudine Est
	 */
	public Coordinate(double phiGradi, double landaGradi)
	{
		phi=(phiGradi*Math.PI)/180; //conversioni dei gradi in radianti
		landa=(landaGradi*Math.PI)/180; //conversioni dei gradi in radianti
		meridianoCentraleFuso=MERIDIANO_CENTRALE_FUSO_9_GRADI;
	}
	
	/**
	 * Riceve in input i gradi ed i minuti decimali
	 * @param phiGradi gradi latitudine Nord
	 * @param phiMinuti minuti decimali latitudine Nord
	 * @param landaGradi gradi longitudine Est
	 * @param landaMinuti minuti decimali longitudine Est
	 */
	public Coordinate(double phiGradi, double phiMinuti, double landaGradi, double landaMinuti)
	{
		phi=phiGradi+(phiMinuti/60);
		phi=(phi*Math.PI)/180; //conversioni dei gradi in radianti
		landa=landaGradi+(landaMinuti/60);
		landa=(landa*Math.PI)/180; //conversioni dei gradi in radianti
		meridianoCentraleFuso=MERIDIANO_CENTRALE_FUSO_9_GRADI;
	}
	
	/**
	 * Riceve in input i gradi, i minuti ed i secondi decimali
	 * @param phiGradi gradi latitudine Nord
	 * @param phiMinuti minuti latitudine Nord
	 * @param phpSecondi secondi decimali latitudine Nord
	 * @param landaGradi gradi longitudine Est
	 * @param landaMinuti minuti longitudine Est
	 * @param landaSecondi secondi decimali longitudine Est
	 */
	public Coordinate(double phiGradi, double phiMinuti, double phpSecondi, double landaGradi, double landaMinuti, double landaSecondi)
	{
		phiMinuti+=phpSecondi/60;
		phi=phiGradi+(phiMinuti/60);
		phi=(phi*Math.PI)/180; //conversioni dei gradi in radianti
		
		landaMinuti+=landaSecondi/60;
		landa=landaGradi+(landaMinuti/60);
		landa=(landa*Math.PI)/180; //conversioni dei gradi in radianti
		meridianoCentraleFuso=MERIDIANO_CENTRALE_FUSO_9_GRADI;
	}
	
	public void convertiRadiantiToUtm()
	{
		double x=0,y=0;
				
		/*
		 * gradiEstPrimo=gradiEst - longitudine meridiano centrale del fuso. 
		 * Vale 9° per il piemonte e 15° per il trentino
		 */
		double landaPrimo=landa-(meridianoCentraleFuso*Math.PI)/180;
		/*
			d=(a*a)/c
			a=6378137m
			c=6356752,314
		*/
		
		
		double v=Math.sqrt(1+epsilonQuadro*Math.cos(phi)*Math.cos(phi));
		double z=Math.atan( Math.tan(phi)/Math.cos(landaPrimo*v));
		double v1=Math.sqrt(1+epsilonQuadro*Math.cos(z)*Math.cos(z));
		
		x=d*arcsinh((Math.cos(z)*Math.tan(landaPrimo))/v1);
		
		y=a*(A1*z-A2*Math.sin(2*z)+A3*Math.sin(4*z)-A4*Math.sin(6*z));
		
		/*
			System.out.println("phi:"+phi);
			System.out.println("landa:"+landa);
			System.out.println("landaPrimo:"+landaPrimo);
			System.out.println("v:"+v);
			System.out.println("d:"+d);
			System.out.println("z:"+z);
			System.out.println("v1:"+v1);
			System.out.println("x:"+x);
			System.out.println("y:"+y);
		*/
		
		utmEst=((0.9996*x)+500000);
		utmNord=0.9996*y;
		
		//System.out.println("Nord > "+utmNord);
		//System.out.println("Est > "+utmEst);
	}
	
	static double arcsinh(double x) 
	{ 
		return Math.log(x + Math.sqrt(x*x + 1.0)); 
	}

	public double getUtmEst() {
		return utmEst;
	}

	public double getUtmNord() {
		return utmNord;
	}

	public double getMeridianoCentraleFuso() {
		return meridianoCentraleFuso;
	}

	public void setMeridianoCentraleFuso(double meridianoCentraleFuso) {
		this.meridianoCentraleFuso = meridianoCentraleFuso;
	}	
}
