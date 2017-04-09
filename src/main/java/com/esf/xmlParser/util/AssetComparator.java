package com.esf.xmlParser.util;

import java.util.Comparator;

import com.esf.xmlParser.entities.Asset;

/**
 * An Asset Comparator
 * 
 * @author Jeegna Patel
 */
public class AssetComparator implements Comparator<Asset> {

	@Override
	public int compare(Asset o1, Asset o2) {
		if (o1 == null || o2 == null) {
			return 1;
		}

		return o1.compareTo(o2);
	}
}
