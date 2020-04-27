package in.org.basic;

import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class ExaminersBills {

    public MainActivity MA;
    public void SetMA(MainActivity MA){this.MA=MA;}

    public void show(String tempstring)
    {
        Toast.makeText(MA,tempstring,Toast.LENGTH_SHORT).show();
    }
    public void Show(float floattemp) { Toast.makeText(MA, (int) floattemp,Toast.LENGTH_SHORT).show(); }

    static Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    static Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

//    void SetMA(MainActivity MA) {
//        this.MA = MA;
//    }

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

            Paragraph P3 = new Paragraph("Name : Shri/Smt/Miss" + "  " + MA.examSubject );   //   + "  "  + MA.internalname);

            doc.add(P3);

            doc.close();
//            Msg.Show("From CreatePDF, in ExminersBill class",MA);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}

/*            Paragraph p0 = new Paragraph(" ");

            Paragraph P1 = new Paragraph("MAHARASHTRA STATE BOARD OF SECONDARY & HIGHER SECONDARY EDUCATION \n" +
                    "MUMBAI DIVISIONAL BOARD, VASHI, NAVIMUMBAI 400703 \n" +
                    "H.S.C. PRACTICAL EXAMINATION FEBRUARY/JULY - " + "\n" +
                    "BILL OF REMUNERATION OF INTERNAL/EXTERNAL EXAMINER" , font1 );

            Paragraph P2 = new Paragraph("The Divisional Secretary, \n" + "Maharashtra State Board of Secondary \n" +
                    "& Higher Secondary Education \n" + "Mumbai Divisional Board, \n" +
                    "Vashi, Navi Mumbai 400703" );
            Paragraph P3 = new Paragraph("Name : Shri/Smt/Miss" + "  "  );   //   + "  "  + MA.internalname);


            P1.setAlignment(Element.ALIGN_CENTER);
            doc.add(P1);

            p0.setAlignment(Element.ALIGN_LEFT);
            doc.add(p0);

            P2.setAlignment(Element.ALIGN_LEFT);
            doc.add(P2);

            p0.setSpacingBefore(4f);
            doc.add(p0);
*/
