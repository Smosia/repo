package com.mediatek.mdmlsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mediatek.mdml.TRAP_TYPE;
import com.mediatek.mdml.PlainDataDecoder;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mediatek.mdml.TRAP_TYPE.TRAP_TYPE_EM;
import static com.mediatek.mdml.TRAP_TYPE.TRAP_TYPE_OTA;
import static com.mediatek.mdml.TRAP_TYPE.TRAP_TYPE_VOLTE;



public class MainActivity extends Activity {
    private static final String TAG = "MDMLSample";
    // UI
    protected Switch m_swtEnable;
    protected Button m_btnEM;
    protected Button m_btnOTA;
    protected Button m_btnVoLTE;
    protected TextView m_textviewResult;
    protected ScrollView m_scrollview;
    protected ProgressBar m_progressCircle;
    protected ProgressBar m_progressBar;
    //
    ConnectionState m_connectState;
    CommandProxy m_cmdProxy;
    commandUIUpdater m_cmdListener;
    TrapReceiverService m_trapReceiverService;      // trap services reference
    boolean mBound = false;         // trap services attach flag
    DemoListener m_trapListener;


    // used to update command proxy related UI widget
    public class commandUIUpdater implements CommandProxyListener{
        public void onCommandPreExecute(){
            Log.d(TAG, "onCommandPreExecute ! sid = " + m_connectState.m_sid);
            // disable related widget
            m_swtEnable.setClickable(false);
            m_btnEM.setClickable(false);
            m_btnOTA.setClickable(false);
            m_btnVoLTE.setClickable(false);
            m_swtEnable.setEnabled(false);
            m_btnEM.setEnabled(false);
            m_btnOTA.setEnabled(false);
            m_btnVoLTE.setEnabled(false);
            // enable progress bar
            m_progressCircle.setVisibility(View.VISIBLE);
            m_progressBar.setVisibility(View.VISIBLE);
        }

        public void onCommandProgressUpdate(int progressPercentage) {
            Log.d(TAG, "onCommandProgressUpdate ! sid = " + m_connectState.m_sid);
                    m_progressBar.setProgress(progressPercentage);
        }

        public void onCommandFinishUpdate(int commandResult) {
            Log.d(TAG, "onCommandFinishUpdate !");
            // toast message about execution result
            if(commandResult == 0) {
                Toast.makeText(MainActivity.this, "Command proxy async task done", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Command proxy async task failed", Toast.LENGTH_SHORT).show();
            }

            // set UI widget visible
            m_swtEnable.setClickable(true);
            m_btnEM.setClickable(true);
            m_btnOTA.setClickable(true);
            m_btnVoLTE.setClickable(true);
            m_swtEnable.setEnabled(true);
            m_btnEM.setEnabled(true);
            m_btnOTA.setEnabled(true);
            m_btnVoLTE.setEnabled(true);
            m_progressCircle.setVisibility(View.INVISIBLE);
            m_progressBar.setVisibility(View.INVISIBLE);

        }
    }

    private class DemoListener implements NewTrapListener {
        public void NewTrapArrival() {
            Log.d(TAG, "frame is incoming:");
            runOnUiThread(new UIUpdater());
            Log.d(TAG, "frame is incoming... done!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadMDML();
        InitTrapReceiverService();
        InitUI();
        ConnectMDM();
        InitDecoder();
    }
    private void LoadMDML()
    {
        // Get MDML objects and connection state from app object
        AppApplication app = (AppApplication) getApplicationContext();
        m_cmdProxy = app.GetCommandProxyObject();
        m_connectState = app.GetConnectionState();

        // set proxy command state listener to global CommandProxy object
        m_cmdListener = new commandUIUpdater();
        m_cmdProxy.RegisterCommandProxyListener(hashCode(), m_cmdListener);
    }
    private void InitTrapReceiverService()
    {
        // bind to  services
        Intent intent = new Intent(this, TrapReceiverService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            TrapReceiverService.LocalBinder binder = (TrapReceiverService.LocalBinder) service;
            m_trapReceiverService = binder.getService();
            mBound = true;
            Log.d(TAG, "onServiceConnected()");
            m_trapListener = new DemoListener();
            m_trapReceiverService.registerLogUpdater(m_trapListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    TrapFileReader trapFileReader = null;
    // inner log updater class
    public class UIUpdater implements Runnable{
        public void run(){
            if(trapFileReader == null)
                trapFileReader = new TrapFileReader();
            Log.d(TAG, "UIUpdater() : Run");
            // Read the latest trap text from file
            StringBuilder trapText = trapFileReader.GetLastTrapContext();
            int trapIndex = trapFileReader.GetCurrentTrapOrder();
            m_textviewResult.setText("["+ trapIndex +"]\n" +trapText.toString());
        }
    }
    private void ConnectMDM()
    {
        // Check connection is ready or not. If not, do connection
        if(m_connectState.m_bConnected == false)
        {
            Log.d(TAG, "Start connection()");
            // create session and setup trap receiver in one operation
            CommandProxyAction op = new CommandProxyAction();
            op.actionType = CommandProxyActionType.ACTION_TYPE_CREATE_SESSION_AND_SET_TRAP_RECEIVER;
            op.serverName = m_connectState.m_serverName;
            m_cmdProxy.ExecuteCommand(op);
        }
    }
    private void InitUI()
    {
        Log.d(TAG, "InitUI()");
        setContentView(R.layout.layout_main);
        m_btnOTA = ( Button ) findViewById( R.id.btnOTA );
        m_btnOTA.setOnClickListener(new ButtonClickHandler());

        m_btnEM = ( Button ) findViewById( R.id.btnEM );
        m_btnEM.setOnClickListener( new ButtonClickHandler()  );

        m_btnVoLTE = ( Button ) findViewById( R.id.btnVoLTE );
        m_btnVoLTE.setOnClickListener(new ButtonClickHandler());
        m_btnEM.setClickable(false);
        m_btnOTA.setClickable(false);
        m_btnVoLTE.setClickable(false);
        m_btnEM.setEnabled(false);
        m_btnOTA.setEnabled(false);
        m_btnVoLTE.setEnabled(false);

        m_progressCircle = (ProgressBar) findViewById( R.id.progressBar_Circle);
        m_progressBar = (ProgressBar) findViewById( R.id.progressBar_Horizontal);
        m_progressCircle.setVisibility(View.INVISIBLE);
        m_progressBar.setVisibility(View.INVISIBLE);
        m_progressBar.setMax(100);

        m_textviewResult = ( TextView ) findViewById( R.id.textviewResult );
        m_scrollview = ((ScrollView) findViewById(R.id.scrollview));
        m_swtEnable = ((Switch)findViewById(R.id.swEnable));
        m_swtEnable.setClickable(false);
        m_swtEnable.setEnabled(false);
        m_swtEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    CommandProxyAction op = new CommandProxyAction();
                    op.actionType = CommandProxyActionType.ACTION_TYPE_ENABLE_TRAP;
                    op.sessionID = m_connectState.m_sid;
                    m_cmdProxy.ExecuteCommand(op);
                } else {
                    CommandProxyAction op = new CommandProxyAction();
                    op.actionType = CommandProxyActionType.ACTION_TYPE_DISABLE_TRAP;
                    op.sessionID = m_connectState.m_sid;
                    m_cmdProxy.ExecuteCommand(op);
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Unregister command listener
        m_cmdProxy.UnRegisterCommandProxyListener(hashCode());
    }

    /* decoder */
    private PlainDataDecoder m_plainDataDecoder;
    private void InitDecoder()
    {
        int i;
        // Get decoder object from app object
        AppApplication app = (AppApplication) getApplicationContext();
        m_plainDataDecoder = app.GetPlainDataDecoder();

        m_OTA_options = m_plainDataDecoder.globalId_getList();
        m_EM_options = m_plainDataDecoder.msgInfo_getMsgList();
        m_VoLTE_options = m_plainDataDecoder.volteInfo_getMsgList();

        int total_id_size = m_OTA_options.length + m_EM_options.length + m_VoLTE_options.length;
        CommandProxyAction[] subscribeCmdArray = new CommandProxyAction[total_id_size];
        int CmdArrayIndex = 0;


        m_OTA_selections = new boolean[m_OTA_options.length];
        m_OTA_selections_lasttime = new boolean[m_OTA_options.length];
        Arrays.fill(m_OTA_selections, true);   // fill true
        Arrays.fill(m_OTA_selections_lasttime, true);   // fill true
        m_OTA_id = new long[m_OTA_options.length];
        for (i = 0; i < m_OTA_options.length; ++i) {
            m_OTA_id[i] = m_plainDataDecoder.globalId_getValue(m_OTA_options[i]);
            // Subscribe all OTAs at beginning, add id into command array
            subscribeCmdArray[CmdArrayIndex] = new CommandProxyAction();
            subscribeCmdArray[CmdArrayIndex].sessionID = m_connectState.m_sid;
            subscribeCmdArray[CmdArrayIndex].trapType = TRAP_TYPE_OTA;
            subscribeCmdArray[CmdArrayIndex].msgID = m_OTA_id[i];
            subscribeCmdArray[CmdArrayIndex].actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
            CmdArrayIndex++;
        }

        m_EM_options = m_plainDataDecoder.msgInfo_getMsgList();
        m_EM_selections = new boolean[m_EM_options.length];
        m_EM_selections_lasttime = new boolean[m_EM_options.length];
        Arrays.fill(m_EM_selections, true);   // fill true
        Arrays.fill(m_EM_selections_lasttime, true);   // fill true
        m_EM_id = new long[m_EM_options.length];
        for (i = 0; i < m_EM_options.length; ++i) {
            m_EM_id[i] = m_plainDataDecoder.msgInfo_getMsgID(m_EM_options[i]);
            // Subscribe all EMs at beginning, add id into command array
            subscribeCmdArray[CmdArrayIndex] = new CommandProxyAction();
            subscribeCmdArray[CmdArrayIndex].actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
            subscribeCmdArray[CmdArrayIndex].sessionID = m_connectState.m_sid;
            subscribeCmdArray[CmdArrayIndex].trapType = TRAP_TYPE_EM;
            subscribeCmdArray[CmdArrayIndex].msgID = m_EM_id[i];
            CmdArrayIndex++;
        }

        m_VoLTE_options = m_plainDataDecoder.volteInfo_getMsgList();
        m_VoLTE_selections = new boolean[m_VoLTE_options.length];
        m_VoLTE_selections_lasttime = new boolean[m_VoLTE_options.length];
        Arrays.fill(m_VoLTE_selections, true);   // fill true
        Arrays.fill(m_VoLTE_selections_lasttime, true);   // fill true
        m_VoLTE_id = new long[m_VoLTE_options.length];
        for (i = 0; i < m_VoLTE_options.length; ++i) {
            m_VoLTE_id[i] = m_plainDataDecoder.volteInfo_getMsgID(m_VoLTE_options[i]);
            // Subscribe all VoLTE messages at beginning, add id into command array
            subscribeCmdArray[CmdArrayIndex] = new CommandProxyAction();
            subscribeCmdArray[CmdArrayIndex].actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
            subscribeCmdArray[CmdArrayIndex].sessionID = m_connectState.m_sid;
            subscribeCmdArray[CmdArrayIndex].trapType = TRAP_TYPE_VOLTE;
            subscribeCmdArray[CmdArrayIndex].msgID = m_VoLTE_id[i];
            CmdArrayIndex++;
        }

        // call CommandProxyAsyncTask to operate commands array
        if(m_connectState.m_bSetupDefaultSubscribe == false) {
            m_connectState.m_bSetupDefaultSubscribe = true;
            m_cmdProxy.ExecuteCommand(subscribeCmdArray);
        }
        
        /* temp code for verifying PSTIME trap type */
        // subscribe TRAP_TYPE_PSTIME
        CommandProxyAction subsPstimeCmd = new CommandProxyAction();
        subsPstimeCmd.actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
        subsPstimeCmd.sessionID = m_connectState.m_sid;
        subsPstimeCmd.trapType = TRAP_TYPE.TRAP_TYPE_PSTIME;
        subsPstimeCmd.msgID = 0;
        m_cmdProxy.ExecuteCommand(subsPstimeCmd);
        /* temp code for verifying PSTIME trap type */
    }

    /* Trap Info */
    protected String[] m_OTA_options;
    protected boolean[] m_OTA_selections;
    protected boolean[] m_OTA_selections_lasttime;
    protected long[] m_OTA_id;
    protected String[] m_EM_options;
    protected boolean[] m_EM_selections;
    protected boolean[] m_EM_selections_lasttime;
    protected long[] m_EM_id;
    protected String[] m_VoLTE_options;
    protected boolean[] m_VoLTE_selections;
    protected boolean[] m_VoLTE_selections_lasttime;
    protected long[] m_VoLTE_id;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Button click */
    public class ButtonClickHandler implements View.OnClickListener {
        public void onClick( View view ) {
            switch (view.getId()) {
                case R.id.btnEM:
                case R.id.btnOTA:
                case R.id.btnVoLTE:
                    showDialog( view.getId() );
                    break;
            }
        }
    }
    protected Dialog onCreateDialog( int id )
    {
        switch(id) {
            case R.id.btnEM:
                return new AlertDialog.Builder(this)
                        .setTitle("EM")
                        .setMultiChoiceItems(m_EM_options, m_EM_selections, new DialogSelectionClickHandler())
                        .setPositiveButton("OK", new DialogButtonClickHandler(TRAP_TYPE_EM))
                        .create();
            case R.id.btnOTA:
                return new AlertDialog.Builder(this)
                        .setTitle("OTA")
                        .setMultiChoiceItems(m_OTA_options, m_OTA_selections, new DialogSelectionClickHandler())
                        .setPositiveButton("OK", new DialogButtonClickHandler(TRAP_TYPE_OTA))
                        .create();
            case R.id.btnVoLTE:
            default:
                return new AlertDialog.Builder(this)
                        .setTitle("VoLTE")
                        .setMultiChoiceItems(m_VoLTE_options, m_VoLTE_selections, new DialogSelectionClickHandler())
                        .setPositiveButton("OK", new DialogButtonClickHandler(TRAP_TYPE_VOLTE))
                        .create();
        }
    }
    public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
    {
        public void onClick( DialogInterface dialog, int clicked, boolean selected )
        {

        }
    }
    public class DialogButtonClickHandler implements DialogInterface.OnClickListener
    {
        private TRAP_TYPE m_type;
        DialogButtonClickHandler(TRAP_TYPE type)
        {
            m_type = type;
        }
        public void onClick( DialogInterface dialog, int clicked )
        {
            switch( clicked )
            {
                case DialogInterface.BUTTON_POSITIVE:
                    sendSubscriptionCmd(m_type);
                    break;
            }
        }
    }

    protected void sendSubscriptionCmd(TRAP_TYPE type){

        try {
            ArrayList<CommandProxyAction> subscribeCmdArray = new ArrayList<CommandProxyAction>(); // dynamic array to store commands

            if (type == TRAP_TYPE_EM) {
                for (int i = 0; i < m_EM_selections.length; ++i) {
                    if (m_EM_selections[i] == m_EM_selections_lasttime[i]) {
                        continue;
                    }
                    if (m_EM_selections[i]) { // subscribe
                        // create subscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_EM;
                        command.msgID = m_EM_id[i];
                        subscribeCmdArray.add(command);
                    } else {    // unsubscribe
                        // create unsubscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_UNSUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_EM;
                        command.msgID = m_EM_id[i];
                        subscribeCmdArray.add(command);
                    }
                    m_EM_selections_lasttime[i] = m_EM_selections[i];

                }
            } else if (type == TRAP_TYPE_OTA) {
                for (int i = 0; i < m_OTA_selections.length; ++i) {
                    if (m_OTA_selections[i] == m_OTA_selections_lasttime[i]) {
                        continue;
                    }
                    if (m_OTA_selections[i]) { // subscribe
                        // create subscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_OTA;
                        command.msgID = m_OTA_id[i];
                        subscribeCmdArray.add(command);
                    } else {    // unsubscribe
                        // create unsubscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_UNSUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_OTA;
                        command.msgID = m_OTA_id[i];
                        subscribeCmdArray.add(command);
                    }
                    m_OTA_selections_lasttime[i] = m_OTA_selections[i];
                }
            } else if (type == TRAP_TYPE_VOLTE) {
                for (int i = 0; i < m_VoLTE_selections.length; ++i) {
                    if (m_VoLTE_selections[i] == m_VoLTE_selections_lasttime[i]) {
                        continue;
                    }
                    if (m_VoLTE_selections[i]) { // subscribe
                        // create subscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_SUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_VOLTE;
                        command.msgID = m_VoLTE_id[i];
                        subscribeCmdArray.add(command);
                    } else {    // unsubscribe
                        // create unsubscribe command
                        CommandProxyAction command = new CommandProxyAction();
                        command.actionType = CommandProxyActionType.ACTION_TYPE_UNSUBSCRIBE_TRAP;
                        command.sessionID = m_connectState.m_sid;
                        command.trapType = TRAP_TYPE_VOLTE;
                        command.msgID = m_VoLTE_id[i];
                        subscribeCmdArray.add(command);
                    }
                    m_VoLTE_selections_lasttime[i] = m_VoLTE_selections[i];
                }
            }

            // call CommandProxyAsyncTask to operate commands array
            if(subscribeCmdArray.size() > 0) {
                // execute commands package by m_cmdProxy
                m_cmdProxy.ExecuteCommand(subscribeCmdArray.toArray(new CommandProxyAction[subscribeCmdArray.size()]));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
