package tddmicroexercises.tirepressuremonitoringsystem;


import org.junit.*;
import static org.junit.Assert.*;

public class AlarmShould {

    @Test
    public void be_off_by_default() {
        Alarm alarm = new Alarm(new Sensor());
        assertFalse(alarm.isAlarmOn());
    }

    @Test
    public void be_off_if_pressure_is_low_but_within_threshold() {
        Alarm alarm = new Alarm(sensorReturning(17));
        alarm.check();
        assertFalse(alarm.isAlarmOn());
    }

    @Test
    public void be_off_if_pressure_is_high_but_within_threshold() {
        Alarm alarm = new Alarm(sensorReturning(21));
        alarm.check();
        assertFalse(alarm.isAlarmOn());
    }

    @Test
    public void be_on_if_pressure_is_below_threshold() {
        Alarm alarm = new Alarm(sensorReturning(16));
        alarm.check();
        assertTrue(alarm.isAlarmOn());
    }

    @Test
    public void alarm_is_on_if_pressure_is_above_threshold() {
        Alarm alarm = new Alarm(sensorReturning(22));
        alarm.check();
        assertTrue(alarm.isAlarmOn());
    }

    private Sensor sensorReturning(final int value) {
        return new Sensor() {
            @Override public double popNextPressurePsiValue() {
                return value;
            }
        };
    }
}
