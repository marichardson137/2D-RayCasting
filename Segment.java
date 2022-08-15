public class Segment {
    
    public Vector2 pointA;
    public Vector2 pointB;

    public Segment(Vector2 pointA, Vector2 pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public void setPointA(Vector2 A) {
        this.pointA = A;
    }

    public void setPointB(Vector2 B) {
        this.pointB = B;
    }

    public Vector2 checkIntersection(Segment lin) {

          // (xi, yj) values for first line
          float x1 = lin.pointA.getX();
          float y1 = lin.pointA.getY();
          float x2 = lin.pointB.getX();
          float y2 = lin.pointB.getY();
  
          // (xi, yj) values for second line
          float x3 = this.pointA.getX();
          float y3 = this.pointA.getY();
          float x4 = this.pointB.getX();
          float y4 = this.pointB.getY();
         
          float den = (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);
          if (den == 0) {
              return null;
          }
  
          float t = (x1 - x3)*(y3 - y4) - (y1 - y3)*(x3 - x4) / den;
          float u = (x1 - x3)*(y1 - y2) - (y1 - y3)*(x1 - x2) / den;
  
          if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
              float Px = x1 + t*(x2 - x1);
              float Py = y1 + t*(y2 - y1);
              return ( new Vector2(Px, Py) );
          }
          else { return null; }
    }

    public Ray asRay() {
        return new Ray(pointA, pointB.subtract(pointA).normalize());
    }
}
