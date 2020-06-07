public class NBody {
	
	public static double readRadius(String file) {
		double radius = 0.0;
		In in = new In(file);
		while (!in.isEmpty()) {
			in.readInt();
			radius = in.readDouble();
			break;
		}
		return radius;
	}
	
	public static Planet[] readPlanets(String file) {
		In in = new In(file);
		int N = in.readInt();
		Planet[] bodies = new Planet[N];
		in.readDouble();
		
		for (int i = 0; i < N; ++i) {
			bodies[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		}
		return bodies;
	}
	
	public static void main(String[] args) {
		double T = Double.valueOf(args[0]);
		double dt = Double.valueOf(args[1]);
		String filename = args[2];
		Planet[] bodies = (filename);
		double radius = readRadius(filename);
		
		StdDraw.enableDoubleBuffering();
		
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		
		// simulation
		double time = 0.0;
		while (time <= T) {
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			for (int i = 0; i < bodies.length; ++i) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for (int i = 0; i < bodies.length; ++i) {
				bodies[i].update(dt, xForces[i], yForces[i]);
			}
			
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet body : bodies) {
				body.draw();
			}
			
			StdDraw.show();
			StdDraw.pause(15);
			time += dt;
		}
		
		// print the final state
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
		                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}
