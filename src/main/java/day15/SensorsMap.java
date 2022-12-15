package day15;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import helper.Geometry;

public class SensorsMap {
    List<Sensor> sensors = new ArrayList<>();
    List<Point> sensorList = new ArrayList<>();
    List<Point> beaconList = new ArrayList<>();

    int xMax, xMin, yMax, yMin;

    public SensorsMap(List<Sensor> sensors, List<Point> sensorList, List<Point> beaconList) {
        super();
        this.sensors = sensors;
        this.sensorList = sensorList;
        this.beaconList = beaconList;

        Integer xMax = null;
        Integer xMin = null;
        Integer yMax = null;
        Integer yMin = null;

        for (Sensor sensor : sensors) {
            int xMaxSensor = sensor.sensor.x + sensor.covering;
            int xMinSensor = sensor.sensor.x - sensor.covering;
            int yMaxSensor = sensor.sensor.y + sensor.covering;
            int yMinSensor = sensor.sensor.x - sensor.covering;

            if (xMax == null || xMaxSensor > xMax) {
                xMax = xMaxSensor;
            }
            if (xMin == null || xMinSensor < xMin) {
                xMin = xMinSensor;
            }
            if (yMax == null || yMaxSensor > yMax) {
                yMax = yMaxSensor;
            }
            if (yMin == null || yMinSensor < yMin) {
                yMin = yMinSensor;
            }
        }

        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;

    }


    public String visualized(int min, int max) {
        StringBuilder sb = new StringBuilder();
        for (int y = min; y <= max; y++) {
            for (int x = min; x <= max; x++) {
                Point p = new Point(x, y);
                if (sensorList.contains(p)) {
                    sb.append('S');
                } else if (beaconList.contains(p)) {
                    sb.append('B');
                } else if (isInRangeOfSensor(p, sensors)) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();

    }

    public String visualized() {
        StringBuilder sb = new StringBuilder();
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                Point p = new Point(x, y);
                if (sensorList.contains(p)) {
                    sb.append('S');
                } else if (beaconList.contains(p)) {
                    sb.append('B');
                } else if (isInRangeOfSensor(p, sensors)) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();

    }


    public Point findSpotBetween(int min, int max) {
        for (int y = min; y <= max; y++) {
            for (int x = min; x <= max; x++) {
                Point p = new Point(x, y);
                Sensor sensor = inRangeOfSensor(p, sensors);

                if (!sensorList.contains(p) && !beaconList.contains(p)) {
                    if (sensor == null) {
                        return p;
                    } else
                        x = xMaxRangeIn(sensor, y) - 1;
                }


            }
        }
        return null;

    }

    public int xMaxRangeIn(Sensor s, int y) {
        return s.covering + 1 - Math.abs(s.sensor.y - y) + s.sensor.x;
    }

    public int getCoveredSpotInY(int y) {
        int result = 0;
        for (int x = xMin; x < xMax; x++) {
            Point p = new Point(x, y);
            if (isInRangeOfSensor(p, sensors)) {
                result++;
            }

        }
        return result;
    }

    private boolean isInRangeOfSensor(final Point p, final List<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            if (!sensorList.contains(p) && !beaconList.contains(p)) {
                if (Geometry.manhattanDistance(sensor.sensor, p) <= sensor.covering) {
                    return true;
                }
            }
        }
        return false;

    }

    private Sensor inRangeOfSensor(final Point p, final List<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            if (!sensorList.contains(p) && !beaconList.contains(p)) {
                if (Geometry.manhattanDistance(sensor.sensor, p) <= sensor.covering) {
                    return sensor;
                }
            }
        }
        return null;
    }

}
