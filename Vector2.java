public class Vector2 {
    
    private float x, y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        if (Float.isNaN(x) || Float.isNaN(y))
            throw new IllegalArgumentException("One or more parameters are NaN");

       this.x = x;
       this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2 add(Vector2 vec) {
        return new Vector2(this.x + vec.x, this.y + vec.y);
    }

    public Vector2 subtract(Vector2 vec) {
        return new Vector2(this.x - vec.x, this.y - vec.y);
    }

    public Vector2 scale(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 multiply(Vector2 vec) {
        return new Vector2(this.x * vec.x, this.y * vec.y);
    }

    public Vector2 divide(Vector2 vec) {
        return new Vector2(this.x / vec.x, this.y / vec.y);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float getAngle() {
        return (float) Math.atan2(this.y, this.x);
    }

    public Vector2 normalize() {
        float length = length();
        return new Vector2(this.x / length, this.y / length);
        }

    // Same thing as Vector2.add but changes the vector itself instead of returning a new one
    public void translate(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    public Vector2 setDir(float angle) {
        float m = this.length();
        return new Vector2((float) (m * Math.cos(angle)), (float) (m * Math.sin(angle)));
    }

    public Vector2 rotate(float degrees) {
        double radians = Math.toRadians(degrees);

        float _x = (float) (x * Math.cos(radians) - y * Math.sin(radians));
        float _y = (float) (x * Math.sin(radians) + y * Math.cos(radians));

        return new Vector2(_x, _y);
    }

    public static Vector2 lerp(Vector2 a, Vector2 b, float t) {
        return a.add(b.subtract(a).scale(t));
    }

    public static float dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y + b.y;
    }

    public static float cross(Vector2 a, Vector2 b) {
        return a.x * b.y - a.y * b.x;
    }

    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float[] toArray() {
        return new float[]{x, y};
    }

}
