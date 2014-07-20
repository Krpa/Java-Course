package hr.fer.zemris.java.tecaj_06.rays;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	
	
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}



	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D dir = ray.direction;
		double A = dir.scalarProduct(dir);
		
		Point3D s = ray.start;
		Point3D c = center;
		Point3D v = s.sub(c);
		double B = dir.scalarMultiply(2).scalarProduct(v);
		double C = v.scalarProduct(v) - radius * radius;
    	
    	double discriminant = B*B - 4*A*C;
    	
    	//nema presjeka
    	if (discriminant < 0)
    		return null;
    	
    	double t1 = (-B + Math.sqrt(discriminant)) / (2*A);
    	double t2 = (-B - Math.sqrt(discriminant)) / (2*A);
    	
    	if (t1 <= 0 && t2 <= 0)
    		return null;
    	
    	Point3D i1 = s.add(dir.scalarMultiply(t1));
    	Point3D i2 = s.add(dir.scalarMultiply(t2));
    	
    	double i1D = i1.sub(s).norm();
    	double i2D = i2.sub(s).norm();
    	
    	Point3D sol = i1;
    	double solD = i1D;
    	if (i1D > i2D) {
    		sol = i2;
    		solD = i2D;
    	}
    	
    	
    	RayIntersection closestIntersection = new RayIntersection(sol, solD, sol.sub(getCenter()).norm() > radius) {

    		@Override
            public Point3D getNormal() {
                return getPoint().sub(getCenter()).normalize();
            }

            @Override
            public double getKdr() {return kdr;}
            @Override
            public double getKdg() {return kdg;}
            @Override
            public double getKdb() {return kdb;}
            @Override
            public double getKrr() {return krr;}
            @Override
            public double getKrg() {return krg;}
            @Override
            public double getKrb() {return krb;}
            @Override
            public double getKrn() {return krn;}
        };
    	
        return closestIntersection;
	}



	public Point3D getCenter() {
		return center;
	}



	public double getRadius() {
		return radius;
	}



	public double getKdr() {
		return kdr;
	}



	public double getKdg() {
		return kdg;
	}



	public double getKdb() {
		return kdb;
	}



	public double getKrr() {
		return krr;
	}



	public double getKrg() {
		return krg;
	}



	public double getKrb() {
		return krb;
	}



	public double getKrn() {
		return krn;
	}

	
	
	
}
