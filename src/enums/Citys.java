package enums;

/**
 * @ClassName: Citys
 * @Description: 枚举类型的城市
 * @author com_emep_mpc
 * @date 2016年10月31日 下午5:07:22
 * 
 */
public enum Citys {

	amt_1("北京", "num_1"), amt_2("上海", "num_2"), amt_3("天津", "num_3"), amt_4(
			"武汉", "num_4"), amt_5("太原", "num_5"), amt_6("繁峙", "num_6"), amt_7(
			"深圳", "num_7"), amt_8("南京", "num_8"), amt_9("合肥", "num_9");

	private final String cityStr;
	private final String numStr;

	Citys(String cityStr, String numStr) {
		this.cityStr = cityStr;
		this.numStr = numStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public String getNumStr() {
		return numStr;
	}

}
