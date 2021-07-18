package com.kihwangkwon.businesslogic.player.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Setter
@Getter
@NoArgsConstructor
public class PlayerRating {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int version;
	
	private String playerId;
	private int number;
	private String position;
	private String playerName;
	private int fieldGoalInside;
	private int fieldGoalOutside;
	private int freethrow;
	private int scoring;
	private int pass;
	private int handling;
	private int offensiveRebound;
	private int defensiveRebound;
	private int defence;
	private int block;
	private int steal;
	private int drawingFouls;
	private int discipline;
	private int iq;
	private int energy;
	private double overall;
	private double potential;
	private int salary;
	
	@Builder
	public PlayerRating(String playerId
						 , int number
						 , String position
						 , String playerName
						 , int fieldGoalInside
						 , int fieldGoalOutside
						 , int freethrow
						 , int scoring
						 , int pass
						 , int handling
						 , int offensiveRebound
						 , int defensiveRebound
						 , int defence
						 , int block
						 , int steal
						 , int drawingFouls
						 , int discipline
						 , int iq
						 , int energy
						 , double overall
						 , double potential
						 , int salary) {
		
		this.playerId = playerId;
		this.number = number;
		this.position = position;
		this.playerName = playerName;
		this.fieldGoalInside = fieldGoalInside;
		this.fieldGoalOutside = fieldGoalOutside;
		this.freethrow = freethrow;
		this.scoring = scoring;
		this.pass = pass;
		this.handling = handling;
		this.offensiveRebound = offensiveRebound;
		this.defensiveRebound = defensiveRebound;
		this.defence = defence;
		this.block = block;
		this.steal = steal;
		this.drawingFouls =drawingFouls;
		this.discipline = discipline;
		this.iq = iq;
		this.energy = energy;
		this.overall = overall;
		this.potential = potential;
		this.salary = salary;
		
	}
	
}
