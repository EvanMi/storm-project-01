package vo;

import org.apache.commons.lang.StringUtils;

public class AreaVo {

	private String beijing = "0";
	private String shanghai = "0";
	private String taiyuan = "0";
	private String tianjin = "0";
	private String fanshi = "0";

	public String getBeijing() {
		return beijing;
	}

	public void setBeijing(String beijing) {
		this.beijing = beijing;
	}

	public String getShanghai() {
		return shanghai;
	}

	public void setShanghai(String shanghai) {
		this.shanghai = shanghai;
	}

	public String getTaiyuan() {
		return taiyuan;
	}

	public void setTaiyuan(String taiyuan) {
		this.taiyuan = taiyuan;
	}

	public String getTianjin() {
		return tianjin;
	}

	public void setTianjin(String tianjin) {
		this.tianjin = tianjin;
	}

	public String getFanshi() {
		return fanshi;
	}

	public void setFanshi(String fanshi) {
		this.fanshi = fanshi;
	}

	public void setData(String areaid, String amt) {
		if (StringUtils.equals(areaid, "1")) {
			this.setBeijing(amt);
		} else if (StringUtils.equals(areaid, "2")) {
			this.setShanghai(amt);
		} else if (StringUtils.equals(areaid, "3")) {
			this.setTaiyuan(amt);
		} else if (StringUtils.equals(areaid, "4")) {
			this.setTianjin(amt);
		} else if (StringUtils.equals(areaid, "5")) {
			this.setFanshi(amt);
		}
	}
}
