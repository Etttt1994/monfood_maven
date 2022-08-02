package com.model.res.service;

import java.util.List;
import java.util.Map;

import com.model.res.ResDto;
import com.model.res.ResVO;

public interface ResService {
	String resRegister(ResVO resVO);

	// 登入
	public ResVO resLogin(ResVO resVO);

	String resModify(ResVO resVO);

	String resetPassword(ResVO resVO);

	String isDuplicateAccount(ResVO resVO);

	List<ResVO> adminFindResAll();

	List<ResVO> adminFindByCategory(Integer resCategory);

	boolean updateResInfo(ResDto resDto);

	ResVO selectByResId(Integer resId);

	List<Map<String, Object>> resFindOrderService(Integer orderId);
}
