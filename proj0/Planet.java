import org.junit.Test;

public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // 引力系数
    private static final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p2) {
        return Math.sqrt((p2.xxPos - xxPos) * (p2.xxPos - xxPos) + (p2.yyPos - yyPos) * (p2.yyPos - yyPos));
    }

    public double calcForceExertedBy(Planet p2) {
        return G * mass * p2.mass / calcDistance(p2) / calcDistance(p2);
    }

    public double calcForceExertedByX(Planet p2) {
        return (p2.xxPos - xxPos) / calcDistance(p2) * calcForceExertedBy(p2);
    }

    public double calcForceExertedByY(Planet p2) {
        return (p2.yyPos - yyPos) / calcDistance(p2) * calcForceExertedBy(p2);
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double rs = 0;
        for (Planet p2 : planets) {
            if (this.equals(p2)) {
                continue;
            }
            rs += calcForceExertedByX(p2);
        }
        return rs;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double rs = 0;
        for (Planet p2 : planets) {
            if (this.equals(p2)) {
                continue;
            }
            rs += calcForceExertedByY(p2);
        }
        return rs;
    }

    public void update(double dt, double fx, double fy) {
        // x
        xxVel += fx / mass * dt;
        xxPos += xxVel * dt;
        // y
        yyVel += fy / mass * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
