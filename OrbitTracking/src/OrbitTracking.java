//
// Java animation: resonant orbits animation and tracking
//
// Sateesh R. Mane, copyright 2018
//

import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.math.*;
import javax.imageio.*;

public class OrbitTracking extends JFrame 
{
    static double scale = 1;
    static JFrame ex;
    static Thread animator;
    
    public OrbitTracking() 
    {
    	add(new Screen());
        setResizable(false);
        pack();
        
        setTitle("Orbit Tracking");    
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        
        addMouseListener(new MouseAdapter()
        {
        	public void mouseReleased(MouseEvent evt) 
            {
            	if (evt.getButton() == MouseEvent.BUTTON1) {      	
            		scale *= 1.05;}
            	
            	else if (evt.getButton() == MouseEvent.BUTTON3) {
            		scale /= 1.05;}
        	    
            } 
        });
        
        /*addKeyListener(new KeyListener()
        {
        	public void keyReleased(KeyEvent evt) 
            {
            	int keyCode = evt.getKeyCode();
            	switch (keyCode) 
            	{
        	    	default:
        	    		break;
        	    		
        	    	case KeyEvent.VK_ESCAPE:
        			    animator.interrupt();
        			    System.exit(0);
        			    break;
        		    
        	    	case KeyEvent.VK_0: 
        			    Screen.xorig = 0;
        			    Screen.porig = 0;
        			    OrbitTracking.scale = 1;
        			    break;
        		    
        	    	case KeyEvent.VK_1: 
        	    		Screen.multipole = Screen.Multipole.SEXTUPOLE;
        	    		new Screen();
        			    break;
        		    
        	    	case KeyEvent.VK_2: 
        	    		Screen.multipole = Screen.Multipole.OCTUPOLE;
        	    		new Screen();
        			    break;
        		    
        	    	case KeyEvent.VK_3: 
        	    		Screen.multipole = Screen.Multipole.COMBINED;
        	    		new Screen();
        			    break;
        		    
        	    	case KeyEvent.VK_LEFT:
        	    		Screen.xorig += 5;
        			    break;
        		    
        	    	case KeyEvent.VK_RIGHT:
        	    		Screen.xorig -= 5;
        			    break;
        		    
        	    	case KeyEvent.VK_UP:
        	    		Screen.porig += 5;
        			    break;
        		    
        	    	case KeyEvent.VK_DOWN:
        	    		Screen.porig -= 5;
        			    break;
        		    
        	    	case KeyEvent.VK_S:
        	    		Screen.save();
        			    break;
            	}
            }

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
        });*/
    }
    
    public static void main(String[] args) 
    {        
        EventQueue.invokeLater(() -> 
        {
			ex = new OrbitTracking();
			ex.setVisible(true);
			
			//terminate the program when the top right "cross" of the frame is clicked
			ex.addWindowListener(new java.awt.event.WindowAdapter() 
			{
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) 
				{	
				    System.exit(0);			        
				}
			});
	    });
    }
}


final class Screen extends JPanel implements Runnable, KeyListener 
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Thread animator;
    
	private static final double k = 3;//Math.sqrt(2);
	private static final double r = 1.0/k;
    
    private static final int COUNT = 100000;
    private static final int ORBITS = 500;
    
    private final static double[] x = new double[COUNT];   //modified
    private final static double[] y = new double[COUNT];   //modified

    private final static double[][] p = new double[ORBITS][COUNT];
    private final static Color co[] = new Color[ORBITS];

    private static final Font font = new Font("TimesRoman", Font.BOLD, 18);
    //private static final Font font = new Font("Helvetica", Font.BOLD, 18);
    private static double ks = 0.015 + 0.01*Math.random();
    private static double ko = 1.0e-4*(1.0 + 0.5*Math.random());
    private static double eps = 0.04*(Math.random()-0.5);
    private static double nu = 3.0/7.0 + eps;
    private static double cc = Math.cos(Math.PI*nu);
    private static double ss = Math.sin(Math.PI*nu);
    private static final double xmax = 1000;
    private static int num = 1;
    static double xorig = 0;
    static double porig = 0;

    enum Multipole { SEXTUPOLE, OCTUPOLE, COMBINED }
    static Multipole multipole = Multipole.SEXTUPOLE;

    // callback for mouse event
    //@Override

    // callback for keypad event
    @Override 
    public void keyReleased(KeyEvent evt) 
    {
    	int keyCode = evt.getKeyCode();
    	switch (keyCode) 
    	{
	    	default:
	    		break;
	    		
	    	case KeyEvent.VK_ESCAPE:
			    animator.interrupt();
			    SwingUtilities.getWindowAncestor(this).dispose();
			    break;
		    
	    	case KeyEvent.VK_0: 
			    xorig = 0;
			    porig = 0;
			    OrbitTracking.scale = 1;
			    break;
		    
	    	case KeyEvent.VK_1: 
			    multipole = Multipole.SEXTUPOLE;
			    init();
			    break;
		    
	    	case KeyEvent.VK_2: 
			    multipole = Multipole.OCTUPOLE;
			    init();
			    break;
		    
	    	case KeyEvent.VK_3: 
			    multipole = Multipole.COMBINED;
			    init();
			    break;
		    
	    	case KeyEvent.VK_LEFT:
			    xorig += 5;
			    break;
		    
	    	case KeyEvent.VK_RIGHT:
			    xorig -= 5;
			    break;
		    
	    	case KeyEvent.VK_UP:
			    porig += 5;
			    break;
		    
	    	case KeyEvent.VK_DOWN:
			    porig -= 5;
			    break;
		    
	    	case KeyEvent.VK_S:
			    save();
			    break;
    	}
    }

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
    
    static void save() 
    {
    	BufferedImage paintImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = paintImage.createGraphics();
		File outputImage = new File("orbits.png");
		g.drawImage(paintImage, 0, 0, null);
		updateDisplay(g);
		
		try 
		{
		    ImageIO.write(paintImage, "PNG", outputImage);
		    //ImageIO.write(paintImage, "JPG", new File("c:/sateesh/orbits.jpg"));
		}
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
    } 

    private void setColors() 
    { 	
    	co[0]= Color.WHITE;
    	co[1]= Color.RED;
    	co[2]= Color.GREEN;
    	co[3]= Color.YELLOW;
    	co[4]= Color.BLUE;
    	co[5]= Color.ORANGE;
    	co[6]= Color.MAGENTA;
    	co[7]= Color.LIGHT_GRAY;
    	co[8]= Color.PINK;
    	co[9]= Color.CYAN;
    	
    	for (int i = 10; i < ORBITS; ++i) 
    	{
    		co[i] = co[i-10];
    	}
    }
    
    private static void update_xp() 
    {
    
    	
    	if(num <2 || (num > COUNT))
    	{
    		return;
    	}
    	int i = num-1;
		double R = r * (k-1);
		double theta = (2 * Math.PI) * i/1000;
		x[i] = R * Math.cos(theta) + (r* (Math.cos((k-1)*theta)));
		y[i] = R * Math.sin(theta) - (r* (Math.sin((k-1)*theta)));
    	
    	
//    	if ((num < 2) || (num > COUNT))
//    	{
//    		return;
//    	}
//    	
//    	int i = num - 1;
//    	for (int iorb = 0; iorb < ORBITS; ++iorb) 
//    	{    	
//		    if (Math.abs(x[iorb][i-1]) > xmax) 
//		    {
//		    	continue;
//		    }
//		    
//		    double xx = x[iorb][i-1]*cc + p[iorb][i-1]*ss;
//		    double pp = p[iorb][i-1]*cc - x[iorb][i-1]*ss;
//		    
//		    switch (multipole)
//		    {
//			    default:
//			    case SEXTUPOLE:
//					pp -= ks*xx*xx;
//					break;
//				
//			    case OCTUPOLE:
//					pp -= ko*xx*xx*xx;
//					break;  
//					
//			    case COMBINED:
//					pp -= ks*xx*xx;
//					pp -= ko*xx*xx*xx;
//					break;            		
//		    }
//		    
//		    x[iorb][i] = xx*cc + pp*ss;
//		    p[iorb][i] = pp*cc - xx*ss;
//		}
    }
    
    public Screen() 
    {
    	init();

    	addKeyListener(this);
    }
    
    private void init() 
    {
    	
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);	
        setFocusable(true);
        
        
        double R = r * (k-1);
        num = 0;
        x[0] = R +r;
        y[0] = 0;
        for(int i =1; i < COUNT; i++)
        {
        	x[i] = x[i-1];
        	y[i] = y[i-1];
        }
        
     
//		xorig = 0;
//		porig = 0;
//		OrbitTracking.scale = 1;
//        setColors();    	
//		ks = 0.015 + 0.01*Math.random();
//		ko = 1.0e-4*(1.0 + 0.5*Math.random());
//		eps = 0.04*(Math.random()-0.5);
//        switch (multipole) 
//        {
//	        default:
//	        case SEXTUPOLE:
//	    	    nu = 3.0/7.0 + eps;
//	    	    break;
//	    	    
//	        case OCTUPOLE:
//	    	    nu = 3.0/10.0 + eps;
//	    	    break;
//	    	    
//	        case COMBINED:
//	    	    nu = 3.0/16.0 + eps;
//	    	    break;
//        }
//        
//		cc = Math.cos(Math.PI*nu);
//		ss = Math.sin(Math.PI*nu);
//    	
//    	num = 1;
//    	double sign = 1;
//    	
//    	for (int iorb = 0; iorb < ORBITS; ++iorb) 
//    	{    	
//		    if (iorb <= 10) 
//		    {
//				x[iorb][0] = -2.0*(4*iorb+1);
//				p[iorb][0] = 0.0;
//		    }
//		    else 
//		    {
//				x[iorb][0] = 2.0*(iorb+1)*(0.5 + Math.random());
//				p[iorb][0] = 100.0*Math.random() - 50.0;
//		    }
//		    
//		    x[iorb][0] *= sign;
//		    p[iorb][0] *= sign;
//		    sign = -sign;
//		    
//	        for (int i = num; i < COUNT; ++i) 
//	        {
//	            	x[iorb][i] = x[iorb][i-1];
//	            	p[iorb][i] = p[iorb][i-1];
//	        }
//    	}
    }
    
    @Override
    public void addNotify() 
    {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    
    @Override
    public void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
        updateDisplay(g);
    }

    private static void updateDisplay(Graphics g) 
    {
    	if (num < COUNT) 
    	{
		    ++num;
		    update_xp();
    	}
    	// display text
    	g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		double inu = (int)(nu*1e4)/1e4;
		double iks = (int)(ks*1e8)/1e4;
		double iko = (int)(ko*1e8)/1e4;
		
//    	switch (multipole) 
//    	{
//	    	default:
//	    	case SEXTUPOLE:
//			    g.drawString("sextupole map", 20, 30);
//			    g.drawString("nu = " + inu, 20, 50);
//			    g.drawString("ks = " + iks, 20, 70);
//			    break;    
//			    
//	    	case OCTUPOLE:
//			    g.drawString("octupole map", 20, 30);
//			    g.drawString("nu = " + inu, 20, 50);
//			    g.drawString("ko = " + iko, 20, 70);
//			    break;  
//			    
//	    	case COMBINED:
//			    g.drawString("combined sextupole & octupole map", 20, 30);
//			    g.drawString("nu = " + inu, 20, 50);
//			    g.drawString("ks = " + iks, 20, 70);
//			    g.drawString("ko = " + iko, 20, 90);
//			    break;            		
//    	}
	
    	// draw the points
//    	for (int iorb = 0; iorb < ORBITS; ++iorb) 
//    	{    	
//		    g.setColor(co[iorb]);
//		    for (int i = 0; i< num; ++i) 
//		    {
//				double xx = (xorig + x[iorb][i])*OrbitTracking.scale*2 + WIDTH*0.5;
//				double pp = (porig + p[iorb][i])*OrbitTracking.scale*3.2 + HEIGHT*0.5;
//				int ix = (int)(xx);
//				int ip = (int)(pp);
//				g.fillRect(ix, ip, 1, 1);
//		    }
//    	}	
    	
    	g.setColor(Color.yellow);
    	for(int i =0; i < num ;++i)
    	{
    		double xx =x[i] * HEIGHT*0.3;
    		double yy =y[i] * HEIGHT*0.3;
    		int ix  =(int)(xx + HEIGHT*0.5);
    		int iy  =(int)(yy + HEIGHT*0.5);
    		g.fillRect(ix, iy, 2, 2);


    	}
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void run() 
    {
        while (animator.interrupted() == false) 
        {
            repaint();
        }
    }
}