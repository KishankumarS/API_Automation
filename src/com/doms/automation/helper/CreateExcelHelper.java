package com.doms.automation.helper;

import java.util.ArrayList;

import com.doms.automation.bean.WarningQueryVO;

public interface CreateExcelHelper {
public void createExcel(ArrayList<WarningQueryVO> warningQueryVOList,ArrayList<String> headers);
}
