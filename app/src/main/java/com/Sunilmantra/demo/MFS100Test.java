package com.Sunilmantra.demo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.Sunilmantra.model.User;
import com.Sunilmantra.viewmodel.UserViewModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MFS100Test extends AppCompatActivity implements MFS100Event {

    /*Button btnInit;
    Button btnUninit;
    Button btnSyncCapture;
    Button btnStopCapture;
    Button btnMatchISOTemplate;
    Button btnExtractISOImage;
    Button btnExtractAnsi;
    Button btnExtractWSQImage;
    Button btnClearLog;
    Button btnRegister;
    Button btnLogin;*/
    TextView lblMessage;
    EditText txtEventLog, edittextuserID;
    //ImageView imgFinger;
    ImageView imgCaptureFinger;
    LinearLayout linfirstScreen,layoutRegistration;
    Button btnBack,buttonRegistration,buttonLogin,buttonRegister,buttonLoginn;
    //CheckBox cbFastDetection;

    String userName="";
    byte[] fingerPrint = null;

    private UserViewModel viewModel;

    private enum ScannerAction {
        Capture, Verify
    }

    byte[] Enroll_Template;
    byte[] Verify_Template;
    private FingerData lastCapFingerData = null;
    ScannerAction scannerAction = ScannerAction.Capture;

    int timeout = 10000;
    MFS100 mfs100 = null;

    private boolean isCaptureRunning = false;
    private boolean registration = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        FindFormControls();

        try {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    @Override
    protected void onStart() {
        if (mfs100 == null) {
            mfs100 = new MFS100(this);
            mfs100.SetApplicationContext(MFS100Test.this);
        } else {
            InitScanner();
        }
        super.onStart();
    }

    protected void onStop() {
        UnInitScanner();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mfs100 != null) {
            mfs100.Dispose();
        }
        super.onDestroy();
    }

    public void FindFormControls() {
        /*btnInit = (Button) findViewById(R.id.btnInit);
        btnUninit = (Button) findViewById(R.id.btnUninit);
        btnMatchISOTemplate = (Button) findViewById(R.id.btnMatchISOTemplate);
        btnExtractISOImage = (Button) findViewById(R.id.btnExtractISOImage);
        btnExtractAnsi = (Button) findViewById(R.id.btnExtractAnsi);
        btnExtractWSQImage = (Button) findViewById(R.id.btnExtractWSQImage);
        btnClearLog = (Button) findViewById(R.id.btnClearLog);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);*/
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        txtEventLog = (EditText) findViewById(R.id.txtEventLog);
        imgCaptureFinger = (ImageView) findViewById(R.id.imgCaptureFinger);
        edittextuserID = findViewById(R.id.edittextuserID);
        layoutRegistration = findViewById(R.id.layoutRegistration);
        linfirstScreen = findViewById(R.id.linfirstScreen);
        btnBack= findViewById(R.id.btnBack);
        buttonRegister= findViewById(R.id.buttonRegister);
        buttonLoginn = findViewById(R.id.buttonLoginn);


        buttonRegistration = findViewById(R.id.buttonRegistration);
                buttonLogin = findViewById(R.id.buttonLogin);
        //imgFinger = (ImageView) findViewById(R.id.imgFinger);
        /*btnSyncCapture = (Button) findViewById(R.id.btnSyncCapture);
        btnStopCapture = (Button) findViewById(R.id.btnStopCapture);
        cbFastDetection = (CheckBox) findViewById(R.id.cbFastDetection);*/

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    public void onControlClicked(View v) {

        switch (v.getId()) {
            /*case R.id.btnInit:
                InitScanner();
                break;
            case R.id.btnUninit:
                UnInitScanner();
                break;
            case R.id.btnSyncCapture:
                scannerAction = ScannerAction.Capture;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;
            case R.id.btnStopCapture:
                StopCapture();
                break;
            case R.id.btnMatchISOTemplate:
                scannerAction = ScannerAction.Verify;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;
            case R.id.btnExtractISOImage:
                ExtractISOImage();
                break;
            case R.id.btnExtractAnsi:
                ExtractANSITemplate();
                break;
            case R.id.btnExtractWSQImage:
                ExtractWSQImage();
                break;
            case R.id.btnClearLog:
                ClearLog();
                break;
            case R.id.btnLogin:
                openRegisterDialog("Login");
                break;
            case R.id.btnRegister:
                openRegisterDialog("Register");
                break;*/

            case R.id.buttonRegistration:
                registration = true;
                layoutRegistration.setVisibility(View.VISIBLE);
                linfirstScreen.setVisibility(View.GONE);
                buttonRegister.setVisibility(View.VISIBLE);
                buttonLoginn.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);

                break;

            case R.id.buttonLogin:
                registration = false;
                layoutRegistration.setVisibility(View.VISIBLE);
                linfirstScreen.setVisibility(View.GONE);
                buttonLoginn.setVisibility(View.VISIBLE);
                buttonRegister.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);
                break;

                case R.id.btnBack:

                    edittextuserID.setText("");
                    imgCaptureFinger.setImageResource(R.drawable.finger);
                    lblMessage.setText("");
                    txtEventLog.setText("");
                    layoutRegistration.setVisibility(View.GONE);
                    btnBack.setVisibility(View.GONE);
                    linfirstScreen.setVisibility(View.VISIBLE);

                break;


            case R.id.imgCaptureFinger:
                scannerAction = ScannerAction.Capture;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;

            case R.id.buttonRegister:

                    registerUser();

                break;

            case R.id.buttonLoginn:

                    loginUser();

                break;

            default:
                break;
        }
    }

    private void registerUser(){

        String username = edittextuserID.getText().toString();

        if(username != null ){
            if (fingerPrint != null){
                CallAPI("save", username,fingerPrint);
            }else{
                Toast.makeText(MFS100Test.this, "Please Capture finger print", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MFS100Test.this, "Please Enter UserID", Toast.LENGTH_SHORT).show();
        }
    }


    private void loginUser(){

        String username = edittextuserID.getText().toString();

        if(username != null ){
            if (fingerPrint != null){
                CallAPI("compare", username,fingerPrint);
            }else{
                Toast.makeText(MFS100Test.this, "Please Capture finger print", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MFS100Test.this, "Please Enter UserID", Toast.LENGTH_SHORT).show();
        }
    }

    private void openRegisterDialog(final String header) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_enter_username);

        final EditText edtEnterUserName = (EditText) dialog.findViewById(R.id.edtEnterUserName);
        TextView txtSubmit = (TextView) dialog.findViewById(R.id.txtSubmit);
        TextView txtHeader = (TextView) dialog.findViewById(R.id.txtHeader);
        txtHeader.setText(header);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEnterUserName.getText().toString().isEmpty()){
                    Toast.makeText(MFS100Test.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }else if (fingerPrint == null) {
                    Toast.makeText(MFS100Test.this, "Please Capture finger print", Toast.LENGTH_SHORT).show();
                }else {
                    if(header.equalsIgnoreCase("Login")){
                        //CallAPI(edtEnterUserName.getText().toString(),fingerPrint);
                        /*userName= edtEnterUserName.getText().toString();
                        try {
                            User user=new User();
                            user=viewModel.getNote(userName);
                            if(user!=null){
                                viewModel.getNotes().observe(MFS100Test.this, (List<User> userList) -> {
                                    // update UI

                                    for(int i=0;i<userList.size();i++){
                                        if(userName.equalsIgnoreCase(userList.get(i).getUser_name())&& Arrays.equals(fingerPrint, userList.get(i).getUser_authentication())){
                                            SetLogOnUIThread("Found");
                                            dialog.dismiss();
                                        }else {
                                            SetLogOnUIThread("Not Found");
                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }else {
                                SetLogOnUIThread("Not Found");
                                dialog.dismiss();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }else {
                        userName= edtEnterUserName.getText().toString();
                        User user = new User(userName,fingerPrint);
                        viewModel.insertNote(user);
                        LiveData<List<User>> notes=viewModel.getNotes();
                        dialog.dismiss();
                    }
                }

            }
        });


        dialog.show();
    }

    @SuppressLint("NewApi")
    private void CallAPI(String apiType, String userName, byte[] fingerPrint) {
        //String url = "http://172.19.76.88:8050/biometric/compare";
        String url;

        if(apiType.equalsIgnoreCase("save")){
            url = "http://192.168.1.153:8050/biometric/save";
        }else{
            url = "http://192.168.1.153:8050/biometric/compare";
        }

        Map<String, String> params = new HashMap();
        params.put("userId", userName);
         String encodeToString = Base64.getEncoder().encodeToString(fingerPrint);
        params.put("isoTemplate", encodeToString);
        //SetLogOnUIThread(params.toString());

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,url, parameters, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response != null){

                    try {
                        String message = response.getString("message");
                        String quality = response.getString("quality");

                        SetLogOnUIThread(message);
                        //SetLogOnUIThread(quality);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    SetLogOnUIThread("Something went wrong");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonRequest);

    }

    private void InitScanner() {
        try {
            int ret = mfs100.Init();
            if (ret != 0) {
                SetTextOnUIThread(mfs100.GetErrorMsg(ret));
            } else {
                SetTextOnUIThread("Init success");
                String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo()
                        + "\nMake: " + mfs100.GetDeviceInfo().Make()
                        + "\nModel: " + mfs100.GetDeviceInfo().Model()
                        + "\nCertificate: " + mfs100.GetCertification();
                SetLogOnUIThread(info);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Init failed, unhandled exception",
                    Toast.LENGTH_LONG).show();
            SetTextOnUIThread("Init failed, unhandled exception");
        }
    }

    private void StartSyncCapture() {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				SetTextOnUIThread("");
                isCaptureRunning = true;
                fingerPrint = null;
		        try {
		            FingerData fingerData = new FingerData();
		           // int ret = mfs100.AutoCapture(fingerData, timeout, cbFastDetection.isChecked());
		            int ret = mfs100.AutoCapture(fingerData, timeout, false);
		            Log.e("StartSyncCapture.RET", ""+ret);
		            if (ret != 0) {
		                SetTextOnUIThread(mfs100.GetErrorMsg(ret));
		            } else {
		                lastCapFingerData = fingerData;
		                final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);

		                MFS100Test.this.runOnUiThread(new Runnable() {
		                    @Override
		                    public void run() {
		                       // imgFinger.setImageBitmap(bitmap);
		                        imgCaptureFinger.setImageBitmap(bitmap);
		                    }
		                });

		                SetTextOnUIThread("Capture Success");
		                String log = "\nQuality: " + fingerData.Quality()
		                        + "\nNFIQ: " + fingerData.Nfiq()
		                        + "\nWSQ Compress Ratio: "
		                        + fingerData.WSQCompressRatio()
		                        + "\nImage Dimensions (inch): "
		                        + fingerData.InWidth() + "\" X "
		                        + fingerData.InHeight() + "\""
		                        + "\nImage Area (inch): " + fingerData.InArea()
		                        + "\"" + "\nResolution (dpi/ppi): "
		                        + fingerData.Resolution() + "\nGray Scale: "
		                        + fingerData.GrayScale() + "\nBits Per Pixal: "
		                        + fingerData.Bpp() + "\nWSQ Info: "
		                        + fingerData.WSQInfo();

		                //sneha
                        fingerPrint = fingerData.ISOTemplate();

		                //SetLogOnUIThread(log);

		                //SetData2(fingerData);


                    }
		        } catch (Exception ex) {
		            SetTextOnUIThread("Error");
		        } finally {
                    isCaptureRunning = false;
                }
            }
		}).start();
    }

    private void StopCapture() {
    	try {
			mfs100.StopAutoCapture();
		} catch (Exception e) {
			SetTextOnUIThread("Error");
		}
    }

    private void ExtractANSITemplate() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[2000]; // length 2000 is mandatory
            byte[] ansiTemplate;
            int dataLen = mfs100.ExtractANSITemplate(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract ANSI Template");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                ansiTemplate = new byte[dataLen];
                System.arraycopy(tempData, 0, ansiTemplate, 0, dataLen);
                WriteFile("ANSITemplate.ansi", ansiTemplate);
                SetTextOnUIThread("Extract ANSI Template Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ANSI Template Error", e);
        }
    }

    private void ExtractISOImage() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(mfs100.GetDeviceInfo().Width() * mfs100.GetDeviceInfo().Height()) + 1078];
            byte[] isoImage;
            int dataLen = mfs100.ExtractISOImage(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract ISO Image");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                isoImage = new byte[dataLen];
                System.arraycopy(tempData, 0, isoImage, 0, dataLen);
                WriteFile("ISOImage.iso", isoImage);
                SetTextOnUIThread("Extract ISO Image Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ISO Image Error", e);
        }
    }

    private void ExtractWSQImage() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(mfs100.GetDeviceInfo().Width() * mfs100.GetDeviceInfo().Height()) + 1078];
            byte[] wsqImage;
            int dataLen = mfs100.ExtractWSQImage(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract WSQ Image");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                wsqImage = new byte[dataLen];
                System.arraycopy(tempData, 0, wsqImage, 0, dataLen);
                WriteFile("WSQ.wsq", wsqImage);
                SetTextOnUIThread("Extract WSQ Image Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract WSQ Image Error", e);
        }
    }

    private void UnInitScanner() {
        try {
            int ret = mfs100.UnInit();
            if (ret != 0) {
                SetTextOnUIThread(mfs100.GetErrorMsg(ret));
            } else {
                //SetLogOnUIThread("Uninit Success");
                SetTextOnUIThread("Uninit Success");
                lastCapFingerData = null;
            }
        } catch (Exception e) {
            Log.e("UnInitScanner.EX", e.toString());
        }
    }

    private void WriteFile(String filename, byte[] bytes) {
        try {
            String path = Environment.getExternalStorageDirectory() + "//FingerData";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + filename;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(bytes);
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void ClearLog() {
        txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.setText("", BufferType.EDITABLE);
            }
        });
    }

    private void SetTextOnUIThread(final String str) {

        lblMessage.post(new Runnable() {
            public void run() {
                lblMessage.setText(str);
            }
        });
    }

    private void SetLogOnUIThread(final String str) {

        txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.append("\n" + str);
            }
        });
    }

    public void SetData2(FingerData fingerData) {
        if (scannerAction.equals(ScannerAction.Capture)) {

            Enroll_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Enroll_Template, 0,
                    fingerData.ISOTemplate().length);



        } else if (scannerAction.equals(ScannerAction.Verify)) {
            Verify_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Verify_Template, 0,
                    fingerData.ISOTemplate().length);


            int ret = mfs100.MatchISO(Enroll_Template, Verify_Template);
            if (ret < 0) {
                SetTextOnUIThread("Error: " + ret + "(" + mfs100.GetErrorMsg(ret) + ")");
            } else {
                if (ret >= 1400) {
                    SetTextOnUIThread("Finger matched with score: " + ret);
                } else {
                    SetTextOnUIThread("Finger not matched, score: " + ret);
                }
            }
        }

        WriteFile("Raw.raw", fingerData.RawData());
        WriteFile("Bitmap.bmp", fingerData.FingerImage());
        WriteFile("ISOTemplate.iso", fingerData.ISOTemplate());


    }

    @Override
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        int ret;
        if (!hasPermission) {
            SetTextOnUIThread("Permission denied");
            return;
        }
        if (vid == 1204 || vid == 11279) {
            if (pid == 34323) {
                ret = mfs100.LoadFirmware();
                if (ret != 0) {
                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));
                } else {
                    SetTextOnUIThread("Load firmware success");
                }
            } else if (pid == 4101) {
                String key = "Without Key";
                ret = mfs100.Init();
                if (ret == 0) {
                    showSuccessLog(key);
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));
                }
            }
        }
    }

    private void showSuccessLog(String key) {
        SetTextOnUIThread("Init success");
        String info = "\nKey: " + key + "\nSerial: "
                + mfs100.GetDeviceInfo().SerialNo() + " Make: "
                + mfs100.GetDeviceInfo().Make() + " Model: "
                + mfs100.GetDeviceInfo().Model()
                + "\nCertificate: " + mfs100.GetCertification();
        //SetLogOnUIThread(info);
    }

    @Override
    public void OnDeviceDetached() {
        UnInitScanner();
        SetTextOnUIThread("Device removed");
    }

    @Override
    public void OnHostCheckFailed(String err) {
        try {
           // SetLogOnUIThread(err);
            Toast.makeText(this, err, Toast.LENGTH_LONG).show();
        } catch (Exception ignored) {
        }
    }
}
