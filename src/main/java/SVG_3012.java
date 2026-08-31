import java.util.Scanner;

public class SVG_3012 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter speed");
        double speed = sc.nextDouble();

        System.out.println("Enter distance");
        double distance = sc.nextDouble();

        System.out.println(buildResult(speed, distance));


    }

    public static double timeCal(double speed, double distance){
        if (distance <= 0 || speed == 0){
            return 0;
        }
        return distance / speed;
    }

    public static String buildResult(double speed, double distance){
        double time = timeCal(speed, distance);
        return "Speed "+speed+ ",  distance: "+ distance+  ",  time: "+time;
    }



}
