import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class SVG_3012Test {

    // ==================== TESTS FOR timeCal() ====================

    @Test
    @DisplayName("timeCal() - Normal case: positive speed and distance")
    void testTimeCal_NormalCase() {
        double speed = 10.0;
        double distance = 50.0;
        double expected = 5.0; // 50 / 10 = 5

        double result = SVG_3012.timeCal(speed, distance);
        assertEquals(expected, result, 0.0001, "Time should be distance/speed");
    }

    @Test
    @DisplayName("timeCal() - Distance is zero")
    void testTimeCal_ZeroDistance() {
        double result = SVG_3012.timeCal(10.0, 0);
        assertEquals(0.0, result, 0.0001, "Should return 0 when distance is 0");
    }

    @Test
    @DisplayName("timeCal() - Distance is negative")
    void testTimeCal_NegativeDistance() {
        double result = SVG_3012.timeCal(10.0, -5.0);
        assertEquals(0.0, result, 0.0001, "Should return 0 when distance is negative");
    }

    @Test
    @DisplayName("timeCal() - Speed is zero")
    void testTimeCal_ZeroSpeed() {
        double result = SVG_3012.timeCal(0, 50.0);
        assertEquals(0.0, result, 0.0001, "Should return 0 when speed is 0");
    }

    @Test
    @DisplayName("timeCal() - Both speed and distance are zero")
    void testTimeCal_BothZero() {
        double result = SVG_3012.timeCal(0, 0);
        assertEquals(0.0, result, 0.0001, "Should return 0 when both speed and distance are 0");
    }

    @Test
    @DisplayName("timeCal() - Speed is negative")
    void testTimeCal_NegativeSpeed() {
        // Note: Method only checks if speed == 0, not negative speeds
        // This test documents current behavior (negative speed gives a negative time)
        double speed = -10.0;
        double distance = 50.0;
        double expected = -5.0; // 50 / -10 = -5

        double result = SVG_3012.timeCal(speed, distance);
        assertEquals(expected, result, 0.0001, "Negative speed produces negative time (current behavior)");
    }

    @Test
    @DisplayName("timeCal() - Speed is extremely small (positive)")
    void testTimeCal_VerySmallSpeed() {
        double speed = 0.0001;
        double distance = 1.0;
        double expected = 10000.0; // 1 / 0.0001 = 10000

        double result = SVG_3012.timeCal(speed, distance);
        assertEquals(expected, result, 0.0001, "Should handle very small speeds");
    }

    @Test
    @DisplayName("timeCal() - Speed and distance are decimals")
    void testTimeCal_DecimalValues() {
        double result = SVG_3012.timeCal(2.5, 7.5);
        assertEquals(3.0, result, 0.0001, "7.5 / 2.5 = 3.0");
    }

    @Test
    @DisplayName("timeCal() - Large numbers")
    void testTimeCal_LargeNumbers() {
        double result = SVG_3012.timeCal(1e9, 1e12);
        assertEquals(1000.0, result, 0.0001, "Should handle large numbers");
    }


    void testTimeCal_Parameterized(double speed, double distance, double expected) {
        double result = SVG_3012.timeCal(speed, distance);
        assertEquals(expected, result, 0.0001,
                String.format("timeCal(%.1f, %.1f) should return %.1f", speed, distance, expected));
    }


    void testTimeCal_InvalidInputsReturnZero(double speed, double distance) {
        double result = SVG_3012.timeCal(speed, distance);
        assertEquals(0.0, result, 0.0001,
                String.format("timeCal(%.1f, %.1f) should return 0", speed, distance));
    }

    // ==================== TESTS FOR buildResult() ====================

    @Test
    @DisplayName("buildResult() - Normal case")
    void testBuildResult_NormalCase() {
        double speed = 10.0;
        double distance = 50.0;
        String expected = "Speed 10.0,  distance: 50.0,  time: 5.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should build the correct result string");
    }

    @Test
    @DisplayName("buildResult() - With zero distance (should return time 0.0)")
    void testBuildResult_ZeroDistance() {
        double speed = 10.0;
        double distance = 0.0;
        String expected = "Speed 10.0,  distance: 0.0,  time: 0.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should include 0.0 time when distance is 0");
    }

    @Test
    @DisplayName("buildResult() - With zero speed (should return time 0.0)")
    void testBuildResult_ZeroSpeed() {
        double speed = 0.0;
        double distance = 50.0;
        String expected = "Speed 0.0,  distance: 50.0,  time: 0.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should include 0.0 time when speed is 0");
    }

    @Test
    @DisplayName("buildResult() - With negative distance (should return time 0.0)")
    void testBuildResult_NegativeDistance() {
        double speed = 10.0;
        double distance = -5.0;
        String expected = "Speed 10.0,  distance: -5.0,  time: 0.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should include 0.0 time for invalid distance");
    }

    @Test
    @DisplayName("buildResult() - With negative speed (time will be negative)")
    void testBuildResult_NegativeSpeed() {
        double speed = -10.0;
        double distance = 50.0;
        String expected = "Speed -10.0,  distance: 50.0,  time: -5.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should include negative time for negative speed");
    }

    @Test
    @DisplayName("buildResult() - With decimal values")
    void testBuildResult_DecimalValues() {
        double speed = 2.5;
        double distance = 7.5;
        String expected = "Speed 2.5,  distance: 7.5,  time: 3.0";

        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result, "Should format decimal values correctly");
    }

    @Test
    @DisplayName("buildResult() - Result contains all three values")
    void testBuildResult_ContainsAllValues() {
        double speed = 15.0;
        double distance = 45.0;

        String result = SVG_3012.buildResult(speed, distance);

        assertTrue(result.contains("Speed 15.0"), "Should contain the speed value");
        assertTrue(result.contains("distance: 45.0"), "Should contain the distance value");
        assertTrue(result.contains("time: 3.0"), "Should contain the time value");
    }

    @Test
    @DisplayName("buildResult() - Result has correct format with spaces")
    void testBuildResult_Format() {
        String result = SVG_3012.buildResult(10.0, 50.0);

        // Check the exact format: "Speed X,  distance: Y,  time: Z"
        assertTrue(result.matches("Speed \\d+\\.0,  distance: \\d+\\.0,  time: \\d+\\.\\d+"),
                "Result should match expected format: 'Speed X,  distance: Y,  time: Z'");
    }

    @Test
    @DisplayName("buildResult() - Verify it calls timeCal() correctly")
    void testBuildResult_CallsTimeCal() {
        // This test verifies buildResult returns what we expect given timeCal's behavior
        double speed = 4.0;
        double distance = 20.0;

        String result = SVG_3012.buildResult(speed, distance);
        String expected = "Speed 4.0,  distance: 20.0,  time: 5.0";

        assertEquals(expected, result, "buildResult should correctly format timeCal result");
    }


    void testBuildResult_Parameterized(double speed, double distance, String expected) {
        String result = SVG_3012.buildResult(speed, distance);
        assertEquals(expected, result,
                String.format("buildResult(%.1f, %.1f) should return '%s'", speed, distance, expected));
    }
}