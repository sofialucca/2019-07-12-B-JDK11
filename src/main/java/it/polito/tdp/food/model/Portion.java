package it.polito.tdp.food.model;

public class Portion {
	private Integer portion_id;
	private Double portion_amount;
	private String portion_display_name;
	private Double calories;
	private Double saturated_fats;
	private Integer food_code;
	
	public Portion(Integer portion_id, Double portion_amount, String portion_display_name, Double calories,
			Double saturated_fats, Integer food_code) {
		super();
		this.portion_id = portion_id;
		this.portion_amount = portion_amount;
		this.portion_display_name = portion_display_name;
		this.calories = calories;
		this.saturated_fats = saturated_fats;
		this.food_code = food_code;
	}
	
	public Integer getPortion_id() {
		return portion_id;
	}
	public void setPortion_id(Integer portion_id) {
		this.portion_id = portion_id;
	}
	public Double getPortion_amount() {
		return portion_amount;
	}
	public void setPortion_amount(Double portion_amount) {
		this.portion_amount = portion_amount;
	}
	public String getPortion_display_name() {
		return portion_display_name;
	}
	public void setPortion_display_name(String portion_display_name) {
		this.portion_display_name = portion_display_name;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public Double getSaturated_fats() {
		return saturated_fats;
	}
	public void setSaturated_fats(Double saturated_fats) {
		this.saturated_fats = saturated_fats;
	}
	public Integer getFood_code() {
		return food_code;
	}
	public void setFood_code(Integer food_code) {
		this.food_code = food_code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((portion_id == null) ? 0 : portion_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portion other = (Portion) obj;
		if (portion_id == null) {
			if (other.portion_id != null)
				return false;
		} else if (!portion_id.equals(other.portion_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{portion_id=" + portion_id + ", portion_amount=" + portion_amount + ", portion_display_name="
				+ portion_display_name + ", food_code=" + food_code + "}";
	}

	
	
}
