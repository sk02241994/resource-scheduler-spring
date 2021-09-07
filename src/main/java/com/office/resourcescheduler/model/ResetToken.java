package com.office.resourcescheduler.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "password_reset_token")
public class ResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "password_reset_token", length = 11)
	private Long tokenId;
	
	@Column(name = "token", length = 50)
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public ResetToken(Long tokenId, String token, User user, LocalDateTime expiryDate) {
		this.tokenId = tokenId;
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}
	
	public ResetToken(String token, User user) {
		this.token = token;
		this.user = user;
		this.expiryDate = LocalDateTime.now().plusMinutes(5);
	}

	public ResetToken() {
	}
	
	
}
