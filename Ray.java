public class Ray {
    
    private Vector2 origin;
    private Vector2 direction;
    
    public Ray(Vector2 origin, Vector2 direction) {
        this.origin = origin;

        if (direction.length() != 1) {
            direction = direction.normalize();
        }
        this.direction = direction;
    }

    public Segment asLine(float length) {
        return new Segment(origin, origin.add(direction.scale(length)));
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
