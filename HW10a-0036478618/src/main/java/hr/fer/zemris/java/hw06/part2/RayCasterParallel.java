package hr.fer.zemris.java.hw06.part2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class RayCasterParallel {

	public static void main(String[] args) {
			RayTracerViewer.show(getIRayTracerProducer(), 
			new Point3D(10,0,0), 
			new Point3D(0,0,0), 
			new Point3D(0,0,10), 
			20, 20);
		}
		private static IRayTracerProducer getIRayTracerProducer() {
			return new IRayTracerProducer() {
				@Override
				public void produce(Point3D eye, Point3D view, Point3D viewUp,
						double horizontal, double vertical, int width, int height,
						long requestNo, IRayTracerResultObserver observer) {
					
					System.out.println("Započinjem izračune...");
					short[] red = new short[width*height];
					short[] green = new short[width*height];
					short[] blue = new short[width*height];
					
					Point3D eyeView = view.sub(eye).modifyNormalize();
					
					Point3D zAxis = null;
					Point3D yAxis = viewUp.normalize().sub(eyeView.scalarMultiply(viewUp.normalize().
												scalarProduct(eyeView))).normalize();
					Point3D xAxis = eyeView.vectorProduct(yAxis).normalize();
					Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).
													add(yAxis.scalarMultiply(vertical/2));

					Scene scene = RayTracerViewer.createPredefinedScene();
					ForkJoinPool pool = new ForkJoinPool();
					pool.invoke(new TracerJob(horizontal, vertical, width, height, 0, height, scene, eye, zAxis, yAxis, xAxis, screenCorner, red, green, blue));
					pool.shutdown();
					System.out.println("Izračuni gotovi...");
					observer.acceptResult(red, green, blue, requestNo);
					System.out.println("Dojava gotova...");
				}
			};
		}
		
		@SuppressWarnings("serial")
		private static class TracerJob extends RecursiveAction {

			private static final int THRESHOLD = 16;
			
			private double horizontal;
			private double vertical;
			private int width;
			private int height;
			private int yMin;
			private int yMax;
			private Scene scene;
			private Point3D eye;
			private Point3D zAxis;
			private Point3D yAxis;
			private Point3D xAxis;
			private Point3D screenCorner;
			
			private short[] red;
			private short[] green;
			private short[] blue;
			
			
			
			
			
			public TracerJob(double horizontal, double vertical, int width,
					int height, int yMin, int yMax, Scene scene,
					Point3D eye, Point3D zAxis, Point3D yAxis, Point3D xAxis,
					Point3D screenCorner, short[] red, short[] green,
					short[] blue) {
				super();
				this.horizontal = horizontal;
				this.vertical = vertical;
				this.width = width;
				this.height = height;
				this.yMin = yMin;
				this.yMax = yMax;
				this.scene = scene;
				this.eye = eye;
				this.zAxis = zAxis;
				this.yAxis = yAxis;
				this.xAxis = xAxis;
				this.screenCorner = screenCorner;
				this.red = red;
				this.green = green;
				this.blue = blue;
			}





			@Override
			protected void compute() {
				if(yMax-yMin+1 <= THRESHOLD) {
					computeDirect();
					return;
				}
				invokeAll(
						new TracerJob(horizontal, vertical, width, height, yMin, yMin+(yMax-yMin)/2, scene, eye, zAxis, yAxis, xAxis, screenCorner, red, green, blue),
						new TracerJob(horizontal, vertical, width, height, yMin+(yMax-yMin)/2, yMax, scene, eye, zAxis, yAxis, xAxis, screenCorner, red, green, blue)
						);
			}





			private void computeDirect() {
				short[] rgb = new short[3];
				int offset = yMin*width;
				for(int y = yMin; y < yMax; y++) {
					for(int x = 0; x < width; x++) {
							Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x/(width-1.0)*horizontal)).
															sub(yAxis.scalarMultiply(y/(height-1.0)*vertical));

							Ray ray = Ray.fromPoints(eye, screenPoint);
							tracer(scene, ray, rgb);
							red[offset] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
							offset++;
					}
				}
			}
			
		}
		
		private static void tracer(Scene scene, Ray ray, short[] rgb) {
			double[] rgbCalculated = new double[3];
			rgbCalculated[0] = 15;
			rgbCalculated[1] = 15;
			rgbCalculated[2] = 15;

			RayIntersection closestIntersection = closestIntersection(scene, ray);
			if (closestIntersection != null) {
				determineColorFor(scene, ray, rgbCalculated, closestIntersection);
			}

			rgb[0] = (short) rgbCalculated[0];
			rgb[1] = (short) rgbCalculated[1];
			rgb[2] = (short) rgbCalculated[2];
		}
		
		private static void determineColorFor(Scene scene, Ray ray,
				double[] rgbCalculated, RayIntersection intersection) {
			for(LightSource ls : scene.getLights()) {
				Ray r_ = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
				RayIntersection s_ = closestIntersection(scene, r_);
				if(s_ != null) {
					double lightSourceIntersectionDistance = ls.getPoint().sub(s_.getPoint()).norm();
					double eyeIntersectionDistance = ls.getPoint().sub(intersection.getPoint()).norm();
					if (Double.compare(lightSourceIntersectionDistance + 1e-6, eyeIntersectionDistance) >= 0) {
						addDiffusseComponent(ls, rgbCalculated, s_);
						addReflectiveComponent(ls, ray, rgbCalculated, s_);
					}
				}
			}
			
		}
		
		private static void addReflectiveComponent(LightSource ls, Ray ray,
				double[] rgbCalculated, RayIntersection s_) {
			Point3D n = s_.getNormal();
			Point3D l = ls.getPoint().sub(s_.getPoint());
			Point3D lProjectionOnN = n.scalarMultiply(l.scalarProduct(n));
			Point3D r = lProjectionOnN.add(lProjectionOnN.negate().add(l).scalarMultiply(-1));
			Point3D v = ray.start.sub(s_.getPoint());
			double cos = r.normalize().scalarProduct(v.normalize());

			if(Double.compare(cos, 0) >= 0) {
		    	cos = Math.pow(cos, s_.getKrn());
				rgbCalculated[0] += ls.getR()*s_.getKrr()*cos;
				rgbCalculated[1] += ls.getG()*s_.getKrg()*cos;
				rgbCalculated[2] += ls.getB()*s_.getKrb()*cos;
		    }
			
		}
		private static void addDiffusseComponent(LightSource ls,
				double[] rgbCalculated, RayIntersection s_) {
			Point3D n = s_.getNormal();
			Point3D l = ls.getPoint().sub(s_.getPoint()).normalize();
			double ln = l.scalarProduct(n);
			rgbCalculated[0] += ls.getR()*s_.getKdr()*Math.max(ln, 0);
			rgbCalculated[1] += ls.getG()*s_.getKdg()*Math.max(ln, 0);
			rgbCalculated[2] += ls.getB()*s_.getKdb()*Math.max(ln, 0);
			
		}
		private static RayIntersection closestIntersection(Scene scene, Ray ray) {
			RayIntersection rez = null;
			for(GraphicalObject obj : scene.getObjects()) {
				RayIntersection temp = obj.findClosestRayIntersection(ray);
				if(rez == null) {
					rez = temp;
				} else if(temp != null && temp.getDistance() < rez.getDistance()) {
					rez = temp;
				}
			}
			return rez;
		}
}
