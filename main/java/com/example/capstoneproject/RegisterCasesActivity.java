package com.example.capstoneproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
//import com.example.capstoneproject.ml.FaceRecognitionModel;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
//import com.google.firebase.ml.vision.face.FirebaseVisionFace;
//import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
//import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
//import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.example.capstoneproject.ml.FaceRecognitionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RegisterCasesActivity extends AppCompatActivity {

    private static int select_pic = 100;
    ImageView img;
    Button selectbtn, otp_btn, submit_det, verify_btn, search_btn;
    EditText miss_name, miss_age, user_num, fir_num, otp_code, aadhaar_num;
    TextView result_display;
    Bitmap bitmap;
    DB_ImageHelper imgHelper;
    String otp,labels[],verificationId;

    FirebaseAuth firebaseAuth;
    PhoneAuthProvider.ForceResendingToken forceResendingToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String TAG="MAIN_TAG";
    private static final float SIMILARITY_THRESHOLD = 0.6f;

     //ProgressBar pgb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cases);

//        int cnt=0;
//        try {
//            BufferedReader bfr=new BufferedReader(new InputStreamReader(getAssets().open("List of Actors.txt")));
//            String line=bfr.readLine();
//            while(line!=null)
//            {
//                labels[cnt]=line;
//                cnt++;
//                line= bfr.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        firebaseAuth=FirebaseAuth.getInstance();

/*
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(RegisterCasesActivity.this,
                        "Verification fails",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, forceResendingToken);
                verificationId=s;
                forceResendingToken=token;
                Toast.makeText(RegisterCasesActivity.this,"Verification Code Sent...",Toast.LENGTH_SHORT).show();
            }
        };*/

        FirebaseApp.initializeApp(this);

        img = findViewById(R.id.imgView);
        selectbtn = findViewById(R.id.selectImg);
        miss_name = findViewById(R.id.missingPersonName);
        miss_age = findViewById(R.id.missingPersonAge);
        user_num = findViewById(R.id.userPhoneNumber);
        fir_num = findViewById(R.id.firNumber);
        aadhaar_num = findViewById(R.id.aadhaarNumber);
        result_display=findViewById(R.id.secondValue);
        otp_code = findViewById(R.id.otpNumber);
        otp_btn = findViewById(R.id.otpBtn);
        verify_btn = findViewById(R.id.verifyOtp);
        search_btn=findViewById(R.id.searchImg);
        submit_det = findViewById(R.id.submitDetails);
        submit_det.setVisibility(View.INVISIBLE);
        //pgb=findViewById(R.id.progressBarId);
        imgHelper = new DB_ImageHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("Notification",
                    "Sum", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        labels=new String[]{"Sharukh.jpg","Mahesh.jpg","Tarak.jpg","Pawan.jpg"};

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phn = user_num.getText().toString().trim();

                if (phn.isEmpty()) {
                    submit_det.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterCasesActivity.this, "Enter 10 digit phone Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    submit_det.setVisibility(View.INVISIBLE);
                    //String phn = user_num.getText().toString().trim();
                    otp = sendVerificationCode(phn);

                    NotificationCompat.Builder builder = new
                            NotificationCompat.Builder(RegisterCasesActivity.this,
                            "Notification");
                    builder.setContentTitle("Missing Person OTP Verification Code");

                    builder.setContentText(otp+" is your One Time Password(OTP) to register case in Missing Person App ");

                    builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                    NotificationManagerCompat managerCompat =
                            NotificationManagerCompat.from(RegisterCasesActivity.this);
                    managerCompat.notify(1, builder.build());

                    /*
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phn, 60, TimeUnit.SECONDS,
                            RegisterCasesActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    //submit_det.setVisibility(View.INVISIBLE);
                                    //pgb.setVisibility(View.GONE);
                                    Toast.makeText(RegisterCasesActivity.this,"Verified",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    submit_det.setVisibility(View.INVISIBLE);
                                    //pgb.setVisibility(View.GONE);
                                    Toast.makeText(RegisterCasesActivity.this, "Some thing is wrong"+e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    result_display.setText("Missing "+e.getMessage());
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    //super.onCodeSent(s, forceResendingToken);
                                    verificationId = s;
                                    //pgb.setVisibility(View.GONE);
                                    Toast.makeText(RegisterCasesActivity.this, "Code sent... ", Toast.LENGTH_SHORT).show();
                                }
                            });*/
                }
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String code = otp_code.getText().toString();
                if(TextUtils.isEmpty(code))
                {
                    submit_det.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterCasesActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {

//                    if (verificationId != null) {
//                        PhoneAuthCredential pac = PhoneAuthProvider.getCredential(verificationId, code);
/*
                        firebaseAuth.signInWithCredential(pac)
                                .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            submit_det.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            Toast.makeText(RegisterCasesActivity.this,"OTP Doesn't Matches",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );

 */
//                        signInWithPhoneAuthCredentials(pac);
//                        submit_det.setVisibility(View.VISIBLE);
                    //}

                    if(verifyCode(code)) {
                        submit_det.setVisibility(View.VISIBLE);
                        Toast.makeText(RegisterCasesActivity.this,"OTP Verified Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(RegisterCasesActivity.this,"Recheck the OTP Once",Toast.LENGTH_SHORT).show();
                }

            }
        });

        submit_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String missingName = miss_name.getText().toString();
                String missingAge = miss_age.getText().toString();
                String userNumber = user_num.getText().toString();
                String firNumber = fir_num.getText().toString();
                String aadhaarNumber = aadhaar_num.getText().toString();

                if (missingName.equals("") && missingAge.equals("") && userNumber.equals("")
                        && firNumber.equals("") && aadhaarNumber.equals("") ) {
                    Toast.makeText(RegisterCasesActivity.this, "Enter all Mandatory details", Toast.LENGTH_SHORT).show();
                } else {
                    if (imgHelper.checkFir(firNumber)) {
                        byte[] ipData = getImageFromDB(selImgUri);
                        Boolean insert = imgHelper.insertDetails(missingName, missingAge, firNumber, aadhaarNumber, userNumber, ipData);
                        if (insert == true) {
                            Toast.makeText(RegisterCasesActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterCasesActivity.this, "Please recheck the details", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterCasesActivity.this, "Fir Number(CASE) already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasStoragePermission(RegisterCasesActivity.this)) {
                    openImageChooser();
                } else {
                    ActivityCompat.requestPermissions(((AppCompatActivity) RegisterCasesActivity.this),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FaceRecognitionModel model = FaceRecognitionModel.newInstance(getApplicationContext());
                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false);
                    inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

                    // Runs model inference and gets result.
                    FaceRecognitionModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    result_display.setText(labels[getMaximum(outputFeature0.getFloatArray())]);

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                    Toast.makeText(getApplicationContext(),"there is a problem",Toast.LENGTH_SHORT).show();
                }
                /*
                if (isMatched())
                    result_display.setText("Image was Matched");
                else
                    result_display.setText("Image Not Matched");*/
            }
        });
    }

    /*
    private void signInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
            firebaseAuth.signInWithCredential(phoneAuthCredential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegisterCasesActivity.this,
                                    "Verified Successfully",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterCasesActivity.this,
                                    "Verification Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        }*/

    int getMaximum(float arr[])
    {
        int max=0;
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i]>arr[max])
                max=i;
        }
        return max;
    }

    private  String sendVerificationCode(String num)
    {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


 /*  private void sendVerificationCode(String num)
    {
        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(num).setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this).setCallbacks(mCallBacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }*/

    private boolean verifyCode(String code) {
        return code.equals(otp);
    }

    private void openImageChooser() {
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(in.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in, "Select Picture"), select_pic);
    }

    Uri selImgUri;
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == select_pic) {

                if (data != null) {
                    selImgUri = data.getData();

                    if(saveImageInDB(selImgUri))
                    {
                        Toast.makeText(this, "Image Saved in DB",
                                Toast.LENGTH_SHORT).show();
                        img.setImageURI(selImgUri);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                img.setVisibility(View.VISIBLE);
                            }
                        },2000);
                    }
                    Uri uri = data.getData();

                    // Get the file name
                    String fileName = getFileName(uri);

                    //result_display.setText(fileName);
//                   Bitmap image=null;
//                    try {
//                        image= MediaStore.Images.Media.getBitmap(this.getContentResolver(),selImgUri);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    img.setImageURI(selImgUri); // Display the selected image
//                    //image=Bitmap.createScaledBitmap(image,224,224,false);
//                    //classifyImage(image);
                }
            }
        }
        super.onActivityResult(reqCode, resCode, data);
    }

    String result=null;
    private String getFileName(Uri uri) {
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(index);
                }
            }
        } else if (uri.getScheme().equals("file")) {
            result = new File(uri.getPath()).getName();
        }
        return result;
    }

    private boolean isMatched()
    {
        if(result==null) return false;
        String name=result;
        for(int i=0;i<labels.length;i++)
        {
            if(name.equalsIgnoreCase(labels[i]))
                return true;
        }
        return false;
    }

    private boolean saveImageInDB(Uri selImageUri)
    {
        try{
            imgHelper.open();
            InputStream iStream=getContentResolver().openInputStream(selImageUri);
            byte[] ipData=UtilsImage.getBytes(iStream);
            imgHelper.close();
            return true;
        }
        catch (Exception e)
        {
            imgHelper.close();
            return false;
        }
    }

    /*private boolean saveImageInDB(Uri selImageUri)
    {
        try{
           /* imgHelper.open();
            InputStream iStream=getContentResolver().openInputStream(selImageUri);
            byte[] ipData=UtilsImage.getBytes(iStream);
            byte[] ipData=getImageFromDB(selImgUri);
           // imgHelper.insertImage(ipData);
            imgHelper.close();
            return true;
        }
        catch (Exception e)
        {
            imgHelper.close();
            return false;
        }
    }*/

    private byte[] getImageFromDB(Uri selImgUri) {
        imgHelper.open();
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selImgUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] ipData = new byte[0];
        try {
            ipData = UtilsImage.getBytes(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipData;
    }

    /*private boolean loadImageFromDB() {
        try {
            imgHelper.open();
            byte bytes[] = imgHelper.retrieveImage();
            imgHelper.close();
            img.setImageBitmap(UtilsImage.getImage(bytes));
            return true;
        } catch (Exception e) {
            imgHelper.close();
            return false;
        }
    }
*/

    private boolean hasStoragePermission(Context con) {
        int read = ContextCompat.checkSelfPermission(con,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }
}