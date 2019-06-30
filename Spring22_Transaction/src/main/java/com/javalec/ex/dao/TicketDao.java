package com.javalec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.javalec.ex.dto.TicketDto;

public class TicketDao {
	JdbcTemplate templateBean;
	PlatformTransactionManager transactionManagerBean;
	
	
	
	public TicketDao() {
		System.out.println(templateBean);
	}



	public void setTemplate(JdbcTemplate template) {
		this.templateBean = template;
	}
	
	
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManagerBean = transactionManager;
	}
	
	
	
	public void buyTicket(final TicketDto dto) {
		System.out.println("buyTicket()");
		System.out.println("dto.getConsumerId() : " + dto.getConsumerId());
		System.out.println("dto.getAmount() : " + dto.getAmount());
		
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus stat = transactionManagerBean.getTransaction(def);
		try {
			templateBean.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					String query = "insert into payedMoney (consumerId, amount) values (?, ?)";
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmt.setString(1, dto.getConsumerId());
					pstmt.setString(2, dto.getAmount());
					return pstmt;
				}
			});
			
			templateBean.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					String query = "insert into ticketBuyed (consumerId, amount) values (?, ?)";
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmt.setString(1, dto.getConsumerId());
					pstmt.setString(2, dto.getAmount());
					return pstmt;
				}
			});
			transactionManagerBean.commit(stat);
		}
		catch(Exception e) {
			transactionManagerBean.rollback(stat);
		}
	}

	
}
