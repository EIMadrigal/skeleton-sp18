public class Planet {
	
	public double xxPos;  // cur x pos
	public double yyPos;  // cur y pos
	public double xxVel;  // cur x velocity
	public double yyVel;  // cur y velocity
	public double mass;  // mass
	public String imgFileName;
	
	private static final double G = 6.67e-11;
	
	public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
		this.xxPos = xxPos;
		this.yyPos = yyPos;
		this.xxVel = xxVel;
		this.yyVel = yyVel;
		this.mass = mass;
		this.imgFileName = imgFileName;
	}
	public Planet(Planet b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}
	
	public double calcDistance(Planet b) {
		double xDis = this.xxPos - b.xxPos;
		double yDis = this.yyPos - b.yyPos;
		return Math.sqrt(xDis * xDis + yDis * yDis);
	}
	
	public double calcForceExertedBy(Planet b) {
		double dis = calcDistance(b);
		return G * this.mass * b.mass / (dis * dis);
	}
	
	public double calcForceExertedByX(Planet b) {
		double dx = b.xxPos - this.xxPos;
		double r = calcDistance(b);
		double f = calcForceExertedBy(b);
		return f * dx / r; 
	}
	
	public double calcForceExertedByY(Planet b) {
		double dy = b.yyPos - this.yyPos;
		double r = calcDistance(b);
		double f = calcForceExertedBy(b);
		return f * dy / r; 
	}

	public double calcNetForceExertedByX(Planet[] bodies) {
		double netForceX = 0.0;
		for (Planet body : bodies) {
			if (!body.equals(this)) {
				netForceX += calcForceExertedByX(body);
			}
		}
		return netForceX;
	}
	
	public double calcNetForceExertedByY(Planet[] bodies) {
		double netForceY = 0.0;
		for (Planet body : bodies) {
			if (!body.equals(this)) {
				netForceY += calcForceExertedByY(body);
			}
		}
		return netForceY;
	}
	
	public void update(double dt, double xForce, double yForce) {
		double aX = xForce / this.mass;
		double aY = yForce / this.mass;
		this.xxVel += dt * aX;
		this.yyVel += dt * aY;
		this.xxPos += dt * xxVel;
		this.yyPos += dt * yyVel;
	}
	
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}
