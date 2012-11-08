package com.mmounirou.spotify.commons.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.common.base.Objects;

public class AutoUpdatedPropertiesConfiguration extends PropertiesConfiguration
{

	public AutoUpdatedPropertiesConfiguration(File appPropertiesFile) throws ConfigurationException
	{
		super(appPropertiesFile);
	}

	private void updateIfSame(String key, Object defaultValue, Object value)
	{
		if ( Objects.equal(value, defaultValue) )
		{
			setProperty(key, defaultValue);
		}
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue)
	{
		BigDecimal value = super.getBigDecimal(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue)
	{

		BigInteger value = super.getBigInteger(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue)
	{

		Boolean value = super.getBoolean(key, defaultValue);

		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue)
	{
		boolean value = super.getBoolean(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public byte getByte(String key, byte defaultValue)
	{

		byte value = super.getByte(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Byte getByte(String key, Byte defaultValue)
	{

		Byte value = super.getByte(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Double getDouble(String key, Double defaultValue)
	{
		Double value = super.getDouble(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public double getDouble(String key, double defaultValue)
	{
		double value = super.getDouble(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Float getFloat(String key, Float defaultValue)
	{

		Float value = super.getFloat(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public float getFloat(String key, float defaultValue)
	{

		float value = super.getFloat(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public int getInt(String key, int defaultValue)
	{

		int value = super.getInt(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue)
	{

		Integer value = super.getInteger(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public List<Object> getList(String key, List<Object> defaultValue)
	{

		List<Object> value = super.getList(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Long getLong(String key, Long defaultValue)
	{

		Long value = super.getLong(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public long getLong(String key, long defaultValue)
	{

		long value = super.getLong(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public Properties getProperties(String key, Properties defaults)
	{

		Properties value = super.getProperties(key, defaults);
		updateIfSame(key, defaults, value);
		return value;
	}

	@Override
	public Short getShort(String key, Short defaultValue)
	{

		Short value = super.getShort(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public short getShort(String key, short defaultValue)
	{

		short value = super.getShort(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}

	@Override
	public String getString(String key, String defaultValue)
	{

		String value = super.getString(key, defaultValue);
		updateIfSame(key, defaultValue, value);
		return value;
	}
}
