package servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift7.TException;

import backtype.storm.generated.DRPCExecutionException;
import backtype.storm.utils.DRPCClient;
import backtype.storm.utils.Utils;
import enums.Citys;

public class TopServlet extends HttpServlet {

	private static final long serialVersionUID = -9181615698734759938L;

	String today = null;
	String todayColumn = "[";
	String todaySpline = "[";
	String todayCity = "[";

	public void init() throws ServletException {

		today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 * [[
	 * "2016-10-08:amt_1 2016-10-08:amt_2 2016-10-08:amt_3 2016-10-08:amt_4 2016-10-08:amt_5"
	 * ,"2016-10-08:amt_1","2016-10-08","amt_1",12219.9000000001], [
	 * "2016-10-08:amt_1 2016-10-08:amt_2 2016-10-08:amt_3 2016-10-08:amt_4 2016-10-08:amt_5"
	 * ,"2016-10-08:amt_2","2016-10-08","amt_2",12491.800000000118], [
	 * "2016-10-08:amt_1 2016-10-08:amt_2 2016-10-08:amt_3 2016-10-08:amt_4 2016-10-08:amt_5"
	 * ,"2016-10-08:amt_3","2016-10-08","amt_3",12155.200000000108], [
	 * "2016-10-08:amt_1 2016-10-08:amt_2 2016-10-08:amt_3 2016-10-08:amt_4 2016-10-08:amt_5"
	 * ,"2016-10-08:amt_4","2016-10-08","amt_4",12293.200000000103], [
	 * "2016-10-08:amt_1 2016-10-08:amt_2 2016-10-08:amt_3 2016-10-08:amt_4 2016-10-08:amt_5"
	 * ,"2016-10-08:amt_5","2016-10-08","amt_5",11709.200000000095]]
	 * 
	 * [[
	 * "2016-10-08:num_1 2016-10-08:num_2 2016-10-08:num_3 2016-10-08:num_4 2016-10-08:num_5"
	 * ,"2016-10-08:num_1","2016-10-08","num_1",1253], [
	 * "2016-10-08:num_1 2016-10-08:num_2 2016-10-08:num_3 2016-10-08:num_4 2016-10-08:num_5"
	 * ,"2016-10-08:num_2","2016-10-08","num_2",1250], [
	 * "2016-10-08:num_1 2016-10-08:num_2 2016-10-08:num_3 2016-10-08:num_4 2016-10-08:num_5"
	 * ,"2016-10-08:num_3","2016-10-08","num_3",1239], [
	 * "2016-10-08:num_1 2016-10-08:num_2 2016-10-08:num_3 2016-10-08:num_4 2016-10-08:num_5"
	 * ,"2016-10-08:num_4","2016-10-08","num_4",1203], [
	 * "2016-10-08:num_1 2016-10-08:num_2 2016-10-08:num_3 2016-10-08:num_4 2016-10-08:num_5"
	 * ,"2016-10-08:num_5","2016-10-08","num_5",1183]]
	 * 
	 * 
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DRPCClient client = new DRPCClient("mpc1", 3772);
		while (true) {
			todayColumn = "[";
			todaySpline = "[";
			todayCity = "[";
			String dateStr = new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date());
			if (!StringUtils.equals(today, dateStr)) {
				today = dateStr;
			}

			String amtArgs = "";
			for (int i = 1; i <= 9; i++) {
				amtArgs += today + ":amt_" + i + " ";
			}
			amtArgs = amtArgs.trim();

			try {
				String saleString = client.execute("getOrderAmt", amtArgs);
				saleString = saleString.substring(2, saleString.length() - 2);
			    System.err.println("销售额:" + saleString);

				String[] amtArr = saleString.split("\\]\\,\\[");

				String numArgs = "";
				for (String amt : amtArr) {
					String amt_id = amt.split("\\,")[3];
					amt_id = amt_id.substring(1, amt_id.length() - 1);
					if (amt_id.equals("amt_1")) {
						numArgs += today + ":" + Citys.amt_1.getNumStr() + " ";
						todayCity += "'" + Citys.amt_1.getCityStr() + "',";

					} else if (amt_id.equals("amt_2")) {
						numArgs += today + ":" + Citys.amt_2.getNumStr() + " ";
						todayCity += "'" + Citys.amt_2.getCityStr() + "',";

					} else if (amt_id.equals("amt_3")) {
						numArgs += today + ":" + Citys.amt_3.getNumStr() + " ";
						todayCity += "'" + Citys.amt_3.getCityStr() + "',";

					} else if (amt_id.equals("amt_4")) {
						numArgs += today + ":" + Citys.amt_4.getNumStr() + " ";
						todayCity += "'" + Citys.amt_4.getCityStr() + "',";

					} else if (amt_id.equals("amt_5")) {
						numArgs += today + ":" + Citys.amt_5.getNumStr() + " ";
						todayCity += "'" + Citys.amt_5.getCityStr() + "',";

					} else if (amt_id.equals("amt_6")) {
						numArgs += today + ":" + Citys.amt_6.getNumStr() + " ";
						todayCity += "'" + Citys.amt_6.getCityStr() + "',";

					} else if (amt_id.equals("amt_7")) {
						numArgs += today + ":" + Citys.amt_7.getNumStr() + " ";
						todayCity += "'" + Citys.amt_7.getCityStr() + "',";

					} else if (amt_id.equals("amt_8")) {
						numArgs += today + ":" + Citys.amt_8.getNumStr() + " ";
						todayCity += "'" + Citys.amt_8.getCityStr() + "',";

					} else if (amt_id.equals("amt_9")) {
						numArgs += today + ":" + Citys.amt_9.getNumStr() + " ";
						todayCity += "'" + Citys.amt_9.getCityStr() + "',";

					}
					Double _amt = Double.parseDouble(amt.split("\\,")[4]);
					todayColumn += new DecimalFormat("#.0").format(_amt);
					todayColumn += ",";
				}

				numArgs = numArgs.trim();
				todayCity = todayCity.subSequence(0, todayCity.length() - 1)
						+ "]";

				//System.out.println(numArgs);
				//System.out.println(todayCity);
				todayColumn = StringUtils.substring(todayColumn, 0,
						todayColumn.length() - 1);
				todayColumn += "]";

				String numString = client.execute("getOrderNum", numArgs);
				numString = numString.substring(2, numString.length() - 2);
				System.err.println("销售量:" + numString);

				String[] numArr = numString.split("\\]\\,\\[");
				for (String num : numArr) {
					String num_id = num.split("\\,")[3];
					num_id = num_id.substring(1, num_id.length() - 1);
					Double _num = Double.parseDouble(num.split("\\,")[4]);
					todaySpline += new DecimalFormat("#.0").format(_num);
					todaySpline += ",";
				}
				todaySpline = StringUtils.substring(todaySpline, 0,
						todaySpline.length() - 1);
				todaySpline += "]";

				// System.out.println(todayColumn);
				// System.out.println(todaySpline);

				String jsonData = "{\'todayColumn\':" + todayColumn
						+ ",\'todaySpline\':" + todaySpline + ",\'todayCity\':"
						+ todayCity + "}";

				boolean flag = this.sentData("jsFun", jsonData, response);
				if (!flag) {
					break;
				}

				Utils.sleep(1500);
				

			} catch (TException e) {
				e.printStackTrace();
			} catch (DRPCExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean sentData(String jsFun, String data,
			HttpServletResponse response) {

		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(
					"<script type=\"text/javascript\">parent." + jsFun + "(\""
							+ data + "\")</script>");
			response.flushBuffer();
			return true;
		} catch (Exception e) {
			System.err.println("long connection was broken!");
			return false;
		}

	}
}
