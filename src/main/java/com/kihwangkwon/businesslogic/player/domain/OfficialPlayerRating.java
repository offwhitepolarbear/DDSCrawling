package com.kihwangkwon.businesslogic.player.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OfficialPlayerRating {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	int version;
	String playerName;
	String position;
	boolean pointGuard;
	boolean shootingGuard;
	boolean smallForward;
	boolean powerForward;
	boolean center;
	int floorRangeTop;
	int floorRangeCorner;
	int floorRangeMidRange;
	int floorRangePaintZone;
	int twoPointRestrictedArea;
	int twoPointInsidePaint;
	int twoPointMidrange;
	int threePointCorner;
	int threePointTop;
	private int freethrow;
	private int scoring;
	private int pass;
	private int handling;
	private int offensiveRebound;
	private int defensiveRebound;
	private int defence;
	private int block;
	private int steal;
	double overall;
}
