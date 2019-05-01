public class Start
{
	public static final boolean trajectory = true;
    
    public static main(String[] args)
    {
        PrintWriter outputFile = null;
  	  	try
  	  	{
  	  	  outputFile = new PrintWriter(new FileOutputStream("trajectory.txt",false));
  	  	}
  	  	catch(FileNotFoundException e)
  	  	{
  	  	  System.out.println("File error.  Program aborted.");
  	  	  System.exit(0);
  	  	}
    
    }
    
    public static void plot(double amp, double[] time, double[] angle)
  	{
  	  	Plot2DPanel plot1 = new Plot2DPanel();

    	// define the legend position
    	plot1.addLegend("SOUTH");

    	// add a line plot to the PlotPanel
    	plot1.addLinePlot("Stone 1", x, y);
    	plot1.setAxisLabel(0,"x (m)");
    	plot1.setAxisLabel(1,"y (m)");
    	BaseLabel title1 = new BaseLabel("Trajectory of Stone at an angle of "
    	  + angle, Color.BLACK, 0.5, 1.1);

    	title1.setFont(new Font("Courier", Font.BOLD, 14));
    	plot1.addPlotable(title1);

    	// put the PlotPanel in a JFrame like a JPanel
    	JFrame frame1 = new JFrame("a plot panel");
    	frame1.setSize(600, 600);
    	frame1.setContentPane(plot1);
    	frame1.setVisible(true);
  	}//plot
}