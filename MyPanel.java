import javax.swing.*;
import java.awt.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {

  final int radius = 5;

  Segment[] boundaries;
  Segment[] rays;

  BufferedImage Background, Foreground;

  Timer loop = new Timer(1000 / 60, new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      repaint();
    }

  });

  public MyPanel() {

    try {
      Background = ImageIO.read(MyPanel.class.getResourceAsStream("assets/Background.png"));
      Foreground = ImageIO.read(MyPanel.class.getResourceAsStream("assets/Foreground.png"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // this.setBackground(Color.BLACK);

    boundaries = new Segment[] {
        // Border
        new Segment(new Vector2(0, 0), new Vector2(MyFrame.WIDTH, 0)),
        new Segment(new Vector2(MyFrame.WIDTH, 0), new Vector2(MyFrame.WIDTH, MyFrame.HEIGHT)),
        new Segment(new Vector2(MyFrame.WIDTH, MyFrame.HEIGHT), new Vector2(0, MyFrame.HEIGHT)),
        new Segment(new Vector2(0, MyFrame.HEIGHT), new Vector2(0, 0)),

        // polygon #1
        new Segment(new Vector2(108, 120), new Vector2(253, 80)),
        new Segment(new Vector2(253, 80), new Vector2(280, 192)),
        new Segment(new Vector2(280, 192), new Vector2(83, 286)),
        new Segment(new Vector2(83, 286), new Vector2(108, 120)),

        // polygon #2
        new Segment(new Vector2(790, 54), new Vector2(982, 83)),
        new Segment(new Vector2(982, 83), new Vector2(1047, 127)),
        new Segment(new Vector2(1047, 127), new Vector2(789, 146)),
        new Segment(new Vector2(789, 146), new Vector2(790, 54)),

        // polygon #3
        new Segment(new Vector2(437, 227), new Vector2(471, 283)),
        new Segment(new Vector2(471, 283), new Vector2(398, 293)),
        new Segment(new Vector2(398, 293), new Vector2(437, 227)),

        // polygon #4
        new Segment(new Vector2(535, 117), new Vector2(747, 240)),
        new Segment(new Vector2(747, 240), new Vector2(907, 203)),
        new Segment(new Vector2(907, 203), new Vector2(889, 479)),
        new Segment(new Vector2(889, 479), new Vector2(664, 430)),
        new Segment(new Vector2(664, 430), new Vector2(535, 117)),

        // polygon #5
        new Segment(new Vector2(169, 552), new Vector2(397, 373)),
        new Segment(new Vector2(397, 373), new Vector2(362, 584)),
        new Segment(new Vector2(362, 584), new Vector2(169, 552)),

        // polygon #6
        new Segment(new Vector2(566, 495), new Vector2(932, 536)),
        new Segment(new Vector2(932, 536), new Vector2(1015, 495)),
        new Segment(new Vector2(1015, 495), new Vector2(1124, 599)),
        new Segment(new Vector2(1124, 599), new Vector2(931, 680)),
        new Segment(new Vector2(931, 680), new Vector2(599, 639)),
        new Segment(new Vector2(599, 639), new Vector2(566, 495)),

    };

    loop.start();

  }

  public void createRays() {

    rays = new Segment[boundaries.length * 3];

    int count = 0;

    for (int s = 0; s < boundaries.length; s++) {

      Vector2 a = new Vector2(MouseManager.mouseX, MouseManager.mouseY); // mouse position
      Vector2 b = boundaries[s].pointB; // boundary corner position
      Vector2 c = b.subtract(a).normalize(); // direction from mouse to each boundary corner

      Segment s1 = new Segment(
          a,
          b);
      Segment s2 = new Segment(
          a,
          a.add(c.setDir(c.getAngle() + 0.00001f).normalize().scale(MyFrame.WIDTH)));
      Segment s3 = new Segment(
          a,
          a.add(c.setDir(c.getAngle() - 0.00001f).normalize().scale(MyFrame.WIDTH)));

      rays[count] = s1;
      rays[count + 1] = s2;
      rays[count + 2] = s3;

      count += 3;

    }
  }

  // Gonna be honest, idk wtf this is doing
  public void sortRays() {

    ArrayList<Segment> list = new ArrayList<Segment>();

    for (int r = 0; r < rays.length; r++) {
      list.add(rays[r]);
    }

    list.sort(new Comparator<Segment>() {

      @Override
      public int compare(Segment e1, Segment e2) {

        Vector2 v1 = e1.pointB.subtract(e1.pointA);
        Vector2 v2 = e2.pointB.subtract(e2.pointA);

        if (v1.getAngle() < v2.getAngle())
          return -1;
        if (v1.getAngle() > v2.getAngle())
          return 1;

        return 0;
      }

    });

    for (int i = 0; i < list.size(); i++) {

      rays[i] = list.get(i);

    }

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2D = (Graphics2D) g;

    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g2D.setColor(Color.BLACK);
    g2D.fillRect(0, 0, MyFrame.WIDTH, MyFrame.HEIGHT);

    // Draw Boundaries
    g2D.setStroke(new BasicStroke(2));
    g2D.setColor(Color.WHITE);

    for (int i = 0; i < boundaries.length; i++) {

      g2D.drawLine((int) boundaries[i].pointA.getX(), (int) boundaries[i].pointA.getY(),
          (int) boundaries[i].pointB.getX(), (int) boundaries[i].pointB.getY());

    }

    createRays();
    sortRays();

    // Cast the Rays
    for (int r = 0; r < rays.length; r++) {
      Segment ray = rays[r];

      Vector2 closestIntersect = ray.pointB;
      float distToIntersect = WIDTH;

      for (int i = 0; i < boundaries.length; i++) {

        Intersect intersection = getIntersection(ray, boundaries[i]);

        if (intersection != null) {
          float d = intersection.distance;

          if (distToIntersect > d) {
            distToIntersect = d;
            closestIntersect = intersection.intersect;
          }
        }
      }

      ray.pointB = closestIntersect;
    }

    // drawRays(g2D);

    g2D.drawImage(Background, 0, 0, null);

    drawPolygons(g2D);

  }

  public void drawRays(Graphics2D g2D) {

    g2D.setColor(Color.GRAY);
    g2D.setStroke(new BasicStroke(1));

    for (int r = 0; r < rays.length; r++) {

      Segment ray = rays[r];

      g2D.drawLine((int) ray.pointA.getX(), (int) ray.pointA.getY(), (int) ray.pointB.getX(), (int) ray.pointB.getY());

      g2D.fillOval((int) ray.pointB.getX() - radius, (int) ray.pointB.getY() - radius,
          radius * 2, radius * 2);
    }
  }

  public void drawPolygons(Graphics2D g2D) {

    for (int r = 0; r < rays.length; r++) {

      Segment ray1 = rays[r];
      Segment ray2;

      // Account for when you make it all the way around the circle of rays
      if (r == rays.length - 1) {
        ray2 = rays[0];
      } else {
        ray2 = rays[r + 1];
      }

      int[] x_points = { (int) ray1.pointA.getX(), (int) ray1.pointB.getX(), (int) ray2.pointB.getX() };
      int[] y_points = { (int) ray1.pointA.getY(), (int) ray1.pointB.getY(), (int) ray2.pointB.getY() };

      Polygon p = new Polygon(x_points, y_points, 3);

      // g2D.fillPolygon(p);

      g2D.setClip(p);

      g2D.drawImage(Foreground, 0, 0, null);
    }
  }

  public Intersect getIntersection(Segment s1, Segment s2) {

    Vector2 p = s1.pointA;
    Vector2 q = s2.pointA;
    Vector2 r = s1.pointB.subtract(s1.pointA);
    Vector2 s = s2.pointB.subtract(s2.pointA);

    float t = (Vector2.cross(q, s) - Vector2.cross(p, s)) / Vector2.cross(r, s);
    float u = (Vector2.cross(p, r) - Vector2.cross(q, r)) / Vector2.cross(s, r);

    if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
      return new Intersect(new Vector2(p.getX() + t * r.getX(), p.getY() + t * r.getY()), t);
    }
    return null;
  }

  class Intersect {

    Vector2 intersect;
    float distance;

    Intersect(Vector2 i, float d) {
      this.intersect = i;
      this.distance = d;
    }

  }

}
