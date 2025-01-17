package com.example.spring.intbatch.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_transaction_yearly", schema = "bankingschema")
public class BankTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banktransaction_id_seq_generator")
	@SequenceGenerator(name = "banktransaction_id_seq_generator", sequenceName = "bank_transaction_yearly_id_seq")
	private int id;
	
	private int month;
	private int day;
	private int hour;
	private int minute;
	private BigDecimal amount;
	private String merchant;
	
	
	public BankTransaction(int id, int month, int day, int hour, int minute, BigDecimal amount, String merchant) {
		this.id = id;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.amount = amount;
		this.merchant = merchant;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	
	@Override
	public String toString() {
		return "BankTransaction [id=" + id + ", month=" + month + ", day=" + day + ", hour=" + hour + ", minute="
				+ minute + ", amount=" + amount + ", merchant=" + merchant + "]";
	}
	
}
