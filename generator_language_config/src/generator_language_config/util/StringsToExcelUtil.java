package generator_language_config.util;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class StringsToExcelUtil {

    private static final String KEY_FLAG = "key";
    public static final String ANNOTATION_FLAG = "##ANN##";
    public static final String DEFAULT_FLAG = "default";


    public void writXLSXExcel(String xmlResPath, File outputFile) throws IOException {
        if (xmlResPath == null || xmlResPath.equals("")) {
            throw new IOException("文件不存在");
        }
        try {
            Map<String, File> files = new HashMap<>();
            listStringFile(xmlResPath, files);

            Set<String> langs = files.keySet();

            LinkedHashMap<String, List<StringEntity>> map = new LinkedHashMap<>();


            XSSFWorkbook xwb = new XSSFWorkbook();
            XSSFSheet sheet = xwb.createSheet("Sheet1");
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue(KEY_FLAG);

            filterIosString(files, langs, map, row);

            XSSFCellStyle style = xwb.createCellStyle();
            int rowIdx = 1;
            for (Map.Entry<String, List<StringEntity>> entry : map.entrySet()) {
                //第三步创建行row:添加表头0行
                if (entry.getKey().contains(ANNOTATION_FLAG)) {
                    continue;
                }
                XSSFRow createRow = sheet.createRow(rowIdx++);
                createRow.createCell(0).setCellValue(entry.getKey());
                for (StringEntity entity : entry.getValue()) {
                    createRow.createCell(entity.cel).setCellValue(entity.value);
                }
            }
            //将excel写入
            OutputStream stream = new FileOutputStream(outputFile.getAbsolutePath() + File.separator + getFileNameNoEx("Strings") + ".xlsx");
            xwb.write(stream);
            stream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void listStringFile(String xmlResPath, Map<String, File> files) {
        File[] tempList = new File(xmlResPath).listFiles();
        for (File value : tempList) {
            if (value.isFile()) {
                if (value.getName().equals("Localizable.strings")) {
                    String parent = value.getParent();
                    String fileFolderName = parent.substring(parent.lastIndexOf(File.separator) + 1);
                    files.put(fileFolderName.substring(0,fileFolderName.indexOf(".")), value);
                    System.out.println(fileFolderName);
                }

            } else if (value.isDirectory() && value.toString().contains("lproj")) {
                listStringFile(value.getAbsolutePath(), files);
            } else {
//                System.out.println("不生成 = " + value.toString());
            }
        }
    }


    private void filterIosString(Map<String, File> files, Set<String> langs, LinkedHashMap<String, List<StringEntity>> map, XSSFRow row) throws DocumentException {
        int col = 1;
        for (String lang : langs) {
            row.createCell(col).setCellValue(lang);
            LinkedHashMap<String, Object> xmls = StringsUtil.readStrings1(files.get(lang));
            for (Map.Entry<String, Object> entry : xmls.entrySet()) {
                List<StringEntity> stringEntities = map.get(entry.getKey());
                if (stringEntities == null) {
                    stringEntities = new ArrayList<>();
                }
                StringEntity stringEntity = new StringEntity(lang, entry.getKey(), entry.getValue().toString());
                stringEntity.cel = col;
                stringEntities.add(stringEntity);
                map.put(entry.getKey(), stringEntities);
            }

            col++;
        }
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


}
