package tddmicroexercises.tirepressuremonitoringsystem;

class Alarm
{
    private static final double LOW_PRESSURE_THRESHOLD = 17;
    private static final double HIGH_PRESSURE_THRESHOLD = 21;

    private Sensor sensor;
    private boolean alarmOn = false;

    Alarm(Sensor sensor) {
        this.sensor = sensor;
    }

    void check()
    {
        double psiPressureValue = sensor.popNextPressurePsiValue();
        alarmOn = psiPressureValue < LOW_PRESSURE_THRESHOLD
                || HIGH_PRESSURE_THRESHOLD < psiPressureValue;
    }

    boolean isAlarmOn()
    {
        return alarmOn; 
    }
}
