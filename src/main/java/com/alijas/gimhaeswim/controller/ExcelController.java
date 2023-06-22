package com.alijas.gimhaeswim.controller;

import com.alijas.gimhaeswim.domain.Competition;
import com.alijas.gimhaeswim.domain.CompetitionEvent;
import com.alijas.gimhaeswim.domain.CompetitionEventApplicationDetail;
import com.alijas.gimhaeswim.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/excel")
@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final CompetitionRepository competitionRepository;

    @GetMapping("/download")
    public void download(HttpServletResponse res, Integer competitionSeq) throws Exception {
        Competition competition = competitionRepository.findById(competitionSeq).get();
        Workbook workbook = new SXSSFWorkbook();

        for (CompetitionEvent competitionEvent : competition.getCompetitionEventList()){
            List<CompetitionEventApplicationDetail> competitionEventApplicationDetailList = competitionEvent.getCompetitionEventApplicationDetailList();
            /**
             * excel sheet 생성
             */
            int index = workbook.getSheetIndex(competitionEvent.getSex()+" "+competitionEvent.getDivision()+" "+competitionEvent.getEventName()+" "+competitionEvent.getMeter()+"m");
            Sheet sheet;
            if (index == -1) {
                // It doesn't exist yet so let's create it
                sheet = workbook.createSheet(competitionEvent.getSex()+" "+competitionEvent.getDivision()+" "+competitionEvent.getEventName()+" "+competitionEvent.getMeter()+"m");
            } else {
                // It already exists so let's get it
                sheet = workbook.getSheetAt(index);
            }
            // 엑셀 sheet 이름
            sheet.setDefaultColumnWidth(28); // 디폴트 너비 설정

            /**
             * header font style
             */
            XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
            headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 0, (byte) 0, (byte) 0}));

            /**
             * header cell style
             */
            XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

            // 테두리 설정
            headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
            headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
            headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
            headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);

            // 배경 설정
            headerXssfCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerXssfCellStyle.setFont(headerXSSFFont);

            /**
             * body cell style
             */
            XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

            // 테두리 설정
            bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
            bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
            bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
            bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);

            /**
             * header data
             */
            int rowCount = 0; // 데이터가 저장될 행

            String headerNames[] = new String[]{"순번", "성명", "소속", "성별", "전화번호", "입금여부"};

            Row headerRow = null;
            Cell headerCell = null;

            headerRow = sheet.createRow(rowCount++);
            for(int i=0; i<headerNames.length; i++) {
                headerCell = headerRow.createCell(i);
                headerCell.setCellValue(headerNames[i]); // 데이터 추가
                headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
            }

            /**
             * body data
             */
            String[][] bodyDatass = new String[competitionEvent.getCompetitionEventApplicationDetailList().size()][6];

            for(int i = 0; i<competitionEventApplicationDetailList.size(); i++){
                bodyDatass[i][0] = String.valueOf((i+1));
                bodyDatass[i][1] = competitionEventApplicationDetailList.get(i).getApplicationDetail().getUser().getUserName();
                bodyDatass[i][2] = competitionEventApplicationDetailList.get(i).getApplicationDetail().getUser().getOrganization();
                bodyDatass[i][3] = String.valueOf(competitionEventApplicationDetailList.get(i).getApplicationDetail().getUser().getSex());
                bodyDatass[i][4] = competitionEventApplicationDetailList.get(i).getApplicationDetail().getUser().getPhoneNumber();
                if(competitionEventApplicationDetailList.get(i).getApplicationDetail().isDeposit()){
                    bodyDatass[i][5] = "O";
                }else{
                    bodyDatass[i][5] = "X";
                }
            }

            Row bodyRow = null;
            Cell bodyCell = null;

            for(String[] bodyDatas : bodyDatass) {
                bodyRow = sheet.createRow(rowCount++);

                for(int i=0; i<bodyDatas.length; i++) {
                    bodyCell = bodyRow.createCell(i);
                    bodyCell.setCellValue(bodyDatas[i]); // 데이터 추가
                    bodyCell.setCellStyle(bodyXssfCellStyle); // 스타일 추가
                }
            }
        }

        /**
         * download
         */
        String fileName = competition.getCompetitionName();
        String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + outputFileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }

}
