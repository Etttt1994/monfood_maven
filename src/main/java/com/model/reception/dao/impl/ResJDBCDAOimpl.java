package com.model.reception.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.model.order.OrderVO;
import com.model.reception.dao.ResDAO;
import com.model.res.ResVO;


public class ResJDBCDAOimpl implements ResDAO {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/MonFood?serverTimezone=Asia/Taipei";
	String userid = "root";
	String password = "password";
	
	//修改 針對營業時間 營業星期 狀態
	private static final String UPDATE = "UPDATE MonFood.RES SET BZ_OPEN_HOURS = ? ,BZ_CLOSE_HOURS = ? ,BZ_WEEK_TIME = ? WHERE RES_ID = ?";
	//查詢單一
	private static final String GET_ONE_STMT = "SELECT * FROM MonFood.RES WHERE RES_ID = ? ";
	
	private static final String GET_ONE_ORDER = "SELECT a.ORDER_ID = ?,a.USER_ID = ? ,a.ORDER_STATUS = ?,a.NOTE = ?, a.ORDER_CREATE = ?, a.TOTAL = ?,b.RES_NAME = ?, b.RES_ACCOUNT = ?, c.AMOUNT = ?, d.PRODUCT_NAME = ?, d.PRODUCT_PRICE = ?\r\n"
			+ "FROM MonFood.ORDER a\r\n"
			+ "join MonFood.RES b\r\n"
			+ "on (a.RES_ID = b.RES_ID)\r\n"
			+ "join MonFood.ORDER_DETAIL c\r\n"
			+ "on (a.ORDER_ID = c.ORDER_ID)\r\n"
			+ "join MonFood.PRODUCT d\r\n"
			+ "on (c.PRODUCT_ID = d.PRODUCT_ID)\r\n"
			+ "where a.RES_ID = ? and ORDER_STATUS = ? ";
	
	private static final String UPDATE_ORDER_STATUS = "UPDATE MonFood.ORDER SET ORDER_STATUS = ? WHERE ORDER_ID = ?";
	
	
	@Override
	public void update(ResVO resVO) {
		Connection con = null; //連線 = 空值
		PreparedStatement pstmt = null; //參數化查詢 = 空值
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);//連線呼叫參數化查詢(修改)
			
			pstmt.setInt(4, resVO.getResId());
			pstmt.setTime(1, resVO.getBzOpenHours());
			pstmt.setTime(2, resVO.getBzCloseHours());
			pstmt.setInt(3, resVO.getBzWeekTime());
//			pstmt.setInt(4,resVO.getStatus());
			pstmt.executeUpdate();//參數化查詢呼叫執行更新
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver.無法加載數據庫驅動程序 "
					+ e.getMessage());
			// Handle any SQL errors(處理任何SQL錯誤)
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured.發生數據庫錯誤 "
					+ se.getMessage());
			// Clean up JDBC resources(清理 JDBC 資源)
		} finally { //一定執行裡面程式碼
			if (pstmt != null) { //參數化查詢 != 空值
				try {
					pstmt.close();//關閉參數化查詢
				} catch (SQLException se) { //接住SQL例外,se = 名稱
					se.printStackTrace(System.err);
					//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
				}
			}
			if (con != null) { //連線 != 空值
				try {
					con.close();//關閉連線
				} catch (Exception e) { //接住例外,e = 名稱
					e.printStackTrace(System.err);
				}	//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
			}
		}		
		
	}


	@Override
	public ResVO findByPrimaryKey(Integer resId) {
		
		ResVO resVO = null;
		ResultSet rs = null; //結果請求 = 空值
		Connection con = null; //連線 = 空值
		PreparedStatement pstmt = null; //參數化查詢 = 空值
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);//連線呼叫參數化查詢(修改)
			
			pstmt.setInt(1, resId);
			rs = pstmt.executeQuery();//結果請求 = 參數化查詢呼叫執行查詢
			
			while (rs.next()) {
				// resVO 也稱為 Domain objects 區域 對象
				resVO = new ResVO();
				resVO.setResId(rs.getInt("RES_ID"));
//				resVO.setResCategory(rs.getInt("RES_CATEGORY"));
//				resVO.setResAccount(rs.getString("RES_ACCOUNT"));
//				resVO.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
//				resVO.setResName(rs.getString("RES_NAME"));
//				resVO.setResPassword(rs.getString("RES_PASSWORD"));
//				resVO.setResTel(rs.getString("RES_TEL"));
//				resVO.setResPic(rs.getBytes("RES_PIC"));
//				resVO.setOwnerName(rs.getString("OWNER_NAME"));
//				resVO.setOwnerTel(rs.getString("OWNER_TEL"));
//				resVO.setBzLocation(rs.getString("BZ_LOCATION"));
//				resVO.setZipCode(rs.getInt("ZIP_CODE"));
				resVO.setBzOpenHours(rs.getTime("BZ_OPEN_HOURS"));
				resVO.setBzCloseHours(rs.getTime("BZ_CLOSE_HOURS"));
				resVO.setBzWeekTime(rs.getInt("BZ_WEEK_TIME"));
//				resVO.setStatus(rs.getInt("STATUS"));
			}
		
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver.無法加載數據庫驅動程序 "
					+ e.getMessage());
			// Handle any SQL errors(處理任何SQL錯誤)
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured.發生數據庫錯誤 "
					+ se.getMessage());
			// Clean up JDBC resources(清理 JDBC 資源)
		} finally { //一定執行裡面程式碼
			if (pstmt != null) { //參數化查詢 != 空值
				try {
					pstmt.close();//關閉參數化查詢
				} catch (SQLException se) { //接住SQL例外,se = 名稱
					se.printStackTrace(System.err);
					//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
				}
			}
			if (con != null) { //連線 != 空值
				try {
					con.close();//關閉連線
				} catch (Exception e) { //接住例外,e = 名稱
					e.printStackTrace(System.err);
				}	//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
			}
		}
		return resVO;		
	}




	public static void main(String[] args) {
		ResJDBCDAOimpl dao = new ResJDBCDAOimpl();
		
		// 修改OK
		ResVO resVO = new ResVO();
		resVO.setResId(5);
		resVO.setBzOpenHours(java.sql.Time.valueOf("3:33:33"));
		resVO.setBzCloseHours(java.sql.Time.valueOf("22:33:33"));
		resVO.setBzWeekTime(1234567);
		dao.update(resVO);
		System.out.println("成功喔!");
		
		// 訂單狀態修改 OK
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderId(1);
		orderVO.setOrderStatus(2);
		dao.updateOrderStatus(orderVO);
		System.out.println("成功喔!");
		
		
		
		// 查詢OK
		ResVO resVO1 = dao.findByPrimaryKey(5);
		System.out.println(resVO1.getResId());
		System.out.println(resVO1.getBzOpenHours()+ ",");
		System.out.println(resVO1.getBzCloseHours() + ",");
		System.out.println(resVO1.getBzWeekTime() + ",");
//		System.out.println(resVO1.getStatus() + ",");		
		System.out.println("---------------------");
			
		
	}


	@Override
	public void updateOrderStatus(OrderVO orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
			
			pstmt.setInt(1, orderVO.getOrderStatus());
			pstmt.setInt(2, orderVO.getOrderId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public Map<String, Object> findByOrder(String orderId) {

		ResultSet rs = null; //結果請求 = 空值
		Connection con = null; //連線 = 空值
		PreparedStatement pstmt = null; //參數化查詢 = 空值
		Map<String, Object> findOrderMap = new HashedMap<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_ORDER);//連線呼叫參數化查詢
			
			pstmt.setInt(1, Integer.parseInt(orderId));
			
			
			rs = pstmt.executeQuery();//結果請求 = 參數化查詢呼叫執行查詢
			
			while (rs.next()) {
				
				
				findOrderMap.put("ORDER_ID", rs.getInt("ORDER_ID"));
				findOrderMap.put("USER_ID", rs.getInt("USER_ID"));
				findOrderMap.put("RES_ID",rs.getInt("RES_ID"));
				findOrderMap.put("ORDER_STATUS",rs.getInt("ORDER_STATUS"));
				findOrderMap.put("NOTE",rs.getString("NOTE"));
				findOrderMap.put("TOTAL",rs.getInt("TOTAL"));
				findOrderMap.put("ORDER_CREATE",rs.getTimestamp("ORDER_CREATE"));
				findOrderMap.put("RES_NAME",rs.getString("RES_NAME"));
				findOrderMap.put("RES_ACCOUNT",rs.getString("RES_ACCOUNT"));
				findOrderMap.put("AMOUNT",rs.getInt("AMOUNT"));
				findOrderMap.put("PRODUCT_NAME",rs.getString("PRODUCT_NAME"));
				findOrderMap.put("PRODUCT_PRICE", rs.getInt("PRODUCT_PRICE"));
			}
		
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver.無法加載數據庫驅動程序 "
					+ e.getMessage());
			// Handle any SQL errors(處理任何SQL錯誤)
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured.發生數據庫錯誤 "
					+ se.getMessage());
			// Clean up JDBC resources(清理 JDBC 資源)
		} finally { //一定執行裡面程式碼
			if (pstmt != null) { //參數化查詢 != 空值
				try {
					pstmt.close();//關閉參數化查詢
				} catch (SQLException se) { //接住SQL例外,se = 名稱
					se.printStackTrace(System.err);
					//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
				}
			}
			if (con != null) { //連線 != 空值
				try {
					con.close();//關閉連線
				} catch (Exception e) { //接住例外,e = 名稱
					e.printStackTrace(System.err);
				}	//printStackTrace()：印出異常信息,並提示程式碼中出錯的位置及原因
			}
		}
		return findOrderMap;		
	}




}
