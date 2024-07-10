package com.moneymanager.anukya.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BhaarathaSharesResponse extends Trace {

	List<BhaarathaShares> bhaarathaSharesList = new ArrayList<>();
	BhaarathaShares bhaarathaSharesTotal = new BhaarathaShares();
}
