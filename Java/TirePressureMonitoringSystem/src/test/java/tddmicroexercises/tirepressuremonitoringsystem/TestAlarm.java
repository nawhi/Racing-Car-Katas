package tddmicroexercises.tirepressuremonitoringsystem;


import org.junit.*;
import static org.junit.Assert.*;

public class TestAlarm {

    @Test
    public void alarm_is_off_by_default() {
        Alarm alarm = new Alarm(new Sensor());
        assertFalse(alarm.isAlarmOn());
    }

}
