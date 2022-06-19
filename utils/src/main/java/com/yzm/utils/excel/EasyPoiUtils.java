package com.yzm.utils.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public enum EasyPoiUtils {
    INSTANCE;

    // 保存路径
    private static String path = "D:\\export";

    /**
     * 功能描述：导出Excel
     *
     * @param tableTitle 表头
     * @param tableName  表名
     * @param fileName   文件名
     * @param clazz      映射实体对象
     * @param data       导出数据
     */
    public String exportExcel(String tableTitle, String tableName, String fileName, Class<?> clazz, List<?> data) {
        ExportParams exportParams = new ExportParams(tableTitle, tableName, ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, clazz, data);
        String name = "";
        if (null != workbook) {
            try {
                File savePath = new File(path);
                if (!savePath.exists()) savePath.mkdirs();
                String saveFile = fileName.concat(String.valueOf(System.currentTimeMillis())).concat(".xls");
                name = savePath.getPath().concat("\\").concat(saveFile);
                FileOutputStream fos = new FileOutputStream(name);
                workbook.write(fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    public String exportExcel(Class<?> clazz, List<?> data) {
        return exportExcel("测试标题", "测试表名", "测试文件名", clazz, data);
    }

    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @param filePath  文件路径(绝对路径)
     * @param pojoClass Excel实体类
     */
    public <T> List<T> importExcel(String filePath, Class<T> pojoClass) {
        // 判断文件是否存在
        if (StringUtils.isBlank(filePath)) return null;
        File file = new File(filePath);
        if (!file.exists()) return null;

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入Excel
     *
     * @param file       上传的文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
        return list;
    }

}
