package com.model.order.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.model.monster.IMonsterDAO;
import com.model.monster.MonsterJDBCDAO;
import com.model.monster.MonsterVO;
import com.model.order.OrderVO;
import com.model.order.dao.OrderDAO;
import com.model.order.dao.impl.OrderJDBCDAOimpl;
import com.model.order.service.OrderService;
import com.model.promotelist.PromoteListVO;
import com.model.promotelist.dao.PromoteListDAO;
import com.model.promotelist.dao.Impl.PromoteListJDBCDAO;
import com.model.user.dao.UserDAO;
import com.model.user.dao.impl.UserDAOImpl;

public class OrderServiceImpl implements OrderService {
	
	private OrderDAO dao;
	
	public OrderServiceImpl() {
		dao = new OrderJDBCDAOimpl();
	}
	
	@Override
	public OrderVO adminFindOrderId(OrderVO orderVO) {
		
		final Integer orderId = orderVO.getOrderId();
		
		// validate orderId
		if(orderId == null) {
			return null;
		}
		
		orderVO = dao.findByPrimaryKey(orderId);
		// validate if the data exists in table
		if(orderVO == null) {
			return null;
		}
		
		// data exists, return to controller
		return orderVO;
		
	}
	
	@Override
	public List<OrderVO> adminFindOrderAll(){
		List<OrderVO> list = dao.getAll();
		return list;
	}
	
	@Override
	public Integer createOrder(OrderVO orderVO) {
		Integer generatedKey = null;
		
		if (orderVO.getPromoteId() == null) {
			generatedKey = dao.insertNoPromote(orderVO);
			if(generatedKey == null) {
				return null;
			}
			return generatedKey;
		}
		
		generatedKey = dao.insert(orderVO);
		if(generatedKey == null) {
			return null;
		}
		return generatedKey;
	}
	
	@Override
	public PromoteListVO promoteCheck(PromoteListVO promoteListVO) {
		
		PromoteListDAO promoteListDAO = new PromoteListJDBCDAO();
		final String promoteCode = promoteListVO.getPromoteCode();
		if (promoteCode == null || "".equals(promoteCode.trim())) {
			return null;
		}

		promoteListVO = promoteListDAO.findByCode(promoteCode);
		return promoteListVO;
	}
	
	@Override
	public MonsterVO monsCheck(Integer userId) throws SQLException {
		Integer orderTimes = dao.getOrderTimes(userId);
		UserDAO userDAO = new UserDAOImpl();
		IMonsterDAO monsterDAO = new MonsterJDBCDAO();
		MonsterVO monsterVO = new MonsterVO();
		
		System.out.println(orderTimes);
		if (orderTimes / 10 < 1) {
			userDAO.updateMonsLv(1, userId);
			monsterVO = monsterDAO.findByPrimaryKey(1);
			return monsterVO;
			
		}else if (orderTimes / 10 == 1) {
			userDAO.updateMonsLv(2, userId);
			monsterVO = monsterDAO.findByPrimaryKey(2);
			return monsterVO;
			
		}else if (orderTimes / 10 == 2) {
			userDAO.updateMonsLv(3, userId);
			monsterVO = monsterDAO.findByPrimaryKey(3);
			return monsterVO;
			
		}else if (orderTimes / 10 == 3) {
			userDAO.updateMonsLv(4, userId);
			monsterVO = monsterDAO.findByPrimaryKey(4);
			return monsterVO;
			
		}else if (orderTimes / 10 >= 4) {
			userDAO.updateMonsLv(5, userId);
			monsterVO = monsterDAO.findByPrimaryKey(5);
			return monsterVO;
			
		}else {
			return null;
		}
	}
	
	@Override
	public Integer orderTimes(Integer userId) {
		Integer orderTimes = dao.getOrderTimes(userId);
		return orderTimes;
	}
	
	
	
	
}
