package timesheet.service.excelService;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jetbrains.annotations.NotNull;
import timesheet.orm.view.HourUserProject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class WriteHourUserProject {

    private static final String EXCEL_FILE_LOCATION = "output.xls";


    public static String writeExcel(@NotNull List<HourUserProject> list) throws IOException, WriteException, ParseException {
        WritableWorkbook wb = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
        WritableSheet ws = wb.createSheet("Sheet 1", 0);

        Label label;
        Label userName;
        Label projectName;
        Number totalHour;

        label = new Label(0, 0 , "Hour User Project");
        ws.addCell(label);

        for (int i=1; i<list.size()+1; i++){
            userName = new Label(0,i,(list.get(i-1).getUserName()+list.get(i-1).getUserSurname()));
            ws.addCell(userName);
            projectName = new Label(1, i, list.get(i-1).getProjectName());
            ws.addCell(projectName);
            totalHour = new Number(2, i,list.get(i-1).getSum());
            ws.addCell(totalHour);
        }
        wb.write();
        wb.close();
        return EXCEL_FILE_LOCATION ;

    }
}
