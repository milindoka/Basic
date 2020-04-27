package in.org.basic;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String examSubject="", examYear = "";
    String Subject, Year;
    ExaminersBills EB = new ExaminersBills();
    Button ExamDetails, LoadButton ;
    String subject;
    ArrayList<String> ExamRelatedDetails = new ArrayList<>();

    String fylenemwithpsth = Environment.getExternalStorageDirectory().getPath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Button Loadbutton;
        LoadButton =  findViewById(R.id.LoadExamDetails);
        LoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something in response to button click

                OpenFileDialog();
                LoadFile(fylenemwithpsth);
                Snackbar.make(view, examSubject, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        StoragePermissionGranted();

        ExamDetails = findViewById(R.id.ExamDetails);
        ExamDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                OpenDialog();
                Snackbar.make(arg0, examSubject, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        });

/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, examSubject, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }


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
/*        if (id == R.id.action_settings)
        {
            OpenDialog();
            return true;
        }
*/
        if (id == R.id.Int_ExaminerBill_Pdf)
        {
//            OpenDialog();
//            EB.CreateInternalPDF();
            CreateInternalPDF();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

/*    private void OpenDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(examSubject);
        alert.setView(input);


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle("Internal Examiner Details");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.examdetails);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                examSubject = input.getText().toString();
                // Do something with value!
                SaveToFile();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
*/

    private void OpenDialog(){

        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
//        dialog.setTitle(" Fill Exam Details");
        dialog.setContentView(R.layout.examdetails);

        final EditText et01 = dialog.findViewById(R.id.ExamSubject);
        final EditText et02 = dialog.findViewById(R.id.ExamYear);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submitButton);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                examSubject = et01.getText().toString();
                examYear = et02.getText().toString();

//                examEndDate = et18.getText().toString();

                SaveToFile();
                dialog.dismiss();
            }
        });

        et01.setText(examSubject);    et02.setText(examYear);

        dialog.show();
        //  To Scroll the dialog box up when Virtual (Soft) keyboard appears
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Button btnOK = (Button) dialog.findViewById(R.id.OKExamDetails);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public boolean StoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
//                Msg.show("Permission Granted", this);
//                Msg.show("Permission Granted");

                return true;
            } else {

                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
//            msg.Show("Permission Granted",getApplicationContext());

    }

    String rootDir;

    void CreateInternalPDF()
    {
        rootDir = Environment.getExternalStorageDirectory().getPath();
        String pdfFileNameWithPath = rootDir + "/"  + "New.pdf";

        try {
            Document doc = new Document();
            doc = new Document(PageSize.A4);
            doc.setMargins(40, 25, 30, 5);

            File pdfFile =new File(pdfFileNameWithPath);
            PdfWriter docWriter = PdfWriter.getInstance(doc, new FileOutputStream(pdfFile));
//            PdfContentByte pcb = docWriter.getDirectContent();
            doc.open();

            Paragraph P3 = new Paragraph("Name of Subject" + "  " + examSubject );   //   + "  "  + MA.internalname);
            Paragraph P4 = new Paragraph("Year of Exam" + "  " + examYear );
            doc.add(P3);     doc.add(P4);

            doc.close();
//            Msg.Show("From CreatePDF, in ExminersBill class",MA);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    void SaveToFile() {

        String FileNameWithPath="";
        FileNameWithPath = "/sdcard/"; // Environment.getExternalStorageDirectory().getAbsolutePath();

        FileNameWithPath += "RemBill.rmb";

        //  EditText Internalname = (EditText) findViewById(R.id.ExaminerName);


        try {
            File myFile = new File(FileNameWithPath);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//            myOutWriter.append(FileNameWithPath);

//            myOutWriter.append("\n");

            myOutWriter.append("==== Details of Examination ====");

            myOutWriter.append("\n");
            myOutWriter.append(examSubject);
            myOutWriter.append("\n");
            myOutWriter.append(examYear);
            myOutWriter.append("\n");

            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();


        }

//        show(" File Saved !!!");
    }

    void OpenFileDialog()
    {	String tempstr;
        //OpenNow=false;
        String rootDir= Environment.getExternalStorageDirectory().getPath();
        List<String> listItems = new ArrayList<String>();
        File mfile=new File(rootDir);
        File[] list=mfile.listFiles();
        String tempupper;
        for(int i=0;i<mfile.listFiles().length;i++)
        {
            tempstr=list[i].getAbsolutePath();
//            tempupper=tempstr.toUpperCase();
//            if(tempupper.endsWith(".RMB") )
            if(tempstr.endsWith(".rmb")){
                listItems.add(list[i].getAbsolutePath());
            }
        }

        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select File To Open...");
        builder.setItems(items, new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int item)
            {String ttt= (String) items[item];
//                LoadFile(ttt);
                //               show(ttt);
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    void LoadFile(String fylenamewithpath){

        try {

            File myFile = new File(fylenamewithpath);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String DataRow = "";

            DataRow=myReader.readLine(); /// blank line separator
//            DataRow=myReader.readLine(); /// blank line separator


//            String tempstr;
            ExamRelatedDetails.removeAll(ExamRelatedDetails);
            while ((DataRow = myReader.readLine()) != null)

            {
                ExamRelatedDetails.add(DataRow);
            }

            myReader.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


}
