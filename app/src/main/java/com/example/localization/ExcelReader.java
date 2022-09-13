package com.example.localization;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class represents an Excel Reader object.
 */
public class ExcelReader {
    private InputStream in;
    private Set<iBeacon> beaconsList; // Stores all beacon objects retrieved from the Excel document

    /**
     * Disallowing use of empty constructor
     */
    private ExcelReader() {}

    /**
     * Constructor initializes this object with an input streamer
     * @param in Input stream object to read from
     */
    public ExcelReader(InputStream in) {
        this.in = in;
        this.beaconsList = new HashSet<>();
    }

    /**
     * Fetches all the beacons from the excel file
     * @throws IOException
     */
    public void fetchAllBeacons() throws IOException {
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(this.in);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();

                //Check the cell type and format accordingly
                switch (cell.getCellType())
                {
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                }
            }
            System.out.println("");
        }
    }
}
