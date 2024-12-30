package EnumTest;

public class PlanetTest {
    public static void main(String[] args) {
        double objectMass = 70; // Masse des Objekts in kg

        for (Planet planet : Planet.values()) {
            System.out.printf("Planet: %s%n", planet);
            System.out.printf("  Mass: %.2e kg%n", planet.getMass());
            System.out.printf("  Radius: %.2e m%n", planet.getRadius());
            System.out.printf("  Surface Gravity: %.2f m/s²%n", planet.surfaceGravity());
            System.out.printf("  Weight of object: %.2f N%n", planet.surfaceWeight(objectMass));
            System.out.println();
        }
    }
}
enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6),
    JUPITER(1.9e+27, 7.1492e7),
    SATURN(5.688e+26, 6.0268e7),
    URANUS(8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

    private final double mass;   // in kilograms
    private final double radius; // in meters

    // Gravitationskonstante
    private static final double G = 6.67430e-11; // in m^3 kg^-1 s^-2

    // Konstruktor
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    // Berechnung der Oberflächenschwerkraft
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    // Berechnung des Gewichts eines Objekts auf dem Planeten
    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }

    // Getter
    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }
}
