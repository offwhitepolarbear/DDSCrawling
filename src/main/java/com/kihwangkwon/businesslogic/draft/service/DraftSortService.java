package com.kihwangkwon.businesslogic.draft.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.draft.domain.DraftSortEnum;

@Service
public class DraftSortService {
	
	public Sort sortByPickAsc() {
		List<Order> sortList = null;
		Order order = Order.asc(DraftSortEnum.pick.toString());
		sortList.add(order);
		Sort sort = Sort.by(sortList);

		return sort;
	}
	
	public Sort sortByOverallDesc() {
		List<Order> sortList = null;

		Order order = Order.desc(DraftSortEnum.overall.toString());
		sortList.add(order);
		Sort sort = Sort.by(sortList);
		
		return sort;
	}
	
	
}

