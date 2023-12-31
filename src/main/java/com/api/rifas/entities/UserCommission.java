package com.api.rifas.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import com.api.rifas.entities.enums.ActionCommissionStatus;
import com.api.rifas.entities.enums.UserCommissionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user_commission")
public class UserCommission implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String raffleCommission;
	private Integer userCommissionStatus;
	private String seller;
	private Double price;
	private Double priceCommission;
	private String account;
	private Integer actionCommissionStatus;
	private String link;
	
	@JsonProperty("price")
    public String getFormattedPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + decimalFormat.format(price);
    }
	
	@JsonProperty("priceCommission")
    public String getFormattedPrice2() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + decimalFormat.format(priceCommission);
    }
	
	public UserCommission() {
	}

	public UserCommission(Long id, String raffleCommission, UserCommissionStatus userCommissionStatus,  String seller, Double price, Double priceCommission,
			String account, ActionCommissionStatus actionCommissionStatus, String link) {
		this.id = id;
		this.raffleCommission = raffleCommission;
		setUserCommissionStatus(userCommissionStatus);
		this.seller = seller;
		this.price = price;
		this.priceCommission = priceCommission;
		this.account = account;
		setActionCommissionStatus(actionCommissionStatus);
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaffleCommission() {
		return raffleCommission;
	}

	public void setRaffleCommission(String raffleCommission) {
		this.raffleCommission = raffleCommission;
	}
	
	public UserCommissionStatus getUserCommissionStatus() {
		return UserCommissionStatus.valueOf(userCommissionStatus);
	}

	public void setUserCommissionStatus(UserCommissionStatus userCommissionStatus) {
		if (userCommissionStatus != null) {
			this.userCommissionStatus = userCommissionStatus.getCode();
		}
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPriceCommission() {
		return priceCommission;
	}

	public void setPriceCommission(Double priceCommission) {
		this.priceCommission = priceCommission;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public ActionCommissionStatus getActionCommissionStatus() {
		return ActionCommissionStatus.valueOf(actionCommissionStatus);
	}

	public void setActionCommissionStatus(ActionCommissionStatus actionCommissionStatus) {
		if (actionCommissionStatus != null) {
			this.actionCommissionStatus = actionCommissionStatus.getCode();
		}
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCommission other = (UserCommission) obj;
		return Objects.equals(id, other.id);
	}
}
