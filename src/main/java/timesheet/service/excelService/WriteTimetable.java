package timesheet.service.excelService;


import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import timesheet.orm.entity.Timetable;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WriteTimetable {

    private static final String EXCEL_FILE_LOCATION = "output.xls";

    public static String writeExcel(@NotNull List<Timetable> timetables) throws IOException, WriteException, ParseException {

        WritableWorkbook wb = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
        WritableSheet ws = wb.createSheet("Sheet 1", 0);
        Label label;
        Label date;
        jxl.write.Number duration;

        label = new Label(0, 0 , "Name");
        ws.addCell(label);
        label = new Label(1, 0 , "Surname");
        ws.addCell(label);
        label = new Label(0, 1 , timetables.get(0).getUsers().getName());
        ws.addCell(label);
        label = new Label(1, 1 , timetables.get(0).getUsers().getSurname());
        ws.addCell(label);
        label = new Label(2, 1 , "durata");
        ws.addCell(label);

        for (int i=2; i<timetables.size()+2; i++){
            label = new Label(0, i, timetables.get(i-2).getProject().getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateUtil = sdf.parse(timetables.get(i-2).getDate().toString());
            date = new Label(1, i, DateFormat.getDateInstance(DateFormat.SHORT).format(dateUtil));
            duration = new Number(2, i,timetables.get(i-2).getDuration() );
            ws.addCell(label);
            ws.addCell(date);
            ws.addCell(duration);
        }
        wb.write();
        wb.close();
        return  EXCEL_FILE_LOCATION;
    }

}
