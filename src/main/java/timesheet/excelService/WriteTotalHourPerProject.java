package timesheet.excelService;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jetbrains.annotations.NotNull;
import timesheet.orm.view.TotalHourPerProject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class WriteTotalHourPerProject {

    private static final String EXCEL_FILE_LOCATION = "output.xls";


        public static String writeExcel(@NotNull List< TotalHourPerProject > list) throws IOException, WriteException, ParseException {
            WritableWorkbook wb = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet ws = wb.createSheet("Sheet 1", 0);

            Label label;
            Label projectName;
            jxl.write.Number totalHour;

            label = new Label(0, 0 , "Total Hour Per Project");
            ws.addCell(label);

            for (int i=1; i<list.size()+1; i++){
                projectName = new Label(0, i, list.get(i-1).getProjectName());
                ws.addCell(projectName);
                totalHour = new Number(1, i,list.get(i-1).getSum());
                ws.addCell(totalHour);
            }
            wb.write();
            wb.close();
            return EXCEL_FILE_LOCATION ;

        }
}
