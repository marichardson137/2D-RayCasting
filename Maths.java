public class Maths {
    
    public static Vector2 lineIntersection(Segment one, Segment two) {

        // (xi, yj) values for first line
        float x1 = one.pointA.getX();
        float y1 = one.pointA.getY();
        float x2 = one.pointB.getX();
        float y2 = one.pointB.getY();

        // (xi, yj) values for second line
        float x3 = two.pointA.getX();
        float y3 = two.pointA.getY();
        float x4 = two.pointB.getX();
        float y4 = two.pointB.getY();
       
        float den = (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);
        if (den == 0) {
            return null;
        }

        float t = (x1 - x3)*(y3 - y4) - (y1 - y3)*(x3 - x4) / den;
        float u = (x1 - x3)*(y1 - y2) - (y1 - y3)*(x1 - x2) / den;

        if (t > 0 && t < 1 && u > 0) {
            float Px = x1 + t*(x2 - x1);
            float Py = y1 + t*(y2 - y1);
            return ( new Vector2(Px, Py) );
        }
        else { return null; }

    }

}
