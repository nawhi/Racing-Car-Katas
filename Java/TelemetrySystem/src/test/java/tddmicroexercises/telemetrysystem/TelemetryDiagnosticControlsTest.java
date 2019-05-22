package tddmicroexercises.telemetrysystem;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TelemetryDiagnosticControlsTest
{

    @Test
    public void allows_diagnostic_info_to_be_set() {
        TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls();
        controls.setDiagnosticInfo("some info");
        assertEquals("some info", controls.getDiagnosticInfo());

    }

    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response() throws Exception {
        TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls();
        controls.checkTransmission();
        assertEquals("LAST TX rate................ 100 MBPS\r\n" +
                "HIGHEST TX rate............. 100 MBPS\r\n" +
                "LAST RX rate................ 100 MBPS\r\n" +
                "HIGHEST RX rate............. 100 MBPS\r\n" +
                "BIT RATE.................... 100000000\r\n" +
                "WORD LEN.................... 16\r\n" +
                "WORD/FRAME.................. 511\r\n" +
                "BITS/FRAME.................. 8192\r\n" +
                "MODULATION TYPE............. PCM/FM\r\n" +
                "TX Digital Los.............. 0.75\r\n" +
                "RX Digital Los.............. 0.10\r\n" +
                "BEP Test.................... -5\r\n" +
                "Local Rtrn Count............ 00\r\n" +
                "Remote Rtrn Count........... 00", controls.getDiagnosticInfo());
    }

    @Test
    public void disconnects_from_client_when_checking_transmission() throws Exception {
        TelemetryClient client = mockClientWithOnlineStatus(true);

        TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);

        controls.checkTransmission();

        verify(client).disconnect();
    }

    @Test(expected=Exception.class)
    public void throws_if_client_is_not_online() throws Exception {
        TelemetryClient client = mockClientWithOnlineStatus(false);
        new TelemetryDiagnosticControls(client).checkTransmission();
    }

    @Test
    public void tries_three_times_to_reconnect_if_client_is_not_online() {
        TelemetryClient client = mock(TelemetryClient.class);
        when(client.isOnline()).thenReturn(false);

        TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);

        try {
            controls.checkTransmission();
        } catch (Exception ignored) {}

        verify(client, times(3)).connect("*111#");
    }

    @Test
    public void sends_diagnostic_message_to_telemetry_client() throws Exception {
        TelemetryClient client = mockClientWithOnlineStatus(true);

        new TelemetryDiagnosticControls(client).checkTransmission();

        verify(client).send(TelemetryClient.DIAGNOSTIC_MESSAGE);
    }

    @Test
    public void stores_diagnostic_info_retrieved_from_client() throws Exception {
        TelemetryClient client = mockClientWithOnlineStatus(true);
        String diagnosticInfo = "some diagnostic info";
        when(client.receive()).thenReturn(diagnosticInfo);

        TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);
        controls.checkTransmission();

        assertEquals(diagnosticInfo, controls.getDiagnosticInfo());


    }

    private TelemetryClient mockClientWithOnlineStatus(boolean b) {
        TelemetryClient client = mock(TelemetryClient.class);
        when(client.isOnline()).thenReturn(b);
        return client;
    }
}
