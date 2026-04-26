package cz.stokratandroid.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void zapnoutBluetooth (View view) {
        // test jestli ma zarizeni modul Bluetooth
        if (btAdapter == null) {
            Toast.makeText(this, "Zařízení nemá k dispozici Bluetooth.", Toast.LENGTH_SHORT).show();
            return;
        }
        // test, jestli je Bluetooth zapnuty
        if (!btAdapter.isEnabled()) {
            // zapnout Bluetooth
            Intent zapnoutBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(zapnoutBtIntent);
        }
    }

    public void vypnoutBluetooth (View view) {
        // test jestli ma zarizeni modul Bluetooth
        if (btAdapter == null) {
            Toast.makeText(this, "Zařízení nemá k dispozici Bluetooth.", Toast.LENGTH_SHORT).show();
            return;
        }
        btAdapter.disable();
    }

    public void udelatViditelne (View view) {
        // zapnout viditelnost zarizeni na dve minuty
        Intent viditelneBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        viditelneBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivity(viditelneBtIntent);
    }

    public void testBluetooth(View view) {
        TextView txtTestBt=(TextView)findViewById(R.id.txtTestBt);

        // test pritomnosti modulu BT
        if (btAdapter == null) {
            txtTestBt.setText("Zařízení neobsahuje Bluetooth modul");
            return;
        }
        else {
            txtTestBt.setText("Bluetooth modul OK");
        }

        // test stavu BT
        if (btAdapter.getState() == BluetoothAdapter.STATE_ON) {
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Bluetooth je zapnutý.");
        }
        else if (btAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON) {
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Bluetooth se zapíná.");
            return;
        }
        else if (btAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Bluetooth se vypíná.");
            return;
        }
        else if (btAdapter.getState() == BluetoothAdapter.STATE_OFF) {
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Bluetooth je vypnutý.");
            return;
        }

        // test viditelnosti
        if (btAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Zařízení je pro okolí viditelné.");
        else
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Zařízení není pro okolí viditelné.");

        // test jestli zařízení vyhledává
        if (btAdapter.isDiscovering())
            txtTestBt.setText(txtTestBt.getText() + "\n" + "Probíhá vyhledávání okolních zařízení.");

        // test sparovanych zarizeni
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        txtTestBt.setText(txtTestBt.getText() + "\n" + "Spárovaná zařízení:");
        if (pairedDevices.size() == 0) {
            txtTestBt.setText(txtTestBt.getText() + " žádné zařízení");
        }
        else {
            for (BluetoothDevice device : pairedDevices) {
                String zarizeniJmeno = device.getName();
                String zarizeniAdresa = device.getAddress();
                txtTestBt.setText(txtTestBt.getText() + "\n" + "  " + zarizeniJmeno + " [" + zarizeniAdresa + "]");
            }
        }

    }

    public void hledatBluetooth (View view) {
        TextView txtOkolniBt=(TextView)findViewById(R.id.txtOkolniBt);
        txtOkolniBt.setText("");

        // registrace udalosti nalezeni zarizeni v okoli
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        // zahajit vyhledavani zarizeni
        btAdapter.startDiscovery();
    }

    // vytvorit instanci BroadcastReceiver pro hledani BT zarizeni
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String zarizeniJmeno = device.getName();
                String zarizeniAdresa = device.getAddress();
                // String zarizeniVerzeBt = device.getBluetoothClass();
                // String zarizeniSparovano = device.getBondState();
                // String zarizeniTyp = device.getType();

                TextView txtOkolniBt=(TextView)findViewById(R.id.txtOkolniBt);
                txtOkolniBt.setText(txtOkolniBt.getText() + zarizeniJmeno + " [" + zarizeniAdresa + "]\n");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // odregistrovat receiver
        // neexistuje overeni registrace, takze pouzijeme try - catch
        try {
            unregisterReceiver(receiver);
        }
        catch (IllegalArgumentException ex) {
        }
    }
}