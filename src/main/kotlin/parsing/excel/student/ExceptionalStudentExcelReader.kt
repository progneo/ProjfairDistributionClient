package parsing.excel.student

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parsing.base.student.ExceptionalStudentReader
import parsing.excel.ExcelReader

class ExceptionalStudentExcelReader : ExcelReader<Int>(), ExceptionalStudentReader {

    override fun getFileContent(source: XSSFWorkbook): List<Int> {
        val sheet = source.getSheetAt(0)

        val studentIds = mutableListOf<Int>()

        for (i in 1..sheet.lastRowNum) {
            val row = sheet.getRow(i)

            val studentId = row.getCell(0).numericCellValue.toInt()

            studentIds.add(studentId)
        }

        return studentIds
    }
}