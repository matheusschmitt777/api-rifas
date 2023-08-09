package com.api.rifas.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.api.rifas.entities.enums.RaffleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_raffle")
public class Raffle implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private Integer quantity;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	
	@JsonProperty("price")
    public String getFormattedPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + decimalFormat.format(price);
    }
	
	private Integer raffleStatus;
	
	private List<Integer> randomNumbers = new ArrayList<>();
	
	@OneToMany(mappedBy = "id.raffle")
	private Set<OrderItem> items = new HashSet<>();
	
	public Raffle() {
	}

	public Raffle(Long id, Integer quantity, String name, String description, Double price, String imgUrl,  RaffleStatus raffleStatus) {
		this.id = id;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		setRaffleStatus(raffleStatus);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public RaffleStatus getRaffleStatus() {
		return RaffleStatus.valueOf(raffleStatus);
	}

	public void setRaffleStatus(RaffleStatus raffleStatus) {
		if (raffleStatus != null) {
			this.raffleStatus = raffleStatus.getCode();
		}
	}
	
	public List<Integer> getRandomNumbers() {
		return randomNumbers;
	}

	public void setRandomNumbers(List<Integer> randomNumbers) {
		this.randomNumbers = randomNumbers;
	}
	
	@JsonIgnore
	public Set<Order> getOrders() {
		Set<Order> set = new HashSet<>();
		for (OrderItem x : items) {
			set.add(x.getOrder());
		}
		return set;
	}

	public void generateRandomNumbers(Integer quantity2) {
        Set<Integer> generatedNumbers = new HashSet<>();

        int maxNumber = (int) Math.round(getQuantity()); // Convertendo para int
        Random random = new Random();
        while (generatedNumbers.size() < quantity2) {
        	int randomNumber = random.nextInt(maxNumber) + 1; // Gera número entre 0 e a quantidade desejada
            generatedNumbers.add(randomNumber);
        }

        randomNumbers = new ArrayList<>(generatedNumbers);
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
		Raffle other = (Raffle) obj;
		return Objects.equals(id, other.id);
	}
}