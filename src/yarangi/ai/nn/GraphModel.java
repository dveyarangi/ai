package yarangi.ai.nn;

import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IViewPoint;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.UIVeil;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.WorldVeil;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.simulations.ICollisionManager;
import yarangi.math.RangedDouble;
import yarangi.math.Vector2D;

public class GraphModel extends Scene
{
	
	private double [][] tx, ty;
	
	private double minx, miny;
	private double maxx, maxy;
	
	private double []nx, ny;

	public GraphModel()
	{
		super("NN debug",  new WorldVeil(1000, 1000) {
			
			@Override
			public void preDisplay(GL gl) { }
			
			@Override
			public void postDisplay(GL gl) { }
			
			@Override
			protected void initViewPoint(IViewPoint viewPoint) 
			{ 
				ViewPoint2D vp = (ViewPoint2D) viewPoint;
				
				vp.setCenter(new Vector2D(0,0));
				vp.setHeight(new RangedDouble(1, 400, 500));
			}
			
			@Override
			public ICollisionManager createCollisionManager() { return null; }
		}, 
		new UIVeil(1000, 1000) {}, 
		1000, 1000, 1);
	}
	
	public void setTrainingSet(double [][] tx, double [][] ty, double minx, double miny, double maxx, double maxy)
	{
		this.tx = tx;
		this.ty = ty;
		
		this.maxx = maxx; this.maxy = maxy;
		this.minx = minx; this.miny = miny;

	}
	
	public void updateNetworkGraph(double []nx, double [] ny)
	{
		this.nx = nx;
		this.ny = ny;
	}
	
	@Override
	public void display(GL gl, double time, RenderingContext context) 
	{
//		System.out.println("disp");
//		super.display(gl, time, context);
		int width = 400;
		int height = 400;
		// background:
		gl.glPushMatrix();
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		// axes:
		gl.glColor4f(.2f, .2f, .2f, 1.0f);
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(0, -1000);
		gl.glVertex2f(0, 1000);
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(-1000, 0);
		gl.glVertex2f(1000, 0);
		gl.glEnd();
		
//		double ax = width/(maxx - minx);
		
		// training set:
		gl.glColor4f(1.0f, 0.2f, 0.2f, 1.0f);
		for(int idx = 0; idx < tx.length; idx ++)
		{
			double x = tx[idx][0];
			double y = ty[idx][0];
			 
			int px = (int)(50*x);
			int py = (int)(50*y);
			
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(px-0.5f, py-0.5f);
			gl.glVertex2f(px-0.5f, py+0.5f);
			gl.glVertex2f(px+0.5f, py+0.5f);
			gl.glVertex2f(px+0.5f, py-0.5f);
			gl.glVertex2f(px-0.5f, py-0.5f);
			gl.glEnd();
			
		}
		
		if(nx == null) return;
		
		// network output:
		gl.glColor4f(0.2f, 1.0f, 0.2f, 1.0f);
		for(int idx = 0; idx < nx.length; idx ++)
		{
	 
			int px = (int)(50*nx[idx]);
			int py = (int)(50*ny[idx]);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(px-0.5f, py-0.5f);
			gl.glVertex2f(px-0.5f, py+0.5f);
			gl.glVertex2f(px+0.5f, py+0.5f);
			gl.glVertex2f(px+0.5f, py-0.5f);
			gl.glVertex2f(px-0.5f, py-0.5f);
			gl.glEnd();
		}
		gl.glPopMatrix();
	}

	@Override
	public Map<String, IAction> getActionsMap() {
		// TODO Auto-generated method stub
		return DefaultActionFactory.fillNavigationActions(new HashMap <String, IAction> (), this.getViewPoint());
	}
}
