package com.mmounirou.spotify.commons.util;

import java.util.List;

import com.google.common.collect.Lists;

public final class ListUtils
{
	private ListUtils()
	{
	}

	public static <E> List<List<E>> split(int count, List<E> artistHrefs)
	{
		int size = artistHrefs.size();
		List<List<E>> results = Lists.newArrayList();
		for (int i = 0; i < Math.ceil(size / (double) count); i++)
		{
			results.add(artistHrefs.subList(i * count, Math.min((i + 1) * count, size)));
		}

		return results;
	}

}
